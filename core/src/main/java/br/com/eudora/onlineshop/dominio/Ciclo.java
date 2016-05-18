package br.com.eudora.onlineshop.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

import br.com.eudora.onlineshop.dao.OrderBy;

@Entity
@OrderBy(property = "inicio")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ciclo {

	@Id
	@GeneratedValue
	private Long id;

	private Date inicio;

	private Date fim;

	// @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	// private List<PrecoCiclo> precoCiclos;

	public Ciclo() {
	}

	public Ciclo(Date inicio, Date fim) {
		this.inicio = inicio;
		this.fim = fim;
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

	@Override
	public boolean equals(Object obj) {
		Ciclo c = null;

		try {
		
			c = (Ciclo) obj;
		
			if (obj == this) {
				return true;
			} else if (c.getId() != null && c.getId().equals(this.getId())) {
				return true;
			} else if (c.getInicio().getTime() == this.getInicio().getTime()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(inicio);
	}

	// public List<PrecoCiclo> getPrecoCiclos() {
	// if(this.precoCiclos == null){
	// this.precoCiclos = new ArrayList<PrecoCiclo>();
	// }
	// return precoCiclos;
	// }
	//
	// public void setPrecoCiclos(List<PrecoCiclo> precoCiclos) {
	// this.precoCiclos = precoCiclos;
	// }

}