package com.viajaperu.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;

@Service
@Transactional
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	PdfService pdfService;
	
	@Value("${spring.mail.username}")
	private String email;
	
	public void senderListEmail(String emailTo) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			File file = pdfService.generatePlacesPdf();
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Listado de boletos");
			helper.setText("Estimado cliente le adjuntamos su boleta con mas detalles sobre su transaccion");
			helper.addAttachment("Cdp.pdf", file);
			javaMailSender.send(message);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}