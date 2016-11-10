package org.brandao.pismo.compras.teste;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.brandao.pismo.produtos.teste.ProductRegistry;
import org.brandao.pismo.produtos.teste.ProductRegistryException;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.client.ProdutosClient;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.jboss.weld.exceptions.UnsupportedOperationException;

@Singleton
@Default
public class ProductRegistryImp 
	implements ProductRegistry{

	private ProdutosClient client;
	
	@Inject
	public ProductRegistryImp(
			@Named("server_config")
			Properties configuration) {

		String productHost   = configuration.getProperty("product.service.host", "localhost");
		int productPort   = Integer.parseInt(configuration.getProperty("product.service.port", "8085"));
		
		this.client = new ProdutosClient(productHost, productPort);
	}

	@Override
	public void registryProduct(Product product)
			throws ProductRegistryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteProduct(Product product) throws ProductRegistryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void lockProduct(String id, SystemCustomer customer, Product product)
			throws ProductRegistryException {
		this.client.lockProduct(id, customer, product);
	}

	@Override
	public boolean isLocked(String id, SystemCustomer customer)
			throws ProductRegistryException {
		return this.isLocked(id, customer);
	}

	@Override
	public void unlockProduct(String id, SystemCustomer customer,
			Product product) throws ProductRegistryException {
		this.client.unlockProduct(id, customer, product);
	}

	@Override
	public ProductStock getProductStock(Product product)
			throws ProductRegistryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Product> search(String name, String description,
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult,
			Integer maxResult) throws ProductRegistryException {
		throw new UnsupportedOperationException();
	}
	
}
