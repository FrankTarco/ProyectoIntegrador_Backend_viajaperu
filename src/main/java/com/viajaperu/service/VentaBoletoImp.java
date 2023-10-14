package com.viajaperu.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.VentaBoleto;
import com.viajaperu.repository.VentaBoletoRepository;
import com.viajaperu.utils.Utilidades;

@Service
public class VentaBoletoImp implements VentaBoletoService{

	@Autowired
	private VentaBoletoRepository repo;
	
	@Override
	public VentaBoleto registarVentaBoleto(VentaBoleto objVentaBoleto) {
		
		Utilidades u = new Utilidades();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String codigo = u.generarIdTrasaccion(repo.ultimoCodVenta(), "Venta");
		
		objVentaBoleto.setCod_venta(codigo);
		objVentaBoleto.setFecha_operacion(sdf.format(new Date()));
				
		return repo.save(objVentaBoleto);
	}

}
