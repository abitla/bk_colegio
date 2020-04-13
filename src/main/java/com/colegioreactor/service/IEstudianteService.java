package com.colegioreactor.service;

import org.springframework.data.domain.Pageable;

import com.colegioreactor.document.Estudiante;
import com.colegioreactor.pagination.PageSupport;

import reactor.core.publisher.Mono;

public interface IEstudianteService extends ICRUD<Estudiante, String>{

	Mono<PageSupport<Estudiante>> listarPage(Pageable page);
}
