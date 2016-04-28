package br.com.eudora.onlineshop.dominio;

public class ItemCompra {
	private ItemProduto itemProduto;
	private int quantidade;
	
	public ItemCompra(ItemProduto produto, int quantidade) {
		super();
		this.itemProduto = produto;
		this.quantidade = quantidade;
	}
	
	public ItemProduto getProduto() {
		return itemProduto;
	}
	public void setProduto(ItemProduto produto) {
		this.itemProduto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
