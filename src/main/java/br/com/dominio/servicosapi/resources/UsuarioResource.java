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
import br.com.dominio.servicosapi.dto.UsuarioDTO;
import br.com.dominio.servicosapi.dto.UsuarioNewDTO;
import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll(){
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDto = list.stream().map(obj -> 
	                 new UsuarioDTO(obj)).collect(Collectors.toList()); 
		
		return ResponseEntity.status(HttpStatus.OK).
				body(listDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id){
		
		Usuario entidade = service.findById(id);						
		return ResponseEntity.status(HttpStatus.OK).body(entidade);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<?> find(@PathVariable String email){
		
		Usuario entidade = service.findByEmail(email);						
		return ResponseEntity.status(HttpStatus.OK).body(entidade);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/page")
	public ResponseEntity<Page<UsuarioDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<UsuarioDTO> listDTO = list.map(obj -> new UsuarioDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO usuarioNewDTO) {
		Usuario usuario = service.fromDTO(usuarioNewDTO);
		usuario = service.save(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}	

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO usuarioDTO, @PathVariable("id") Long id) {
		Usuario usuario = service.fromDTO(usuarioDTO);
		usuario.setId(id);
		usuario = service.update(usuario);
		return ResponseEntity.noContent().build();
	}
		
	@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
