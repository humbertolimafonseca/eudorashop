package br.com.eudora.onlineshop.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Perfil {
	
	@Id
	private Long id;
	
	private String nome;
	
	@ManyToMany
	private List<Perfil> subPerfis;
	
	@OneToMany
	private List<Funcionalidade> funcionalidades;
	
	public boolean possuiAcesso(String url){
		return true;
	}

}
