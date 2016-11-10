package org.brandao.pismo.produtos.teste.persistence.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.brandao.pismo.produtos.teste.entity.ProductStock;

@Entity
@Table(name="rw_product_stock")
public class ProductStockHibernateEntity {

	@Id
	@Column(name="cod_stock")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_product", nullable=false)
	private ProductHibernateEntity product;
	
	@Column(name="vlr_stock", updatable=true, nullable=false)
	private int available;

	public ProductStockHibernateEntity(){
	}
	
	public ProductStockHibernateEntity(ProductStock entity){
		this.setAvailable(entity.getAvailable());
		this.setProduct(entity.getProduct() == null? null : new ProductHibernateEntity(entity.getProduct()));
	}
	
	public int getId() {
		return id;
	}

	public ProductHibernateEntity getProduct() {
		return product;
	}

	public void setProduct(ProductHibernateEntity product) {
		this.product = product;
		this.id = product == null? 0 : product.getId();
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public ProductStock toEntity(){
		ProductStock e = new ProductStock();
		e.setAvailable(this.getAvailable());
		e.setProduct(this.product == null? null : this.product.toEntity());
		
		return e;
	}
}
