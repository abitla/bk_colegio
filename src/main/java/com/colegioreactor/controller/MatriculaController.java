package com.colegioreactor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.colegioreactor.document.Matricula;

import com.colegioreactor.service.IMatriculaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/matricula")
@RestController
public class MatriculaController {
	
	@Autowired
	private IMatriculaService service;
	
	@PostMapping
	public Mono<ResponseEntity<Matricula>> registrar(@Valid @RequestBody Matricula matricula){
		
		return service.registrar(matricula)
				      .map(p -> 
				              ResponseEntity
				    		  .status(HttpStatus.CREATED)
				    		  .contentType(MediaType.APPLICATION_STREAM_JSON)
				    		  .body(p)
				    	   );		
	}
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Matricula>>> listar(){
		Flux<Matricula> matriculas = service.listar();
		return Mono.just(ResponseEntity.ok()
				  						.contentType(MediaType.APPLICATION_STREAM_JSON)
				  						.body(matriculas));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Matricula>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id).map(p->ResponseEntity.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@PutMapping
	public Mono<ResponseEntity<Matricula>> modificar(@Valid @RequestBody Matricula matricula){
		
		return service.modificar(matricula)
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
