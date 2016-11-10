package org.brandao.pismo.teste.persistence.hibernate;

import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.EntityAccessTransaction;
import org.hibernate.Transaction;

/**
 * Isola uma transação do Hibernate.
 * @author Brandao.
 *
 */
public class HibernateEntityAccessTransaction 
	implements EntityAccessTransaction{

	private Transaction tx;
	
	public HibernateEntityAccessTransaction(Transaction tx){
		this.tx = tx;
	}
	
	@Override
	public void commit() throws EntityAccessException {
		try{
			tx.commit();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	public void rollback() throws EntityAccessException {
		try{
			tx.rollback();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

}
