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

import com.viajaperu.models.EquipoBus;
import com.viajaperu.service.EquipoBusService;
import com.viajaperu.utils.AppSettings;
import com.viajaperu.utils.Utilidades;

@RestController()
@RequestMapping("/rest/equipobus")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class EquipoBusController {

	@Autowired
	private EquipoBusService serviceEquipoBus;

	@GetMapping()
	public ResponseEntity<?> listarEquipoBus() {
		return ResponseEntity.ok(serviceEquipoBus.listaEquipoBus());

	}
	
	@GetMapping("/conductores")
	public ResponseEntity<?>conductoresDisponibles(){
		return ResponseEntity.ok(serviceEquipoBus.conductoresDisponibles());
	}
	
	@GetMapping("/copilotos/{codigo}")
	public ResponseEntity<?>copilotosDisponibles(@PathVariable("codigo")String codigo){
		return ResponseEntity.ok(serviceEquipoBus.copilotosDisponibles(codigo));
	}
	
	@GetMapping("/terramozas")
	public ResponseEntity<?>terramozasDisponibles(){
		return ResponseEntity.ok(serviceEquipoBus.terramozasDisponibles());
	}
	


	@GetMapping("/porcodigo/{codigo}")
	public ResponseEntity<?> busPorCodigo(@PathVariable("codigo") String codigo) {
		Optional<EquipoBus> b = serviceEquipoBus.buscarPorId(codigo);

		return ResponseEntity.ok(b);
	}

	@PostMapping()
	public ResponseEntity<?> registrarEquipoBus(@RequestBody EquipoBus objEquipoBus) {

		HashMap<String, String> salida = new HashMap<>();

		Utilidades util = new Utilidades();

		String cod = util.generarId(serviceEquipoBus.ultimoIdEquipo(), "EQU");

		try {
			String codchof = objEquipoBus.getCodchofer();
			String codcopi = objEquipoBus.getCodcopiloto();

			if (codchof.equals(codcopi)) {

				salida.put("mensaje", "No puede Registrar al conductor mas de una vez en el mismo Equipo de trabajo");

			} else {

				List<EquipoBus> validachofer = serviceEquipoBus.buscarconductorrepetido(objEquipoBus.getCodchofer(),
						objEquipoBus.getCodchofer());
				List<EquipoBus> validacopiloto = serviceEquipoBus.buscarconductorrepetido(objEquipoBus.getCodcopiloto(),
						objEquipoBus.getCodcopiloto());
				List<EquipoBus> condTerramoza = serviceEquipoBus.listarEquipoTerramoza(objEquipoBus.getCodterramoza());

				if (CollectionUtils.isEmpty(validachofer)) {

					if (CollectionUtils.isEmpty(validacopiloto)) {
						if (CollectionUtils.isEmpty(condTerramoza)) {
							objEquipoBus.setCod_equipo(cod);
							EquipoBus equipoinsertado = serviceEquipoBus.registraActualizaEquipo(objEquipoBus);
							if (equipoinsertado == null) {
								salida.put("mensaje", "Error!!!!.....No se pudo crear el Equipo de Trabajo");
							} else {
								salida.put("mensaje", "Equipo insertado correctamente");
							}

						} else {
							salida.put("mensaje", "La Terramoza :   " + objEquipoBus.getCodterramoza()
									+ " ya forma parte de otro Equipo de trabajo");

						}

					} else {
						salida.put("mensaje", "El copiloto :   " + objEquipoBus.getCodcopiloto()
								+ " ya forma parte de otro Equipo de trabajo");

					}

				} else {

					salida.put("mensaje", "El conductor :   " + objEquipoBus.getCodchofer()
							+ " ya forma parte de otro Equipo de trabajo");
				}

			}

		} catch (Exception e) {
			salida.put("mensaje",
					"Ocurrio un error  ! -> Alguno de los codigos insertados no exite en la Base de Datos : "
							+ e.getMessage());
		}

		return ResponseEntity.ok(salida);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> eliminarEquipo(@PathVariable("codigo") String codigo) {

		HashMap<String, String> salida = new HashMap<>();

		Optional<EquipoBus> equipo = serviceEquipoBus.buscarPorId(codigo);

		if (equipo.isPresent()) {
			serviceEquipoBus.eliminarEquipo(codigo);
			salida.put("mensaje", "Se elimino al Equipo  : " + codigo);

		} else {
			salida.put("mensaje", "No se encontro al equipo de trabajo con codigo " + codigo);
		}

		return ResponseEntity.ok(salida);

	}

	@PutMapping()
	public ResponseEntity<?> actualizarEquipo(@RequestBody EquipoBus objEquipoBus) {

		HashMap<String, String> salida = new HashMap<>();

		try {
			
			String codchof = objEquipoBus.getCodchofer();
			String codcopi = objEquipoBus.getCodcopiloto();

			if (codchof.equals(codcopi)) {

				salida.put("mensaje", "No puede Registrar al conductor mas de una vez en el mismo Equipo de trabajo");

			} else {
			
			Optional<EquipoBus> verificar = serviceEquipoBus.buscarPorId(objEquipoBus.getCod_equipo());
			if (verificar.isPresent()) {

				List<EquipoBus> validachofer = serviceEquipoBus.buscarconductorrepetido(objEquipoBus.getCodchofer(),
						objEquipoBus.getCodchofer());
				List<EquipoBus> validacopiloto = serviceEquipoBus.buscarconductorrepetido(objEquipoBus.getCodcopiloto(),
						objEquipoBus.getCodcopiloto());
				List<EquipoBus> condTerramoza = serviceEquipoBus.listarEquipoTerramoza(objEquipoBus.getCodterramoza());

				if (CollectionUtils.isEmpty(validachofer)) {

					if (CollectionUtils.isEmpty(validacopiloto)) {
						if (CollectionUtils.isEmpty(condTerramoza)) {
							EquipoBus equipoUpdate = serviceEquipoBus.registraActualizaEquipo(objEquipoBus);
							if (equipoUpdate == null) {
								salida.put("mensaje", "No se actualizo el bus por un error");
							} else {
								salida.put("mensaje", "El Equipo de trabajo " + objEquipoBus.getCod_equipo()
										+ " se ha actualizado correctamente");
							}

						} else {
							salida.put("mensaje", "La Terramoza :   " + objEquipoBus.getCodterramoza()
									+ " ya forma parte de otro Equipo de trabajo");

						}

					} else {
						salida.put("mensaje", "El copiloto :   " + objEquipoBus.getCodcopiloto()
								+ " ya forma parte de otro Equipo de trabajo");

					}

				} else {

					salida.put("mensaje", "El conductor :   " + objEquipoBus.getCodchofer()
							+ " ya forma parte de otro Equipo de trabajo");
				}

			} else {
				salida.put("mensaje",
						"El Equipo de Trabajo  " + objEquipoBus.getCod_equipo() + " no ha sido creado en el sistema");
			}
			}

		} catch (Exception e) {
			salida.put("mensaje", "Ocurrio un error " + e.getMessage());
		}

		return ResponseEntity.ok(salida);

	}

}
