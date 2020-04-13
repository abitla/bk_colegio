package com.colegioreactor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegioreactor.document.Matricula;
import com.colegioreactor.repo.IMatriculaRepo;
import com.colegioreactor.service.IMatriculaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class MatriculaServiceImpl implements IMatriculaService {

	@Autowired
	private IMatriculaRepo repo;
	
	@Override
	public Flux<Matricula> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Mono<Matricula> listarPorId(String v) {
		// TODO Auto-generated method stub
		return repo.findById(v);
	}

	@Override
	public Mono<Matricula> registrar(Matricula t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Matricula> modificar(Matricula t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public Mono<Void> eliminar(String v) {
		// TODO Auto-generated method stub
		return repo.deleteById(v);
	}

}
