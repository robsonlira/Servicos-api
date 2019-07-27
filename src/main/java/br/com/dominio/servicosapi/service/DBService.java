package br.com.dominio.servicosapi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.model.Grupo;
import br.com.dominio.servicosapi.model.Permissao;
import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Status;
import br.com.dominio.servicosapi.model.Unidade;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Agendamentos;
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
		
	@Autowired
	private Agendamentos agendamentos;
	
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
        
        Agendamento ag1 = new Agendamento();
        Agendamento ag2 = new Agendamento();
        
        ag1.setData(LocalDateTime.now());
        ag1.setObservacao("Observação do serviço");
        ag1.setServico(servico1);
        ag1.setStatus(Status.PENDENTE.getCod());
        ag1.setValor(servico1.getValor());
        ag1.setUsuario(usuario);
        ag1.setDataCriacao(LocalDateTime.now());
        
        ag2.setData(LocalDateTime.now());
        ag2.setServico(servico3);
        ag2.setStatus(Status.PENDENTE.getCod());
        ag2.setValor(servico3.getValor());
        ag2.setUsuario(usuario);
        ag2.setDataCriacao(LocalDateTime.now());

        agendamentos.saveAll(Arrays.asList(ag1, ag2));
		
	}

}
