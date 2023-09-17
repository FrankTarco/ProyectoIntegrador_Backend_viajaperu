package com.viajaperu.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Cliente;
import com.viajaperu.service.ClienteService;
import com.viajaperu.utils.Utilidades;

@RestController()
@RequestMapping("/rest/cliente")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@GetMapping
	public ResponseEntity<?>listado(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?>buscarPorCodigo(@PathVariable("codigo")String codigo){
		
		return ResponseEntity.ok(service.buscarXCodigo(codigo));
	}
	
	@GetMapping("/documento/{numero}")
	public ResponseEntity<?>buscarPorDocumento(@PathVariable("numero")String numero){
		
		return ResponseEntity.ok(service.buscarPorDocumento(numero));
	}
	
	@PostMapping
	public ResponseEntity<?>registar(@RequestBody Cliente cliente){
		
		HashMap<String, Object> salida = new HashMap<>();
		Utilidades util = new Utilidades();
		
		String codigo = util.generarIdTrasaccion(service.ultimoCodigo(), "cliente");
		
		try {
			
			cliente.setCod_cliente(codigo);
			Cliente cliente_registrado = service.registrar(cliente);
			if(cliente_registrado == null) {
				salida.put("mensaje", "Ocurrio un error");
			}
			else {
				salida.put("mensaje", "Se registro con exito");
			}
			
		} catch (Exception e) {
			salida.put("mensaje", "Error al conectar con la BD");
		}
		
		return ResponseEntity.ok(salida);
	}
	
}
