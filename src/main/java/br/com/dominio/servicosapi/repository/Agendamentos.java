package br.com.dominio.servicosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dominio.servicosapi.model.Agendamento;

public interface Agendamentos extends JpaRepository<Agendamento, Long> {

}
