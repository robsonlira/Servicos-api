package br.com.dominio.servicosapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.model.Usuario;

public interface Agendamentos extends JpaRepository<Agendamento, Long> {
	
	@Transactional(readOnly = true)
	Page<Agendamento> findByUsuario(Usuario usuario, Pageable pageRequest);

}
