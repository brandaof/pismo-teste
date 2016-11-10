package org.brandao.pismo.compras.teste.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.brandao.pismo.teste.entity.SystemCustomer;

@Entity
@Table(name="rw_customer")
public class SystemCustomerHibernateEntity {

	@Id
	@GeneratedValue
	@Column(name="cod_customer")
	private long id;
	
	@Column(name="dsc_name")
	private String name;
	
	@Column(name="dsc_pass")
	private String password;

	public SystemCustomerHibernateEntity(){
	}
	
	public SystemCustomerHibernateEntity(SystemCustomer entity){
		this.setId(entity.getId());
		this.setName(entity.getName());
		this.setPassword(entity.getPassword());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SystemCustomer toEntity(){
		SystemCustomer e = new SystemCustomer();
		e.setId(this.getId());
		e.setName(this.getName());
		e.setPassword(this.getPassword());
		return e;
	}
}
