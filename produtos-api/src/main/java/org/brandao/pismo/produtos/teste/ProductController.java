package org.brandao.pismo.produtos.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.pismo.produtos.teste.ProductRegistry;
import org.brandao.pismo.produtos.teste.ProductRegistryException;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Provê os recursos necessários que permitem a 
 * manipulação dos produtos.
 * 
 * @author Brandao
 *
 */
@Singleton
public class ProductController {

	private ProductRegistry registry;
	
	@Inject
	public ProductController(ProductRegistry registry){
		this.registry = registry;
	}
	
	/**
	 * Registra ou atualizar um determinado produto.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao 
	 * tentar registrar ou atualizar o produto.
	 */
	public long registryProduct(Product product) throws ProductRegistryException{
		this.registry.registryProduct(product);
		return product.getId();
	}
	
	/**
	 * Apaga um determinado produto.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar
	 * apagar o produto.
	 */
	public void deleteProduct(Product product) throws ProductRegistryException{
		this.registry.registryProduct(product);
	}
	
	/**
	 * Bloqueia um produto do estoque para a venda.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no bloqueio.
	 */
	public void lockProduct(String id, SystemCustomer customer, Product product) throws ProductRegistryException{
		this.registry.lockProduct(id, customer, product);
	}
	
	/**
	 * Verifica se um produto está bloqueado.
	 * @param id Identificação.
	 * @param customer Usuário do sistema.
	 * @return Verdadeiro se o produto está bloqueado. Caso contrário, falso.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar 
	 * verificar o bloqueio.
	 */
	public boolean isLocked(String id, SystemCustomer customer) throws ProductRegistryException{
		return this.registry.isLocked(id, customer);
	}
	
	/**
	 * Desbloqueia um produto do estoque.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no desbloqueio.
	 */
	public void unlockProduct(String id, SystemCustomer customer, Product product) throws ProductRegistryException{
		this.registry.unlockProduct(id, customer, product);
	}
	
	/**
	 * Obtém informações do estoque de um determinado produto.
	 * @param product Produto.
	 * @return Estoque.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar obter 
	 * as informações sobre o estoque do produto.
	 */
	public ProductStock getProductStock(Product product) throws ProductRegistryException{
		return this.registry.getProductStock(product);
	}
	
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
	public List<Product> search(String name, String description, 
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult, 
			Integer maxResult) throws ProductRegistryException{
		return this.registry.search(name, description, minPrice, maxPrice, firstResult, maxResult);
	}
	
}
