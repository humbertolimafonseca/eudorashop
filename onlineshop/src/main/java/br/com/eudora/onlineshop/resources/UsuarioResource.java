package br.com.eudora.onlineshop.resources;

import java.security.NoSuchAlgorithmException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Endereco;
import br.com.eudora.onlineshop.dominio.Usuario;
import br.com.eudora.onlineshop.manager.UsuarioManager;
import br.com.eudora.onlineshop.util.SenhaUtil;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("usuario")
public class UsuarioResource extends OnlineShopResource<UsuarioManager, Usuario, Long> {

	@Context
	private HttpServletRequest request;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("nome") String nome, @FormParam("email") String email,
			@FormParam("senha") String senha, @FormParam("logradouro") String logradouro, @FormParam("cep") String cep,
			@FormParam("endereco") String endereco, @FormParam("numero") String numero,
			@FormParam("complemento") String complemento) {

		try {
			String senhaMd5 = SenhaUtil.ToMD5(senha);
			Endereco end = new Endereco(logradouro, cep, endereco, new Integer(numero).intValue(), complemento);

			getManager().salvar(new Usuario(nome, email, senhaMd5, end));

		} catch (ChaveDuplicadaException e) {
			e.printStackTrace();
			return Response.serverError().entity("Usuário com o mesmo nome já criado.").build();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return Response.serverError().entity("Ocorreu um erro na criação da senha.").build();
		}

		return Response.ok("Usuário criado com sucesso!").build();
	}

	@GET
	@Path("/logged")
	public Response getLogged() {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		if (u == null) {
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

	@POST
	@Path("/login")
	public Response login(@FormParam("login") String login, @FormParam("senha") String senha) {

		try {
			Usuario user = getManager().encontrar(new Usuario(login, null, null, null)).get(0);

			if(user.getSenha().equals(SenhaUtil.ToMD5(senha))){
				request.getSession(true).setAttribute("usuario", user);
				
				return Response.ok().entity(new Usuario(user.getNome(), user.getEmail(), null, user.getEndereco())).build();
				
			} else{
				logout();
				return Response.serverError().entity("Não foi possível realizar o login. Confira o usuário e a senha.").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Não foi possível realizar o login. Confira o usuário e a senha.").build();
		}

		
	}

	@Override
	protected String getMensagemDelete(Usuario obj) {
		return "Usuário removido com sucesso!";
	}

}
