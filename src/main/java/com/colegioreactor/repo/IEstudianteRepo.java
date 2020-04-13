package com.colegioreactor.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.colegioreactor.document.Estudiante;

public interface IEstudianteRepo extends ReactiveMongoRepository<Estudiante, String>{

}
