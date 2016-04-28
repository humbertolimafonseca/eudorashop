package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Embeddable
public class PrecoPeriodo {
	
	private Date inicio;
	
	private Date fim;
	
	private BigDecimal valor;
	
	private String moeda;
	
	@Transient
	private Money preco;
	
	public PrecoPeriodo() {
		// TODO Auto-generated constructor stub
	}

	public PrecoPeriodo(Date inicio, Date fim, String valor, String moeda) {
		super();
		
		this.inicio = inicio;
		this.fim = fim;
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
		this.preco = Money.of(this.valor, moeda);
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
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
		
		if(preco == null){
			this.preco = Money.of(this.valor, moeda);
		}
		
		return preco;
	}

	public void setPreco(Money preco) {
		this.preco = preco;
	}
	
	

}
