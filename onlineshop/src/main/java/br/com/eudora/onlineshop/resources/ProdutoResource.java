package br.com.eudora.onlineshop.resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.javamoney.moneta.Money;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Imagem;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.dominio.Produto;
import br.com.eudora.onlineshop.manager.MarcaManager;
import br.com.eudora.onlineshop.manager.ProdutoManager;
import br.com.eudora.onlineshop.manager.TagManager;
import br.com.eudora.onlineshop.util.ErroAoSalvarImagem;
import br.com.eudora.onlineshop.util.ImageUtil;
import br.com.eudora.onlineshop.util.UploadedFile;
import tarefas.CdiUtil;

@ApplicationPath("/resources")
@Path("produto")
public class ProdutoResource {

	@Context
	private ServletContext context;

	@Context
	private HttpServletRequest request;

	ProdutoManager manager = CdiUtil.get(ProdutoManager.class);
	TagManager tagManager = CdiUtil.get(TagManager.class);
	MarcaManager marcaManager = CdiUtil.get(MarcaManager.class);

	String path;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addImagem(FormDataMultiPart uploadedInputStream) throws IOException {

		List<UploadedFile> lista = new ArrayList<UploadedFile>();
		FormDataBodyPart bodyPart = uploadedInputStream.getField("files[]");

		FormDataContentDisposition cd = bodyPart.getFormDataContentDisposition();
		cd.getType();
		InputStream inputStream = bodyPart.getValueAs(InputStream.class);

		BufferedImage img = ImageIO.read(inputStream);

		createPath();

		ImageIO.write(img, bodyPart.getMediaType().getSubtype(), new File(path + cd.getFileName()));

		long size = Long.valueOf(uploadedInputStream.getHeaders().getFirst("Content-Length"));

		UploadedFile file = new UploadedFile(cd.getFileName(), size);

		lista.add(file);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return Response.ok(mapper.writer().withRootName("files").writeValueAsString(lista)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

	@POST
	@Path("/edit/{id}")
	public Response edit(FormDataMultiPart uploadedInputStream, @PathParam("id") String id) {

		Produto produto = manager.encontrar(Long.parseLong(id));

		String nome = uploadedInputStream.getField("nome").getValue();
		String descricao = uploadedInputStream.getField("descricao").getValue();
		String preco = uploadedInputStream.getField("preco").getValue();
		String tags = uploadedInputStream.getField("tags").getValue();
		String marca = uploadedInputStream.getField("marca").getValue();
		String codigo = uploadedInputStream.getField("codigo").getValue();

		FormDataBodyPart bodyPart = uploadedInputStream.getField("imagem");

		FormDataContentDisposition cd = bodyPart.getFormDataContentDisposition();
		InputStream is = bodyPart.getValueAs(InputStream.class);

		try {
			if(!cd.getFileName().equals("")){
				ImageUtil.save(cd.getFileName(), is, "produto", null, true);
				Imagem imagem = produto.getImagens().get(0);
				imagem.setNome(cd.getFileName());
			}
			Marca m = marcaManager.encontrar(Long.parseLong(marca));

			produto.setDescricao(descricao);
			produto.setMarca(m);
			produto.setNome(nome);
			produto.setPreco(new BigDecimal(preco));
			produto.setCodigo(codigo);

			produto.getTags().clear();

			String[] tagss = tags.split(",");

			for (String tag : tagss) {
				produto.addTag(tagManager.encontrar(tag));
			}

			manager.atualizar(produto);
			
			if(!cd.getFileName().equals("")){
				ImageUtil.persiste(cd.getFileName(), "produto", produto.getId().toString());
			}

		} catch (ErroAoSalvarImagem e) {
			Response.serverError().entity("Erro ao salvar imagem").build();
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return Response.ok("Produto atualizado com sucesso.").build();

	}

//	@POST
//	@Path("/edit/{id}")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response edit(@FormParam("descricao") String descricao, @FormParam("nome") String nome,
//			@FormParam("preco") String preco, @FormParam("tags") String tags, @PathParam("id") String id) {
//
//		Produto p = manager.encontrar(Long.parseLong(id));
//		p.setNome(nome);
//		p.setDescricao(descricao);
//		p.setMoeda("BRL");
//		p.setPreco(new BigDecimal(preco));
//		p.getTags().clear();
//
//		String[] tagss = tags.split(",");
//
//		for (String tag : tagss) {
//			p.addTag(tagManager.encontrar(tag));
//		}
//
//		try {
//			manager.atualizar(p);
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return Response.ok("Produto atualizada com sucesso!").build();
//	}

	private void createPath() {
		String realpath = context.getRealPath("");
		path = realpath + "/images/produto/";

		File f = new File(path);
		f.mkdirs();
	}

	@GET
	@Path("/{id}")
	public Response load(@PathParam("id") String id) {
		Produto p = manager.encontrar(new Long(id));

		return Response.ok().entity(p).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {

		try {
			manager.remover(new Long(id));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok("Produto removido").build();
	}

	@GET
	@Produces("image/*")
	@Path("/thumbnail/{nome}")
	public Response thumbnail(@PathParam("nome") String nome) {

		BufferedImage image;
		createPath();
		File f = new File(path + nome);
		try {

			image = ImageIO.read(f);
			float h = image.getHeight();
			float w = image.getWidth();
			float land = (float) (h / w);
			h = 80;
			w = 60;
			if (land > 1) {
				// altura maior
				h = 80;
				w = 60;
			}

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
	public Response getLista() {

		return Response.ok(manager.getList()).build();
	}

	@POST
	@Path("/add")
	public Response addProduto(FormDataMultiPart uploadedInputStream) {

		String nome = uploadedInputStream.getField("nome").getValue();
		String descricao = uploadedInputStream.getField("descricao").getValue();
		String preco = uploadedInputStream.getField("preco").getValue();
		String tags = uploadedInputStream.getField("tags").getValue();
		String marca = uploadedInputStream.getField("marca").getValue();
		String inicio = uploadedInputStream.getField("inicio").getValue();
		String fim = uploadedInputStream.getField("fim").getValue();
		String codigo = uploadedInputStream.getField("codigo").getValue();

		FormDataBodyPart bodyPart = uploadedInputStream.getField("imagem");

		FormDataContentDisposition cd = bodyPart.getFormDataContentDisposition();
		InputStream is = bodyPart.getValueAs(InputStream.class);

		try {

			ImageUtil.save(cd.getFileName(), is, "produto", null, true);
			Marca m = marcaManager.encontrar(Long.parseLong(marca));
			Produto produto = new Produto(nome, descricao,codigo, m, new BigDecimal(preco).floatValue(), "BRL",
					parse(inicio), parse(fim), cd.getFileName(), "");

			String[] tagss = tags.split(",");

			for (String tag : tagss) {
				produto.addTag(tagManager.encontrar(tag));
			}

			manager.salvar(produto);
			ImageUtil.persiste(cd.getFileName(), "produto", produto.getId().toString());

		} catch (ErroAoSalvarImagem e) {
			Response.serverError().entity("Erro ao salvar imagem").build();
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok("Produto criado com sucesso.").build();

	}

	private Date parse(String fim) {
		try {
			return DateUtils.parseDate(fim, new String[]{"dd/MM/yyyy"});
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@POST
	@Path("/imagem/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addImagemPrincipal(FormDataMultiPart uploadedInputStream) throws IOException {
		List<UploadedFile> lista = new ArrayList<UploadedFile>();
		FormDataBodyPart bodyPart = uploadedInputStream.getField("imagemPrincipal");

		FormDataContentDisposition cd = bodyPart.getFormDataContentDisposition();
		cd.getType();
		InputStream inputStream = bodyPart.getValueAs(InputStream.class);

		BufferedImage img = ImageIO.read(inputStream);

		request.getSession(true).setAttribute("imagemPricipal", img);

		return Response.ok().build();
	}

	@GET
	@Path("/imagem/{id}")
	@Produces("image/*")
	public Response viewImagem(@PathParam("id") String nome) {

		BufferedImage image;
		try {

			image = (BufferedImage) request.getSession().getAttribute("imagemPricipal");
			float h = image.getHeight();
			float w = image.getWidth();
			float land = (float) (h / w);
			h = 80;
			w = 60;
			if (land > 1) {
				// altura maior
				h = 80;
				w = 60;
			}

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

}
