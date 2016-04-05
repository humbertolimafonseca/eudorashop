package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Entity
public class Produto {
	@Id
	@GeneratedValue
	private Long id;

	private String nome;

	private String descricao;

	@Transient
	private Money valor;

	private BigDecimal preco;

	private String moeda;

	@ManyToMany
	private List<Tag> tags;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
	private List<Avaliacao> avaliacoes;

	@ManyToOne
	private Marca marca;

	@OneToMany
	private List<Imagem> imagens;

	public Produto() {
	}

	public Produto(String nome, String descricao, float preco, String moeda, String imagemPrincipal,
			String... imagens) {
		
		super();
		this.nome = nome;
		this.valor = Money.of(new BigDecimal(preco), moeda);

		for (String img : imagens) {
			addImagem(img, "produto", false);
		}
		addImagem(imagemPrincipal, "produto" , true);

		this.moeda = moeda;
		this.preco = new BigDecimal(preco);
		avaliacoes = new ArrayList<Avaliacao>();
		this.descricao = descricao;
		
	}

	public void addAvaliacao(int estrelas, Cliente c, String desc) {
		this.avaliacoes.add(new Avaliacao(estrelas, desc, c, this));
	}

	public void addImagem(String nome, String tema, boolean principal) {

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

	public Money getValor() {
		return Money.of(preco, moeda);
	}

	public void setValor(Money valor) {
		this.valor = valor;
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

}
