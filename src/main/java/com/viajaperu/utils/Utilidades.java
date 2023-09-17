package com.viajaperu.utils;

public class Utilidades {

public String generarId(String id,String entidad) {
		
		String cod = "";
		if(id == null) {
			String letra = entidad.substring(0,1);
			cod = letra+"0000001";
		}
		else {
			
			String formateado = id.substring(1);
			String letra = id.substring(0,1);
			int numero = Integer.parseInt(formateado)+1;
			
			if(numero >= 1000000)
				cod = letra+numero;
			else if(numero >= 100000)
				cod = letra+"0"+numero;
			else if(numero >= 10000)
				cod = letra+"00"+numero;	
			else if(numero >= 1000)
				cod = letra+"000"+numero;
			else if(numero >= 100)
				cod = letra+"0000"+numero;
			else if(numero >=10)
				cod = letra+"00000"+numero;
			else
				cod = letra+"000000"+numero;
			
		}
		
		return cod;
		
	}
	
public String generarIdTrasaccion(String id,String entidad) {
	
	String cod = "";
	if(id == null) {
		String letra = entidad.substring(0,2).toUpperCase();
		cod = letra+"0000001";
	}
	else {
		
		String formateado = id.substring(2);
		String letra = id.substring(0,2).toUpperCase();
		int numero = Integer.parseInt(formateado)+1;
		
		if(numero >= 1000000)
			cod = letra+numero;
		else if(numero >= 100000)
			cod = letra+"0"+numero;
		else if(numero >= 10000)
			cod = letra+"00"+numero;	
		else if(numero >= 1000)
			cod = letra+"000"+numero;
		else if(numero >= 100)
			cod = letra+"0000"+numero;
		else if(numero >=10)
			cod = letra+"00000"+numero;
		else
			cod = letra+"000000"+numero;
		
	}
	
	return cod;
	
}


}
