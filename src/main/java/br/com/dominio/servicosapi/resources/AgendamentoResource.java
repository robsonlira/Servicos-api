package br.com.dominio.servicosapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.dominio.servicosapi.dto.AgendamentoDTO;
import br.com.dominio.servicosapi.dto.AgendamentoNewDTO;
import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.service.AgendamentoService;

@RestController
@RequestMapping(value = "/agendamentos")
public class AgendamentoResource {
	
	@Autowired
	private AgendamentoService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<AgendamentoDTO>> findAll(){
		
		List<Agendamento> list = service.findAll();
		List<AgendamentoDTO> listDto = list.stream().map(obj -> 
		            new AgendamentoDTO(obj)).collect(Collectors.toList()); 
		
		return ResponseEntity.status(HttpStatus.OK).
				body(listDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id){
		
		Agendamento entidade = service.findById(id);						
		return ResponseEntity.status(HttpStatus.OK).body(entidade);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<AgendamentoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="data") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Agendamento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AgendamentoDTO> listDTO = list.map(obj -> new AgendamentoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody AgendamentoNewDTO agendamentoNewDTO) {
		Agendamento agendamento = service.fromDTO(agendamentoNewDTO);
		agendamento = service.save(agendamento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(agendamento.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}	

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AgendamentoDTO agendamentoDTO, @PathVariable("id") Long id) {
		Agendamento agendamento = service.fromDTO(agendamentoDTO);
		agendamento.setId(id);
		agendamento = service.update(agendamento);
		return ResponseEntity.noContent().build();
	}
			
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
