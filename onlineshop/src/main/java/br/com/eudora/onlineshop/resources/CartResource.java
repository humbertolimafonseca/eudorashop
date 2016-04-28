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

import br.com.eudora.onlineshop.dominio.ItemCompra;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.dominio.Produto;

@ApplicationPath("/resources")
@Path("cart")
public class CartResource {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<ItemCompra> itens(){
		List<ItemCompra> lista = new ArrayList<ItemCompra>();
		return lista;
	}
	
	@GET
    @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public ItemCompra detalheItem( @PathParam("id") Long id) {
        return new ItemCompra(new ItemProduto(),2);
    }

}
