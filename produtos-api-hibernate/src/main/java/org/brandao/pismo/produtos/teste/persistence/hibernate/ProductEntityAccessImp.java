package org.brandao.pismo.produtos.teste.persistence.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.persistence.ProductEntityAccess;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductHibernateEntity;
import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.hibernate.AbstractEntityAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Default
@Dependent
public class ProductEntityAccessImp
	extends AbstractEntityAccess<Product, ProductHibernateEntity>
	implements ProductEntityAccess{

	@Inject
	public ProductEntityAccessImp(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> search(String name, String description,
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult,
			Integer maxResult) throws EntityAccessException {
		
		try{
			Criteria c = 
					session
						.createCriteria(ProductHibernateEntity.class, "product")
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			if(name != null){
				name = "%" + name.trim().replaceAll("\\s+","%") + "%";
				c.add(Restrictions.eq("product.name", name));
			}

			if(description != null){
				description = "%" + description.trim().replaceAll("\\s+","%") + "%";
				c.add(Restrictions.eq("product.description", description));
			}
			
			if(minPrice != null || maxPrice != null){
				if(minPrice != null && maxPrice != null){
					c.add(Restrictions.between("product.price.value", minPrice, maxPrice));
				}
				else
				if(minPrice != null){
					c.add(Restrictions.ge("product..price.value", minPrice));
				}
				else
				if(maxPrice != null){
					c.add(Restrictions.ge("product..price.value", minPrice));
				}
			}
			
			if(firstResult != null)
				c.setFirstResult(firstResult);
			
			if(maxResult != null)
				c.setMaxResults(maxResult);
			
			return this.toCollection((List<ProductHibernateEntity>)c.list());
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

	@Override
	protected ProductHibernateEntity toPersistenceEntity(Product entity)
			throws Throwable {
		return new ProductHibernateEntity(entity);
	}

	@Override
	protected Product toEntity(ProductHibernateEntity entity) throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(Product entity, Serializable id) throws Throwable {
		entity.setId((int)id);
	}

	@Override
	protected Serializable getPersistenceID(ProductHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(Product value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}


}
