package br.com.eudora.onlineshop.dominio;

import java.math.BigDecimal;
import java.util.List;

import org.javamoney.moneta.Money;

public class Carrinho {
	private List<ItemCompra> itens;
	
	public Money total(){
		
		return null;
	}
	
	
	public static void main(String[] args) {
		
		Money money = Money.of(BigDecimal.valueOf(12.45), "BRL");
		System.out.println( money.getNumber().doubleValue() );
	}
}
