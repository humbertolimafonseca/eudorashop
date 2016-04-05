package br.com.eudora.onlineshop.manager;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dao.DaoGenerico;

public abstract class Manager<D extends DaoGenerico<T,I>, T, I extends Serializable> {

	@Inject
	protected D dao;

	public T salvar(T entity) throws ChaveDuplicadaException {
		return dao.salvar(entity);
	}

	public T atualizar(T entity) {
		return dao.atualizar(entity);
	}

	public void remover(I id) {
		dao.remover(id);
	}

	public List<T> getList() {
		return dao.getList();
	}

	public T encontrar(I id) {
		return dao.encontrar(id);
	}
	
	public List<T> encontrar(T entity) {
		return dao.encontrar(entity);
	}
	
	
	
}
