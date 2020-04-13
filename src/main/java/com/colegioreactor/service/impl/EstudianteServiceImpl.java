package com.colegioreactor.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.colegioreactor.document.Estudiante;
import com.colegioreactor.pagination.PageSupport;
import com.colegioreactor.repo.IEstudianteRepo;
import com.colegioreactor.service.IEstudianteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstudianteServiceImpl implements IEstudianteService{

	@Autowired
	private IEstudianteRepo repo;

	@Override
	public Flux<Estudiante> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Mono<Estudiante> listarPorId(String v) {
		// TODO Auto-generated method stub
		return repo.findById(v);
	}

	@Override
	public Mono<Estudiante> registrar(Estudiante t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Estudiante> modificar(Estudiante t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Void> eliminar(String v) {
		// TODO Auto-generated method stub
		 return repo.deleteById(v);
	}

	@Override
	public Mono<PageSupport<Estudiante>> listarPage(Pageable page) {
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
