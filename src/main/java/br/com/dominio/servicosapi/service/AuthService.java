package br.com.dominio.servicosapi.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private Usuarios repository;
	
	@Autowired
	private PasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Optional<Usuario> optional = repository.findByEmail(email);
		if (optional.get() == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		Usuario usuario = optional.get();
		
		String newPass = newPassword();
		usuario.setSenha(pe.encode(newPass));
		
		repository.save(usuario);
		emailService.sendNewPasswordEmail(usuario, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
