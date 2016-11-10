package org.brandao.pismo.produtos.teste.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.persistence.BasicEntityAccess;
import org.brandao.pismo.teste.persistence.EntityAccessException;

/**
 * Prevê recursos que permitem manipular os dados persistentes da
 * entidade <code>{@link Product}</code>.
 * 
 * @author Brandao
 *
 */
public interface ProductEntityAccess 
	extends BasicEntityAccess<Product>{
	
	/**
	 * Recupera uma coleção de produtos que adequem-se aos critérios informados. 
	 * @param name Nome.
	 * @param description Descrição.
	 * @param minPrice Preço mínimo.
	 * @param maxPrice Preço máximo.
	 * @param firstResult Define a partir de qual produto encontrado se inicia a coleta. 
	 * @param maxResult Quantidade máxima de produtos a serem coletados.
	 * @return Coleção.
	 * @throws EntityAccessException Lançada se ocorrer alguma 
	 * falha ao tentar coletar os produtos.
	 */
	
	List<Product> search(String name, String description, 
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult, 
			Integer maxResult) throws EntityAccessException;

}
