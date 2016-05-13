package br.com.eudora.onlineshop.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.eudora.onlineshop.dao.OrderBy;

@Entity
@OrderBy(property="nome")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Marca implements OnlineShopEntity<Long>{
	public static final String MARCA_IMAGE_FOLDER = "marca";
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	private String nome;
	private String descricao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@javax.persistence.OrderBy("inicio ASC")
	private List<Ciclo> ciclos; 
	
	@OneToOne(cascade = CascadeType.ALL)
	private Imagem logomarca;
	
	public Marca() {
	}
	
	public Marca(String nome, String descricao, String logomarca) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.logomarca = new Imagem(logomarca, getEntityResourceName(), true);
	}
	
	public void addCiclo(Date inicio, Date fim){
		
		this.getCiclos().add(new Ciclo(inicio, fim));
	}
	
	public void removeCiclo(Ciclo c){
		this.getCiclos().remove(c);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Imagem getLogomarca() {
		return logomarca;
	}

	public void setLogomarca(Imagem logomarca) {
		this.logomarca = logomarca;
	}
	
	public void criaLogomarca(String logomarca) {
		this.logomarca = new Imagem( logomarca , getEntityResourceName(), true);
		this.logomarca.setTemaKey(id.toString());
	}

	public Long getId() {
		return id;
	}



	public String getEntityResourceName() {
		return "marca";
	}



	public List<Ciclo> getCiclos() {
		if(ciclos == null){
			ciclos = new ArrayList<Ciclo>();
		}
		return ciclos;
	}



	public void setCiclos(List<Ciclo> ciclos) {
		this.ciclos = ciclos;
	}



	public void addCiclo(Ciclo ciclo) {
		this.getCiclos().add(ciclo);
	}
	
	
	public Ciclo cicloAtual(){
		for (Ciclo ciclo : ciclos) {
			Date hoje = new Date();
			if(hoje.after(ciclo.getInicio()) && hoje.before(ciclo.getFim())){
				return ciclo;
			}
		}
		
		return null;
	}




}
