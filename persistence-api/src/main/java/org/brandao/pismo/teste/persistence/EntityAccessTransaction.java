package org.brandao.pismo.teste.persistence;

/**
 * Provê recursos que permitem a manipulação de uma transação.  
 * 
 * @author Brandao
 *
 */
public interface EntityAccessTransaction {

	/**
	 * Confirma a transação.
	 * @throws EntityAccessException Lançada se alguma falha ocorrer 
	 * ao confirmar a transação.
	 */
	void commit() throws EntityAccessException;
	
	/**
	 * Cancela a transação.
	 * @throws EntityAccessException Lançada se alguma falha ocorrer 
	 * ao cancelar a transação.
	 */
	void rollback() throws EntityAccessException;
	
}
