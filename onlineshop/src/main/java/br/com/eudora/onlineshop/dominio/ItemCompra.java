package br.com.eudora.onlineshop.dominio;

import org.javamoney.moneta.Money;

import br.com.eudora.onlineshop.util.CurrencyUtil;

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
	
	public Money subtotal(){
		return itemProduto.getPrecoAtual().multiply(this.quantidade);
	}
	
	public String getValorSubtotal(){
		return CurrencyUtil.format( itemProduto.getPrecoAtual().multiply(this.quantidade) );
	}
}
