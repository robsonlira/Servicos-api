package br.com.dominio.servicosapi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.dominio.servicosapi.dto.UsuarioDTO;
import br.com.dominio.servicosapi.dto.UsuarioNewDTO;
import br.com.dominio.servicosapi.model.Grupo;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Grupos;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.service.exceptions.DataIntegrityException;
import br.com.dominio.servicosapi.service.exceptions.EmailUsuarioJaCadastradoException;
import br.com.dominio.servicosapi.service.exceptions.ObjectNotFoundException;
//import br.com.dominio.avaliacao.ws.service.exceptions.SenhaObrigatoriaUsuarioException;
import br.com.dominio.servicosapi.service.exceptions.SenhaObrigatoriaUsuarioException;

@Service
public class UsuarioService {
	
	@Autowired
    private Usuarios repository;
	
	@Autowired
    private Grupos grupoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> findAll(){
		return repository.findAll();
	}
	
    public Usuario findById(Long id) {
		
    	//UserSS user = UserService.authenticated();
    	//if (user==null || !user.hasRole("ADMIN") && !id.equals(user.getId())) {
		//	throw new AuthorizationException("Acesso negado");
		//}
    	    	
    	Optional<Usuario> optional = repository.findById(id);
    	
    	return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}
	
    public Usuario findByEmail(String email) {
		//UserSS user = UserService.authenticated();
		//if (user == null || !user.hasRole("ADMIN") && !email.equals(user.getUsername())) {
		//	throw new AuthorizationException("Acesso negado");
		//}
	
		Optional<Usuario> optional = repository.findByEmail(email);

		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Email: " + email + ", Tipo: " + Usuario.class.getName()));

    }
    
		
	@Transactional
	public Usuario save(Usuario entidade) {
		
		Optional<Usuario> optional = repository.findByEmail(entidade.getEmail());
		
		if (optional.isPresent() && !optional.get().equals(entidade)) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if (entidade.isNovo() && StringUtils.isEmpty(entidade.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		//if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
		//	usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		//} else if (StringUtils.isEmpty(usuario.getSenha())) {
		//	usuario.setSenha(usuarioExistente.get().getSenha());
		//}
		
		entidade.setConfirmacaoSenha(entidade.getSenha());
		
		if (!entidade.isNovo() && entidade.getAtivo() == null) {
			entidade.setAtivo(optional.get().getAtivo());
		}
		
		return repository.save(entidade);
	}
	
	@Transactional
	public Usuario update(Usuario entidade) {
		Usuario newObj = findById(entidade.getId());
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
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		Usuario usuario = new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), objDto.getAtivo(), null);
		usuario.setDataNascimento(objDto.getDataNascimento());
		return usuario;
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		// Grupo Usuario
		Grupo grupo = grupoRepository.getOne(2L);
		
		Usuario usuario = new Usuario(null, objDto.getNome(), objDto.getEmail(), true, passwordEncoder.encode(objDto.getSenha()));
		usuario.setDataNascimento(objDto.getDataNascimento());
		usuario.getGrupos().add(grupo);
		return usuario;
	}

	private void updateData(Usuario newObj, Usuario entidade) {
		newObj.setNome(entidade.getNome());
		newObj.setEmail(entidade.getEmail());
	}

}
