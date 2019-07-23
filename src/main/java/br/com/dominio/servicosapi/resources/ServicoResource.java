package br.com.dominio.servicosapi.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.service.ServicoService;

@RestController
@RequestMapping(value = "/servicos")
public class ServicoResource {
	
	@Autowired
	private ServicoService service;
	
	@GetMapping
	public ResponseEntity<List<Servico>> listar(){
		
		return ResponseEntity.status(HttpStatus.OK).
				body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id){
		
		Servico entidade = service.findById(id);						
		return ResponseEntity.status(HttpStatus.OK).body(entidade);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Servico servico) {
		servico = service.salvar(servico);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(servico.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Servico servico, @PathVariable("id") Long id) {
		servico.setId(id);
		service.update(servico);
		return ResponseEntity.noContent().build();
	}
	
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}