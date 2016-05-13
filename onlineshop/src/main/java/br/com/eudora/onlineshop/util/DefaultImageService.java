package br.com.eudora.onlineshop.util;

import java.io.File;
import java.io.InputStream;

import br.com.eudora.onlineshop.manager.ImageService;

public class DefaultImageService implements ImageService{
	
	private static final DefaultImageService instance = new DefaultImageService();
	private static boolean ignoreErrors = false;
	
	public static DefaultImageService getInstance() {
		return instance;
	}

	@Override
	public void create(String filename, InputStream inputStream, String source, String id, boolean temp)
			throws ErroAoSalvarImagem {
		try{
			ImageUtil.save(filename, inputStream, source, id, temp);
		}catch(ErroAoSalvarImagem e){
			if(ignoreErrors){
				//faz nada
			}else{
				throw e;
			}
		}
		
		
	}

	@Override
	public void persiste(String filename, String source, String id) throws ErroAoSalvarImagem {
		try{
			ImageUtil.persiste(filename, source, id);
		}catch(ErroAoSalvarImagem e){
			if(ignoreErrors){
				//faz nada
			}else{
				throw e;
			}
		}
	}

	@Override
	public File retrieve(String filename, String source, String id) throws ErroAoSalvarImagem {
		return ImageUtil.recupera(filename, source, id);
	}

	@Override
	public void remove(String filename, String source, String id) {
		ImageUtil.remove(filename, source, id);
	}

	public static void ignoreErrors() {
		ignoreErrors = true;
	}
	
	public static void doNotIgnoreErrors() {
		ignoreErrors = false;
	}
	
}
