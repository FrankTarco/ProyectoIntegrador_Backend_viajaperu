package com.viajaperu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_bus")
@Getter
@Setter
public class Bus {

	@Id
	private String cod_bus;
	private String placa;
	private String marca;
	private int total_asientos;
	private int total_pasajeros;
	private String año_fabricacion;
	private int cantidad_ruedas;
	private int cod_combustible;
	private int cod_servicio;
	private int estado;
	
	@ManyToOne
	@JoinColumn(name="cod_combustible", insertable = false, updatable = false)
	private Combustible objCombustible;
	
	@ManyToOne
	@JoinColumn(name="cod_servicio", insertable = false, updatable = false)
	private Servicio objServicio;
	
}
