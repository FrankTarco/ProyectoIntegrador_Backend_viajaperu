package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Destino;
import com.viajaperu.service.DestinoService;
import com.viajaperu.utils.Utilidades;

@RestController()
@RequestMapping("/rest/destino")
public class DestinoController {

	@Autowired
	private DestinoService service;
	
	@GetMapping("/listado")
	public ResponseEntity<?>listado(){
		
		return ResponseEntity.ok(service.listarTodo());
	}
	
	@PostMapping("/registra")
	public ResponseEntity<?>registrar(@RequestBody Destino objDestino){
		
		HashMap<String, String> salida = new HashMap<>();
		Utilidades util = new Utilidades();
		String cod = util.generarId(service.ultimoRegistroString(), "Destino");
		
		try {
			//PRIMERA VERIFICACION POR NOMBRE DE DESTINO
			List<Destino>verificarNombre = service.buscarPorNombreAndSucursal(objDestino.getNombre(),objDestino.getSucursal());
			if(CollectionUtils.isEmpty(verificarNombre)) {
				objDestino.setCod_destino(cod);				
				Destino destinoRegistrado = service.registarActualizar(objDestino);
				if(destinoRegistrado == null) {
					salida.put("mensaje", "No se registro, verifique la informacion");
				}
				else {
					salida.put("mensaje", "Se registro correctamente el destino");
				}
				
			}else {
				salida.put("mensaje", "El nombre de destino "+ objDestino.getNombre()+ "y la sucursal "+objDestino.getSucursal()+" ya existen");
				
			}
			
			
		} catch (Exception e) {
			salida.put("mensaje", "Ocurrio un error en la conexion " + e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
		
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?>actualizar(@RequestBody Destino objDestino){
		
		HashMap<String, String> salida = new HashMap<>();
		
		try {
			Optional<Destino> destinoExiste = service.buscarDestinoPorCodigo(objDestino.getCod_destino());
			if(destinoExiste.isPresent()) {
				List<Destino>destinoRepetido = service.buscarDestinoDiferenteMismo(objDestino.getNombre(),objDestino.getSucursal(),objDestino.getCod_destino());
				
				if(CollectionUtils.isEmpty(destinoRepetido)) {
					//PROCEDE A ACTUALIZAR
					Destino destinoActualizado = service.registarActualizar(objDestino);
					if(destinoActualizado==null) {
						
						salida.put("mensaje", "No se pudo actualizar, verificar");
					}else {
						salida.put("mensaje", "Se actualizo el destino " + objDestino.getCod_destino());
					}
					
				}else {
					salida.put("mensaje", "El nobmre de destino "+objDestino.getNombre()+" ya existe");
				}
				
			}else {
				salida.put("mensaje", "El destino "+objDestino.getCod_destino()+" no existe");
			}			
			
		} catch (Exception e) {
			salida.put("mensaje", "Ocurrio un error en la conexion " + e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
		
	}
	
	
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<?>Eliminar(@PathVariable("codigo")String codigo){
		
		HashMap<String, String> salida = new HashMap<>();
		
		Optional<Destino> DestinoExiste = service.buscarDestinoPorCodigo(codigo);
		if(DestinoExiste.isPresent()) {
			
			service.eliminarDestino(codigo);
			salida.put("mensaje", "Se elimino el destino "+ codigo);
			
		}else {
			salida.put("mensaje", "El destino"+codigo+" no existe en el sistema");
		}
			
		return ResponseEntity.ok(salida);
		
	}
	
	
}
