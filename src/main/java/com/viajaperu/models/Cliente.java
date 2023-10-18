package com.viajaperu.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="tb_cliente")
@Getter
@Setter
public class Cliente {

	@Id
	private String cod_cliente;				
	private String nombre;								
	private int cod_tipodocumento;
	@Column(name="numero_documento")
	private String numeroDocumento; 		
	private String email;					
	private String telefono;				
	private String direccion;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_tipodocumento",insertable = false,updatable = false)
	private TipoDocumento objTipoDocumento;
	
}
