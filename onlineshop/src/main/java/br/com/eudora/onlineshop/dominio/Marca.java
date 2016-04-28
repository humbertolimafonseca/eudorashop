package br.com.eudora.onlineshop.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.eudora.onlineshop.dao.OrderBy;

@Entity
@OrderBy(property="nome")
public class Marca implements OnlineShopEntity<Long>{
	public static final String MARCA_IMAGE_FOLDER = "marca";
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	private String nome;
	private String descricao;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Imagem logomarca;
	
	public Marca() {
	}
	
	

	public Marca(String nome, String descricao, String logomarca) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.logomarca = new Imagem(logomarca, MARCA_IMAGE_FOLDER, true);
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
		this.logomarca = new Imagem( logomarca , MARCA_IMAGE_FOLDER, true);
		this.logomarca.setTemaKey(id.toString());
	}

	public Long getId() {
		return id;
	}



	@Override
	public String getEntityResourceName() {
		return "marca";
	}

}
