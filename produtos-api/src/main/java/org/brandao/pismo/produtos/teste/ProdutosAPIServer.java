package org.brandao.pismo.produtos.teste;

import io.vertx.ext.web.Router;

import org.brandao.pismo.produtos.teste.handlers.DeleteProductHandler;
import org.brandao.pismo.produtos.teste.handlers.GetProductStockHandler;
import org.brandao.pismo.produtos.teste.handlers.IsLockedProductHandler;
import org.brandao.pismo.produtos.teste.handlers.LockProductHandler;
import org.brandao.pismo.produtos.teste.handlers.RegistryProductHandler;
import org.brandao.pismo.produtos.teste.handlers.SearchHandler;
import org.brandao.pismo.produtos.teste.handlers.UnlockProductHandler;
import org.brandao.pismo.teste.AbstractAPIServer;
import org.brandao.pismo.teste.EntityContext;

/**
 * Classe central da aplicação.
 * 
 * @author Brandao.
 *
 */
public class ProdutosAPIServer extends AbstractAPIServer {

	public ProdutosAPIServer(){
	}
	
    protected String getDefaultPort(){
    	return "8085";
    }
	
	@Override
	protected void initRouter(Router router) {
		router.post("/api/products")
			.handler(EntityContext.getEntity(RegistryProductHandler.class, this));
		router.delete("/api/products/:id")
			.handler(EntityContext.getEntity(DeleteProductHandler.class, this));
		
		router.put("/api/products/lock/:id/:product")
			.handler(EntityContext.getEntity(LockProductHandler.class, this));
		router.put("/api/products/islock/:id")
			.handler(EntityContext.getEntity(IsLockedProductHandler.class, this));
		router.put("/api/products/unlock/:id/:product")
			.handler(EntityContext.getEntity(UnlockProductHandler.class, this));
		
		router.get("/api/products/search")
			.handler(EntityContext.getEntity(SearchHandler.class, this));
		router.get("/api/products/:product")
			.handler(EntityContext.getEntity(GetProductStockHandler.class, this));
	}
	
}
