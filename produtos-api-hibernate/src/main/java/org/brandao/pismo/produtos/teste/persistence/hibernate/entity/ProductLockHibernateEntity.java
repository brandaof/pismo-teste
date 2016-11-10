package org.brandao.pismo.produtos.teste.persistence.hibernate.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.brandao.pismo.produtos.teste.entity.persistence.entity.ProductLock;

@Entity
@Table(name="rw_product_lock")
public class ProductLockHibernateEntity {

	@EmbeddedId
	private ProductlockPK pk;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_product")
	private ProductHibernateEntity product;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_customer", insertable=false, updatable=false)
	private SystemCustomerHibernateEntity owner;

	public ProductLockHibernateEntity(){
		this.pk = new ProductlockPK();
	}
	
	public ProductLockHibernateEntity(ProductLock entity){
		this.pk = new ProductlockPK();
		this.setId(entity.getId());
		this.setOwner(entity.getOwner() == null? null :  new SystemCustomerHibernateEntity(entity.getOwner()));
		this.setProduct(entity.getProduct() == null? null :  new ProductHibernateEntity(entity.getProduct()));
	}
	
	public void setPk(ProductlockPK pk) {
		this.pk = pk;
	}

	public ProductlockPK getPk() {
		return pk;
	}

	public String getId() {
		return this.pk.getId();
	}

	public void setId(String id) {
		this.pk.setId(id);
	}

	public ProductHibernateEntity getProduct() {
		return product;
	}

	public void setProduct(ProductHibernateEntity product) {
		this.product = product;
	}

	public SystemCustomerHibernateEntity getOwner() {
		return owner;
	}

	public void setOwner(SystemCustomerHibernateEntity owner) {
		this.owner = owner;
		this.pk.setOwner(owner == null? null : owner.getId());
	}

	public ProductLock toEntity(){
		ProductLock e = new ProductLock();
		e.setId(this.getId());
		e.setOwner(this.getOwner() == null? null : this.owner.toEntity());
		e.setProduct(this.getProduct() == null? null :  this.product.toEntity());
		return e;
	}
}
