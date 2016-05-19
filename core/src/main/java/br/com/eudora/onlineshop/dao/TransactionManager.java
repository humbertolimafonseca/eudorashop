package br.com.eudora.onlineshop.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.hibernate.jpa.HibernateEntityManagerFactory;

public class TransactionManager {

	static HibernateEntityManagerFactory emf ;
	
	static ThreadLocal<EntityManager> local = new ThreadLocal<EntityManager>();
	
	public static EntityManager getEntityManager() {
		checkStart();
		if(local.get() == null || !local.get().isOpen()){
			local.set(emf.createEntityManager());
			local.get().setFlushMode(FlushModeType.COMMIT);
		}
		return local.get();
	}
	
	private static void checkStart(){

		try{
			if(emf == null){
				emf = (HibernateEntityManagerFactory) Persistence
						.createEntityManagerFactory("eudorashop");
			}
		}catch(Exception e){
			throw new ErroInicioDB(e);
		}
	}
	
	public static void beginTransaction() {
		checkStart();
		
		EntityTransaction t = getEntityManager().getTransaction();
		t.begin();
	}

	public static void commitTransaction() {
		checkStart();
		EntityTransaction t = getEntityManager().getTransaction();
		t.commit();
	}

	public static void rolllBackTransaction() {
		checkStart();
		EntityTransaction t = getEntityManager().getTransaction();
		t.rollback();
	}


	public static HibernateEntityManagerFactory getEmf() {
		checkStart();
		return emf; 
	} 

	public static boolean isTransactionActive() {
		checkStart();
		return getEntityManager().getTransaction().isActive();
	}
	
	public static void close() {
		checkStart();
		getEntityManager().close();
		local.remove();	 
	}

}
