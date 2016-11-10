package org.brandao.pismo.produtos.teste.persistence.hibernate;

import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.PriceHibernateEntity;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductHibernateEntity;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductLockHibernateEntity;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.ProductStockHibernateEntity;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.SystemCustomerHibernateEntity;
import org.brandao.pismo.teste.persistence.hibernate.AbstractHibernateInitializer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Singleton
public class HibernateInitializer 
	extends AbstractHibernateInitializer{

	@Inject
	public HibernateInitializer(
			@Named("server_config")
			Properties configuration) {
		super(configuration);
	}

	@Produces
	@RequestScoped
	public Session openSession(SessionFactory sessionFactory){
		Session session = super.openSession(sessionFactory);
		return session;
	}

	public void close(@Disposes Session session){
		session.close();
	}
	
	@Produces
	@Singleton
	public SessionFactory createSessionFactory() 
			throws PropertyVetoException {
		
		List<Class<?>> list = 
				Arrays.asList(
						PriceHibernateEntity.class,
						ProductHibernateEntity.class,
						ProductLockHibernateEntity.class,
						ProductStockHibernateEntity.class,
						SystemCustomerHibernateEntity.class);
		return super.createSessionFactory(list);
	}
	
}
