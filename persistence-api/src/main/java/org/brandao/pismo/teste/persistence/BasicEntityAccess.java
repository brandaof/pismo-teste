package org.brandao.pismo.teste.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Provê recursos que permitem a manipulação de entidades
 * persistentes.
 * 
 * @author Brandao.
 *
 * @param <T> Tipo da entidade.
 */
public interface BasicEntityAccess<T> {

	/**
	 * Faz a persistência de uma entidade.
	 * @param value Entidade.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados da entidade. 
	 */
	void save(T value) throws EntityAccessException;
	
	/**
	 * Atualiza uma entidade.
	 * @param value Entidade.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados da entidade. 
	 */
	void update(T value) throws EntityAccessException;
	
	/**
	 * Apaga uma entidade.
	 * @param value Entidade.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar apagar 
	 * os dados da entidade. 
	 */
	void delete(T value) throws EntityAccessException;
	
	/**
	 * Obtém a entidade com uma determinada identificação.
	 * @param value Identificação.
	 * @return Entidade.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar recuperar 
	 * os dados da entidade. 
	 */
	T findById(Serializable value) throws EntityAccessException;

	/**
	 * Obtém uma coleção contendo todas as entidades existentes.
	 * @return Coleção.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar recuperar 
	 * os dados das entidades. 
	 */
	List<T> findAll() throws EntityAccessException;
	
	/**
	 * Obtém uma coleção limitada de entidades.
	 * @param first Define a partir de qual entidade encontrada se inicia a coleta. 
	 * @param max Quantidade máxima de entidades a serem coletados.
	 * @return Coleção.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar recuperar 
	 * os dados das entidades. 
	 */
	List<T> findAll(int first, int max) throws EntityAccessException;
	
	/**
	 * Inicia uma transação.
	 * @return Transação.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao 
	 * tentar iniciar a transação.
	 */
	EntityAccessTransaction beginTransaction() throws EntityAccessException;
	
	/**
	 * Força a execução das alterações.
	 */
	void flush();
	
}
