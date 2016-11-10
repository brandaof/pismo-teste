package org.brandao.pismo.compras.teste.handlers;

import javax.inject.Inject;

import org.brandao.pismo.compras.teste.OrderController;
import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.ServerAuthProvider.SystemUser;
import org.brandao.pismo.teste.util.RestUtil;

import io.vertx.ext.web.RoutingContext;

public class CancelOrderHandler 
	extends APIHandler{

	@Inject
	public OrderController controller;
	
	@Override
	public void execute(RoutingContext routingContext) {
		try{
			SystemUser systemUser  = (SystemUser) routingContext.user();
			String id = routingContext.request().getParam("id");
			
			Order order = new Order();
			order.setId(id);
			controller.cancelOrder(order, systemUser.getCustomer());
			RestUtil.send(routingContext, null, -1);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}
	}

}
