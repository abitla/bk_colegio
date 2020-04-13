package com.colegioreactor.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.colegioreactor.document.Matricula;

public interface IMatriculaRepo extends ReactiveMongoRepository<Matricula, String> {

}
