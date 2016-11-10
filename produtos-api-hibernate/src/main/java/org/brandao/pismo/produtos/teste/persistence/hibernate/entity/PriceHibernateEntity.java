package org.brandao.pismo.produtos.teste.persistence.hibernate.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PriceHibernateEntity {

	@Column(name="vlr_price_value")
	private BigDecimal value;
	
	@Column(name="vlr_currency_value")
	private String currency;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
