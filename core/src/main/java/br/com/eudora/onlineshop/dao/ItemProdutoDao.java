package br.com.eudora.onlineshop.dao;

import br.com.eudora.onlineshop.dominio.Ciclo;
import br.com.eudora.onlineshop.dominio.ItemProduto;

public class ItemProdutoDao extends DaoGenerico<ItemProduto, Long>{

	public ItemProdutoDao() {
		super(ItemProduto.class);
	}
	
	public void removePrecoCiclo(Ciclo cicloAtual) {
		//remover os precos dos items que possuem esse ciclo;
		String ql = "DELETE FROM PrecoCiclo WHERE ciclo_id = :c";

		entityManager.createQuery(ql).setParameter("c", cicloAtual.getId()).executeUpdate();
		
	}


}
