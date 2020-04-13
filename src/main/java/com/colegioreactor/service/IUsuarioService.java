package com.colegioreactor.service;

import com.colegioreactor.document.Usuario;
import com.colegioreactor.security.User;

import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<Usuario, String>{

	Mono<User> buscarPorUsuario(String usuario);

}
