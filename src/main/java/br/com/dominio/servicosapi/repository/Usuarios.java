package br.com.dominio.servicosapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {

	@Transactional(readOnly = true)
	public Optional<Usuario> findByEmail(String email);
}
