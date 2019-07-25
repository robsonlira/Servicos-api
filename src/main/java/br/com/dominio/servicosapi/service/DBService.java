package br.com.dominio.servicosapi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dominio.servicosapi.model.Grupo;
import br.com.dominio.servicosapi.model.Permissao;
import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Unidade;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Grupos;
import br.com.dominio.servicosapi.repository.Permissoes;
import br.com.dominio.servicosapi.repository.Servicos;
import br.com.dominio.servicosapi.repository.Usuarios;

@Service
public class DBService {
	
	@Autowired
	private Grupos grupos;
	
	@Autowired
	private Permissoes permissoes;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Servicos servicos;
		
	public void instantiateTestDatabase() throws ParseException {
				
		Servico servico1 = new Servico(null,"Serviço 1","Descrição do serviço 1", 30, Unidade.MINUTO, new BigDecimal("5.50"));
		Servico servico2 = new Servico(null,"Serviço 2","Descrição do serviço 2", 25, Unidade.MINUTO, new BigDecimal("3.50"));
		Servico servico3 = new Servico(null,"Serviço 3","Descrição do serviço 3", 40, Unidade.MINUTO, new BigDecimal("7.50"));
				
		Permissao permissao1 = new Permissao(null,"ADMIN");
		Permissao permissao2 = new Permissao(null,"USER");
		
		permissao1 = permissoes.save(permissao1);
		permissao2 = permissoes.save(permissao2);
		
		Grupo grupo1 = new Grupo(null,"Administrador"); 
		Grupo grupo2 = new Grupo(null,"Cliente"); 
			        		
		grupo1.getPermissoes().add(permissao1);
		grupo1.getPermissoes().add(permissao2);
		grupo2.getPermissoes().add(permissao2);

		grupos.saveAll(Arrays.asList(grupo1, grupo2));
		servicos.saveAll(Arrays.asList(servico1, servico2, servico3));
		
		Usuario usuario = new Usuario(null,"Admin","admin@gmail.com", true, "123" );
		
        usuario.getGrupos().add(grupo1);
        usuarios.saveAndFlush(usuario);
		
	}

}
