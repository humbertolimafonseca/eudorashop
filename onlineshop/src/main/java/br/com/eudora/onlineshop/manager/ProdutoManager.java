package br.com.eudora.onlineshop.manager;

import java.util.List;

import br.com.eudora.onlineshop.dao.ProdutoDao;
import br.com.eudora.onlineshop.dominio.Produto;

public class ProdutoManager extends Manager<ProdutoDao, Produto, Long>{

	public Produto encontrarPorCodigo(String codigo) {
		Produto p = new Produto();
		p.setCodigo(codigo);
		List<Produto> l = dao.encontrar(p);
		
		if(l.size() != 1){
			return null;
		}
		
		return l.get(0);
	}
	
}
