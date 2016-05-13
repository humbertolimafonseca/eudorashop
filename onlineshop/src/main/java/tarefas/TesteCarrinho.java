package tarefas;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dominio.Carrinho;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.util.CurrencyUtil;

@RunWith(WeldJUnit4Runner.class)
public class TesteCarrinho{
	
	@Inject
	ItemProdutoManager manager;
	
	@Test
	public void testPrecoTotalCarrinho() throws Throwable{
		Carrinho carrinho = new Carrinho();
		
		for (ItemProduto ip : manager.getList()) {
			carrinho.addItem(ip, 1);
		}

		System.out.println("VALOR TOTAL: " +  CurrencyUtil.format(carrinho.getTotal()));
	}
	
}
