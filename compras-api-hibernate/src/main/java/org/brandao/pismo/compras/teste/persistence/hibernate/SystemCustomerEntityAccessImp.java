package org.brandao.pismo.compras.teste.persistence.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.brandao.pismo.compras.teste.persistence.entity.SystemCustomerHibernateEntity;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.SystemCustomerEntityAccess;
import org.brandao.pismo.teste.persistence.hibernate.AbstractEntityAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Default
@Dependent
public class SystemCustomerEntityAccessImp 
	extends AbstractEntityAccess<SystemCustomer, SystemCustomerHibernateEntity>
	implements SystemCustomerEntityAccess{

	@Inject
	public SystemCustomerEntityAccessImp(Session session) {
		super(session);
	}

	@Override
	protected SystemCustomerHibernateEntity toPersistenceEntity(
			SystemCustomer entity) throws Throwable {
		return new SystemCustomerHibernateEntity(entity);
	}

	@Override
	protected SystemCustomer toEntity(SystemCustomerHibernateEntity entity)
			throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(SystemCustomer entity, Serializable id)
			throws Throwable {
		entity.setId((Long)id);
	}

	@Override
	protected Serializable getPersistenceID(SystemCustomerHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(SystemCustomer value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

	@Override
	public SystemCustomer findByName(String name) throws EntityAccessException {
		try{
			Criteria c = 
					session
						.createCriteria(SystemCustomerHibernateEntity.class, "user")
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			c.add(Restrictions.eq("user.name", name));
			SystemCustomerHibernateEntity user = (SystemCustomerHibernateEntity) c.uniqueResult();
			
			return user == null? null : user.toEntity();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

}
