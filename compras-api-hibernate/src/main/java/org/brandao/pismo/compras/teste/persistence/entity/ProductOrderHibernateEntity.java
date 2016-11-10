package org.brandao.pismo.compras.teste.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.compras.teste.entity.OrderStatus;
import org.brandao.pismo.compras.teste.entity.ProductOrder;
import org.brandao.pismo.produtos.teste.entity.Price;
import org.brandao.pismo.produtos.teste.entity.Product;

@Entity
@Table(name="rw_product_order")
public class ProductOrderHibernateEntity 
	implements Serializable{

	private static final long serialVersionUID = 657467049748965343L;

	@Id
	@Column(name="cod_product_order")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="cod_order", nullable=false)
	private OrderHibernateEntity order;
	
	@Column(name="vlr_cod_product", nullable=false)
	private int productId;
	
	@Column(name="dsc_name_product", nullable=false)
	private String name;
	
	@Column(name="dsc_description_product", nullable=false)
	private String description;
	
	@Column(name="vlr_price_product", nullable=false)
	private BigDecimal value;
	
	@Column(name="vlr_currency_product", nullable=false)
	private String currency;
	
	@Enumerated
	@Column(name="set_status", nullable=false)
	private OrderStatus status;

	public ProductOrderHibernateEntity(){
	}
	
	public ProductOrderHibernateEntity(OrderHibernateEntity order, ProductOrder entity){
		Product product = entity.getProduct();
		if(product != null){
			Price price = product.getPrice();
			
			if(price != null){
				this.currency = price.getCurrency();
				this.value = price.getValue();
			}
			
			this.description = product.getDescription();
			this.name = product.getName();
			this.productId = product.getId();
		}
		
		this.id = entity.getId();
		this.order = order;
		this.status = entity.getStatus();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderHibernateEntity getOrder() {
		return order;
	}

	public void setOrder(OrderHibernateEntity order) {
		this.order = order;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public ProductOrder toEntity(Order order){
		
		Price price = new Price();
		price.setCurrency(this.currency);
		price.setValue(this.value);

		Product product = new Product();
		product.setDescription(this.description);
		product.setId(this.productId);
		product.setName(this.name);
		product.setPrice(price);
		
		ProductOrder productOrder = new ProductOrder();
		productOrder.setId(this.id);
		productOrder.setProduct(product);
		productOrder.setStatus(this.status);
		
		return productOrder;
	}
}
