package tarefas;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.service.spi.Manageable;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.eudora.onlineshop.dao.TransactionManager;
import br.com.eudora.onlineshop.dominio.Ciclo;
import br.com.eudora.onlineshop.dominio.ItemProduto;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.dominio.PrecoCiclo;
import br.com.eudora.onlineshop.dominio.Produto;
import br.com.eudora.onlineshop.manager.ItemProdutoManager;
import br.com.eudora.onlineshop.manager.MarcaManager;
import br.com.eudora.onlineshop.manager.ProdutoManager;
import br.com.eudora.onlineshop.util.DefaultImageService;

@RunWith(WeldJUnit4Runner.class)
public class TesteDeletaCiclo {
	
	@Inject
	ProdutoManager manager;
	
	@Inject
	ItemProdutoManager itemManager;
	
	@Inject
	MarcaManager marcaManager;
	
	@Test
	public void test() throws Throwable{
		TransactionManager.beginTransaction();
		Marca m = new Marca("NOva Marca", "Descrição", "teste.jpg");
		m.addCiclo(DateUtils.addDays(new Date(),-5), DateUtils.addDays(new Date(), 5));
		m.addCiclo(DateUtils.addMonths(new Date(), 1), DateUtils.addMonths(new Date(), 2));
		m.addCiclo(DateUtils.addMonths(new Date(), 2), DateUtils.addMonths(new Date(), 3));
		
		DefaultImageService.ignoreErrors();
		
		marcaManager.salvar(m);
		
		Produto p = new Produto("Novo Produto", "Descrição produto", "2", m, "teste.jpg",new String[]{});
		manager.salvar(p);
		
		ItemProduto ip = new ItemProduto(p,"BRL","10",2);
		ip.addPrecoCiclo(new PrecoCiclo("15", "BRL", m.cicloAtual()));
		itemManager.salvar(ip);
		
		TransactionManager.commitTransaction();
		
		
		TransactionManager.beginTransaction();
		
		Ciclo atual = m.cicloAtual();
		m.removeCiclo(atual);
		
		itemManager.removePrecoCiclo(atual);
		
		marcaManager.atualizar(m);
		
		TransactionManager.commitTransaction();
		
		TransactionManager.beginTransaction();
		
		m.addCiclo(DateUtils.addDays(new Date(),-5), DateUtils.addDays(new Date(), 5));
		
		TransactionManager.commitTransaction();
		
		TransactionManager.beginTransaction();
		ip.addPrecoCiclo(new PrecoCiclo("15", "BRL", m.cicloAtual()));
		TransactionManager.commitTransaction();
		
//		TransactionManager.beginTransaction();
//		
//			marcaManager.remover(m.getId());
//			manager.remover(p.getId());
//			itemManager.remover(ip.getId());
//			
//		TransactionManager.commitTransaction();
		
	}

}
