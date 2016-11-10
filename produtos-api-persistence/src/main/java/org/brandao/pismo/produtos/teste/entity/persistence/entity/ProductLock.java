package org.brandao.pismo.produtos.teste.entity.persistence.entity;

import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Representa o bloqueio de um produto.
 * 
 * @author Brandao.
 *
 */
public class ProductLock {

	private String id;
	
	private Product product;
	
	private SystemCustomer owner;

	/**
	 * Obtém a identificação.
	 * @return Identificação.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Define a identificação.
	 * @param id Identificação.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtém o produto.
	 * @return Produto.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Define o produto.
	 * @param product Produto.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Obtém a origem do bloqueio.
	 * @return Origem.
	 */
	public SystemCustomer getOwner() {
		return owner;
	}

	/**
	 * Define a origem do bloqueio.
	 * @param owner Origem.
	 */
	public void setOwner(SystemCustomer owner) {
		this.owner = owner;
	}
	
}
