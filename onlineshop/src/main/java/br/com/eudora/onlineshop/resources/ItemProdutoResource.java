package br.com.eudora.onlineshop.resources;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.DateUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Ciclo;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.dominio.PrecoCiclo;
import br.com.eudora.onlineshop.dominio.Produto;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.manager.MarcaManager;
import br.com.eudora.onlineshop.manager.ProdutoManager;
import br.com.eudora.onlineshop.manager.TagManager;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("item-produto")
public class ItemProdutoResource extends OnlineShopResource<ItemProdutoManager, ItemProduto, Long> {

	@Context
	private ServletContext context;

	@Context
	private HttpServletRequest request;

	ProdutoManager produtoManager = CdiUtil.get(ProdutoManager.class);
	TagManager tagManager = CdiUtil.get(TagManager.class);
	MarcaManager marcaManager = CdiUtil.get(MarcaManager.class);

	@POST
	@Path("/add")
	public Response add(@FormParam("produto") String produto, 
			@FormParam("custo") String custo,
			@FormParam("quantidade") String quantidade,
			@FormParam("precosCiclos") String precosCiclos) {

		try {

			Produto p = produtoManager.encontrarPorCodigo(produto);

			ItemProduto itemProduto = new ItemProduto(p, "BRL", custo, Integer.parseInt(quantidade));
			
//			parse(inicio1), parse(fim1), preco1,
//			parse(inicio2), parse(fim2), preco2,
				
			getManager().salvar(itemProduto);

		} catch (ChaveDuplicadaException e) {
			Response.serverError().entity(e.getMessage()).build();
			e.printStackTrace();
		}

		return Response.ok("Item de Produto criado com sucesso.").build();

	}

	@POST
	@Path("/edit/{id}")
	public Response edit(@PathParam("id") String id, @FormParam("produto") String produto,
			@FormParam("precosCiclos") String precosCiclos,
			@FormParam("custo") String custo, @FormParam("quantidade") String quantidade) 
					throws JsonParseException, JsonMappingException, IOException {
		
		PrecoCiclo[] pcs = new ObjectMapper().readValue(precosCiclos, PrecoCiclo[].class);
		
		ItemProduto item = getManager().encontrar(new Long(id));

		Produto p = produtoManager.encontrarPorCodigo(produto);

		item.setProduto(p);

		item.setCustoCompra(new BigDecimal(custo));
//		item.getPreco1().setInicio(parse(inicio1));
//		item.getPreco1().setFim(parse(fim1));
//		item.getPreco2().setInicio(parse(inicio2));
//		item.getPreco2().setFim(parse(fim2));

		item.setQuantidade(new Integer(quantidade));
		item.getPrecosCiclo().clear();
		
		for (PrecoCiclo pc : pcs) {
			pc.setCiclo( p.getMarca().getCiclos().get(0) );
			pc.setMoeda("BRL");
			item.addPrecoCiclo(pc);
		}

		getManager().atualizar(item);

		return Response.ok("Item de Produto atualizado com sucesso.").build();

	}

	private Date parse(String fim) {
		try {
			return DateUtils.parseDate(fim, new String[] { "dd/MM/yyyy" });
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected String getMensagemDelete(ItemProduto ip) {
		return "Item removido com sucesso. Item: " + ip.getId();
	}

}
