package org.brandao.pismo.produtos.teste.persistence.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductlockPK implements Serializable{

	private static final long serialVersionUID = 8448731474374047063L;

	@Column(name="cod_lock")
	private String id;
	
	@Column(name="cod_customer")
	private long owner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}
	
}
