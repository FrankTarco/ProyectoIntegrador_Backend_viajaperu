package com.viajaperu.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_combustible")
@Getter
@Setter
public class Combustible {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int cod_combustible;
	private String nombre;
	private int estado;
	
}
