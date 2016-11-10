package org.brandao.pismo.produtos.teste.handlers;

import javax.inject.Inject;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.ServerAuthProvider.SystemUser;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por desbloquear um produto.
 * 
 * @author Brandao
 *
 */
public class UnlockProductHandler 
	extends APIHandler{

	@Inject
	private ProductController controller;
	
	@Override
	protected void execute(RoutingContext routingContext) {
		try{
			SystemUser systemUser     = (SystemUser) routingContext.user();
			SystemCustomer customer   = systemUser.getCustomer();
			HttpServerRequest request = routingContext.request();
			String id                 = request.getParam("id");
			int product               = Integer.parseInt(request.getParam("product"));
			Product entity = new Product();
			entity.setId(product);
			
			controller.unlockProduct(id, customer, entity);
			
			RestUtil.send(routingContext, null, -1);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}	}

}
