package org.brandao.pismo.produtos.teste.entity;

import java.math.BigDecimal;

/**
 * Representa o preço de um produto.
 * 
 * @author Brandao
 *
 */
public class Price {

	private BigDecimal value;
	
	private String currency;

	public Price() {
	}
	
	public Price(BigDecimal value, String currency) {
		this.value = value;
		this.currency = currency;
	}

	/**
	 * Obtém o valor.
	 * @return Valor.
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Define o valor.
	 * @param value Valor.
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * Obtém a moeda. No formato ISO 4217.
	 * @return Moeda.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Define a moeda.
	 * @param currency Moeda.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
