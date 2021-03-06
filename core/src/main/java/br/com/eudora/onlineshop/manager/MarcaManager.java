package br.com.eudora.onlineshop.manager;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dao.MarcaDao;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.util.DefaultImageService;
import br.com.eudora.onlineshop.util.ErroAoSalvarImagem;

public class MarcaManager extends Manager<MarcaDao, Marca, Long> {

	@Override
	public Marca salvar(Marca entity) throws ChaveDuplicadaException, ErroAoSalvarImagem {

		Marca m = super.salvar(entity);

		salvaImagens(m);

		return m;
	}

	@Override
	public Marca atualizar(Marca entity) throws ErroAoSalvarImagem {
		Marca m = super.atualizar(entity);

		salvaImagens(m);

		return m;
	}

	private void salvaImagens(Marca m) throws ErroAoSalvarImagem {
		
		ImageService imageService = DefaultImageService.getInstance();
		
		imageService.persiste(m.getLogomarca().getNome(), Marca.MARCA_IMAGE_FOLDER, m.getId().toString());
	}

}
