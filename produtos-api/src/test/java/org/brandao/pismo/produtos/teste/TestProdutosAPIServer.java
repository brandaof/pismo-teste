package org.brandao.pismo.produtos.teste;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

public class TestProdutosAPIServer 
	extends ProdutosAPIServer{

	protected <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		Class<?> clazz = 
				pat.getAnnotatedType().getJavaClass();
		
		if(clazz.getName().indexOf(".ServerConfigurationLoader") != -1 ||
			clazz.getName().indexOf(".HibernateInitializer") != -1){
			pat.veto();
		}
		
	}
	
}
