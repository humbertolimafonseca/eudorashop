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
		getEntityManager().close();
	}

	public static void rolllBackTransaction() {
		EntityTransaction t = getEntityManager().getTransaction();
		t.rollback();
		getEntityManager().close();
	}


	public static HibernateEntityManagerFactory getEmf() {
		return emf;
	}

}
