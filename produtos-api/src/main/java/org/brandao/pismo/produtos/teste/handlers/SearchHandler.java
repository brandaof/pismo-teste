package org.brandao.pismo.produtos.teste.handlers;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import org.brandao.pismo.produtos.teste.ProductController;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.APIHandler;
import org.brandao.pismo.teste.util.RestUtil;

/**
 * Esta classe é responsável por pesquisar os produtos.
 * 
 * @author Brandao
 *
 */
public class SearchHandler 
	extends APIHandler{

	@Inject
	private ProductController controller;
	
	@Override
	protected void execute(RoutingContext routingContext) {
		try{
			HttpServerRequest request = routingContext.request();
			String name               = request.getParam("name") == null? null : request.getParam("name").replaceAll("\\-+", " ");
			String description        = request.getParam("description") == null? null : request.getParam("description").replaceAll("\\-+", " ");
			BigDecimal minPrice       = request.getParam("minPrice") == null? null : new BigDecimal(request.getParam("minPrice"));
			BigDecimal maxPrice       = request.getParam("maxPrice") == null? null : new BigDecimal(request.getParam("maxPrice"));
			Integer firstResult       = request.getParam("firstResult") == null? null : Integer.parseInt(request.getParam("firstResult")); 
			Integer maxResult         = request.getParam("maxResult") == null? null : Integer.parseInt(request.getParam("maxResult"));
			
			List<Product> response = controller.search(
					name, description, minPrice, maxPrice, firstResult, maxResult);
			
			RestUtil.send(routingContext, response, 201);
		}
		catch(Throwable e){
			RestUtil.send(routingContext, e.getMessage(), 400);
		}	}

}
