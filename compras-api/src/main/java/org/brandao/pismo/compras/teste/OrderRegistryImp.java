package org.brandao.pismo.compras.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.pismo.compras.teste.OrderRegistry;
import org.brandao.pismo.compras.teste.OrderRegistryException;
import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.compras.teste.entity.OrderStatus;
import org.brandao.pismo.compras.teste.entity.ProductOrder;
import org.brandao.pismo.compras.teste.persistence.OrderEntityAccess;
import org.brandao.pismo.produtos.teste.ProductRegistry;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIServer;
import org.brandao.pismo.teste.EntityContext;
import org.brandao.pismo.teste.entity.SystemCustomer;


@Singleton
public class OrderRegistryImp implements OrderRegistry{

	private ProductRegistry productRegistry;
	
	private APIServer app;
	
	@Inject
	public OrderRegistryImp(
			ProductRegistry productRegistry, APIServer app){
		this.productRegistry = productRegistry; 
		this.app = app;
	}
	
	private OrderEntityAccess getOrderEntityAccess(){
		return EntityContext.getEntity(OrderEntityAccess.class, app);
	}
	
	public Order registryOrder(SystemCustomer customer, List<Product> list)
			throws OrderRegistryException {
		
		try{
			Order o = this.createOrder(customer, list);
			
			OrderEntityAccess entityAccess = this.getOrderEntityAccess();
			
			entityAccess.save(o);
			entityAccess.flush();
			
			this.purchase(o, customer);

			entityAccess.update(o);
			entityAccess.flush();
			return o;
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}

	public void updateOrder(Order order, SystemCustomer customer) throws OrderRegistryException {

		try{
			OrderEntityAccess entityAccess = this.getOrderEntityAccess();
			
			this.markToUpdate(order);
			entityAccess.update(order);
			entityAccess.flush();
			
			this.update(order, customer);
			
			entityAccess.update(order);
			entityAccess.flush();
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
		
	}

	public void cancelOrder(Order order, SystemCustomer customer) throws OrderRegistryException {

		try{
			OrderEntityAccess entityAccess = this.getOrderEntityAccess();
			this.cancel(order, customer);
			entityAccess.update(order);
			entityAccess.flush();
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
		
	}
	
	public Order getOrder(String id, SystemCustomer customer)
			throws OrderRegistryException {
		
		try{
			OrderEntityAccess entityAccess = this.getOrderEntityAccess();
			return entityAccess.findById(id);
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}

	/* métodos internos */
	
	private Order createOrder(SystemCustomer customer, List<Product> list){
		Order o = new Order();
		o.setId(UUID.randomUUID().toString());
		
		List<ProductOrder> itens = new ArrayList<ProductOrder>();
		for(Product p: list){
			ProductOrder pOrder = new ProductOrder();
			pOrder.setId(UUID.randomUUID().toString());
			pOrder.setProduct(p);
			pOrder.setStatus(OrderStatus.PENDING);
			itens.add(pOrder);
		}
		o.setProducts(itens);
		o.setCustomer(customer);
		
		return o;
	}
	
	private void markToUpdate(Order order){
		for(ProductOrder p: order.getProducts()){
			if(p.getStatus() == OrderStatus.CANCELED){
				p.setStatus(OrderStatus.PENDING_CANCEL);
			}
			else
			if(p.getStatus() == OrderStatus.CONFIRMED || p.getStatus() == null){
				p.setStatus(OrderStatus.PENDING);
			}
		}
		
	}
	
	private void purchase(Order order, SystemCustomer customer){
		for(ProductOrder p: order.getProducts()){
			this.purchaseProduct(p, customer);
		}
	}

	private void update(Order order, SystemCustomer customer){
		for(ProductOrder p: order.getProducts()){
			if(p.getStatus() == OrderStatus.CANCELED || p.getStatus() == OrderStatus.PENDING_CANCEL){
				this.cancelProduct(p, customer);
			}
			else
			if(p.getStatus() == OrderStatus.CONFIRMED || p.getStatus() == OrderStatus.PENDING){
				this.purchaseProduct(p, customer);
			}
		}
	}
	
	private void cancel(Order order, SystemCustomer customer){
		for(ProductOrder p: order.getProducts()){
			this.cancelProduct(p, customer);
		}
	}

	private void cancelProduct(ProductOrder product, SystemCustomer customer){
		try{
			if(this.productRegistry.isLocked(product.getId(), customer)){
				this.productRegistry.unlockProduct(product.getId(), customer, product.getProduct());
				product.setStatus(OrderStatus.CANCELED);
			}
			else{
				product.setStatus(OrderStatus.CANCELED);
			}
		}
		catch(Throwable e){
			product.setStatus(OrderStatus.PENDING_CANCEL);
		}
	}

	private void purchaseProduct(ProductOrder product, SystemCustomer customer){
		try{
			if(!this.productRegistry.isLocked(product.getId(), customer)){
				this.productRegistry.lockProduct(product.getId(), customer, product.getProduct());
				product.setStatus(OrderStatus.CONFIRMED);
			}
			else{
				product.setStatus(OrderStatus.CONFIRMED);
			}
		}
		catch(Throwable e){
			product.setStatus(OrderStatus.PENDING);
		}
	}
	
}
