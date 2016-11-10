package org.brandao.pismo.produtos.teste.persistence.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.produtos.teste.persistence.ProductStockEntityAccess;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductStockHibernateEntity;
import org.brandao.pismo.teste.persistence.hibernate.AbstractEntityAccess;
import org.hibernate.Session;

@Default
@Dependent
public class ProductStockEntityAccessImp 
	extends AbstractEntityAccess<ProductStock, ProductStockHibernateEntity>
	implements ProductStockEntityAccess{

	@Inject
	public ProductStockEntityAccessImp(Session session) {
		super(session);
	}

	@Override
	protected ProductStockHibernateEntity toPersistenceEntity(
			ProductStock entity) throws Throwable {
		return new ProductStockHibernateEntity(entity);
	}

	@Override
	protected ProductStock toEntity(ProductStockHibernateEntity entity)
			throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(ProductStock entity, Serializable id) throws Throwable {
	}

	@Override
	protected Serializable getPersistenceID(ProductStockHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(ProductStock value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

}
