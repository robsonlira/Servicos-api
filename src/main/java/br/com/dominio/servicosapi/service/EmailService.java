package br.com.dominio.servicosapi.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.dominio.servicosapi.model.Usuario;

public interface EmailService {	
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
}
