package org.brandao.pismo.compras.teste;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.brandao.pismo.produtos.teste.ProdutosAPIServer;

public class TestProdutosAPIServer 
	extends ProdutosAPIServer{

	protected <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		Class<?> clazz = 
				pat.getAnnotatedType().getJavaClass();
		
		String typePackageName = clazz.getPackage().getName();
		if(typePackageName.startsWith("org.brandao.pismo.compras")){
			pat.veto();
		}
	}
	
}
