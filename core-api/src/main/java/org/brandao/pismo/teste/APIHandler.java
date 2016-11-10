package org.brandao.pismo.teste;

import javax.inject.Inject;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Classe base para a execução de uma ação da app.
 * 
 * @author Brandao.
 *
 */
public abstract class APIHandler
	implements Handler<RoutingContext> {

	@Inject
	private APIServer app;
	
	@Override
	public void handle(RoutingContext event) {
		RequestContext requestContext = 
				 app.getContainer().instance().select(
						 RequestContext.class, 
						 UnboundLiteral.INSTANCE).get();
		requestContext.activate();
		try{
			this.execute(event);
		}
		finally{
			requestContext.deactivate();
		}
	}
	
	/**
	 * Executa a ação.
	 * @param event Contexto.
	 */
	protected abstract void execute(RoutingContext event);

}
