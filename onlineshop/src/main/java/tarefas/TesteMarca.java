package tarefas;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.manager.MarcaManager;

@RunWith(WeldJUnit4Runner.class)
public class TesteMarca{
	
	@Inject
	MarcaManager manager;
	
	@Test
	public void testChaveDuplicada() throws ChaveDuplicadaException{
		
		for (Marca tag : manager.getList()) {
			System.out.println("Removendo " + tag.getNome());
			manager.remover(tag.getId());
		}

		Marca m = new Marca("nome", "descricao", "marca.png");
		
		manager.salvar(m);
		
		
	}
	
}
