package br.com.eudora.onlineshop.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.jpa.criteria.OrderImpl;
import org.hibernate.metadata.ClassMetadata;

public abstract class DaoGenerico<T, I extends Serializable> {
	
	EntityManager entityManager = TransactionManager.getEntityManager();

	private Class<T> persistedClass;

	protected DaoGenerico(Class<T> persistedClass) {
		this.persistedClass = persistedClass;
	}

	public T salvar(@Valid T entity) throws ChaveDuplicadaException {
		
		if(getId(entity) != null && encontrar( getId(entity) ) != null){
			throw new ChaveDuplicadaException();
		}
		
		entityManager.persist(entity);

		return entity;
	}

	public T atualizar(@Valid T entity) {
		entityManager.merge(entity);
		return entity;
	}

	public void remover(I id) {
		T entity = encontrar(id);
		T mergedEntity = entityManager.merge(entity);
		entityManager.remove(mergedEntity);
	}

	public List<T> getList() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		Root r = query.from(persistedClass);
		OrderBy ob = persistedClass.getAnnotation(OrderBy.class);
		
		if(ob != null){
			query = query.orderBy(builder.asc(r.get(ob.property())));
		}
		
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
		ClassMetadata classMetadata = TransactionManager.getEmf().getSessionFactory().getClassMetadata( entityClass );
		if (classMetadata == null) {
			throw new IllegalArgumentException( entityClass + " is not an entity" );
		}
		return (I) classMetadata.getIdentifier( entity, null );
	}
}
