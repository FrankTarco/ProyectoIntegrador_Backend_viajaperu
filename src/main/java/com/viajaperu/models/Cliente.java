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
	
	@ManyToOne
	@JoinColumn(name="cod_tipodocumento",insertable = false,updatable = false)
	private TipoDocumento objTipoDocumento;
	
}
