package br.com.eudora.onlineshop.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.ManagedType;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.metadata.ClassMetadata;

public abstract class DaoGenerico<T, I extends Serializable> {
	
	HibernateEntityManagerFactory emf = (HibernateEntityManagerFactory) Persistence.createEntityManagerFactory("eudorashop");
	
	protected EntityManager entityManager = emf.createEntityManager();

	private Class<T> persistedClass;

	protected DaoGenerico(Class<T> persistedClass) {
		this.persistedClass = persistedClass;
	}

	public T salvar(@Valid T entity) throws ChaveDuplicadaException {
		
		if(getId(entity) != null && encontrar( getId(entity) ) != null){
			throw new ChaveDuplicadaException();
		}
		
		EntityTransaction t = entityManager.getTransaction();
		t.begin();
		entityManager.persist(entity);
		entityManager.flush();
		t.commit();
		return entity;
	}

	public T atualizar(@Valid T entity) {
		EntityTransaction t = entityManager.getTransaction();
		t.begin();
		entityManager.merge(entity);
		entityManager.flush();
		t.commit();
		return entity;
	}

	public void remover(I id) {
		T entity = encontrar(id);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		T mergedEntity = entityManager.merge(entity);
		entityManager.remove(mergedEntity);
		entityManager.flush();
		tx.commit();
	}

	public List<T> getList() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);
		return entityManager.createQuery(query).getResultList();
	}

	public T encontrar(I id) {
		return entityManager.find(persistedClass, id);
	}
	
	public List<T> encontrar(T entity) {
		Session s = (Session) entityManager.getDelegate();
		Example e = Example.create(entity);
		s.createCriteria(entity.getClass()).add(e);
		
		return s.createCriteria(entity.getClass()).add(e).list();
	}
	
	public I getId(Object entity){
		Class entityClass = Hibernate.getClass( entity );
		ClassMetadata classMetadata = emf.getSessionFactory().getClassMetadata( entityClass );
		if (classMetadata == null) {
			throw new IllegalArgumentException( entityClass + " is not an entity" );
		}
		return (I) classMetadata.getIdentifier( entity, null );
	}
}
