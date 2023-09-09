package com.viajaperu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.repository.CombustibleRepository;
import com.viajaperu.repository.ServicioRepository;

@RestController()
@RequestMapping("/rest/util")
public class UtilController {

	@Autowired
	private CombustibleRepository repoCombu;
	
	@Autowired
	private ServicioRepository repoServicio;
	
	@GetMapping("/lista/combustible")
	public ResponseEntity<?>listaCombustible(){
		
		return ResponseEntity.ok(repoCombu.findAll());
	}
	
	
	@GetMapping("/lista/servicio")
	public ResponseEntity<?>listaServicios(){
		
		return ResponseEntity.ok(repoServicio.findAll());
	}
	
}
