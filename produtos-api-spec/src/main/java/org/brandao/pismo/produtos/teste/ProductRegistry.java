package org.brandao.pismo.produtos.teste;

import java.math.BigDecimal;
import java.util.List;

import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Provê recursos que permitem a manipulação de produtos.
 * 
 * @author Brandao
 *
 */
public interface ProductRegistry {

	/**
	 * Registra ou atualizar um determinado produto.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao 
	 * tentar registrar ou atualizar o produto.
	 */
	void registryProduct(Product product) throws ProductRegistryException;

	/**
	 * Apaga um determinado produto.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar
	 * apagar o produto.
	 */
	void deleteProduct(Product product) throws ProductRegistryException;
	
	/**
	 * Bloqueia um produto do estoque para a venda.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no bloqueio.
	 */
	void lockProduct(String id, SystemCustomer customer, Product product) throws ProductRegistryException;

	/**
	 * Verifica se um produto está bloqueado.
	 * @param id Identificação.
	 * @param customer Usuário do sistema.
	 * @return Verdadeiro se o produto está bloqueado. Caso contrário, falso.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar 
	 * verificar o bloqueio.
	 */
	boolean isLocked(String id, SystemCustomer customer) throws ProductRegistryException;
	
	/**
	 * Desbloqueia um produto do estoque.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no desbloqueio.
	 */
	void unlockProduct(String id, SystemCustomer customer, Product product) throws ProductRegistryException;
	
	/**
	 * Obtém informações do estoque de um determinado produto.
	 * @param product Produto.
	 * @return Estoque.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar obter 
	 * as informações sobre o estoque do produto.
	 */
	ProductStock getProductStock(Product product) throws ProductRegistryException;
	
	/**
	 * Recupera uma coleção de produtos que adequem-se aos critérios informados. 
	 * @param name Nome.
	 * @param description Descrição.
	 * @param minPrice Preço mínimo.
	 * @param maxPrice Preço máximo.
	 * @param firstResult Define a partir de qual produto encontrado se inicia a coleta. 
	 * @param maxResult Quantidade máxima de produtos a serem coletados.
	 * @return Coleção.
	 * @throws ProductRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar coletar os produtos.
	 */
	List<Product> search(String name, String description, 
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult, 
			Integer maxResult) throws ProductRegistryException;
	
}
