package org.brandao.pismo.produtos.teste.persistence.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.brandao.pismo.produtos.teste.entity.Price;
import org.brandao.pismo.produtos.teste.entity.Product;

@Entity
@Table(name="rw_product")
public class ProductHibernateEntity {

	@Id
	@GeneratedValue
	@Column(name="cod_product")
	private int id;
	
	@Column(name="dsc_name")
	private String name;
	
	@Column(name="dsc_description")
	private String description;

	@Embedded
	private PriceHibernateEntity price;

	public ProductHibernateEntity() {
	}

	public ProductHibernateEntity(Product entity) {
		this.setDescription(entity.getDescription());
		this.setId(entity.getId());
		this.setName(entity.getName());
		
		Price price = entity.getPrice();
		if(price != null){
			PriceHibernateEntity ePrice = new PriceHibernateEntity();
			ePrice.setCurrency(price.getCurrency());
			ePrice.setValue(price.getValue());
			this.setPrice(ePrice);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public PriceHibernateEntity getPrice() {
		return price;
	}

	public void setPrice(PriceHibernateEntity price) {
		this.price = price;
	}

	public Product toEntity(){
		Product e = new Product();
		e.setDescription(this.getDescription());
		e.setId(this.getId());
		e.setName(this.getName());
		
		PriceHibernateEntity price = this.getPrice();
		
		if(price != null){
			Price ePrice = new Price();
			price.setCurrency(price.getCurrency());
			price.setValue(price.getValue());
			e.setPrice(ePrice);
		}
		
		return e;		
	}
}
