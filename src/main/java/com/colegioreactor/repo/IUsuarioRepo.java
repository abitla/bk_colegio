package com.colegioreactor.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.colegioreactor.document.Usuario;

import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends ReactiveMongoRepository<Usuario, String>{

	 Mono<Usuario> findOneByUsuario(String usuario);

}
