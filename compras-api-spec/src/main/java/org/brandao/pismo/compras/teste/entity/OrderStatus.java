package org.brandao.pismo.compras.teste.entity;

/**
 * Descreve os status de um pedido.
 * 
 * @author Brandao
 *
 */
public enum OrderStatus {

	/**
	 * O pedido está pendente.
	 */
	PENDING,
	
	/**
	 * O pedido está confirmado.
	 */
	CONFIRMED,
	
	/**
	 * O pedido está cancelado.
	 */
	CANCELED,
	
	/**
	 * O pedido está pendente de cancelamento.
	 */
	PENDING_CANCEL;
	
}
