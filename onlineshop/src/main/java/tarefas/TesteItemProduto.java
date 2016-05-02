package tarefas;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dao.TransactionManager;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.dominio.Produto;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.manager.ProdutoManager;

@RunWith(WeldJUnit4Runner.class)
public class TesteItemProduto{
	
	@Inject
	ProdutoManager manager;
	
	@Inject
	ItemProdutoManager itemManager;
	
	@Test
	public void test() throws Throwable{
		Produto p = manager.getList().get(0);
		ItemProduto ip = new ItemProduto(p, "BRL","5.98",3);
		
//		new Date(), new Date(), "12.34",
//		 new Date(), new Date(), "11.55"
		TransactionManager.beginTransaction();
		itemManager.salvar(ip);
		TransactionManager.commitTransaction();
	}
	
}
