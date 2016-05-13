package br.com.eudora.onlineshop.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Avaliacao implements OnlineShopEntity<Long>{

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private Produto produto;
	/**
	 * 0-5
	 */
	private int estrelas;
	
	private String descricao;
	
	@ManyToOne
	private Cliente cliente;
	
	public Avaliacao(int estrelas2, String desc, Cliente c, Produto produto) {
		super();
		this.estrelas = estrelas;
		this.descricao = descricao;
		this.cliente = cliente;	
		this.produto = produto;
	}

	public int getEstrelas() {
		return estrelas;
	}

	public void setEstrelas(int estrelas) {
		this.estrelas = estrelas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getEntityResourceName() {
		return "avaliacao";
	}
	
}
