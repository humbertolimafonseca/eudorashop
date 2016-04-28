package br.com.eudora.onlineshop.resources;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.eudora.onlineshop.manager.Manager;
import tarefas.CdiUtil;

public class OnlineShopResource<M extends Manager<?,T,I>, T , I extends Serializable> {
	
	private M manager;
	
	@SuppressWarnings("unchecked")
	public OnlineShopResource() {
		Class<M> managerClass = (Class<M>) ((ParameterizedType) getClass()
	            .getGenericSuperclass()).getActualTypeArguments()[0];
		
		this.manager = CdiUtil.get(managerClass);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/{id}")
	public Response load(@PathParam("id") String id) {
		Class<I> managerClass = (Class<I>) ((ParameterizedType) getClass()
	            .getGenericSuperclass()).getActualTypeArguments()[2];
		
		I key = null;
		
		if (managerClass.equals(Long.class)) {
			key = ((I) new Long(id));
		} else if (managerClass.equals(String.class)) {
			key = (I) id ;
		}
		
		T obj = manager.encontrar(key);
		
		return Response.ok().entity( obj ).build();
	}
	
	
	@GET
	public Response list() {

		return Response.ok( manager.getList()).build();
	}
	
	
	public static void main(String[] args) {
		ItemProdutoResource o = new ItemProdutoResource();
	}



	public M getManager() {
		return manager;
	}

}
