package br.com.eudora.onlineshop.dao;

import br.com.eudora.onlineshop.dominio.Produto;


public class ProdutoDao extends DaoGenerico<Produto, Long>{
	
	public ProdutoDao() {
		super(Produto.class);
	}

	public Produto encontrarPorCodigo(String codigo) {
		String ql = "FROM Produto WHERE codigo = :c";
		try{
			return (Produto) entityManager.createQuery(ql).setParameter("c", codigo).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

}
