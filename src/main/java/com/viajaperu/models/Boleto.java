package com.viajaperu.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_pasajero")
	private Pasajero pasajero;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_venta")
	@JsonIgnore
	private VentaBoleto venta;
}
