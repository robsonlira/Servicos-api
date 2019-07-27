package br.com.dominio.servicosapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import br.com.dominio.servicosapi.model.Servico;
import br.com.dominio.servicosapi.model.Usuario;

public class AgendamentoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String observacao;	
	private Servico servico;	
	private BigDecimal valor;
	private Usuario usuario;		
	private LocalDate dataEvento;
	private LocalTime horaEvento;

	public AgendamentoNewDTO() {}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
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
