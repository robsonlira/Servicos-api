package br.com.dominio.servicosapi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "servico")
public class Servico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@NotBlank(message = "Nome é obrigatório")
	@Column(name="nome", length = 60, unique = true)
	private String nome;
	
	@Column(name="descricao")
	private String descricao;

	@NotNull(message="Duração é obrigatório")
	@Min(value = 1, message = "Duração não pode ser menor que 1" )
	@Column(name="duracao")
	private Integer duracao;
	
	@NotNull(message="Unidade é obrigatório")	
	@Column(name="id_unidade")
	private Integer unidade;

	@NotNull(message="Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@Column(name="valor", precision=14, scale=2)
	private BigDecimal valor;

	
	public Servico() {		
	}
		
	public Servico(Long id, String nome, String descricao, Integer duracao, Unidade unidade, BigDecimal valor) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.duracao = duracao;
		this.unidade = unidade.getCod();
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Unidade getIdUnidade() {
		return Unidade.toEnum(unidade);
	}

	public void setIdUnidade(Unidade unidade) {
		this.unidade = unidade.getCod();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
