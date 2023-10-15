package com.viajaperu.creditcard;

import java.util.List;

public interface TarjetaCreditoService {

	public List<TarjetaCredito>verificarTarjetaPorNumero(String numero);
	public TarjetaCredito actualizarRegistrar(TarjetaCredito obj);
}
