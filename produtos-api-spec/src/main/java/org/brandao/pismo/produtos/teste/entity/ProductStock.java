package org.brandao.pismo.produtos.teste.entity;

/**
 * Representa o estoque de um determinado produto.
 * 
 * @author Brandao.
 *
 */
public class ProductStock {

	private int id;
	
	private Product product;
	
	private int available;

	/**
	 * Obtém a identificação.
	 * @return Identificação.
	 */
	public int getId() {
		return id;
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
		this.id = product == null? 0 : product.getId(); 
	}

	/**
	 * Obtém a quantidade de produtos disponíveis.
	 * @return Quantidade.
	 */
	public int getAvailable() {
		return available;
	}

	/**
	 * Define a quantidade de produtos disponíveis.
	 * @param available Quantidade.
	 */
	public void setAvailable(int available) {
		this.available = available;
	}
	
}
