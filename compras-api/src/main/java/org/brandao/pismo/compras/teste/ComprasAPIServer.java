package org.brandao.pismo.compras.teste;

import io.vertx.ext.web.Router;

import org.brandao.pismo.compras.teste.handlers.CancelOrderHandler;
import org.brandao.pismo.compras.teste.handlers.GetOrderHandler;
import org.brandao.pismo.compras.teste.handlers.RegistryOrderHandler;
import org.brandao.pismo.compras.teste.handlers.UpdateOrderHandler;
import org.brandao.pismo.teste.AbstractAPIServer;
import org.brandao.pismo.teste.EntityContext;

/**
 * Classe central da aplicação.
 * 
 * @author Brandao.
 *
 */
public class ComprasAPIServer extends AbstractAPIServer {

	public ComprasAPIServer(){
	}

	@Override
	protected void initRouter(Router router) {
		router.post("/api/order")
			.handler(EntityContext.getEntity(RegistryOrderHandler.class, this));
		router.post("/api/order/:id")
			.handler(EntityContext.getEntity(UpdateOrderHandler.class, this));
		router.delete("/api/order/:id")
			.handler(EntityContext.getEntity(CancelOrderHandler.class, this));
		router.get("/api/order/:id")
			.handler(EntityContext.getEntity(GetOrderHandler.class, this));
		
	}
	
}
