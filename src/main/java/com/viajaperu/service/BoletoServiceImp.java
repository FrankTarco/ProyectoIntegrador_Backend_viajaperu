package com.viajaperu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Boleto;
import com.viajaperu.repository.BoletoRepository;
import com.viajaperu.utils.Utilidades;

@Service
public class BoletoServiceImp implements BoletoService{

	@Autowired
	private BoletoRepository repo;
	
	
	@Override
	public Boleto registrar(Boleto objBoleto) {
		
		Utilidades util = new Utilidades();
		String codigo = util.generarIdTrasaccion(repo.ultimoCodigoBoleto(),"BOLETO");
		
		objBoleto.setCod_boleto(codigo);
		
		return repo.save(objBoleto);
	}


	@Override
	public List<Integer> listarBoletosVendidos(String codigo) {
		
		return repo.listarBoletosCompradosPorItinerario(codigo);
	}


}
