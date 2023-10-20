package com.viajaperu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.repository.CombustibleRepository;
import com.viajaperu.repository.LicenciaRepository;
import com.viajaperu.repository.ServicioRepository;
import com.viajaperu.repository.TipoDocumentoRepository;
import com.viajaperu.service.BoletoService;
import com.viajaperu.service.PagoService;
import com.viajaperu.service.VentaBoletoService;
import com.viajaperu.utils.AppSettings;

@RestController()
@RequestMapping("/rest/util")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class UtilController {

	@Autowired
	private CombustibleRepository repoCombu;
	
	@Autowired
	private ServicioRepository repoServicio;
	
	
	@Autowired
	private LicenciaRepository repoLicencia;
	
	@Autowired
	private TipoDocumentoRepository repotipoDoc;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private VentaBoletoService vbService;
	
	@Autowired
	private PagoService pService;
	
	
	@GetMapping("/lista/licencia")
	public ResponseEntity<?>listaLicencia(){
		return ResponseEntity.ok(repoLicencia.findAll());
		
	}
	@GetMapping("/lista/tipodoc")
	public ResponseEntity<?>listaTipoDocumento(){
		return ResponseEntity.ok(repotipoDoc.findAll());
		
	}
	
	
	@GetMapping("/lista/combustible")
	public ResponseEntity<?>listaCombustible(){
		
		return ResponseEntity.ok(repoCombu.findAll());
	}
	
	
	@GetMapping("/lista/servicio")
	public ResponseEntity<?>listaServicios(){
		
		return ResponseEntity.ok(repoServicio.findAll());
	}
	
	@GetMapping("/lista/boletos/{codigo}")
	public ResponseEntity<?>listarBoletosVendidos(@PathVariable("codigo") String codigo){
		return ResponseEntity.ok(boletoService.listarBoletosVendidos(codigo));
	}
	
	@GetMapping("/lista/ventas")
	public ResponseEntity<?>listarVentas(){
		return ResponseEntity.ok(vbService.listarVentas());
	}
	
	@GetMapping("/listaventa/{codigo}")
	public ResponseEntity<?>ventasPorCodigo(@PathVariable("codigo")String codigo){
		return ResponseEntity.ok(pService.buscarPagoPorCodigo(codigo));
	}
	
}
