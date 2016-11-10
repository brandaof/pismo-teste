package org.brandao.pismo.produtos.teste;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.brandao.pismo.produtos.teste.persistence.hibernate.HibernateInitializer;
import org.brandao.pismo.produtos.teste.persistence.hibernate.entity.SystemCustomerHibernateEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;

@Singleton
public class TestHibernateInitializer 
	extends HibernateInitializer{

	@Inject
	public TestHibernateInitializer(
			@Named("server_config")
			Properties configuration) {
		super(configuration);
	}

	@Produces
	@RequestScoped
	public Session openSession(SessionFactory sessionFactory){
		return super.openSession(sessionFactory);
	}

	public void close(@Disposes Session session){
		session.close();
	}
	
	@Produces
	@Singleton
	public SessionFactory createSessionFactory() 
			throws PropertyVetoException {
		SessionFactory factory = super.createSessionFactory();
		
		org.hibernate.cfg.Configuration config = 
				new org.hibernate.cfg.Configuration();
		
		config.addProperties(this.configuration);
		
		SchemaExport ex = new SchemaExport(config);
		ex.create(true, true);
		
		Session session = factory.openSession();
		SystemCustomerHibernateEntity e = new SystemCustomerHibernateEntity();
		e.setName("teste");
		e.setPassword("jUj9bKW8652XRQLotzx1kA==");
		session.persist(e);
		session.flush();
		session.close();
		return factory;
	}
	
}
