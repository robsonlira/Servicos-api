package br.com.dominio.servicosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dominio.servicosapi.model.Servico;

public interface Servicos extends JpaRepository<Servico, Long> {

}
