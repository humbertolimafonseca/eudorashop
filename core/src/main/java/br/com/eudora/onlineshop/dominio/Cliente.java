package br.com.eudora.onlineshop.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario{
	
	private Date dtNascimento;

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	
	
}
