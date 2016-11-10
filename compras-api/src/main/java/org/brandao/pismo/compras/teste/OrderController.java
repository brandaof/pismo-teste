package org.brandao.pismo.compras.teste;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.pismo.compras.teste.OrderRegistry;
import org.brandao.pismo.compras.teste.OrderRegistryException;
import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Provê os recursos necessários que permitem a 
 * manipulação dos pedidos.
 * 
 * @author Brandao
 *
 */
@Singleton
@Default
public class OrderController {

	private OrderRegistry orderRegistry;

	@Inject
	public OrderController(OrderRegistry orderRegistry){
		this.orderRegistry = orderRegistry;
	}
	
	/**
	 * Registra uma novo pedido.
	 * @param customer Usuário.
	 * @param list Produtos.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar registrar o pedido.
	 */
	public Order registryOrder(SystemCustomer customer, List<Product> list) throws OrderRegistryException{
		return this.orderRegistry.registryOrder(customer, list);
	}

	/**
	 * Atualiza um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	public void updateOrder(Order order, SystemCustomer customer) throws OrderRegistryException{
		this.orderRegistry.updateOrder(order, customer);
	}

	/**
	 * Cancela um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	public void cancelOrder(Order order, SystemCustomer customer) throws OrderRegistryException{
		this.orderRegistry.cancelOrder(order, customer);
	}
	
	/**
	 * Obtém um pedido.
	 * @param id Identificação.
	 * @param customer Usuário.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar recuperar o pedido.
	 */
	public Order getOrder(String id, SystemCustomer customer) throws OrderRegistryException{
		return this.orderRegistry.getOrder(id, customer);
	}
	
}
