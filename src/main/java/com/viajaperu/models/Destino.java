package com.viajaperu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_destino")
@Getter
@Setter
public class Destino {

	@Id
	private String cod_destino;
	private String nombre;
	private String sucursal;
	private String ubicacion;
	private int estado;
	
}
