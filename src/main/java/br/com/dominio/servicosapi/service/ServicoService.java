package br.com.dominio.servicosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.repository.Servicos;
import br.com.dominio.servicosapi.service.exceptions.DataIntegrityException;
import br.com.dominio.servicosapi.service.exceptions.ObjectNotFoundException;

@Service
public class ServicoService {
	
	@Autowired
	private Servicos repository;
	
	public List<Servico> findAll() {
		return repository.findAll();
	}
	
	public Servico findById(Long id) {
		Optional<Servico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Servico.class.getName()));
	}
	
	@Transactional
	public Servico update(Servico obj) {
		Servico newObj = findById(obj.getId());
		//updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}
	
	@Transactional
	public Servico salvar(Servico entidade) {
		
		return repository.saveAndFlush(entidade);
	}

}
