package org.brandao.pismo.teste.persistence;

import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Prevê recursos que permitem manipular os dados persistentes da
 * entidade <code>{@link SystemCustomer}</code>.
 * 
 * @author Brandao
 *
 */
public interface SystemCustomerEntityAccess 
	extends BasicEntityAccess<SystemCustomer>{

	/**
	 * Obtém um usuário a partir de seu nome.
	 * @param name Nome.
	 * @return Usuário.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar recuperar 
	 * os dados da entidade. 
	 */
	SystemCustomer findByName(String name) throws EntityAccessException;
	
}
