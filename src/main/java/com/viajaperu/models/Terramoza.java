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
@Setter
@Getter
@Table(name="tb_terramoza")


public class Terramoza {
	
	@Id
	private String cod_terramoza;
	private String nombre;
	private String ape_mater;
	private String ape_pater;
	private int cod_tipodocumento;
	@Column(name="numero_documento")
	private String numerodocumento;
	
	@ManyToOne
	@JoinColumn(name="cod_tipodocumento",insertable=false,updatable=false)
	private TipoDocumento objTipoDocumento;
	
}
