package br.com.eudora.onlineshop.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.eudora.onlineshop.dao.OrderBy;

@Entity
@OrderBy(property="inicio")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Ciclo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Date inicio;
	
	private Date fim;
	
	@ManyToOne
	private Marca marca;

	
	public Ciclo() {
		// TODO Auto-generated constructor stub
	}
	
	public Ciclo(Date inicio, Date fim, Marca m) {
		this.inicio = inicio;
		this.fim = fim;
		this.marca = m;
	}
	
	@Override
	public String toString() {
		return DateFormatUtils.format(inicio, "MM/yyyy");
	}

	public Date getInicio() {
		return inicio;
	}


	public Date getFim() {
		return fim;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}


}
