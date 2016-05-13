package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

import br.com.eudora.onlineshop.dao.OrderBy;
import br.com.eudora.onlineshop.util.CurrencyUtil;

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
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="itemProduto")
	private List<PrecoCiclo> precosCiclo;
	
	@Transient
	private String moeda;
	
	public ItemProduto() {
	}

	public ItemProduto(Produto produto, String moeda, String valorCompra, Integer qtd) {
		super();
		this.produto = produto;
		this.custoCompra = new BigDecimal(valorCompra);
		this.quantidade = qtd;
		this.moeda = moeda;
	}
	
	public void addPrecoCiclo(String valor, Ciclo ciclo){
		this.precosCiclo.add(new PrecoCiclo(valor,this.moeda, ciclo));
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

	public void setId(Long id) {
		this.id = id;
	}

	public void addPrecoCiclo(PrecoCiclo pc) {
		if(this.precosCiclo == null){
			this.precosCiclo = new ArrayList<PrecoCiclo>();
		}
		
		pc.setItemProduto(this);
		
		this.precosCiclo.add(pc);
	}
	
	public String getPrecoCicloAtual(){
		Ciclo cicloAtual = getProduto().getMarca().cicloAtual();
		for (PrecoCiclo precoCiclo : precosCiclo) {
			if(precoCiclo.getCiclo().equals(cicloAtual)){
				
				return CurrencyUtil.format( precoCiclo.getPreco() );
			}
		}
		
		throw new RuntimeException("Item de Produto sem preço atual.");
	}
	
	public Money getPrecoAtual(){
		Ciclo cicloAtual = getProduto().getMarca().cicloAtual();
		for (PrecoCiclo precoCiclo : precosCiclo) {
			if(precoCiclo.getCiclo().equals(cicloAtual)){
				
				return  precoCiclo.getPreco();
			}
		}
		
		throw new RuntimeException("Item de Produto sem ciclo Atual.");
	}

	public List<PrecoCiclo> getPrecosCiclo() {
		return precosCiclo;
	}

	public void setPrecosCiclo(List<PrecoCiclo> precosCiclo) {
		this.precosCiclo = precosCiclo;
	}
	
	@Override
	public boolean equals(Object obj) {
		ItemProduto ip = null;

		try {
		
			ip = (ItemProduto) obj;
		
			if (obj == this) {
				return true;
			} else if (ip.getId().equals(this.getId())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	
	public static void main(String[] args) {
		Money m = Money.of(new BigDecimal(2.33), "BRL");
		m.getCurrency().getCurrencyCode();
		
		MonetaryAmountFormat fmt =  MonetaryFormats.getAmountFormat(new Locale("pt","BR"));
		
		
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		
		System.out.println(fmt.format(m));
		System.out.println(format.format(m.getNumber()));
		
		 double amount =200.0;
		 System.out.println(NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(amount));
		
	}

}







