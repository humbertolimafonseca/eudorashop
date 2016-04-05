package br.com.eudora.onlineshop.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Imagem {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	private String tema;
	private String temaKey;
	private boolean principal;

	public Imagem(String nome, String tema, boolean principal) {
		super();
		this.nome = nome;
		this.tema = tema;
		this.principal = principal;
	}
	
	public Imagem() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getTemaKey() {
		return temaKey;
	}

	public void setTemaKey(String temaKey) {
		this.temaKey = temaKey;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

}
