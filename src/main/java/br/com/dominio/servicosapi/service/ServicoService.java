package br.com.dominio.servicosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Unidade;
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
	public Servico save(Servico entidade) {
		entidade.setId(null);
		return repository.saveAndFlush(entidade);
	}

	@Transactional
	public Servico update(Servico entidade) {
		Servico newObj = findById(entidade.getId());
		updateData(newObj, entidade);
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
	

	public Page<Servico> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	private void updateData(Servico newObj, Servico entidade) {
		newObj.setNome(entidade.getNome());
		newObj.setDuracao(entidade.getDuracao());
		newObj.setDescricao(entidade.getDescricao());
		newObj.setValor(entidade.getValor());
		newObj.setUnidade(entidade.getUnidade());
        		
	}
}
