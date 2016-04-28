package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.eudora.onlineshop.dao.OrderBy;

/**
 * Representa o item de um produto
 * 
 * @author 03435691409
 *
 */
@Entity
@OrderBy(property="id")
public class ItemProduto implements OnlineShopEntity<Long>{
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Produto produto;

	private BigDecimal custoCompra;
	
	private Integer quantidade;
	
	@Embedded
	@AttributeOverrides( {
		@AttributeOverride(name="inicio", column = @Column
		(name ="inicio1")),
		@AttributeOverride(name="moeda", column = @Column
		(name ="moeda1")),

		@AttributeOverride(name="valor", column = @Column
		(name ="valor1")),
		@AttributeOverride(name="fim",
		column = @Column(name = "fim1"))
	}) 
	private PrecoPeriodo preco1;
	
	@Embedded
	@AttributeOverrides( {
		@AttributeOverride(name="inicio", column = @Column
		(name ="inicio2")),
		@AttributeOverride(name="valor", column = @Column
		(name ="valor2")),
		@AttributeOverride(name="moeda", column = @Column
		(name ="moeda2")),
		@AttributeOverride(name="fim",
		column = @Column(name = "fim2"))
	}) 
	private PrecoPeriodo preco2;
	
	
	public ItemProduto() {
	}

	public ItemProduto(Produto produto, Date inicio, Date fim, String valor, String moeda,Date inicio2, Date fim2, String valor2, String valorCompra, Integer qtd) {
		super();
		this.produto = produto;
		this.preco1 = new PrecoPeriodo(inicio, fim, valor, moeda);
		this.preco2 = new PrecoPeriodo(inicio2, fim2, valor2, moeda);
		this.custoCompra = new BigDecimal(valorCompra);
		this.quantidade = qtd;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEntityResourceName() {
		return "itemProduto";
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getCustoCompra() {
		return custoCompra;
	}

	public void setCustoCompra(BigDecimal custoCompra) {
		this.custoCompra = custoCompra;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public PrecoPeriodo getPreco1() {
		return preco1;
	}

	public void setPreco1(PrecoPeriodo preco1) {
		this.preco1 = preco1;
	}

	public PrecoPeriodo getPreco2() {
		return preco2;
	}

	public void setPreco2(PrecoPeriodo preco2) {
		this.preco2 = preco2;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
