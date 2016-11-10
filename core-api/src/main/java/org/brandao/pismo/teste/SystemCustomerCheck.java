package org.brandao.pismo.teste;

import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Verifica a credencial de um determinado usuário.
 * 
 * @author Brandao.
 *
 */
public interface SystemCustomerCheck {

	/**
	 * Verfiica o usuário e senha de um usuário.
	 * @param user Identificação.
	 * @param pass Senha.
	 * @return Usuário.
	 */
	SystemCustomer accept(String user, String pass);
	
}
