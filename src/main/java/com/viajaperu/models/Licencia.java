package com.viajaperu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_licencia")
@Getter
@Setter
public class Licencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_licencia;
	private String descripcion;
	private int estado;
	

}
