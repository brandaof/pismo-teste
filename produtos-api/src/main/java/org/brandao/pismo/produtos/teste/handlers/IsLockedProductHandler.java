package org.brandao.pismo.produtos.teste.handlers;

import javax.inject.Inject;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.ServerAuthProvider.SystemUser;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por verificar se um 
 * produto está bloqueado.
 * 
 * @author Brandao
 *
 */
public class IsLockedProductHandler 
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
			
			boolean result = controller.isLocked(id, customer);
			
			RestUtil.send(routingContext, result, 201);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}
	}

}
