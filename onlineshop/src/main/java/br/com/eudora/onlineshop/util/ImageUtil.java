package br.com.eudora.onlineshop.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static String realpath = "/home/03435691409/eudora";

	public static void save(String filename, InputStream inputStream, String source, String id, boolean temp)
			throws ErroAoSalvarImagem {
		BufferedImage img;
		try {
			img = ImageIO.read(inputStream);
			
			String path = createPath(source, id, temp);

			ImageIO.write(img, "png", new File(path + filename));
			
		} catch (IOException e) {
			throw new ErroAoSalvarImagem(e);
		}

		
	}

	public static void persiste(String filename, String source, String id) throws ErroAoSalvarImagem {

		String path = realpath + "/images/" + source + "/temp/" + filename;
		
		String pathJaExiste = realpath + "/images/" + source + "/" + id + "/" + filename;

		try{
			if(new File(pathJaExiste).exists()){
				return;
			}
			
			FileInputStream is = new FileInputStream(new File(path));
	
			save(filename, is, source, id, false);
			
			remove(filename, source, id);
			
		}catch(IOException ex){
			throw new ErroAoSalvarImagem(ex);
		}
		
	}
	
	public static File recupera(String filename, String source, String id) throws ErroAoSalvarImagem {

		String path = realpath + "/images/" + source + "/temp/" + filename;
		
		if(id != null){
			path = realpath + "/images/" + source + "/"+id+"/" + filename;
		}

		return new File(path);

	}

	private static void remove(String filename, String source, String id) {
		
		String path = createPath(source, id, true) + filename;

		File f = new File(path);
		f.delete();
	}

	private static String createPath(String source, String id, boolean temp) {
		String path;
		if (temp) {
			path = realpath + "/images/" + source + "/temp/";
		} else {
			path = realpath + "/images/" + source + "/" + id + "/";
		}

		File f = new File(path);
		f.mkdirs();

		return path;
	}

	public static void main(String[] args) throws IOException, ErroAoSalvarImagem {
		realpath = "/home/03435691409/Documentos/Imagens";
		
		String path = realpath + "/aprovado.png";

		FileInputStream is = new FileInputStream(new File(path));
		
		save("nova.png", is, "TESTE", null, true);
		
		persiste("nova.png", "TESTE", "1");
		
	}

}
