package com.viajaperu.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.creditcard.TarjetaCredito;
import com.viajaperu.creditcard.TarjetaCreditoService;
import com.viajaperu.models.Pago;
import com.viajaperu.models.VentaBoleto;
import com.viajaperu.models.VentaRequest;
import com.viajaperu.service.ClienteService;
import com.viajaperu.service.EmailService;
import com.viajaperu.service.PagoService;
import com.viajaperu.service.PdfService;
import com.viajaperu.service.TransaccionService;
import com.viajaperu.service.VentaBoletoService;
import com.viajaperu.utils.AppSettings;

import jakarta.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/rest/transaccion")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TransaccionController {

	@Autowired
	private TransaccionService service;
	
	@Autowired
	private VentaBoletoService vbService;
	
	@Autowired
	private TarjetaCreditoService tcService;
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired PagoService pservice;
	
	@PostMapping()
	public ResponseEntity<?>registrar(@RequestBody VentaRequest request){
		
		HashMap<String, Object> salida = new HashMap<>();
		
		try {
			List<TarjetaCredito>tarjeta_existe = tcService.verificarTarjetaPorNumero(request.getPago().getNumero_tarjeta());
			if(CollectionUtils.isEmpty(tarjeta_existe)) {
				salida.put("mensaje", "Error: El numero de tarjeta proporcionado no existe");
			}
			else {
				
				if(tarjeta_existe.get(0).getCredito()<request.getVentaBoleto().getPrecioTotal()) {
					salida.put("mensaje", "Error: El saldo en la tarjeta es insuficiente");						
				}
				else {
					Pago pago_registro = service.registrarPago2(request.getVentaBoleto(),request.getPasajeros(),request.getBoletos(),request.getCliente(),request.getPago());
						
					if(pago_registro == null) {
						salida.put("mensaje", "Error: No se pudo concetrar su pago");
					}
					else {
						salida.put("mensaje", "Se registro el pago correctamente");
						//RESTRAR EL CREDITO AL CLIENTE
						tarjeta_existe.get(0).setCredito(tarjeta_existe.get(0).getCredito()-request.getVentaBoleto().getPrecioTotal());
						tcService.actualizarRegistrar(tarjeta_existe.get(0));
						
						//AGREGAR EL CREDITO A VIAJA PERU
						List<TarjetaCredito>cuenta_viajaperu = tcService.verificarTarjetaPorNumero(AppSettings.NUMERO_TARJETA);
						cuenta_viajaperu.get(0).setCredito(cuenta_viajaperu.get(0).getCredito() + request.getVentaBoleto().getPrecioTotal());
						tcService.actualizarRegistrar(cuenta_viajaperu.get(0));
						
					}			
				}						
			}
				
		} catch (Exception e) {
			salida.put("mensaje", "No se pudo conectar a la BD");
		}
		
		return ResponseEntity.ok(salida);
	}
	
	
	@PostMapping("/ventaBoleto")
	public ResponseEntity<?>registraVentaBoleto(@RequestBody VentaBoleto objVenta){
		
		vbService.registarVentaBoleto(objVenta);
		
		return ResponseEntity.ok("Se realizo la operacion");
	}
	
	@GetMapping("/vertarjeta/{numero}")
	public ResponseEntity<?>verificarTarjeta(@PathVariable("numero")String numero){
		
		return ResponseEntity.ok(tcService.verificarTarjetaPorNumero(numero));
	}
	
	@PutMapping("/actualizatarjeta")
	public ResponseEntity<?>actualizarTarjeta(@RequestBody TarjetaCredito obj){
		HashMap<String, String>salida = new HashMap<>();
		
		try {
			List<TarjetaCredito>ubicar_tarjeta=tcService.verificarTarjetaPorNumero(obj.getNumero());
			
			if(CollectionUtils.isEmpty(ubicar_tarjeta)) {
				salida.put("mensaje", "Error: La tarjeta de credito no existe");
			}
			else {
				
				Pago encontrarPago = pservice.buscarPagoPorNumero(obj.getNumero());				
				//ACTUALIZAR EL CREDITO DE LA TARJETA
				obj.setCredito(obj.getCredito()-encontrarPago.getVenta().getPrecioTotal());
				tcService.actualizarRegistrar(obj);
				salida.put("mensaje", "El saldo de la tarjeta fue actualizado");
				
			}
			
		} catch (Exception e) {
			salida.put("mensaje", "Error: No se pudo conectar al sistema");
		}
		
		return ResponseEntity.ok(salida);
	}
	
	
	@GetMapping("/export")
	public void descargarPDF(HttpServletResponse response) {
		try {
			
			Path file = Paths.get(pdfService.generatePlacesPdf("placesPDF").getAbsolutePath());
			if(Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename"+ file.getFileName());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//DESCARGAR ARCHIVO DE LA FACTURA
	@GetMapping("/export/factura")
	public void descargarFactura(HttpServletResponse response) {
		try {
			
			Path file = Paths.get(pdfService.generatePlacesPdf("facturaPDF").getAbsolutePath());
			if(Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename"+ file.getFileName());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/email/{correo}")
	public void enviarEmail(@PathVariable("correo")String correo) {
		this.emailService.senderListEmail(correo);
	}
	
	//ENVIAR CORREO DE LA FACTURA
	@GetMapping("/email/factura/{correo}")
	public void enviarEmailFactura(@PathVariable("correo")String correo) {
		this.emailService.senderFacturaEmail(correo);
	}
	
	@GetMapping("/cliente/{numero}")
	public ResponseEntity<?>buscarPorDocumento(@PathVariable("numero")String numero){
		
		return ResponseEntity.ok(clienteService.clientPorDocument(numero));
	}
	
	
}
