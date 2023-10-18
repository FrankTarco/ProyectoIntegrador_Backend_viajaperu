package com.viajaperu.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.viajaperu.models.EquipoBus;
import com.viajaperu.models.Itinerario;
import com.viajaperu.service.DestinoService;
import com.viajaperu.service.EquipoBusService;
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
	
	@Autowired
	private EquipoBusService ebService;
	
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
			
			//VALIDAR LAS FECHAS
			LocalDateTime fsalida = LocalDateTime.parse(objItinerario.getFecha_salida());
	        LocalDateTime fllegada = LocalDateTime.parse(objItinerario.getFecha_llegada());
	        
	        if(fsalida.compareTo(fllegada)==0) {
	        	salida.put("mensaje", "Error: La fecha de salida y llegada no pueden ser las mismas");
	        }
	        else if(fsalida.compareTo(fllegada)>0) {
	        	salida.put("mensaje", "Error: La fecha de salida no puede ser menor a la de llegada");
	        }
	        else if(fsalida.plusHours(4).compareTo(fllegada)>0){
	        	salida.put("mensaje", "Error: La fecha de llegada debe tener como margen minimo 4 horas");
	        }
	        else {
	        	//AQUI PASA LA VALIDACION PARA LAS FECHAS
	        	//AHORA VALIDAR QUE EL CODIGO DE EQUIPO SEA VALIDO
	        	Optional<EquipoBus>encontrar_equipo = ebService.buscarPorId(objItinerario.getCodequipo());
	        	if(encontrar_equipo.isEmpty()) {
	        		salida.put("mensaje", "Error: El equipo bus no existe en el sistema");
	        	}
	        	else {
	        		
	        		//UBICAR AL BUS PARA VALIDAR QUE NO SE SELECCIONE EN EL MISMO DIA
	        		List<Itinerario>bus_itinerario = itinerarioService.listarItinerariosPorBus(objItinerario.getCodbus());
	        		Boolean res_fecha_validar = true;	        		
	        		for(Itinerario iti :bus_itinerario) {
	        			LocalDateTime fecha_validar = LocalDateTime.parse(iti.getFecha_salida(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        			LocalDateTime fecha_validar_data = LocalDateTime.parse(objItinerario.getFecha_salida());
	        			
	        			if(fecha_validar.toLocalDate().compareTo(fecha_validar_data.toLocalDate())==0) {
	        				res_fecha_validar = false;
	        				break;
	        			}
	        			
	        		}
	        		//VALIDAR QUE EL BUS NO COINCIDA
	        		if(!res_fecha_validar) {
	        			salida.put("mensaje", "Error: El bus ya ha sido asignado en la misma fecha");
	        		}
	        		
	        		else {
	        			
	        			List<Itinerario>equipo_itinerario = itinerarioService.listarItinerariosPorEquipo(objItinerario.getCodequipo());
	        			Boolean res_fecha_equipo=true;
	        			
	        			for(Itinerario i:equipo_itinerario) {
	        				LocalDateTime fec_validar = LocalDateTime.parse(i.getFecha_salida(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		        			LocalDateTime fec_validar_data = LocalDateTime.parse(objItinerario.getFecha_salida());
		        			
		        			if(fec_validar.toLocalDate().compareTo(fec_validar_data.toLocalDate())==0) {
		        				res_fecha_equipo = false;
		        				break;
		        			}
	        			}
	        			
	        			if(!res_fecha_equipo) {
	        				salida.put("mensaje", "Error: El equipo ya ha sido asignado en la misma fecha");
	        			}
	        			
	        			else {
	        				
	        				objItinerario.setCod_itinerario(cod);
	    					Itinerario nuevo_itinerario = itinerarioService.registraActualizaItinerario(objItinerario);
	    					
	    					if(nuevo_itinerario == null) {
	    						salida.put("mensaje", "No se registro el itinerario");
	    						
	    					}else {
	    						salida.put("mensaje", "Se registro el itinerario " + nuevo_itinerario.getCod_itinerario());
	    					}
	        				
	        			}
	        			
	        		}
	        			
	        	}
	        	 	
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
