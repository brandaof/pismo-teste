package org.brandao.pismo.produtos.teste.entity;

/**
 * Representa um produto.
 * 
 * @author Brandao.
 *
 */
public class Product {

	private int id;
	
	private String name;
	
	private String description;
	
	private Price price;

	/**
	 * Obtém a identificação;
	 * @return Identificação.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Define a identifição.
	 * @param id Identificação.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtém o nome.
	 * @return Nome.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define o nome.
	 * @param name Nome.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtém a descrição.
	 * @return Descrição.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Define a descrição.
	 * @param description Descrição.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obtém o preço.
	 * @return Preço.
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * Define o preço.
	 * @param price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}
	
}
