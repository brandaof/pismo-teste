package org.brandao.pismo.teste.persistence.hibernate;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Properties;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Classe base de lançamento do Hibernate.
 * 
 * @author Brandao.
 *
 */
public class AbstractHibernateInitializer {

	protected Properties configuration;
	
	/**
	 * Cria uma instância a partir de uma 
	 * determinada configuração.
	 * @param configuration Configuração.
	 */
	public AbstractHibernateInitializer(Properties configuration){
		this.configuration = configuration;
	}
	
	/**
	 * Abre uma sessão com a base de dados a partir de uma fábrica.
	 * @param sessionFactory Fábrica.
	 * @return  Sessão.
	 */
	public Session openSession(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.AUTO);
		return session;
	}
	
	/**
	 * Inicia a fábrica de sessões a partir de uma lista de 
	 * entidades persistentes.
	 * @param list Entidades.
	 * @return Fábrica.
	 * @throws PropertyVetoException {@see PropertyVetoException}
	 */
	public SessionFactory createSessionFactory(List<Class<?>> list) 
			throws PropertyVetoException {
		
		org.hibernate.cfg.Configuration config = 
				new org.hibernate.cfg.Configuration();
		
		config.addProperties(this.configuration);
		
		for(Class<?> clazz: list){
			config.addAnnotatedClass(clazz);
		}
		
		/*
		SchemaExport ex = new SchemaExport(config);
		ex.create(true, true);
		*/
		
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(config.getProperties());
		ServiceRegistry serviceRegistry = builder.buildServiceRegistry(); 	
		return config.buildSessionFactory(serviceRegistry);
	}
	
}
