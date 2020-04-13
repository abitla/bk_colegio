package com.colegioreactor.document;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "estudiante")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Estudiante {

	@Id
	private String id;
	
	@NotEmpty(message = "Debe tsener un nombre")
	private String nombres;
	
	@NotEmpty(message = "Debe tener apellidos")
	private String apellidos;
	
	@NotEmpty(message = "Debe tener DNI")
	private String dni;
	
	
	private Double edad;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Double getEdad() {
		return edad;
	}

	public void setEdad(Double edad) {
		this.edad = edad;
	}
	
	
	
}
