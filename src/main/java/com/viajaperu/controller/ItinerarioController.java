package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Itinerario;
import com.viajaperu.service.DestinoService;
import com.viajaperu.service.ItinerarioService;
import com.viajaperu.utils.AppSettings;
import com.viajaperu.utils.Utilidades;

@RestController
@RequestMapping("/rest/itinerario")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ItinerarioController {
	@Autowired
	private ItinerarioService itinerarioService;
	
	@Autowired
	private DestinoService destinoService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Itinerario>> listaItinerario() {
		List<Itinerario> lista = itinerarioService.listaItinerario();
		return ResponseEntity.ok(lista);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> itinerarioPorCodigo(@PathVariable("codigo") String codigo) {
		Optional<Itinerario> b = itinerarioService.buscarPorId(codigo);
		return ResponseEntity.ok(b);
	}
	
	
	@GetMapping("/disponible")
	public ResponseEntity<?>itinerariosDisponiblesClient(@RequestParam("origen") String origen,
            @RequestParam("llegada") String llegada,
            @RequestParam("fecha") String fecha){		
		return ResponseEntity.ok(itinerarioService.itinerariosDisponiblesCliente(origen,llegada,fecha));
	}
	
	
	@GetMapping("/destinos")
	public ResponseEntity<?>listarDestinosDiferentes(){
		return ResponseEntity.ok(destinoService.listarDestinosDiferentes());
	}
	
	@GetMapping("/destinos/{origen}")
	public ResponseEntity<?>listarDestinosDiferentesSiMismo(@PathVariable("origen")String origen){
		
		return ResponseEntity.ok(destinoService.listarDestinosDiferentesSiMismos(origen));
	}
	
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?>registrarItinerario(@RequestBody Itinerario objItinerario){
		HashMap<String, String> salida = new HashMap<>();
		Utilidades util = new Utilidades();
		String cod = util.generarId(itinerarioService.ultimoIdItinerario(), "IT");//sino una letra mas
		
		try {
			objItinerario.setCod_itinerario(cod);
			Itinerario nuevo_itinerario = itinerarioService.registraActualizaItinerario(objItinerario);
			
			if(nuevo_itinerario == null) {
				salida.put("mensaje", "No se registro el itinerario");
				
			}else {
				salida.put("mensaje", "Se registro el itinerario " + nuevo_itinerario.getCod_itinerario());
			}
					
			
		} catch (Exception e) {
			salida.put("mensaje",
					"Ocurrio un error  ! -> Alguno o todos los datos insertados no existen en la Base de Datos : "
							+ e.getMessage());
		}	
		
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping
	public ResponseEntity<?>actualizarItinerario(@RequestBody Itinerario objItinerario){
		HashMap<String, String> salida = new HashMap<>();
		try {
		
			Optional<Itinerario>itinerario_existe = itinerarioService.buscarPorId(objItinerario.getCod_itinerario());
			
			if(itinerario_existe.isEmpty()) {
				salida.put("mensaje", "No existe el itinerario en el sistema");
			}else {
				Itinerario reg = itinerarioService.registraActualizaItinerario(objItinerario);
				if(reg == null) {
					salida.put("mensaje", "No se actualizo el itinerario");
				}else {
					salida.put("mensaje", "Se actualizo el itinerario correctamente");
				}
			}
			
		} catch (Exception e) {
			salida.put("mensaje", "No se pudo conectar al sistema");
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
