package org.brandao.pismo.compras.teste;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.brandao.pismo.compras.teste.ComprasAPIServer;

public class TestComprasAPIServer 
	extends ComprasAPIServer{

	protected <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		Class<?> clazz = 
				pat.getAnnotatedType().getJavaClass();
		
		String typePackageName = clazz.getPackage().getName();
		if(typePackageName.startsWith("org.brandao.pismo.produtos")){
			pat.veto();
		}
	}
	
}
