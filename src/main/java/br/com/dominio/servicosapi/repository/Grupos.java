package br.com.dominio.servicosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dominio.servicosapi.model.Grupo;

public interface Grupos extends JpaRepository<Grupo, Long> {

}
