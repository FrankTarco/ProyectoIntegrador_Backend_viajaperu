package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Bus;
import com.viajaperu.service.BusService;
import com.viajaperu.utils.AppSettings;
import com.viajaperu.utils.Utilidades;


@RestController()
@RequestMapping("/rest/bus")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class BusController {

	@Autowired
	private BusService service;
	
	@GetMapping()
	public ResponseEntity<?>listarBuses(){	
		return ResponseEntity.ok(service.listarBuses());
	}
	
	
	@GetMapping("/porcodigo/{codigo}")
	public ResponseEntity<?>busPorCodigo(@PathVariable("codigo")String codigo){
		Optional<Bus> b = service.buscarPorId(codigo);
		
		return ResponseEntity.ok(b);
	}
	
	
	@PostMapping()
	public ResponseEntity<?>registrar(@RequestBody Bus objBus){		
		HashMap<String, String> salida = new HashMap<>();	
		Utilidades util = new Utilidades();	
		String cod = util.generarId(service.ultimoIdString(),"Bus");	
		try {
			
			//VALIDACION DE PLACA REPETIDA
			List<Bus> busplaca = service.listarBusXPlaca(objBus.getPlaca());
			
			if(CollectionUtils.isEmpty(busplaca)) {
				//SE PROCEDE A REGISTRAR
				if(objBus.getTotal_asientos()<=objBus.getTotal_pasajeros()) {
					
					salida.put("mensaje", "Error: Los asientos no pueden ser menores o iguales a los disponibles para los pasajeros");
				}
				else {
					
					if(objBus.getTotal_asientos() != (objBus.getTotal_pasajeros()+3)) {
						salida.put("mensaje", "Error: La diferencia entre asientos y pasajeros solo puede ser de 3");	
					}
					else {
						objBus.setCod_bus(cod);
						objBus.setMarca(objBus.getMarca().toUpperCase());
						Bus busInsertado = service.registrarActualizarBus(objBus);
						if(busInsertado == null) {
							salida.put("mensaje", "Error: No se inserto el bus por un error");
						}
						else {
							salida.put("mensaje", "Bus insertado correctamente");
						}
						
					}			
					
				}		
				
			}
			else {
				//SI ENCUENTRA LA PLACA
				salida.put("mensaje", "Error: La placa: "+ objBus.getPlaca() + " ya se encuentra registrada");
			}
			
			
		} catch (Exception e) {
			salida.put("mensaje", "Error: No se pudo realizar la operacion " + e.getMessage());
		}
		
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping()
	public ResponseEntity<?>actualizarBus(@RequestBody Bus objBus){	
		HashMap<String, String> salida = new HashMap<>();	
		try {		
			Optional<Bus> verificar = service.buscarPorId(objBus.getCod_bus());		
			if(verificar.isPresent()) {				
				//VALIDAR QUE NO SE REPITA LA PLACA CON OTROS REGISTROS
				List<Bus> busPlaca = service.listarBusXPlacaDiferenteCodigo(objBus.getPlaca(), objBus.getCod_bus());
				if(CollectionUtils.isEmpty(busPlaca)) {	
					//PROCEDE A ACTUALIZAR
					Bus busActualizado = service.registrarActualizarBus(objBus);			
					if(busActualizado == null) {
						salida.put("mensaje", "Error: No se actualizo el bus por un error");
					}				
					else {
						salida.put("mensaje", "Bus "+ objBus.getCod_bus()+ " correctamente");
					}
					
				}else {
					
					salida.put("mensaje", "Error: La placa: "+objBus.getPlaca()+" ya existe en el sistema");
				}
						
			}
			else {
				salida.put("mensaje", "Error: El bus "+objBus.getCod_bus()+" no existe en el sistema");
			}
			
		} catch (Exception e) {
			salida.put("mensaje", "Error: No se pudo realizar la operacion " + e.getMessage());
		}		
		return ResponseEntity.ok(salida);	
	}
	
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>eliminarBus(@PathVariable("codigo")String codigo){
		
		HashMap<String, String> salida = new HashMap<>();
		
		Optional<Bus> verificar = service.buscarPorId(codigo);
		
		if(verificar.isPresent()) {
			List<Bus>validar = service.encontrarBusenItinerario(codigo);
			if(CollectionUtils.isEmpty(validar)) {
				service.eliminarBus(codigo);
				salida.put("mensaje", "Se elimino al bus");
			}
			else {
				salida.put("mensaje", "Error: El bus ya ha sido asignado a un itinerario");
			}
					
		}
		else {
			salida.put("mensaje", "Error: No se encontro al bus de codigo " + codigo);
		}
		
		return ResponseEntity.ok(salida);
		
	}
	
	
	
}
