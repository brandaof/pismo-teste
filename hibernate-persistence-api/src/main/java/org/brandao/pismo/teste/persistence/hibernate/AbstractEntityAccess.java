package org.brandao.pismo.teste.persistence.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.brandao.pismo.teste.persistence.BasicEntityAccess;
import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.EntityAccessTransaction;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Classe base de um manipulador de entidades persistentes usado Hibernate.
 * 
 * @author Brandao
 *
 * @param <T> Tipo da da entidade.
 * @param <K> Tipo persistente da entidade.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractEntityAccess<T, K> 
	implements BasicEntityAccess<T>{

	protected Session session;
	
	public AbstractEntityAccess(Session session){
		this.session = session;
	}
	
	public void save(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			this.session.save(pEntity);
			T newValue = this.toEntity(pEntity);
			this.setId(value, this.getID(newValue));
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public void update(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			pEntity = (K)session.merge(pEntity);
			session.update(pEntity);
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public void delete(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			session.delete(pEntity);
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public T findById(Serializable value) throws EntityAccessException {
		try{
			Serializable pk = this.toPersistenceID(value);
			K pEntity = (K)session.get(this.getEntityClass(), pk);
			return pEntity == null? null : this.toEntity(pEntity);
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public List<T> findAll() throws EntityAccessException {
		try{
			Criteria criteria = session.createCriteria(this.getEntityClass());
			return this.toCollection((List<K>) criteria.list());
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public List<T> findAll(int first, int max) throws EntityAccessException{
		try{
			Criteria criteria = session.createCriteria(this.getEntityClass());

			criteria.setFirstResult(first);
			criteria.setMaxResults(max);
			
			return this.toCollection((List<K>) criteria.list());
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	protected List<T> toCollection(List<K> list) throws Throwable{
		List<T> result = new ArrayList<T>();
		
		for(K e: list){
			result.add(this.toEntity(e));
		}
		
		return result;
	}
	
	protected Class<T> getEntityClass() {
		 return (Class<T>) ((ParameterizedType) getClass()
				 .getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public EntityAccessTransaction beginTransaction() throws EntityAccessException{
		try{
			return new HibernateEntityAccessTransaction(session.beginTransaction());
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public void flush(){
		this.session.flush();
	}
	
	/**
	 * Converte uma entidade para sua equivalente persistente.
	 * @param entity Entitdade.
	 * @return Entidade perssitente.
	 * @throws Throwable Lançada se ocorrer alguma falha na conversão.
	 */
	protected abstract K toPersistenceEntity(T entity) throws Throwable;

	/**
	 * Converte uma entidade persistente em seu equivalente.
	 * @param entity Entidade persistente.
	 * @return Entidade.
	 * @throws Throwable Lançada se ocorrer alguma falha na conversão.
	 */
	protected abstract T toEntity(K entity) throws Throwable;

	/**
	 * Define a identificação da entidade.
	 * @param entity Entidade.
	 * @param id Identificação.
	 * @throws Throwable Lançada se ocorrer alguma falha ao tentar definir
	 * a identificação da entidade.
	 */
	protected abstract void setId(T entity, Serializable id) throws Throwable;

	/**
	 * Obtém a identificação da entidade persistente.
	 * @param value Entidade.
	 * @return Identificação.
	 * @throws Throwable Lançada se ocorrer alguma falha ao tentar 
	 * obter a identificação da entidade.
	 */
	protected abstract Serializable getPersistenceID(K value) throws Throwable;

	/**
	 * Obtém a identificação de uma entidade.
	 * @param value Entidade.
	 * @return Identificação.
	 * @throws Throwable Lançada se ocorrer alguma falha ao tentar 
	 * obter a identificação da entidade.
	 */
	protected abstract Serializable getID(T value) throws Throwable;

	/**
	 * Converte uma identificação em seu equivalente persistente.
	 * @param value Identificação.
	 * @return Identificação persistente.
	 * @throws Throwable Lançada se ocorrer alguma falha na conversão.
	 */
	protected abstract Serializable toPersistenceID(Serializable value) throws Throwable;
	
}
