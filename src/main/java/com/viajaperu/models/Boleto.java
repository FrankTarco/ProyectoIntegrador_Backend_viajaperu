package com.viajaperu.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="tb_boleto")
@Getter
@Setter
public class Boleto {

	@Id
	private String cod_boleto;					
	private int numero_asiento;		
	private double precio;
	
	@ManyToOne
	@JoinColumn(name="cod_pasajero")
	private Pasajero pasajero;
	
	@ManyToOne
	@JoinColumn(name="cod_venta")
	private VentaBoleto venta;
}
