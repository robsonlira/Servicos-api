package br.com.dominio.servicosapi.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private Usuarios repository;
			 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> optional = repository.findByEmail(email);
		
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(email);
		}
		
		Usuario usuario = optional.get();
				
		return new UserSS(usuario.getId().intValue(), usuario.getEmail(), usuario.getSenha(), getPermissoes(usuario));
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
				
		List<String> permissoes = repository.permissoes(usuario);
				
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));				
		
		return authorities;
	}

}
