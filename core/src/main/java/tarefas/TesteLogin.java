package tarefas;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dao.TransactionManager;
import br.com.eudora.onlineshop.dominio.Endereco;
import br.com.eudora.onlineshop.dominio.Usuario;
import br.com.eudora.onlineshop.manager.UsuarioManager;
import br.com.eudora.onlineshop.util.SenhaUtil;

@RunWith(WeldJUnit4Runner.class)
public class TesteLogin {
	
	@Inject
	UsuarioManager manager;
	
	@Test
	public void test() throws Throwable{
		TransactionManager.beginTransaction();
		
		Endereco endereco = new Endereco("Rua", "50000-000", "João Barbalho", 121, "apto 1102");
		
		Usuario user = new Usuario("usuario teste", "usuario@gmail.com", SenhaUtil.ToMD5("senha"), endereco);
		
		manager.salvar(user);
		
		TransactionManager.commitTransaction();
		
		TransactionManager.beginTransaction();
		Usuario u =  manager.encontrar(new Usuario("usuario teste", null, null, null)).get(0);
		
		Assert.assertEquals( user.getSenha(), SenhaUtil.ToMD5("senha") );
		
	}

}
