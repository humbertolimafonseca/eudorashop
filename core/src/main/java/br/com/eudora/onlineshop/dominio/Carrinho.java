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
		ItemCompra ic = getItem(ip.getProduto().getId().toString());
		if(ic != null ){
			ic.setQuantidade(ic.getQuantidade() + qtd );
		}else{
			this.itens.add(new ItemCompra(ip, qtd));
		}
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

	public void removeItem(String id) {
		getItens().remove(getItem(id));
	}
	
	public ItemCompra getItem(String id){
		for (ItemCompra itemCompra : itens) {
			String idproduto = itemCompra.getProduto().getProduto().getId().toString(); 
			if(idproduto.equals(id)){
				return itemCompra;
			}
		}
		return null;
	}

	public void subtraiItem(String id) {
		ItemCompra ic = getItem(id);
		if(ic.getQuantidade()>1){
			ic.setQuantidade(ic.getQuantidade()-1);
		}else{
			removeItem(id);
		}
	}
	
}
