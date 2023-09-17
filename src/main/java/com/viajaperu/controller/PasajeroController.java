package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Pasajero;
import com.viajaperu.service.PasajeroService;
import com.viajaperu.utils.Response;
import com.viajaperu.utils.Utilidades;

@RestController()
@RequestMapping("/rest/pasajero")
public class PasajeroController {

	@Autowired
	private PasajeroService service;
	
	@GetMapping
	public ResponseEntity<?>listado(){
		
		return ResponseEntity.ok(service.listarPasajeros());
	}
	
	@PostMapping
	public ResponseEntity<?>registar(@RequestBody Pasajero objPasajero){
		
		Response res = new Response();
		HashMap<String, Response> salida = new HashMap<>();
		Utilidades util = new Utilidades();
		
		String codPasajero = util.generarId(service.codigoPasajeroString(),"Pasajero");
		
		try {
		 List<Pasajero>verificar = service.buscarPasajeroXDocumento(objPasajero.getNumeroDocumento());
		
		 if(CollectionUtils.isEmpty(verificar)) {
			 			 
	 
			 objPasajero.setCod_pasajero(codPasajero);
			 Pasajero pasajero_registrado = service.registrarActualizar(objPasajero);
			 
			 if(pasajero_registrado == null) {
				 res.setEstado(false);
				 res.setMensaje("No se logro el registro en la BD - Intentelo denuevo");
				 salida.put("mensaje", res);
			 }
			 else {
				 res.setEstado(true);
				 res.setObjeto(pasajero_registrado);
				 res.setMensaje("Se registro correctamente el pasajero: " + pasajero_registrado.getCod_pasajero());
				 salida.put("mensaje", res);
			 }
			 
		 }else {			
			 res.setMensaje("El numero de documento "+objPasajero.getNumeroDocumento()+ " ya existe en la BD");
			 res.setEstado(false);
			 salida.put("mensaje", res);
		 }
		 
		} catch (Exception e) {
			res.setEstado(false);
			 res.setMensaje("Error al conectarse a la base de datos");
			salida.put("mensaje", res);
		}
		
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping
	public ResponseEntity<?>actualizar(@RequestBody Pasajero objPasajero){
		
		Response res = new Response();
		HashMap<String, Response> salida = new HashMap<>();
		
		try {
			Optional<Pasajero>verificar = service.buscarPasajeroXCodigo(objPasajero.getCod_pasajero());
			
			if(verificar.isPresent()) {
				Pasajero pasajero_actualizado = service.registrarActualizar(objPasajero);
				if(pasajero_actualizado == null) {
					
					res.setEstado(false);
					res.setMensaje("No se pudo actualizar - Intentelo denuevo");
					salida.put("mensaje", res);
					
				}else {
					res.setEstado(true);
					res.setMensaje("Se actualizo correctamente");
					res.setObjeto(pasajero_actualizado);
					salida.put("mensaje", res);
				}
				
			}else {
				res.setEstado(false);
				res.setMensaje("El pasajero " + objPasajero.getCod_pasajero() + " ya fue registrado");
				salida.put("mensaje", res);
			}
			
			
		} catch (Exception e) {
			res.setEstado(false);
			res.setMensaje("Error al conectarse a la base de datos");
			salida.put("mensaje", res);
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?>BuscarCodigo(@PathVariable("codigo")String codigo){
		
		Optional<Pasajero>encontrado = service.buscarPasajeroXCodigo(codigo);
		
		return ResponseEntity.ok(encontrado);
	}
	
	
	
}
