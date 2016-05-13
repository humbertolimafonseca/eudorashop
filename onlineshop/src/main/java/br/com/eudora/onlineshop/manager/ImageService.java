package br.com.eudora.onlineshop.manager;

import java.io.File;
import java.io.InputStream;

import br.com.eudora.onlineshop.util.ErroAoSalvarImagem;

public interface ImageService {
	
	public void create(String filename, InputStream inputStream, String source, String id, boolean temp)
			throws ErroAoSalvarImagem;

	public void persiste(String filename, String source, String id) throws ErroAoSalvarImagem;
	
	public File retrieve(String filename, String source, String id) throws ErroAoSalvarImagem;

	public void remove(String filename, String source, String id);

}
