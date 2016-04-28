package br.com.eudora.onlineshop.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.jpa.HibernateEntityManagerFactory;

public class TransactionManager {

	static HibernateEntityManagerFactory emf = (HibernateEntityManagerFactory) Persistence
			.createEntityManagerFactory("eudorashop");
	
	static ThreadLocal<EntityManager> local = new ThreadLocal<EntityManager>();
	
	public static EntityManager getEntityManager() {
		if(local.get() == null || !local.get().isOpen()){
			local.set(emf.createEntityManager());
		}
		return local.get();
	}

	public static void beginTransaction() {
		EntityTransaction t = getEntityManager().getTransaction();
		t.begin();
	}

	public static void commitTransaction() {
		EntityTransaction t = getEntityManager().getTransaction();
		t.commit();
	}

	public static void rolllBackTransaction() {
		EntityTransaction t = getEntityManager().getTransaction();
		t.rollback();
	}


	public static HibernateEntityManagerFactory getEmf() {
		return emf;
	}

	public static boolean isTransactionActive() {
		return getEntityManager().getTransaction().isActive();
	}
	
	public static void close() {
		getEntityManager().close();
	}

}
