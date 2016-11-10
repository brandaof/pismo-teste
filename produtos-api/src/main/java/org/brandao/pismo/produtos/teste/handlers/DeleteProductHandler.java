package org.brandao.pismo.produtos.teste.handlers;

import javax.inject.Inject;

import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por apaga um produto.
 * 
 * @author Brandao
 *
 */
public class DeleteProductHandler 
	extends APIHandler{

	@Inject
	private ProductController controller;
	
	@Override
	protected void execute(RoutingContext routingContext) {
		try{
			int id = Integer.parseInt(routingContext.request().getParam("id"));
			Product request = new Product();
			request.setId(id);
			
			controller.deleteProduct(request);
			
			RestUtil.send(routingContext, null, -1);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}
	}

}
