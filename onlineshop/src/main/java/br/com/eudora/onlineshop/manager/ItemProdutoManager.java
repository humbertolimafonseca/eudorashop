package br.com.eudora.onlineshop.manager;

import br.com.eudora.onlineshop.dao.ItemProdutoDao;
import br.com.eudora.onlineshop.dominio.Ciclo;
import br.com.eudora.onlineshop.dominio.ItemProduto;

public class ItemProdutoManager extends Manager<ItemProdutoDao, ItemProduto, Long>{

	public void removePrecoCiclo(Ciclo cicloAtual) {
		dao.removePrecoCiclo(cicloAtual);
	}
	
}
