package org.brandao.pismo.compras.teste.persistence.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.compras.teste.persistence.OrderEntityAccess;
import org.brandao.pismo.compras.teste.persistence.entity.OrderHibernateEntity;
import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.hibernate.AbstractEntityAccess;
import org.hibernate.Session;

@Default
@Dependent
public class OrderEntityAccessImp 
	extends AbstractEntityAccess<Order, OrderHibernateEntity>
	implements OrderEntityAccess{

	public void save(Order value) throws EntityAccessException {
		try{
			OrderHibernateEntity pEntity = this.toPersistenceEntity(value);
			this.session.save(pEntity);
			Order newValue = this.toEntity(pEntity);
			this.setId(value, this.getID(newValue));
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}
	}

	public void update(Order value) throws EntityAccessException {
		try{
			OrderHibernateEntity pEntity = this.toPersistenceEntity(value);
			pEntity = (OrderHibernateEntity) this.session.merge(pEntity);
			this.session.update(pEntity);
			Order newValue = this.toEntity(pEntity);
			this.setId(value, this.getID(newValue));
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}		
	}

	public void delete(Order value) throws EntityAccessException {
		try{
			OrderHibernateEntity pEntity = this.toPersistenceEntity(value);
			pEntity = (OrderHibernateEntity) this.session.merge(pEntity);
			this.session.delete(pEntity);
			Order newValue = this.toEntity(pEntity);
			this.setId(value, this.getID(newValue));
    	}
    	catch(Throwable e){
    		throw new EntityAccessException(e);
    	}			
	}
	
	@Inject
	public OrderEntityAccessImp(Session session) {
		super(session);
	}

	@Override
	protected OrderHibernateEntity toPersistenceEntity(Order entity)
			throws Throwable {
		return new OrderHibernateEntity(entity);
	}

	@Override
	protected Order toEntity(OrderHibernateEntity entity) throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(Order entity, Serializable id) throws Throwable {
		entity.setId((String)id);
	}

	@Override
	protected Serializable getPersistenceID(OrderHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(Order value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

	/*
	protected void syncProducts(List<ProductOrderHibernateEntity> list, Session session){
		if(list != null){
			for(ProductOrderHibernateEntity e: list){
				
				ProductOrderHibernateEntity pProductEntity = 
						(ProductOrderHibernateEntity)this.session.get(
								ProductOrderHibernateEntity.class, e.getId());
				
				if(pProductEntity != null){
					this.session.save(e);
				}
				else{
					this.session.update(e);
				}
			}
		}
	}
	*/
}
