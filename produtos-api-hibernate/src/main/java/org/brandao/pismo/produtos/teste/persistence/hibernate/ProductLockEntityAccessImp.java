package org.brandao.pismo.produtos.teste.persistence.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.brandao.pismo.produtos.teste.entity.persistence.entity.ProductLock;
import org.brandao.pismo.produtos.teste.entity.persistence.entity.ProductLockID;
import org.brandao.pismo.produtos.teste.persistence.ProductLockEntityAccess;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductLockHibernateEntity;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductlockPK;
import org.brandao.pismo.teste.persistence.hibernate.AbstractEntityAccess;
import org.hibernate.Session;

@Default
@Dependent
public class ProductLockEntityAccessImp 
	extends AbstractEntityAccess<ProductLock, ProductLockHibernateEntity>
	implements ProductLockEntityAccess{

	@Inject
	public ProductLockEntityAccessImp(Session session) {
		super(session);
	}

	@Override
	protected ProductLockHibernateEntity toPersistenceEntity(ProductLock entity)
			throws Throwable {
		return new ProductLockHibernateEntity(entity);
	}

	@Override
	protected ProductLock toEntity(ProductLockHibernateEntity entity)
			throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(ProductLock entity, Serializable id) throws Throwable {
	}

	@Override
	protected Serializable getPersistenceID(ProductLockHibernateEntity value)
			throws Throwable {
		return value.getPk();
	}

	@Override
	protected Serializable getID(ProductLock value) throws Throwable {
		return new ProductLockID(value);
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		ProductLockID id = (ProductLockID)value;
		ProductlockPK pk = new ProductlockPK();
		pk.setId(id.getId());
		pk.setOwner(id.getOwner().getId());
		return pk;
	}

}
