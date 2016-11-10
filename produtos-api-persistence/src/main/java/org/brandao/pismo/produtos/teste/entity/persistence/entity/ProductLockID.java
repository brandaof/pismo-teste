package org.brandao.pismo.produtos.teste.entity.persistence.entity;

import java.io.Serializable;

import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Identificação de um bloqueio de produto.
 * 
 * @author Brandao.
 *
 */
public class ProductLockID 
	implements Serializable{

	private static final long serialVersionUID = 6844262373018350338L;

	private String id;
	
	private SystemCustomer owner;
	
	public ProductLockID(ProductLock entity){
		this.id = entity.getId();
		this.owner = entity.getOwner();
	}

	public ProductLockID(String id, SystemCustomer owner) {
		this.id = id;
		this.owner = owner;
	}

	/**
	 * Obtém a identificação do bloqueio.
	 * @return Identificação.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtém o proprietário do bloqueio.
	 * @return Proprietário.
	 */
	public SystemCustomer getOwner() {
		return owner;
	}

}
