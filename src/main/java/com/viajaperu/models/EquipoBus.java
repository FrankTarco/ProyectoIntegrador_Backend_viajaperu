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
@Table(name="tb_equipobus")
@Getter
@Setter
public class EquipoBus {

	@Id
	private String cod_equipo;
	@Column(name="cod_chofer")
	private String codchofer;
	@Column(name="cod_copiloto")
	private String codcopiloto;
	@Column(name="cod_terramoza")
	private String codterramoza; 	
	private int estado; 			
	
	
	  	@ManyToOne
		@JoinColumn(name="cod_chofer", insertable = false, updatable = false)
		private Conductor objConductor;
	  	
	  	@ManyToOne
		@JoinColumn(name="cod_terramoza", insertable = false, updatable = false)
		private Terramoza objTerramoza;
	
	  	@ManyToOne
		@JoinColumn(name="cod_copiloto", insertable = false, updatable = false)
		private Conductor objCopiloto;
}
