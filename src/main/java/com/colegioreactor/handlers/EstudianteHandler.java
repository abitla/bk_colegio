package com.colegioreactor.handlers;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.colegioreactor.service.IEstudianteService;
import com.colegioreactor.document.Estudiante;
import com.colegioreactor.validators.RequestValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class EstudianteHandler {

	@Autowired
	private IEstudianteService service;
	
	@Autowired
	private RequestValidator validadorGeneral;
	
	
	public Mono<ServerResponse> listar(ServerRequest req){
		Flux<Estudiante> estudiantes = service.listar();
		estudiantes = estudiantes.sort(
				(p1, p2) -> p2.getEdad().intValue() - p1.getEdad().intValue());
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(estudiantes, Estudiante.class);
	}
	
	public Mono<ServerResponse> listarParalelamente(ServerRequest req){
		Flux<Estudiante> estudiantes = service.listar();
		estudiantes = estudiantes.parallel()
				.runOn(Schedulers.elastic())
				.flatMap(p -> service.listarPorId(p.getId()))
				.ordered((p1, p2) -> p2.getEdad().intValue() - p1.getEdad().intValue());
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(estudiantes, Estudiante.class);
	}
	
	
	public Mono<ServerResponse> listarPorId(ServerRequest req){
		String id = req.pathVariable("id");
		
		return service.listarPorId(id)
				.flatMap(p -> ServerResponse.ok()
							.contentType(MediaType.APPLICATION_STREAM_JSON)
							.body(fromValue(p))
				)
				.switchIfEmpty(ServerResponse
						.notFound()
						.build()
				);
					
	}
	
	public Mono<ServerResponse> registrar(ServerRequest req){		
		Mono<Estudiante> EstudianteMono = req.bodyToMono(Estudiante.class);
		
		return EstudianteMono.flatMap(this.validadorGeneral::validar)				
		.flatMap(service::registrar)
				.flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
								.contentType(MediaType.APPLICATION_STREAM_JSON)
								.body(fromValue(p))
						);
		
	}
	
	public Mono<ServerResponse> modificar(ServerRequest req){		
		Mono<Estudiante> EstudianteMono = req.bodyToMono(Estudiante.class);
		
		return EstudianteMono.flatMap(this.validadorGeneral::validar)	
				.flatMap(service::modificar)		
			.flatMap(p -> ServerResponse.ok()
			.contentType(MediaType.APPLICATION_STREAM_JSON)
			.body(fromValue(p))
		).switchIfEmpty(ServerResponse
				.notFound()
				.build()
		);					
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest req){
		String id = req.pathVariable("id");
		
		return service.listarPorId(id)
				.flatMap(p -> service.eliminar(p.getId())
							.then(ServerResponse
									.noContent()
									.build()
							)						
				)
				.switchIfEmpty(ServerResponse
						.notFound()
						.build());		
	}
}
