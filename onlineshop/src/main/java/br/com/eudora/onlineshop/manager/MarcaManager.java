package br.com.eudora.onlineshop.manager;

import java.io.IOException;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dao.MarcaDao;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.util.ImageUtil;

public class MarcaManager extends Manager<MarcaDao, Marca, Long>{
	
	@Override
	public Marca salvar(Marca entity) throws ChaveDuplicadaException {
		
		Marca m = super.salvar(entity);
		
		try {
			salvaImagens(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m;
	}
	
	@Override
	public Marca atualizar(Marca entity) {
		Marca m = super.atualizar(entity);
		
		try {
			salvaImagens(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m;
	}
	

	private void salvaImagens(Marca m) throws IOException {
		ImageUtil.persiste(m.getLogomarca().getNome(), Marca.MARCA_IMAGE_FOLDER, m.getId().toString());
	}
	
}
