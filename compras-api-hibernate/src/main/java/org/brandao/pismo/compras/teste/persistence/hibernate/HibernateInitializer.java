package org.brandao.pismo.compras.teste.persistence.hibernate;

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

import org.brandao.pismo.compras.teste.persistence.entity.OrderHibernateEntity;
import org.brandao.pismo.compras.teste.persistence.entity.ProductOrderHibernateEntity;
import org.brandao.pismo.compras.teste.persistence.entity.SystemCustomerHibernateEntity;
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

	public void openSession(@Disposes Session session){
		session.close();
	}
	
	@Produces
	@Singleton
	public SessionFactory createSessionFactory() 
			throws PropertyVetoException {
		
		List<Class<?>> list = 
				Arrays.asList(
						OrderHibernateEntity.class,
						ProductOrderHibernateEntity.class,
						SystemCustomerHibernateEntity.class);
		return super.createSessionFactory(list);
	}
	
}
