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

import com.viajaperu.models.Conductor;
import com.viajaperu.service.ConductorService;
import com.viajaperu.utils.AppSettings;
import com.viajaperu.utils.Utilidades;

@RestController()
@RequestMapping("/rest/conductor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ConductorController {

	@Autowired
	private ConductorService serviceconductor;

	@GetMapping()
	public ResponseEntity<?> listarConductor() {
		return ResponseEntity.ok(serviceconductor.listarConductor());

	}

	@GetMapping("/porcodigo/{codigo}")
	public ResponseEntity<?> busPorCodigo(@PathVariable("codigo") String codigo) {
		Optional<Conductor> b = serviceconductor.buscarPorId(codigo);

		return ResponseEntity.ok(b);
	}

	@PostMapping()
	public ResponseEntity<?> registrar(@RequestBody Conductor objConductor) {

		HashMap<String, String> salida = new HashMap<>();

		Utilidades util = new Utilidades();

		String cod = util.generarId(serviceconductor.ultimoIdString(), "CON");

		try {

			List<Conductor> conddocumento = serviceconductor.listarConductorXDocumento(objConductor.getNrodocumento());

			List<Conductor> condlicencia = serviceconductor.listarConductorXLicencia(objConductor.getNrolicencia());

			if (CollectionUtils.isEmpty(conddocumento)) {
				// SE PROCEDE A REGISTRAR
				if (CollectionUtils.isEmpty(condlicencia)) {
					
					objConductor.setCod_conductor(cod);
					Conductor conductoronsertado = serviceconductor.registrarActualizarConductor(objConductor);
					if (conductoronsertado == null) {
						salida.put("mensaje", "Error!!!!.....No se inserto el Conductor");
					} else {
						salida.put("mensaje", "Conductor insertado correctamente");
					}
				}else {
					
					salida.put("mensaje", "Error: El Conductor con licencia -> ..." + objConductor.getNrolicencia()
					+ " ...ya se encuentra registrado");
					
				}
			

			}else {
				
				salida.put("mensaje", "Error: El Conductor con Documento -> ..." + objConductor.getNrodocumento()
				+ " ...ya se encuentra registrado");
			}
		} catch (Exception e) {
			salida.put("mensaje", "Ocurrio un Error " + e.getMessage());
		}

		return ResponseEntity.ok(salida);
	}

	@PutMapping()
	public ResponseEntity<?> actualizarCondutor(@RequestBody Conductor objConductor) {

		HashMap<String, String> salida = new HashMap<>();

		try {
			Optional<Conductor> verificar = serviceconductor.buscarPorId(objConductor.getCod_conductor());
			if (verificar.isPresent()) {
				// VALIDAR QUE NO SE REPITA LA PLACA CON OTROS REGISTROS
				List<Conductor> conductorlicencia = serviceconductor.listarConductorXLicenciaDiferenteCodigo(
						objConductor.getNrolicencia(), objConductor.getCod_conductor());
				if (CollectionUtils.isEmpty(conductorlicencia)) {
					
					List<Conductor> conductordocumento = serviceconductor.listarConductorXDocumentoDiferenteCodigo(objConductor.getNrodocumento(), objConductor.getCod_conductor());
					if (CollectionUtils.isEmpty(conductordocumento)) {
						
						Conductor conductorActualizado = serviceconductor.registrarActualizarConductor(objConductor);
						if (conductorActualizado == null) {
							salida.put("mensaje", "Error!!!...No se actualizo el conductor");
						}

						else {
							salida.put("mensaje",
									"Conductor -> " + objConductor.getCod_conductor() + " Actulizado correctamente");
						}
						
					}else {
						
						salida.put("mensaje", "El documento: " + objConductor.getNrodocumento() + " ya existe en el sistema");
					}
					
					
				

				} else {

					salida.put("mensaje", "La Licencia: " + objConductor.getNrolicencia() + " ya existe en el sistema");
				}

			} else {
				salida.put("mensaje", "El Conductor " + objConductor.getCod_conductor() + " no existe en el sistema");
			}

		} catch (Exception e) {
			salida.put("mensaje", "Ocurrio un error " + e.getMessage());
		}

		return ResponseEntity.ok(salida);

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> eliminarBus(@PathVariable("codigo") String codigo) {

		HashMap<String, String> salida = new HashMap<>();
		Optional<Conductor> verificar = serviceconductor.buscarPorId(codigo);

		if (verificar.isPresent()) {
			
			List<Conductor> verificarExiste = serviceconductor.buscarConductoresRegistradosEquipoBus(codigo);
			if(CollectionUtils.isEmpty(verificarExiste)) {
				serviceconductor.eliminarConductor(codigo);
				salida.put("mensaje", "Se elimino al Conductor..." + codigo);
			}
			else{
				salida.put("mensaje", "Error: El conductor " + codigo + " ya ha sido asignado a un Equipo de bus");
			}
			
		} else {
			salida.put("mensaje", "Error: No se encontro al conductor  de codigo..." + codigo);
		}

		return ResponseEntity.ok(salida);

	}

}
