package tarefas;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dominio.Tag;
import br.com.eudora.onlineshop.manager.TagManager;

@RunWith(WeldJUnit4Runner.class)
public class TesteTag{
	
	@Inject
	TagManager manager;
	
	@Test(expected=ChaveDuplicadaException.class)
	public void testChaveDuplicada() throws ChaveDuplicadaException{
		
		for (Tag tag : manager.getList()) {
			manager.remover(tag.getNome());
		}
		
		Tag tag = new Tag("Baton");

		manager.salvar(tag);
		
		Tag tag2 = new Tag("Baton");

		manager.salvar(tag2);
		
	}
	
}
