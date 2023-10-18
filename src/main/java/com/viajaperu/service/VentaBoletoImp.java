package com.viajaperu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Override
	public List<VentaBoleto> listarVentas() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public VentaBoleto buscarBoletaPorCodigo(String codigo) {
		// TODO Auto-generated method stub
		return repo.encontrarVenta(codigo);
	}

	@Override
	public Optional<VentaBoleto> encontrarVenta(String codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

}
