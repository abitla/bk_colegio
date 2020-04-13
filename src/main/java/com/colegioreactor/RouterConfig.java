package com.colegioreactor;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.colegioreactor.handlers.CursoHandler;
import com.colegioreactor.handlers.EstudianteHandler;
import com.colegioreactor.handlers.MatriculaHandler;

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> rutasEstudiante(EstudianteHandler handler){
		return route(GET("/v2/estudiante"), handler::listar)
				.andRoute(GET("/v2/estudiante/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/estudiante"), handler::registrar)
				.andRoute(PUT("/v2/estudiante"), handler::modificar)
				.andRoute(DELETE("/v2/estudiante/{id}"), handler::eliminar);
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutasCursos(CursoHandler handler){
		return route(GET("/v2/curso"), handler::listar)
				.andRoute(GET("/v2/curso/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/curso"), handler::registrar)
				.andRoute(PUT("/v2/curso"), handler::modificar)
				.andRoute(DELETE("/v2/curso/{id}"), handler::eliminar);
	}
	@Bean
	public RouterFunction<ServerResponse> rutasMatriculas(MatriculaHandler handler){
		return route(GET("/v2/matricula"), handler::listar)
				.andRoute(GET("/v2/matricula/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/matricula"), handler::registrar)
				.andRoute(PUT("/v2/matricula"), handler::modificar)
				.andRoute(DELETE("/v2/matricula/{id}"), handler::eliminar);
	}
}
