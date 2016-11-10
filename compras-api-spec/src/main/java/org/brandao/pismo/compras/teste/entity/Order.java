package org.brandao.pismo.compras.teste.entity;

import java.util.List;

import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Representa um pedido.
 * 
 * @author Brandao.
 *
 */
public class Order {

	private String id;
	
	private SystemCustomer customer;
	
	private List<ProductOrder> products;

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
	 * Obtém o usuário.
	 * @return Usuário.
	 */
	public SystemCustomer getCustomer() {
		return customer;
	}

	/**
	 * Define o usuário.
	 * @param customer Usuário.
	 */
	public void setCustomer(SystemCustomer customer) {
		this.customer = customer;
	}

	/**
	 * Obtém os produtos.
	 * @return Produtos.
	 */
	public List<ProductOrder> getProducts() {
		return products;
	}

	/**
	 * Define os produtos.
	 * @param products Produtos.
	 */
	public void setProducts(List<ProductOrder> products) {
		this.products = products;
	}
	
	public OrderStatus getStatus(){
		if(this.products == null)
			return null;
		
		boolean allConfirmed = true;
		boolean allCanceled  = true;
		boolean pending      = false;
		for(ProductOrder product: this.products){
			
			OrderStatus status = product.getStatus();
			
			allConfirmed = allConfirmed  && status == OrderStatus.CONFIRMED;
			allCanceled  = allCanceled  && status == OrderStatus.CANCELED;
			pending      = pending || status == null || status == OrderStatus.PENDING;
			
		}
		
		return 
			pending? 
				OrderStatus.PENDING :
				(allConfirmed? OrderStatus.CONFIRMED : OrderStatus.CANCELED);
	}
	
	/*
	 * Obtém o valor total.
	 * @return Valor.
	 */
	//Removido por conta de problemas a decodificação do json no cliente
	/*
	public Price getTotal(){
		if(this.products == null)
			return null;
		
		BigDecimal total = BigDecimal.ZERO;
		String currency  = null;
		
		for(ProductOrder product: this.products){
			Price p = product.getProduct().getPrice();
			total = total.add(p.getValue());
			
			if(currency != null && currency.equals(p.getValue())){
				throw new IllegalStateException("Price.currency");
			}
			else
			if(currency == null){
				currency = p.getCurrency();
			}
		}
		
		if(currency == null)
			throw new IllegalStateException("currency");

		return new Price(total, currency);
	}
	*/	
}
