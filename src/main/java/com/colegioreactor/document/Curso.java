package com.colegioreactor.document;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "curso")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Curso {

	@Id
	private String id;
	@NotNull
	private String nombre;
	@NotNull
	private String siglas;
	@NotNull
	private Boolean estado;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
	
}
