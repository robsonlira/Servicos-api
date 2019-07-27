package br.com.dominio.servicosapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.dominio.servicosapi.model.Agendamento;
import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Status;
import br.com.dominio.servicosapi.model.Usuario;

public class AgendamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
	
	private Long id;
	
	private String observacao;	
	private Long servico;
	
	@NotNull(message="Status é obrigatório")
	private Integer status;
	private BigDecimal valor;
	private Long usuario;	
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@NotNull(message="Data é obrigatório")
	private LocalDate dataEvento;
	@NotNull(message="Hora é obrigatório")
	private LocalTime horaEvento;

	public AgendamentoDTO() {}
	
	public AgendamentoDTO(Agendamento agendamento) {
		super();
		this.id = agendamento.getId();
		this.observacao = agendamento.getObservacao();
		this.servico = agendamento.getServico().getId();
		this.status = agendamento.getStatus();
		this.valor = agendamento.getValor();
		this.usuario = agendamento.getUsuario().getId();
		this.dataEvento = LocalDate.parse(agendamento.getData().format(df));
		this.horaEvento = LocalTime.parse(agendamento.getData().format(tf));
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getServico() {
		return servico;
	}

	public void setServico(Long servico) {
		this.servico = servico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public LocalDate getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(LocalDate dataEvento) {
		this.dataEvento = dataEvento;
	}

	public LocalTime getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(LocalTime horaEvento) {
		this.horaEvento = horaEvento;
	}	
	
}
