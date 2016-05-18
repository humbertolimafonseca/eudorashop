package br.com.eudora.onlineshop.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.javamoney.moneta.Money;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class PrecoCiclo implements Serializable{
	
	private BigDecimal valor;
	
	private String moeda;
	
	@Id
	@ManyToOne( optional=false)
	private Ciclo ciclo;
	
	@Id
	@ManyToOne( optional=false)
	@JsonIgnore
	private ItemProduto itemProduto;
	
	public PrecoCiclo() {
	}

	public PrecoCiclo(String valor, String moeda, Ciclo ciclo) {
		super();
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
		this.ciclo = ciclo;
		
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public Money getPreco() {
		
		return Money.of(this.valor, moeda);
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public ItemProduto getItemProduto() {
		return itemProduto;
	}

	public void setItemProduto(ItemProduto itemProduto) {
		this.itemProduto = itemProduto;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		PrecoCiclo pc = null;

		try {
		
			pc = (PrecoCiclo) obj;
		
			if (obj == this) {
				return true;
			} else if (pc.getCiclo().equals(this.getCiclo()) && pc.getItemProduto().equals(this.getItemProduto())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getCiclo(), getItemProduto());
	}
	
	

}
