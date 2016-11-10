package org.brandao.pismo.produtos.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.pismo.produtos.teste.ProductRegistry;
import org.brandao.pismo.produtos.teste.ProductRegistryException;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.produtos.teste.entity.persistence.entity.ProductLock;
import org.brandao.pismo.produtos.teste.entity.persistence.entity.ProductLockID;
import org.brandao.pismo.produtos.teste.persistence.ProductEntityAccess;
import org.brandao.pismo.produtos.teste.persistence.ProductLockEntityAccess;
import org.brandao.pismo.produtos.teste.persistence.ProductStockEntityAccess;
import org.brandao.pismo.teste.APIServer;
import org.brandao.pismo.teste.EntityContext;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.persistence.EntityAccessException;
import org.brandao.pismo.teste.persistence.EntityAccessTransaction;

@Singleton
@Default
public class ProductRegistryImp implements ProductRegistry{

	private APIServer app;
	
	@Inject
	public ProductRegistryImp(APIServer app){
		this.app = app;
	}
	
	private ProductEntityAccess getProductEntityAccess(){
		return EntityContext.getEntity(ProductEntityAccess.class, app);
	}
	
	private ProductStockEntityAccess getProductStockEntityAccess(){
		return EntityContext.getEntity(ProductStockEntityAccess.class, app);
	}
	
	private ProductLockEntityAccess getProductLockEntityAccess(){
		return EntityContext.getEntity(ProductLockEntityAccess.class, app);
	}
	
	@Override
	public synchronized void registryProduct(Product product)
			throws ProductRegistryException {

		if(product == null){
			throw new ProductRegistryException("product");
		}
		
		if(product.getId() <= 0){
			this.save(product);
		}
		else{
			this.update(product);
		}
	}

	/**
	 * Salva o produto e cria o estoque.
	 * @param product produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao 
	 * tentar persistir os dados.
	 */
	private void save(Product product) throws ProductRegistryException{
		
		EntityAccessTransaction tx = null;
		try{
			ProductStockEntityAccess productStockEntityAccess = 
					this.getProductStockEntityAccess();
			
			ProductEntityAccess entityAccess = 
					this.getProductEntityAccess();
			
			ProductStock pe = new ProductStock();
			pe.setAvailable(0);
			pe.setProduct(product);
			
			tx = productStockEntityAccess.beginTransaction();
			entityAccess.save(product);
			entityAccess.flush();
			
			productStockEntityAccess.save(pe);
			productStockEntityAccess.flush();			
			tx.commit();
		}
		catch(Throwable e){
			try{
				if(tx != null){
					tx.rollback();
				}
			}
			catch(Throwable x){
			}
			throw new ProductRegistryException(e);
		}
		
	}
	
	/**
	 * Atualiza os dados do produto. 
	 * @param product produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao 
	 * tentar persistir os dados.
	 */
	private void update(Product product) throws ProductRegistryException{
		try{
			ProductEntityAccess entityAccess = 
					this.getProductEntityAccess();
			
			entityAccess.save(product);
			entityAccess.flush();
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
		
	}	
	@Override
	public synchronized void deleteProduct(Product product) throws ProductRegistryException {

		if(product == null){
			throw new ProductRegistryException("product", new NullPointerException());
		}

		if(product.getId() <= 0){
			throw new ProductRegistryException("product.id ", new IllegalStateException());
		}
		
		try{
			ProductEntityAccess entityAccess = this.getProductEntityAccess();
			entityAccess.delete(product);
			entityAccess.flush();
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
		
	}

	@Override
	public synchronized void lockProduct(String id, SystemCustomer customer, Product product)
			throws ProductRegistryException {

		if(customer == null){
			throw new ProductRegistryException("customer", new NullPointerException());
		}
		
		if(product == null){
			throw new ProductRegistryException("product", new NullPointerException());
		}
		
		if(id == null){
			throw new ProductRegistryException("id", new NullPointerException());
		}
		
		if(product.getId() <= 0){
			throw new ProductRegistryException("product.id ", new IllegalStateException());
		}

		if(customer.getId() <= 0){
			throw new ProductRegistryException("customer.id ", new IllegalStateException());
		}
		
		if(id.trim().length() == 0){
			throw new ProductRegistryException("id ", new IllegalStateException());
		}
		
		EntityAccessTransaction tx = null;
		try{
			ProductLockEntityAccess productLockEntityAccess = 
					this.getProductLockEntityAccess();

			ProductStockEntityAccess productStockEntityAccess = 
					this.getProductStockEntityAccess();
			
			/* A retirada do produto do estoque e seu bloqueio 
			 * deve ser um processo atomico*/
			
			tx = productLockEntityAccess.beginTransaction();
			this.lockProduct(id, product, customer, productLockEntityAccess);
			this.selectProduct(productStockEntityAccess, product);
			tx.commit();
		}
		catch(Throwable e){
			try{
				if(tx != null){
					tx.rollback();
				}
			}
			catch(Throwable x){
			}
			throw new ProductRegistryException(e);
		}
		
	}
	
	public boolean isLocked(String id, SystemCustomer customer) throws ProductRegistryException{
		
		if(customer == null){
			throw new ProductRegistryException("customer", new NullPointerException());
		}
		
		if(id == null){
			throw new ProductRegistryException("id", new NullPointerException());
		}
		
		if(customer.getId() <= 0){
			throw new ProductRegistryException("customer.id ", new IllegalStateException());
		}
		
		if(id.trim().length() == 0){
			throw new ProductRegistryException("id ", new IllegalStateException());
		}
		
		try{
			ProductLockEntityAccess productLockEntityAccess = 
					this.getProductLockEntityAccess();
			
			ProductLockID productID = new ProductLockID(id, customer);
			return productLockEntityAccess.findById(productID) != null;
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}		
		
	}
		
	@Override
	public synchronized void unlockProduct(String id, SystemCustomer customer, Product product)
			throws ProductRegistryException {

		if(customer == null){
			throw new ProductRegistryException("customer", new NullPointerException());
		}
		
		if(product == null){
			throw new ProductRegistryException("product", new NullPointerException());
		}
		
		if(id == null){
			throw new ProductRegistryException("id", new NullPointerException());
		}
		
		if(product.getId() <= 0){
			throw new ProductRegistryException("product.id ", new IllegalStateException());
		}

		if(customer.getId() <= 0){
			throw new ProductRegistryException("customer.id ", new IllegalStateException());
		}
		
		if(id.trim().length() == 0){
			throw new ProductRegistryException("id ", new IllegalStateException());
		}
		
		EntityAccessTransaction tx = null;
		try{
			ProductLockEntityAccess productLockEntityAccess = 
					this.getProductLockEntityAccess();

			ProductStockEntityAccess productStockEntityAccess = 
					this.getProductStockEntityAccess();
			
			tx = productLockEntityAccess.beginTransaction();
			this.unselectProduct(productStockEntityAccess, product);
			this.unlockProduct(id, product, customer, productLockEntityAccess);
			tx.commit();
		}
		catch(Throwable e){
			try{
				if(tx != null){
					tx.rollback();
				}
			}
			catch(Throwable x){
			}
			throw new ProductRegistryException(e);
		}		
	}

	@Override
	public ProductStock getProductStock(Product product)
			throws ProductRegistryException {
		
		if(product == null){
			throw new ProductRegistryException("product", new NullPointerException());
		}
		
		if(product.getId() <= 0){
			throw new ProductRegistryException("product.id ", new IllegalStateException());
		}
		
		try{
			ProductStockEntityAccess productStockEntityAccess = 
					this.getProductStockEntityAccess();
			
			ProductStock stock = productStockEntityAccess.findById(product.getId());
			
			if(stock == null){
				throw new IllegalStateException("não foi encontrado o estoque do produto: " + product.getId());
			}
			
			return stock;
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
		
	}

	@Override
	public List<Product> search(String name, String description,
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult,
			Integer maxResult) throws ProductRegistryException {
		
		try{
			ProductEntityAccess productEntityAccess = 
					this.getProductEntityAccess();
			
			return productEntityAccess.search(name, description, 
					minPrice, maxPrice, firstResult, maxResult);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}			
	}

	/* Métodos internos */
	
	/**
	 * Tenta registrar o bloqueio de um determinado produto.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @param productLockEntityAccess Acesso às entidades <code>{@link ProductLock}</code>.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados das entidades. 
	 */
	private void lockProduct(String id, Product product, SystemCustomer customer,
			ProductLockEntityAccess productLockEntityAccess) throws EntityAccessException{
		
		ProductLockID lockId = new ProductLockID(id, customer);
		ProductLock lock = productLockEntityAccess.findById(lockId);
		
		if(lock != null){
			throw new IllegalStateException("produto já bloqueado: " + id);
		}
		
		lock = new ProductLock();
		lock.setId(id);
		lock.setOwner(customer);
		lock.setProduct(product);
		
		productLockEntityAccess.save(lock);
		productLockEntityAccess.flush();
	}

	/**
	 * Apaga o bloqueio de um determinado produto.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @param productLockEntityAccess Acesso às entidades <code>{@link ProductLock}</code>.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados das entidades. 
	 */
	private void unlockProduct(String id, Product product, SystemCustomer customer,
			ProductLockEntityAccess productLockEntityAccess) throws EntityAccessException{
		
		ProductLock lock = productLockEntityAccess.findById(id);
		
		if(lock == null){
			throw new IllegalStateException("produto não bloqueado: " + id);
		}
		
		productLockEntityAccess.delete(lock);
		productLockEntityAccess.flush();
	}
	
	/**
	 * Remove um produto do estoque.
	 * @param entityAccess Acesso às entidades <code>{@link ProductStock}</code>.
	 * @param product Produto.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados das entidades. 
	 */
	private void selectProduct(ProductStockEntityAccess entityAccess, 
			Product product) throws EntityAccessException{
		
		ProductStock stock = entityAccess.findById(product.getId());
		int available      = stock.getAvailable();
		
		if(available <= 0 ){
			throw new IllegalStateException("available");
		}
		
		available -= 1;
		
		stock.setAvailable(available);
		entityAccess.update(stock);
		entityAccess.flush();
	}

	/**
	 * Recoloca um produto no estoque.
	 * @param entityAccess Acesso às entidades <code>{@link ProductStock}</code>.
	 * @param product Produto.
	 * @throws EntityAccessException Lançada se ocorrer alguma falha ao tentar persistir 
	 * os dados das entidades. 
	 */
	private void unselectProduct(ProductStockEntityAccess entityAccess, 
			Product product) throws EntityAccessException{
		
		ProductStock stock = entityAccess.findById(product.getId());
		int available      = stock.getAvailable();
		
		available += 1;
		
		stock.setAvailable(available);
		entityAccess.update(stock);
		entityAccess.flush();
	}
	
}
