package com.colegioreactor.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, V> {

	Flux<T> listar();

	Mono<T> listarPorId(V v);

	Mono<T> registrar(T t);

	Mono<T> modificar(T t);

	Mono<Void> eliminar(V v);
}
