package com.colegioreactor.service;

import org.springframework.data.domain.Pageable;

import com.colegioreactor.document.Curso;
import com.colegioreactor.document.Estudiante;
import com.colegioreactor.pagination.PageSupport;

import reactor.core.publisher.Mono;

public interface ICursoService extends ICRUD<Curso, String> {
	Mono<PageSupport<Curso>> listarPage(Pageable page);
}
