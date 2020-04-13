package com.colegioreactor.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.colegioreactor.document.Curso;
import com.colegioreactor.document.Estudiante;
import com.colegioreactor.pagination.PageSupport;
import com.colegioreactor.repo.ICursoRepo;
import com.colegioreactor.service.ICursoService;
import com.colegioreactor.service.IEstudianteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CursoServiceImpl implements ICursoService{

	@Autowired
	private ICursoRepo repo;

	@Override
	public Flux<Curso> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Mono<Curso> listarPorId(String v) {
		// TODO Auto-generated method stub
		return repo.findById(v);
	}

	@Override
	public Mono<Curso> registrar(Curso t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Curso> modificar(Curso t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Void> eliminar(String v) {
		// TODO Auto-generated method stub
		return repo.deleteById(v);
	}

	@Override
	public Mono<PageSupport<Curso>> listarPage(Pageable page) {
		// TODO Auto-generated method stub
		return repo.findAll()
		        .collectList()
		        .map(list -> new PageSupport<>(
		            list
		                .stream()
		                .skip(page.getPageNumber() * page.getPageSize())
		                .limit(page.getPageSize())
		                .collect(Collectors.toList()),
		            page.getPageNumber(), page.getPageSize(), list.size()));
	}

	
	

}
