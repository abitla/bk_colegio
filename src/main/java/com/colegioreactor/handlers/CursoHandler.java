package com.colegioreactor.handlers;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.colegioreactor.service.ICursoService;
import com.colegioreactor.document.Curso;
import com.colegioreactor.validators.RequestValidator;

import reactor.core.publisher.Mono;

@Component
public class CursoHandler {

	@Autowired
	private ICursoService service;
	
	@Autowired
	private RequestValidator validadorGeneral;
		
	public Mono<ServerResponse> listar(ServerRequest req){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(service.listar(), Curso.class);
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
		Mono<Curso> CursoMono = req.bodyToMono(Curso.class);
		
		//CON VALIDACION MANUAL
		/*return CursoMono.flatMap(p -> {
			Errors errores = new BeanPropertyBindingResult(p, Curso.class.getName());
			validador.validate(p, errores);
			
			if(errores.hasErrors()) {
				return Flux.fromIterable(errores.getFieldErrors())
						.map(error -> new ValidacionDTO(error.getField(), error.getDefaultMessage()))
						.collectList()
						.flatMap(listaErrores -> {
							return ServerResponse.badRequest()
									.contentType(MediaType.APPLICATION_STREAM_JSON)
									.body(fromValue(listaErrores));
						});
			}else {
				return service.registrar(p)
						.flatMap(x -> ServerResponse
								.created(URI.create(req.uri().toString().concat("/").concat(x.getId())))
								.contentType(MediaType.APPLICATION_STREAM_JSON)
								.body(fromValue(x))
								);								
			}
		});*/
		
		//CON VALIDACION AUTOMATICA
		return CursoMono.flatMap(this.validadorGeneral::validar)				
		.flatMap(service::registrar)
				.flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
								.contentType(MediaType.APPLICATION_STREAM_JSON)
								.body(fromValue(p))
						);
		
		//SIN VALIDACION
		/*return CursoMono.flatMap(p -> {
			return service.registrar(p);
		})
			.flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
			.contentType(MediaType.APPLICATION_STREAM_JSON)
			.body(fromValue(p))
		);*/
		
	}
	
	public Mono<ServerResponse> modificar(ServerRequest req){		
		Mono<Curso> CursoMono = req.bodyToMono(Curso.class);
		
		return CursoMono.flatMap(this.validadorGeneral::validar)	
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
