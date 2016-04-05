package br.com.eudora.onlineshop.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Funcionalidade {
	
	@Id
	private String nome;
	
	@Transient
	private List<String> urls;

}
