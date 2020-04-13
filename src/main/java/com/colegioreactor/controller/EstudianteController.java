package com.colegioreactor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colegioreactor.document.Estudiante;
import com.colegioreactor.service.IEstudianteService;
import com.colegioreactor.pagination.PageSupport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/v1/estudiante")
public class EstudianteController {

	@Autowired
	private IEstudianteService service;
	
	@PostMapping
	public Mono<ResponseEntity<Estudiante>> registrar(@Valid @RequestBody Estudiante estudiante){
		
		return service.registrar(estudiante)
				      .map(p->ResponseEntity
				    		  .status(HttpStatus.CREATED)
				    		  .contentType(MediaType.APPLICATION_STREAM_JSON)
				    		  .body(p)
				    	   );		
	}
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Estudiante>>> listar(){
		Flux<Estudiante> estudiantes = service.listar();
		estudiantes = estudiantes.parallel()
				.runOn(Schedulers.elastic())
				.flatMap(p -> service.listarPorId(p.getId()))
				.ordered((p1, p2) -> p2.getEdad().intValue() - p1.getEdad().intValue());
		return Mono.just(ResponseEntity.ok()
				  						.contentType(MediaType.APPLICATION_STREAM_JSON)
				  						.body(estudiantes));
		
				
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Estudiante>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id).map(p->ResponseEntity.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/pageable")
	public Mono<ResponseEntity<PageSupport<Estudiante>>> listarPageable(
			@RequestParam(name = "page", defaultValue="0") int page,
			@RequestParam(name = "size", defaultValue="10") int size
			){
		
		Pageable pageRequest = PageRequest.of(page, size);
		
		return service.listarPage(pageRequest)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.body(p)
				).defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@PutMapping
	public Mono<ResponseEntity<Estudiante>> modificar(@Valid @RequestBody Estudiante estudiante){
		
		return service.modificar(estudiante)
				      .map(p->ResponseEntity
				    		  .status(HttpStatus.OK)
				    		  .body(p)
				    	  )
				      .defaultIfEmpty(ResponseEntity.notFound().build());		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
		return service.listarPorId(id).flatMap(p->{
			return service.eliminar(p.getId()).then(
					 Mono.just(
							  new ResponseEntity<Void>(HttpStatus.NO_CONTENT)
							 )
					);
		}).defaultIfEmpty(ResponseEntity.notFound().build());
					  
	}
}
