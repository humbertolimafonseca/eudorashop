package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.javamoney.moneta.Money;

import br.com.eudora.onlineshop.util.CurrencyUtil;

public class Carrinho {
	private List<ItemCompra> itens;
	private String currency = "BRL";
	
	public Carrinho() {
		itens = new ArrayList<ItemCompra>();
	}
	
	public Money getTotal(){
		Money m = Money.of(new BigDecimal("0"), currency);
		
		for (ItemCompra itemCompra : itens) {
			m = m.add(itemCompra.subtotal());
		}
		
		return m;
	}
	
	public void addItem(ItemProduto ip, int qtd){
		this.itens.add(new ItemCompra(ip, qtd));
	}

	public List<ItemCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
	
	public String getValorTotal(){
		return CurrencyUtil.format(getTotal());
	}
	
}
