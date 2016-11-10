package org.brandao.pismo.teste;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.spi.BeanManager;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Classe base de uma aplicação.
 * 
 * @author Brandao
 *
 */
public abstract class AbstractAPIServer 
	extends AbstractVerticle 
	implements APIServer{

	public static final String SERVER_CONFIGURATION 	= "server_config";

    protected static Weld weld;
    
    protected static WeldContainer container;
	
    protected Properties configuration;
    
    protected Router router;
	
    protected SystemCustomerCheck systemCustomerCheck;
	
    protected BeanManager beanManager;
    
    /**
     * Contrói a classe da aplicação. Iniciado pelo vertx.
     */
	public AbstractAPIServer(){
	}
	
	/**
	 * Inicia o container IoC
	 */
    private void startContext(){
        weld = new Weld();
        weld.addExtension(this);
        container = weld.initialize();
		this.beanManager = container.getBeanManager();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                weld.shutdown();
            }
        });
    }
    
    /**
     * Obtém a porta padrão da app.
     * @return Porta.
     */
    protected String getDefaultPort(){
    	return "8080";
    }
    
    /**
     * Obtém o BeanManager.
     */
    public BeanManager getBeanManager() {
		return beanManager;
	}
	
    /**
     * Obtém o container IoC.
     */
    public WeldContainer getContainer() {
		return container;
	}

	/**
     * Inicia a aplicação.
     */
	@Override
	public void start(Future<Void> fut) throws IOException {
		
		this.startContext();
		
		ServerConfigurationLoader configLoader = EntityContext.getEntity(ServerConfigurationLoader.class, this);
		this.configuration           = configLoader.getConfiguration();
		this.systemCustomerCheck     = EntityContext.getEntity(SystemCustomerCheck.class, this);
		this.router                  = Router.router(vertx);
		AuthProvider authProvider    = new ServerAuthProvider(vertx, container, systemCustomerCheck);
		AuthHandler basicAuthHandler = BasicAuthHandler.create(authProvider);
		 
		router.route().handler(BodyHandler.create());
		router.route().handler(basicAuthHandler);

		this.initRouter(router);

		vertx
			.createHttpServer()
			.requestHandler(router::accept)
			.listen(
					config().getInteger("http.port", 
							Integer.parseInt(
									this.configuration.getProperty(
											"http.port", 
											getDefaultPort()
									)
							)
					),
					result -> {
						if (result.succeeded()) {
							fut.complete();
						}
						else{
							fut.fail(result.cause());
						}
					}
			);
	}
	
	/**
	 * Registra as rotas da aplicação.
	 * @param router Rotas.
	 */
	protected abstract void initRouter(Router router);
	
}
