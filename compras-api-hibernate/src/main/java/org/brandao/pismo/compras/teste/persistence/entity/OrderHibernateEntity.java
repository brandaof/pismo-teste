package org.brandao.pismo.compras.teste.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.compras.teste.entity.ProductOrder;

@Entity
@Table(name="rw_order")
public class OrderHibernateEntity implements Serializable{

	private static final long serialVersionUID = 907076088275757931L;

	@Id
	@Column(name="cod_order")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_customer")
	private SystemCustomerHibernateEntity owner;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="order", cascade=CascadeType.ALL)
	private List<ProductOrderHibernateEntity> products;

	public OrderHibernateEntity(){
	}
	
	public OrderHibernateEntity(Order order){
		this.setId(order.getId());
		this.setOwner(order.getCustomer() == null? null :  new SystemCustomerHibernateEntity(order.getCustomer()));
		
		List<ProductOrder> list = order.getProducts();
		
		if(list != null){
			List<ProductOrderHibernateEntity> products = new ArrayList<ProductOrderHibernateEntity>();
			
			for(ProductOrder o: list){
				products.add(new ProductOrderHibernateEntity(this, o));
			}
			this.setProducts(products);
		}

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemCustomerHibernateEntity getOwner() {
		return owner;
	}

	public void setOwner(SystemCustomerHibernateEntity owner) {
		this.owner = owner;
	}

	public List<ProductOrderHibernateEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductOrderHibernateEntity> products) {
		this.products = products;
	}
	
	public Order toEntity(){
		Order order = new Order();
		order.setCustomer(this.owner == null? null : this.owner.toEntity());
		order.setId(this.id);
		
		if(this.products != null){
			
			List<ProductOrder> products = new ArrayList<ProductOrder>();
			
			for(ProductOrderHibernateEntity o: this.products){
				products.add(o.toEntity(order));
			}
			order.setProducts(products);
		}
		
		return order;
	}
}
