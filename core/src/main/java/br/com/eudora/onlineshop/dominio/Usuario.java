package br.com.eudora.onlineshop.dominio;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique=true, nullable=false)
	private String nome;

	private String email;

	private String senha;
	
	@Transient
	private Perfil perfil;
	
	@Embedded
	private Endereco endereco;
	
	
	public Usuario(String nome, String email, String senha, Endereco endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
	}
	
	public Usuario() {
		// TODO Auto-generated constructor stub
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
