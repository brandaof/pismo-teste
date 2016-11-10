package org.brandao.pismo.teste;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.environment.se.WeldContainer;

/**
 * Representa o servidor da app.
 * 
 * @author Brandao.
 *
 */
public interface APIServer extends Extension{

	/**
	 * Obtém o gerênciador de entidades da aplicação.
	 * @return Gerênciador.
	 */
    BeanManager getBeanManager();
 
    /**
     * Obtém o container IoC da aplicação.
     * @return Container.
     */
    WeldContainer getContainer();
    
}
