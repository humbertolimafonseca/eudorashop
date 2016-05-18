package br.com.eudora.onlineshop.resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Ciclo;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.manager.MarcaManager;
import br.com.eudora.onlineshop.util.ErroAoSalvarImagem;
import br.com.eudora.onlineshop.util.ImageUtil;
import br.com.eudora.onlineshop.util.hateoas.DefaultHateoasResponse;
import br.com.eudora.onlineshop.util.hateoas.HateosResponseList;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("marca")
@ManagedBean
public class MarcaResource {

	private static final String CONTEXTO = "http://localhost:8080/onlineshop";

	MarcaManager manager = CdiUtil.get(MarcaManager.class);
	
	ItemProdutoManager itemManager = CdiUtil.get(ItemProdutoManager.class);
	
	@Context
	private ServletContext context;
	
	@Context
	private HttpServletRequest request;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("nome") String nome, @FormParam("descricao") String descricao, 
			@FormParam("imgLogo") String imgLogo,  @FormParam("ciclos") String ciclos) throws JsonParseException, JsonMappingException, IOException {

		try {
			List<Ciclo> cis = new ArrayList<Ciclo>();
			
			Marca m = new Marca(nome, descricao, imgLogo);
			
			Ciclo[] c = new ObjectMapper().readValue(ciclos, Ciclo[].class);
			
			for (Ciclo ciclo : c) {
				m.addCiclo(ciclo);
			} 
			
			manager.salvar(m); 
			
		}catch(ErroAoSalvarImagem ex){
			return Response.serverError().entity("Erro ao incluir a imagem.").build();
		} catch (ChaveDuplicadaException e) {
			return Response.serverError().entity("Chave Duplicada.").build();
		}
		
		return Response.ok("Marca criada com sucesso!").build();
	}
	
	@POST
	@Path("/edit/{id}/{imgLogo}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response edit(@FormParam("nome") String nome,
			@FormParam("descricao") String descricao,
			@FormParam("ciclos") String ciclos,
			@PathParam("id") String id,
			@PathParam("imgLogo") String imgLogo
			) throws JsonParseException, JsonMappingException, IOException {

			
		Marca m = manager.encontrar(Long.parseLong(id));
		m.setNome(nome);
		m.setDescricao(descricao);
		m.getLogomarca().setNome(imgLogo);
		
		ObjectMapper objectMapper  = new ObjectMapper();
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		Ciclo[] c = objectMapper.readValue(ciclos, Ciclo[].class);

		
		List<Ciclo> novosCiclos = new ArrayList<Ciclo>();
		List<Ciclo> removidosCiclos = new ArrayList<Ciclo>();
		
		novosCiclos.addAll(Arrays.asList(c));
		novosCiclos.removeAll(m.getCiclos());
		
		removidosCiclos.addAll(m.getCiclos());
		removidosCiclos.removeAll(Arrays.asList(c));
		
		for (Ciclo ciclo : novosCiclos) {
			m.addCiclo(ciclo);
		} 
		
		//atualiza os ciclos antigos.
		for (Ciclo ciclo : m.getCiclos()) {
			for (int i = 0; i < c.length; i++) {
				if(c[i].equals(ciclo)){
					ciclo.setInicio(c[i].getInicio());
					ciclo.setFim(c[i].getFim());
				}
			}
		}
		
		 m.getCiclos().removeAll(removidosCiclos);
		 
		 for (Ciclo ciclo : removidosCiclos) {
			itemManager.removePrecoCiclo(ciclo);
		}
		
		try {
			manager.atualizar(m);
		} catch (ErroAoSalvarImagem e) {
			return Response.serverError().entity("Erro ao salvar Imagem.").build();
		}

		return Response.ok("Marca atualizada com sucesso!").build();
	}
	
	@GET
	@Path("/{id}")
	public Response load(@PathParam("id") String id) {
		Marca marca = manager.encontrar(new Long(id));
		
		return Response.ok().entity( new DefaultHateoasResponse(marca, CONTEXTO)).build();
	}
	
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addImg")
	public Response addImagem(FormDataMultiPart uploadedInputStream) {
		
		//ImageUtil.realpath = context.getRealPath("");
		
		FormDataBodyPart bodyPart = uploadedInputStream.getField("imagem");

		FormDataContentDisposition cd = bodyPart.getFormDataContentDisposition();
		
		InputStream inputStream = bodyPart.getValueAs(InputStream.class);

		try {
			
			ImageUtil.save(cd.getFileName(), inputStream, Marca.MARCA_IMAGE_FOLDER, null, true);
			
			return Response.ok().build();
			
		} catch (ErroAoSalvarImagem e) {
			e.printStackTrace();
			
			return Response.serverError().entity("Erro ao salvar imagem").build();
		}

	}
	
	@GET
	@Produces("image/*")
	@Path("/logomarca/{logomarca}/{id}")
	public Response getImagemById(@PathParam("logomarca") String logomarca, @PathParam("id") String id) {
		
		BufferedImage image;
		
		try {
			File f;
			f = ImageUtil.recupera(logomarca, Marca.MARCA_IMAGE_FOLDER, id);
			
			image = ImageIO.read(f);
			float h = image.getHeight();
			float w = image.getWidth();
			float land = (float) (h / w);
			h = Math.min(100, h);
			w = h / land;

			Image img = image.getScaledInstance((int) w, (int) h, BufferedImage.SCALE_SMOOTH);

			BufferedImage bi = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.getGraphics();

			g.drawImage(img, 0, 0, null);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", out);

			final byte[] imgData = out.toByteArray();

			final InputStream bigInputStream = new ByteArrayInputStream(imgData);

			CacheControl cc = new CacheControl();
			cc.setNoCache(true);

			return Response.ok(bigInputStream).cacheControl(cc).build();

		} catch (IOException e) {
			e.printStackTrace();

			return Response.noContent().build();
		}
		
		
	}
	
	@GET
	@Produces("image/*")
	@Path("/logomarca/{logomarca}")
	public Response getImagem(@PathParam("logomarca") String logomarca) {

		return getImagemById(logomarca, null);
	}
	

	@GET
	public Response getLista() {

		return Response.ok( new HateosResponseList( manager.getList(), CONTEXTO)).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {

		manager.remover(new Long(id));

		return Response.ok("Marca removida" ).build();
	}

}
