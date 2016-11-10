package org.brandao.pismo.compras.teste.entity;

import org.brandao.pismo.produtos.teste.entity.Product;

/**
 * Representa um pedido de produto.
 * 
 * @author Brandao.
 *
 */
public class ProductOrder {

	private String id;
	
	private Product product;
	
	private OrderStatus status;
	
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
	 * Obtém o status;
	 * @return Status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * Define o status.
	 * @param status Status.
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
}
