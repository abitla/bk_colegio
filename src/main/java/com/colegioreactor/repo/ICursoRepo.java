package com.colegioreactor.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.colegioreactor.document.Curso;

public interface ICursoRepo extends ReactiveMongoRepository<Curso, String> {

}
