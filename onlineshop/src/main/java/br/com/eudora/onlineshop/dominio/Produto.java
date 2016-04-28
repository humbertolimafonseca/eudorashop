package br.com.eudora.onlineshop.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.eudora.onlineshop.dao.OrderBy;

@Entity
@OrderBy(property="nome")
public class Produto {
	@Id
	@GeneratedValue
	private Long id;

	private String nome;

	private String descricao;
	
	private String codigo;
	
	@ManyToMany
	private List<Tag> tags = new ArrayList<Tag>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
	private List<Avaliacao> avaliacoes;

	@ManyToOne
	private Marca marca;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Imagem> imagens = new ArrayList<Imagem>();

	public Produto() {
	}

	public Produto(String nome, String descricao, String codigo, Marca marca , String imagemPrincipal,
			String... imagens) {
		
		super();
		this.nome = nome;
		this.codigo = codigo;

		for (String img : imagens) {
			addImagem(img, "produto", false);
		}
		addImagem(imagemPrincipal, "produto" , true);

		avaliacoes = new ArrayList<Avaliacao>();
		this.descricao = descricao;
		this.marca = marca;
		
	}

	public void addAvaliacao(int estrelas, Cliente c, String desc) {
		this.avaliacoes.add(new Avaliacao(estrelas, desc, c, this));
	}

	public void addImagem(String nome, String tema, boolean principal) {
		if(nome != null && !nome.equals("")){
			if (principal) {
				for (Imagem img : this.imagens) {
					if (img.isPrincipal()) {
						img.setPrincipal(false);
					}
				}
			}
	
			Imagem i = new Imagem(nome, tema, principal);
			imagens.add(i);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public List<Avaliacao> getAvaliacoes() {
		return Collections.unmodifiableList(avaliacoes);
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
