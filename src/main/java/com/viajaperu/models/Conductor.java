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
@Table(name="tb_conductor")
@Getter
@Setter
public class Conductor {

	
	
	@Id
    private String cod_conductor;
    private int cod_tipodocumento;
    @Column(name="nro_documento")
    private String nrodocumento;
    private String ape_chofer;
    private String nom_chofer;
    private int cod_licencia;
    private String nrolicencia;
    private String telefono;
    
    
    @ManyToOne
	@JoinColumn(name="cod_tipodocumento", insertable = false, updatable = false)
	private TipoDocumento objTipoDocumento;
    
    @ManyToOne
	@JoinColumn(name="cod_licencia", insertable = false, updatable = false)
	private Licencia objLicencia;
    
}
