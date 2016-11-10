package org.brandao.pismo.produtos.teste.handlers;

import javax.inject.Inject;

import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por registrar um produto.
 * 
 * @author Brandao
 *
 */
public class RegistryProductHandler 
	extends APIHandler{

	@Inject
	private ProductController controller;
	
	@Override
	protected void execute(RoutingContext routingContext) {
		try{
			Product product = RestUtil.getEntity(routingContext.getBodyAsString(), Product.class);
			
			long response = controller.registryProduct(product);
			
			RestUtil.send(routingContext, response, 201);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}
	}

}
