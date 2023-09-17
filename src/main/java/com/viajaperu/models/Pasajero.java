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
@Table(name="tb_pasajero")
@Getter
@Setter
public class Pasajero {

	@Id
	private String cod_pasajero;
	private String nombre;
	private String apellidos;
	private int cod_tipodocumento;
	@Column(name="numero_documento")
	private String numeroDocumento;
	private int edad;
	
	@ManyToOne
	@JoinColumn(name="cod_tipodocumento",insertable = false,updatable = false)
	private TipoDocumento objTipoDocumento;	
	
	
}
