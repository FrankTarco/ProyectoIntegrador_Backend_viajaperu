package com.viajaperu.creditcard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaCreditoServiceImp implements TarjetaCreditoService{

	@Autowired
	private TarjetaCreditoRepository repo;
	
	@Override
	public List<TarjetaCredito> verificarTarjetaPorNumero(String numero) {
		// TODO Auto-generated method stub
		return repo.findByNumero(numero);
	}

	@Override
	public TarjetaCredito actualizarRegistrar(TarjetaCredito obj) {		
		return repo.save(obj);
	}

}
