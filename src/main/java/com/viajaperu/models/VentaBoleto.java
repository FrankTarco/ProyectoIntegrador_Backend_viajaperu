package com.viajaperu.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="tb_ventaboleto")
@Getter
@Setter
public class VentaBoleto {

	@Id
	private String cod_venta;				
	private String fecha_operacion;		
	private double precioTotal;
	private String cod_itinerario;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_itinerario",insertable=false,updatable = false)
	private Itinerario itinerario;
	
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Boleto>boletos;
	
}
