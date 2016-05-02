package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class PrecoCiclo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Date inicio;
	
	private Date fim;
	
	private BigDecimal valor;
	
	private String moeda;
	
	@ManyToOne
	private Ciclo ciclo;
	
	public PrecoCiclo() {
	}

	public PrecoCiclo(Date inicio, Date fim, String valor, String moeda) {
		super();
		
		this.inicio = inicio;
		this.fim = fim;
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
		this.valor = new BigDecimal(valor);
		this.moeda = moeda;
		
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
		
		return Money.of(this.valor, moeda);
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
	
	

}
