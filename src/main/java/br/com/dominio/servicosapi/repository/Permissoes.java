package br.com.dominio.servicosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dominio.servicosapi.model.Permissao;

public interface Permissoes extends JpaRepository<Permissao, Long> {

}
