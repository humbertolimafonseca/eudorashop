package br.com.eudora.onlineshop.dominio;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

	private String logradouro;
	private String cep;
	private String endereco;
	private int numero;
	private String complemento;

	public Endereco(String logradouro, String cep, String endereco, int numero, String complemento) {
		super();
		this.logradouro = logradouro;
		this.cep = cep;
		this.endereco = endereco;
		this.numero = numero;
		this.complemento = complemento;
	}

	public Endereco() {
		super();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
