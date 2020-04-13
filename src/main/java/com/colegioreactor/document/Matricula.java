package com.colegioreactor.document;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "matricula")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Matricula {
	
	@Id 
	private String id;
	
	@NotNull
	private LocalDateTime fechaMatricula;
	
	@NotNull
	@DBRef
	private Estudiante estudiante;
	
	@NotNull
	@DBRef
	private List<Curso> cursos;
	
	@NotNull
	private Boolean estado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(LocalDateTime fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
	
	
	

}
