package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.EquipoBus;
import com.viajaperu.models.Itinerario;
import com.viajaperu.service.ItinerarioService;
import com.viajaperu.utils.Utilidades;

@RestController
@RequestMapping("/url/itinerario")
public class ItinerarioController {
	@Autowired
	private ItinerarioService itinerarioService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Itinerario>> listaItinerario() {
		List<Itinerario> lista = itinerarioService.listaItinerario();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> ultimoId() {
		return ResponseEntity.ok(itinerarioService.ultimoIdItinerario());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> itinerarioPorCodigo(@PathVariable("codigo") String codigo) {
		Optional<Itinerario> b = itinerarioService.buscarPorId(codigo);
		return ResponseEntity.ok(b);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?>registrarItinerario(@RequestBody Itinerario objItinerario){
		HashMap<String, String> salida = new HashMap<>();
		Utilidades util = new Utilidades();
		String cod = util.generarId(itinerarioService.ultimoIdItinerario(), "IT");//sino una letra mas
		
		try {
			
					
			
		} catch (Exception e) {
			salida.put("mensaje",
					"Ocurrio un error  ! -> Alguno o todos los datos insertados no existen en la Base de Datos : "
							+ e.getMessage());
		}	
		
		
		return ResponseEntity.ok(salida);
	}
	
	
	@DeleteMapping("/{codigo}")
	@ResponseBody
	public ResponseEntity<?> eliminarItinerario(@PathVariable("codigo") String codigo) {

		HashMap<String, String> salida = new HashMap<>();

		Optional<Itinerario> equipo = itinerarioService.buscarPorId(codigo);

		if (equipo.isPresent()) {
			itinerarioService.eliminarItinerario(codigo);
			salida.put("mensaje", "Se elimino el Itinerario  : " + codigo);

		} else {
			salida.put("mensaje", "No se encontró ningún Itinerario con codigo " + codigo);
		}

		return ResponseEntity.ok(salida);

	}
	
}
