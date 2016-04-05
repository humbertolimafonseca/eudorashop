package br.com.eudora.onlineshop.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import br.com.eudora.onlineshop.dominio.Item;
import br.com.eudora.onlineshop.dominio.Produto;

@ApplicationPath("/resources")
@Path("cart")
public class CartResource extends Application{
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Item> itens(){
		List<Item> lista = new ArrayList<Item>();
		lista.add(new Item(new Produto("Livro 1","desc", 10.00f,"BRL", "img/livro.jpg"), 2));
		lista.add(new Item(new Produto("Livro 2","desc", 50.00f,"BRL", "img/livro.jpg"), 2));
		lista.add(new Item(new Produto("Livro 3","desc", 30.00f,"BRL", "img/livro.jpg"), 2));
		lista.add(new Item(new Produto("Livro 4","desc", 40.00f,"BRL", "img/livro.jpg"), 2));
		
		return lista;
	}
	
	@GET
    @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Item detalheItem( @PathParam("id") Long id) {
        return new Item(new Produto("Livro " + id,"desc", 10.00f, "BRL", "img/livro.jpg"),2);
    }

}
