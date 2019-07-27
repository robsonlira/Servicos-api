package br.com.dominio.servicosapi.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dominio.servicosapi.dto.AgendamentoDTO;
import br.com.dominio.servicosapi.dto.AgendamentoNewDTO;
import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Status;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Agendamentos;
import br.com.dominio.servicosapi.repository.Servicos;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.service.exceptions.DataIntegrityException;
import br.com.dominio.servicosapi.service.exceptions.ObjectNotFoundException;

@Service
public class AgendamentoService {
	
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
	
	@Autowired
    private Agendamentos repository;	

	@Autowired
    private Servicos servicoRepository;
	
	@Autowired
    private Usuarios usuarioRepository;	

	
	public List<Agendamento> findAll(){
		return repository.findAll();
	}
	
    public Agendamento findById(Long id) {
		
    	Optional<Agendamento> optional = repository.findById(id);
    	
    	return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Agendamento.class.getName()));
	}
	    	
	@Transactional
	public Agendamento save(Agendamento entidade) {
				
		if (entidade.isNovo()) {
			entidade.setDataCriacao(LocalDateTime.now());
			entidade.setStatus(Status.PENDENTE.getCod());
		}				
				
		return repository.save(entidade);
	}
	
	@Transactional
	public Agendamento update(Agendamento entidade) {
		Agendamento newObj = findById(entidade.getId());
		updateData(newObj, entidade);
		return repository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o registro");
		}
	}
	
	public Page<Agendamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
		
	public Agendamento fromDTO(AgendamentoDTO objDto) {
												
		Agendamento ag = new Agendamento(objDto.getId(), LocalDateTime.parse(
			objDto.getDataEvento().format(df)+"T"+
		    objDto.getHoraEvento().format(tf)+":00"),
			objDto.getObservacao(),
			servicoRepository.getOne(objDto.getServico()),
			Status.toEnum(objDto.getStatus()),
			objDto.getValor(),
			usuarioRepository.getOne(objDto.getUsuario()),
			null,
			objDto.getDataEvento(),
			objDto.getHoraEvento());

		return ag;						
	}
	
	public Agendamento fromDTO(AgendamentoNewDTO objDto) {
						
		Agendamento ag = new Agendamento(null, LocalDateTime.parse(
				objDto.getDataEvento().format(df)+"T"+
		        objDto.getHoraEvento().format(tf)+":00"),
				objDto.getObservacao(),
				objDto.getServico(),
				null,
				objDto.getValor(),
				objDto.getUsuario(),
				null,
				objDto.getDataEvento(),
				objDto.getHoraEvento()); 
		return ag;
	}

	private void updateData(Agendamento newObj, Agendamento entidade) {
		newObj.setObservacao(entidade.getObservacao());
		newObj.setData(LocalDateTime.parse(entidade
				.getDataEvento().format(df)+"T"+entidade
				.getHoraEvento().format(tf)+":00"));
		newObj.setDataEvento(entidade.getDataEvento());
		newObj.setHoraEvento(entidade.getHoraEvento());
		newObj.setStatus(entidade.getStatus());
		
		
	}

}
