package tarefas;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.time.DateUtils;
import org.glassfish.jersey.servlet.internal.Utils;

import br.com.eudora.onlineshop.dao.ChaveDuplicadaException;
import br.com.eudora.onlineshop.dao.MarcaDao;
import br.com.eudora.onlineshop.dao.ProdutoDao;
import br.com.eudora.onlineshop.dominio.Cliente;
import br.com.eudora.onlineshop.dominio.Marca;
import br.com.eudora.onlineshop.dominio.Produto;


public class CriarBanco {
	
	static ProdutoDao dao = new ProdutoDao();
	static MarcaDao marcaDao = new MarcaDao();

	public static void main(String[] args) {
//		insertCliente();
		insertProduto();
	}

	private static void insertCliente() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eudorashop");
		EntityManager em = factory.createEntityManager();
		Cliente cliente = new Cliente();
		cliente.setNome("Humberto");
		cliente.setDtNascimento(new Date());
		em.getTransaction().begin();
		em.persist(cliente);
		em.getTransaction().commit();

		List<Cliente> lista = em.createQuery("from Cliente").getResultList();

		for (Cliente cliente2 : lista) {
			System.out.println(cliente2.getId() + " - " + cliente2.getNome());
		}
		factory.close();
	}

	public static void insertProduto() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eudorashop");
		EntityManager em = factory.createEntityManager();
		
		List<Produto> lista = dao.getList();
		
		for (Produto produto : lista) {
//			produto.setPreco(new BigDecimal("2.45"));
//			produto.setMoeda("BRL");
//			System.out.println(produto.getValor());
			em.getTransaction().begin();
			em.merge(produto);
			em.getTransaction().commit();
		}
		
		Marca m = marcaDao.getList().get(0);
		
		Produto p = new Produto("Batom","desc","E4456",m, "/img/batom1.jpg");
		
		Cliente c = em.find(Cliente.class, 401l);
		p.addAvaliacao(3, c, "Bom produto");
//		em.getTransaction().begin();
//		em.persist(p);
//		em.getTransaction().commit();
		
		try {
			em.getTransaction().begin();
			dao.salvar(p);
			em.getTransaction().commit();
		} catch (ChaveDuplicadaException e) {
			e.printStackTrace();
		}

		lista = dao.getList();
		
//		MonetaryAmountFormat formatoPadrao = MonetaryFormats.getAmountFormat(Locale.getDefault());
//		String formatadoPadraoBR = formatoPadrao.format(valorEmReais);
//		System.out.println("Formato BR: " + formatadoPadraoBR);
		
//		em.getTransaction().begin();
		for (Produto produto : lista) {
//			em.remove(produto);
			
			System.out.println(produto.getId() + " - " + produto.getNome()+ " - " );
//			+	NumberFormat.getCurrencyInstance(new Locale("pt","BR")).format( produto.getValor().getNumber().doubleValue()) );
		}
//		em.getTransaction().commit();
		factory.close();
	}

}
