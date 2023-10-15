package com.viajaperu.creditcard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_tarjetacredito")
@Getter
@Setter
public class TarjetaCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_tarjeta;
	private String persona;
	@Column(name="numero_tarjeta")
	private String numero;
	private String cvv_tarjeta;
	private double credito;
	private String vencimiento;
	
	
}
