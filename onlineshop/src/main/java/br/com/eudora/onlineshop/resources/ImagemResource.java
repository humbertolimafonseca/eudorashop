package br.com.eudora.onlineshop.resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import br.com.eudora.onlineshop.util.ImageUtil;

@ApplicationPath("/resources")
@Path("imagem")
public class ImagemResource {

	@Context
	private ServletContext context;
	
	@Context
	private HttpServletRequest request;

	@GET
	@Produces("image/*")
	@Path("/{source}/{id}/{name}")
	public Response getImagemById(@PathParam("source") String source, @PathParam("id") String id, @PathParam("name") String name) {
		
		BufferedImage image;
		
		try {
			File f;
			f = ImageUtil.recupera(name, source, id);
			
			image = ImageIO.read(f);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			ImageIO.write(image, "png", out);

			final byte[] imgData = out.toByteArray();

			final InputStream bigInputStream = new ByteArrayInputStream(imgData);

			CacheControl cc = new CacheControl();
			cc.setNoCache(true);

			return Response.ok(bigInputStream).cacheControl(cc).build();

		} catch (IOException e) {
			System.out.println("Erro ao carregar imagem.");
			return Response.noContent().build();
		}
		
		
	}
}