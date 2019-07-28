package br.com.dominio.servicosapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	@Transactional(readOnly = true)
	public Optional<Usuario> findByEmail(String email);
}
