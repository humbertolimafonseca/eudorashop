package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<PrecoCiclo> precosCiclo;
	
	@Transient
	private String moeda;
	
//	@Embedded
//	@AttributeOverrides( {
//		@AttributeOverride(name="inicio", column = @Column
//		(name ="inicio1")),
//		@AttributeOverride(name="moeda", column = @Column
//		(name ="moeda1")),
//
//		@AttributeOverride(name="valor", column = @Column
//		(name ="valor1")),
//		@AttributeOverride(name="fim",
//		column = @Column(name = "fim1"))
//	}) 
//	private PrecoCiclo preco1;
//	
//	@Embedded
//	@AttributeOverrides( {
//		@AttributeOverride(name="inicio", column = @Column
//		(name ="inicio2")),
//		@AttributeOverride(name="valor", column = @Column
//		(name ="valor2")),
//		@AttributeOverride(name="moeda", column = @Column
//		(name ="moeda2")),
//		@AttributeOverride(name="fim",
//		column = @Column(name = "fim2"))
//	}) 
//	private PrecoCiclo preco2;
	
	
	public ItemProduto() {
	}

	public ItemProduto(Produto produto, String moeda, String valorCompra, Integer qtd) {
		super();
		this.produto = produto;
//		this.preco1 = new PrecoCiclo(inicio, fim, valor, moeda);
//		this.preco2 = new PrecoCiclo(inicio2, fim2, valor2, moeda);
		this.custoCompra = new BigDecimal(valorCompra);
		this.quantidade = qtd;
		this.moeda = moeda;
	}
	
	public void addPrecoCiclo(Date inicio, Date fim, String valor){
		this.precosCiclo.add(new PrecoCiclo(inicio, fim, valor,this.moeda));
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

//	public PrecoCiclo getPreco1() {
//		return preco1;
//	}
//
//	public void setPreco1(PrecoCiclo preco1) {
//		this.preco1 = preco1;
//	}
//
//	public PrecoCiclo getPreco2() {
//		return preco2;
//	}
//
//	public void setPreco2(PrecoCiclo preco2) {
//		this.preco2 = preco2;
//	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addPrecoCiclo(PrecoCiclo pc) {
		this.precosCiclo.add(pc);
	}

	public List<PrecoCiclo> getPrecosCiclo() {
		return precosCiclo;
	}

	public void setPrecosCiclo(List<PrecoCiclo> precosCiclo) {
		this.precosCiclo = precosCiclo;
	}

}
