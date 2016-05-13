package br.com.eudora.onlineshop.resources;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Hibernate;

import br.com.eudora.onlineshop.dominio.Carrinho;
import br.com.eudora.onlineshop.dominio.ItemCompra;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.manager.MarcaManager;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("carrinho")
public class CartResource {
	
	@Context
	private ServletContext context;

	@Context
	private HttpServletRequest request;
	
	ItemProdutoManager itemProdutoManager = CdiUtil.get(ItemProdutoManager.class);
	
	@GET
	public Response getCarrinho(){
		
		Carrinho carrinho = carrinho();
		
		return  Response.ok().entity(carrinho).build();
	}



	private Carrinho carrinho() {
		Carrinho carrinho = (Carrinho) request.getSession(true).getAttribute("carrinho");
		
		if(carrinho == null){
			carrinho = new Carrinho();
			request.getSession().setAttribute("carrinho", carrinho);
		}
		return carrinho;
	}
	
	
	
	@GET
	@Path("/addItem/{item}/{qtd}")
	public Response addItem(@PathParam("item") String id, @PathParam("qtd") String qtd){
		
		ItemProduto ip = itemProdutoManager.encontrar(new Long(id));
		
		int quantidade = new Integer(qtd).intValue();
		
		carrinho().addItem(ip, quantidade);
		
		return Response.ok("Item adicionado ao carrinho!").build();
	}
	
	@GET
	@Path("/limpar")
	public Response limpar(){
		
		carrinho().getItens().clear();
		
		return Response.ok().build();
	}
	

}
