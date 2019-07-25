package br.com.dominio.servicosapi.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UsuarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Nome é obrigatório")
	@Length(min=3, max=80, message="O tamanho deve ser entre 3 e 80 caracteres")
	@Column(name="nome", length = 80)
	private String nome;

	@NotEmpty(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Column(name="email", length = 60, unique = true)
	private String email;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String senha;
	
	private String confirmacaoSenha;
	private Date dataNascimento;
	
	public UsuarioNewDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	
}
