package br.com.eudora.onlineshop.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Tag;
import br.com.eudora.onlineshop.manager.TagManager;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("tag")
public class TagResource extends Application {

	TagManager manager = CdiUtil.get(TagManager.class);

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("nome") String nome) {

		try {
			manager.salvar(new Tag(nome));
		} catch (ChaveDuplicadaException e) {
			e.printStackTrace();
			return Response.serverError().entity("Tag com o mesmo nome já criada.").build();
		}

		return Response.ok("Tag criada com sucesso!").build();
	}

	@GET
	public Response getLista() {

		return Response.ok(manager.getList()).build();
	}

	@DELETE
	@Path("/{nome}")
	public Response delete(@PathParam("nome") String nome) {

		manager.remover(nome);

		return Response.ok("Tag removida: " + nome).build();
	}

}
