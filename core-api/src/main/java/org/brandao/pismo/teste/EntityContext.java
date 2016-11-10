package org.brandao.pismo.teste;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

/**
 * Provê as entidades de uma aplicação.
 * 
 * @author Brandao.
 *
 */
public final class EntityContext
	implements Extension{

	/**
	 * Obtém uma entidade de uma determinada aplicação.
	 * @param type Tipo da entidade.
	 * @param app Aplicação.
	 * @return Entidade.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEntity(Class<?> type, Extension app){
		BeanManager beanManager = ((APIServer)app).getBeanManager();
		
		if(beanManager == null){
			throw new IllegalStateException("beanManager");
		}
		
		Bean<T> bean = (Bean<T>)beanManager.getBeans(type).iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        Object obj = beanManager.getReference(bean, bean.getBeanClass(), ctx);
        return (T) obj;
	}

	/**
	 * Obtém uma entidade de uma determinada aplicação.
	 * @param name Nome da entidade.
	 * @param app Aplicação.
	 * @return Entidade.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEntity(String name, Extension app){
		BeanManager beanManager = ((APIServer)app).getBeanManager();
		
		if(beanManager == null){
			throw new IllegalStateException("beanManager");
		}
		
		Set<Bean<?>> set = beanManager.getBeans(name);
		Bean<T> bean = (Bean<T>)set.iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        Object obj = beanManager.getReference(bean, bean.getBeanClass(), ctx);
        return (T) obj;
	}
	
}
