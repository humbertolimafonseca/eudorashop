package br.com.eudora.onlineshop.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {
	
	@Id
	private String nome;

	public Tag(String nome) {
		super();
		this.nome = nome;
	}
	
	public Tag() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	} 
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag tag = (Tag) obj;
			if(tag.nome.equals(this.nome)){
				
			}
		}
		return false;
	}
	
}
