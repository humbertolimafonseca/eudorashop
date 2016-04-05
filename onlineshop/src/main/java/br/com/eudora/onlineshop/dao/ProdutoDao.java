package br.com.eudora.onlineshop.dao;

import br.com.eudora.onlineshop.dominio.Produto;

public class ProdutoDao extends DaoGenerico<Produto, Long>{
	
	public ProdutoDao() {
		super(Produto.class);
	}

}
