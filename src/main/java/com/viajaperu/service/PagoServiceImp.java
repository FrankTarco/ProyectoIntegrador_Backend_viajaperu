package com.viajaperu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Pago;
import com.viajaperu.repository.PagoRepository;
import com.viajaperu.utils.Utilidades;

@Service
public class PagoServiceImp implements PagoService{

	@Autowired
	private PagoRepository repo;
	
	@Override
	public Pago registrarPago(Pago objPago) {
		
		Utilidades util = new Utilidades();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String codigo = util.generarIdTrasaccion(repo.ultimoCodigoPago(),"PAGO");
		objPago.setCod_pago(codigo);
		objPago.setFecha_operacion(sdf.format(new Date()));
		
		return repo.save(objPago);
	}

	@Override
	public Pago buscarPagoPorNumero(String numero) {
		// TODO Auto-generated method stub
		return repo.buscarPagosPorNumeroTarjeta(numero);
	}

	
}
