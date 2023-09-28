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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Terramoza;
import com.viajaperu.service.TerramozaService;
import com.viajaperu.utils.AppSettings;
import com.viajaperu.utils.Utilidades;


@RestController
@RequestMapping("/rest/terramoza")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TerramozaController {
	@Autowired
	private TerramozaService terramozaService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Terramoza>> listaTerramoza() {
		List<Terramoza> lista = terramozaService.listaTerramoza();
		return ResponseEntity.ok(lista);
	}
	
	
	@GetMapping("/porcodigo/{codigo}")
	public ResponseEntity<?>terramozaPorCodigo(@PathVariable("codigo")String codigo){	
		return ResponseEntity.ok(terramozaService.buscaTerramoza(codigo));
	}
	
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> insertaTerramoza(@RequestBody Terramoza objTerramoza){
		HashMap<String, String> mensaje = new HashMap<>();
		Utilidades util = new Utilidades();
		String cod = util.generarId(terramozaService.ultimoRegistroString(), "Terramoza");
		
		try {
				List<Terramoza>verificarDni = terramozaService.buscarTerramozaPorDni(objTerramoza.getNumerodocumento());
				if(CollectionUtils.isEmpty(verificarDni)) {
					objTerramoza.setCod_terramoza(cod);
					System.out.println(objTerramoza);
					Terramoza objSalida = terramozaService.insertaTerramoza(objTerramoza);
					if(objSalida == null) {
						mensaje.put("mensaje", "No se pudo realizar el registro");
					}
				else {
					System.out.println(objTerramoza);
					mensaje.put("mensaje", "Se registro correctamente la Terramoza");
				}
				
				}else {
				mensaje.put("mensaje", "El número de DNI: "+ objTerramoza.getNumerodocumento()+" ya está registrado");	
			}
			
			}catch (Exception e) {
			mensaje.put("mensaje", "Ocurrio un error en la conexion " + e.getMessage());
			}
			return ResponseEntity.ok(mensaje);
		}
			

		
		
	
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<?> actualizaTerramoza(@RequestBody Terramoza objTerramoza){
		HashMap<String, String> mensaje = new HashMap<>();
		
		
		//List<Terramoza>verificarDniyCodigo = terramozaService.buscarTerramozaPorDni(objTerramoza.getNumerodocumento());
		//if(CollectionUtils.isEmpty(verificarDni))
		Optional<Terramoza> optTerramoza = terramozaService.buscaTerramoza(objTerramoza.getCod_terramoza());
		
		if (optTerramoza.isPresent()) {
			List<Terramoza>verificarDniyCodigo = terramozaService.buscarTerramozaPorDniyCodigo(objTerramoza.getNumerodocumento(),objTerramoza.getCod_terramoza());
			if(CollectionUtils.isEmpty(verificarDniyCodigo)){
				Terramoza objSalida = terramozaService.actualizaTerramoza(objTerramoza);
				if (objSalida == null) {
					mensaje.put("mensaje", "Error en el registro");
				}else {
					mensaje.put("mensaje", "Se ha actualizado la Terramoza de ID " + objSalida.getCod_terramoza());
				}
	
			}else {
				mensaje.put("mensaje", "El DNI "+ objTerramoza.getNumerodocumento() + " ya se encuentra registrado");	
			}
				
			
		}else {
			mensaje.put("mensaje", "No existe alumno de ID " + objTerramoza.getCod_terramoza());
		}
		return ResponseEntity.ok(mensaje);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> eliminaTerramoza(@PathVariable("id") String cod_terramoza){
		HashMap<String, String> mensaje = new HashMap<>();
		Optional<Terramoza> optTerramoza = terramozaService.buscaTerramoza(cod_terramoza);
		if (optTerramoza.isPresent()) {
			terramozaService.eliminaTerramoza(cod_terramoza);
			mensaje.put("mensaje", "Se ha eliminado la Terramoza de ID " + cod_terramoza);
		}else {
			mensaje.put("mensaje", "No existe la Terramoza de ID " + cod_terramoza);
		}
		return ResponseEntity.ok(mensaje);
	}
	
	
	
	
}
