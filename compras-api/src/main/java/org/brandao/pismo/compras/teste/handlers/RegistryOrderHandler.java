package org.brandao.pismo.compras.teste.handlers;

import java.util.List;

import javax.inject.Inject;

import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.compras.teste.OrderController;
import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.ServerAuthProvider.SystemUser;
import org.brandao.pismo.teste.util.RestUtil;

public class RegistryOrderHandler 
	extends APIHandler{

	@Inject
	private OrderController controller;
	
	@Override
	public void execute(RoutingContext routingContext) {
		try{
			SystemUser systemUser  = (SystemUser) routingContext.user();
			List<Product> products = 
				(List<Product>)RestUtil.getCollection(routingContext.getBodyAsString(), Product.class);
			
			Order response = controller.registryOrder(systemUser.getCustomer(), products);
			RestUtil.send(routingContext, response, 201);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}
	}

}
