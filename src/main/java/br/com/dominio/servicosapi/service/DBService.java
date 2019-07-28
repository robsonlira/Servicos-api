package br.com.dominio.servicosapi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private PasswordEncoder pe;
	
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
		
		Usuario usuario = new Usuario(null,"Admin","admin@gmail.com", true, pe.encode("123") );
		Usuario usuario2 = new Usuario(null,"Robson","robson@gmail.com", true, pe.encode("123") );
		Usuario usuario3 = new Usuario(null,"Jose","jose@gmail.com", true, pe.encode("123") );
		Usuario usuario4 = new Usuario(null,"Felipe","felipe@gmail.com", true, pe.encode("123") );
		Usuario usuario5 = new Usuario(null,"Tadeu","tadeu@gmail.com", true, pe.encode("123") );

        usuario.getGrupos().add(grupo1);
        usuario2.getGrupos().add(grupo2);
        usuario3.getGrupos().add(grupo2);
        usuario4.getGrupos().add(grupo2);
        usuarios.saveAll(Arrays.asList(usuario, usuario2, usuario3, usuario4, usuario5));
        //saveAndFlush(usuario);
        
        Agendamento ag1 = new Agendamento(null, LocalDateTime.parse("2019-07-30T10:30:30"), "Observação do serviço",
        		servico1, Status.PENDENTE, servico1.getValor(), usuario2, LocalDateTime.now(), 
    			LocalDate.parse("2019-07-30"), LocalTime.of(10, 30));
        
        Agendamento ag2 = new Agendamento(null, LocalDateTime.parse("2019-07-30T11:40:30"), "Observação do serviço",
        		servico3, Status.PENDENTE, servico3.getValor(), usuario3, LocalDateTime.now(), 
    			LocalDate.parse("2019-07-30"), LocalTime.of(11, 40));
        
        Agendamento ag3 = new Agendamento(null, LocalDateTime.parse("2019-08-02T09:25:30"), "Observação do serviço",
        		servico3, Status.PENDENTE, servico3.getValor(), usuario4, LocalDateTime.parse("2019-07-30T20:24:10"), 
    			LocalDate.parse("2019-08-02"), LocalTime.of(9, 25,30));

        Agendamento ag4 = new Agendamento(null, LocalDateTime.parse("2019-08-02T10:25:30"), "Observação do serviço",
        		servico3, Status.PENDENTE, servico3.getValor(), usuario5, LocalDateTime.parse("2019-07-30T20:24:10"), 
    			LocalDate.parse("2019-08-02"), LocalTime.of(10, 25,30));

        Agendamento ag5 = new Agendamento(null, LocalDateTime.parse("2019-08-02T09:25:30"), "Observação do serviço",
        		servico2, Status.PENDENTE, servico3.getValor(), usuario3, LocalDateTime.parse("2019-07-30T20:24:10"), 
    			LocalDate.parse("2019-08-02"), LocalTime.of(9, 25,30));

        Agendamento ag6 = new Agendamento(null, LocalDateTime.parse("2019-08-05T10:00:00"), "Observação do serviço",
        		servico2, Status.PENDENTE, servico3.getValor(), usuario2, LocalDateTime.parse("2019-07-30T20:24:10"), 
    			LocalDate.parse("2019-08-05"), LocalTime.of(10, 0, 0));

        agendamentos.saveAll(Arrays.asList(ag1, ag2, ag3, ag4, ag5, ag6));
		
	}

}
