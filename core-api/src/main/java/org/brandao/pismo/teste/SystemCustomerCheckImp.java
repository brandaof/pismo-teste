package org.brandao.pismo.teste;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.persistence.SystemCustomerEntityAccess;
import org.brandao.pismo.teste.util.AESCrypt;

@Default
@Singleton
public class SystemCustomerCheckImp 
	implements SystemCustomerCheck{

	private APIServer app;
	
	@Inject
	public SystemCustomerCheckImp(APIServer app) {
		this.app = app;
	}
	
	@Override
	public SystemCustomer accept(String user, String pass) {
		try{
			SystemCustomerEntityAccess entityAccess = 
					EntityContext.getEntity(SystemCustomerEntityAccess.class, this.app);
			
			SystemCustomer customer = entityAccess.findByName(user);
			
			if(customer == null)
				return null;
			
			pass = AESCrypt.crypt(pass);
			
			if(customer.getPassword().equals(pass)){
				return customer;
			}
			else{
				return null;
			}
		}
		catch(Throwable e){
			return null;
		}
	}

}
