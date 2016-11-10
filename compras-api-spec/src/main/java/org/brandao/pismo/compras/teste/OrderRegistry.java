package org.brandao.pismo.compras.teste;

import java.util.List;

import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.entity.SystemCustomer;

/**
 * Provê recursos que permitem manipular um pedido.
 * 
 * @author Brandao.
 *
 */
public interface OrderRegistry {

	/**
	 * Registra uma novo pedido.
	 * @param customer Usuário.
	 * @param list Produtos.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar registrar o pedido.
	 */
	Order registryOrder(SystemCustomer customer, List<Product> list) throws OrderRegistryException;

	/**
	 * Atualiza um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	void updateOrder(Order order, SystemCustomer customer) throws OrderRegistryException;

	/**
	 * Cancela um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	void cancelOrder(Order order, SystemCustomer customer) throws OrderRegistryException;
	
	/**
	 * Obtém um pedido.
	 * @param id Identificação.
	 * @param customer Usuário.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar recuperar o pedido.
	 */
	Order getOrder(String id, SystemCustomer customer) throws OrderRegistryException;
	
}
