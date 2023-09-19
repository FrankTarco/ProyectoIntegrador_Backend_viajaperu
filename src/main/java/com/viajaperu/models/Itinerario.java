package com.viajaperu.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tb_itinerario")


public class Itinerario {
	@Id
	private String cod_itinerario;		
	private String fecha_salida;
	private String fecha_llegada;
	
	@Column(name="cod_origen")
	private String codorigen;
	
	@Column(name="cod_llegada")
	private String codllegada;		
	
	@Column(name="cod_equipo")
	private String codequipo;
	
	@Column(name="cod_bus")
	private String codbus;				
	
	private double precio;			
	private int estado;			
	
	@ManyToOne
	@JoinColumn(name="cod_origen",insertable=false,updatable=false)
	private Destino objOrigen;
	
	@ManyToOne
	@JoinColumn(name="cod_llegada",insertable=false,updatable=false)
	private Destino objLlegada;
	
	@ManyToOne
	@JoinColumn(name="cod_equipo",insertable=false,updatable=false)
	private EquipoBus objEquipobus;
	
	@ManyToOne
	@JoinColumn(name="cod_bus",insertable=false,updatable=false)
	private Bus objBus;
	
	
}
