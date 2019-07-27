package br.com.dominio.servicosapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "agendamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Agendamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	@NotNull(message="Data e hora obrigatório")
	@Column(name = "data")
	private LocalDateTime data;
	
	private String observacao;
	
	@NotEmpty(message = "Serviço é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_servico")
	private Servico servico;

	@Column(name="id_status")
	private Integer status;
	
	@Column(name="valor", precision=14, scale=2)
	private BigDecimal valor;

	@NotEmpty(message = "Usuário é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;	
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@Transient
	private LocalDate dataEvento;

	@Transient
	private LocalTime horaEvento;
	
	public Agendamento() {		
	}
				
	public Agendamento(Long id, LocalDateTime data, String observacao, Servico servico, 
			Status status, BigDecimal valor, Usuario usuario, LocalDateTime dataCriacao, 
			LocalDate dataEvento, LocalTime horaEvento) {
		super();
		this.id = id;
		this.data = data;
		this.observacao = observacao;
		this.servico = servico;
		this.status = status.getCod();
		this.valor = valor;
		this.usuario = usuario;
		this.dataCriacao = dataCriacao;
		this.dataEvento = dataEvento;
		this.horaEvento = horaEvento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isNovo() {
		return id == null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
