package org.brandao.pismo.produtos.teste.handlers;

import javax.inject.Inject;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por recuperar um produto.
 * 
 * @author Brandao
 *
 */
public class GetProductStockHandler 
	extends APIHandler{

	@Inject
	private ProductController controller;
	
	@Override
	protected void execute(RoutingContext routingContext) {
		try{
			HttpServerRequest request = routingContext.request();
			int product               = Integer.parseInt(request.getParam("product"));
			Product entity = new Product();
			entity.setId(product);
			
			ProductStock response = controller.getProductStock(entity);
			
			RestUtil.send(routingContext, response, 201);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}	}

}
