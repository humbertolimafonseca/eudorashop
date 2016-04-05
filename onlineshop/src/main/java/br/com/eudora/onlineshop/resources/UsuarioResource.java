package br.com.eudora.onlineshop.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Usuario;
import br.com.eudora.onlineshop.manager.UsuarioManager;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("usuario")
public class UsuarioResource extends Application {

	UsuarioManager manager = CdiUtil.get(UsuarioManager.class);
	
	@Context
	private HttpServletRequest request;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("nome") String nome, @FormParam("email") String email, @FormParam("senha") String senha) {

		try {
			manager.salvar(new Usuario(nome, senha, email));
		} catch (ChaveDuplicadaException e) {
			e.printStackTrace();
			return Response.serverError().entity("Usuário com o mesmo nome já criado.").build();
		}

		return Response.ok("Usuário criado com sucesso!").build();
	}

	@GET
	public Response getLista() {

		return Response.ok(manager.getList()).build();
	}
	
	@GET
	@Path("/logged")
	public Response getLogged() {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		
		if(u == null){
			return Response.serverError().build();
		}
		
		return Response.ok().entity(u).build();
	}
	
	@GET
	@Path("/logout")
	public Response logout() {
		request.getSession().invalidate();
		
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {

		manager.remover(new Long(id));

		return Response.ok("Usuário removido").build();
	}
	
	@POST
	@Path("/login")
	public Response login(@FormParam("login") String login, @FormParam("senha") String senha){
		
//		manager.encontrar(new Usuario(nome, null, senha));
		
		Usuario u = new Usuario("Ana Flávia", "flaviannafonseca@gmail.com","");
		request.getSession(true).setAttribute("usuario", u);
		
		return Response.ok().entity(u).build();
	}

}
