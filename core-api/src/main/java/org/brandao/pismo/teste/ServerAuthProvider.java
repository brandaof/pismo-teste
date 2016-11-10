package org.brandao.pismo.teste;

import org.brandao.pismo.teste.entity.SystemCustomer;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.WeldContainer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * Autoriza o acesso de um determinado usuário 
 * aos recursos da aplicação.
 * 
 * @author Brandao
 *
 */
public class ServerAuthProvider 
	implements AuthProvider{

	private Vertx vertx;
	
	private SystemCustomerCheck systemCustomerCheck;
	
	private WeldContainer container;
	
	public ServerAuthProvider(Vertx vertx, 
			WeldContainer container, SystemCustomerCheck systemCustomerCheck) {
		this.vertx = vertx;
		this.systemCustomerCheck = systemCustomerCheck;
		this.container = container;
	}
	
	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
		vertx.executeBlocking(fut -> {
			RequestContext requestContext = 
					container.instance().select(
							 RequestContext.class, 
							 UnboundLiteral.INSTANCE).get();
			requestContext.activate();
			try{
				String username = authInfo.getString("username");
				String password = authInfo.getString("password");
				
				SystemCustomer customer = systemCustomerCheck.accept(username, password);
				
				if(customer == null){
					 throw new VertxException("invalid user");
				}
				     	 
				SystemUser user = new SystemUser(username, customer);
				fut.complete(user);
			}
			finally{
				requestContext.deactivate();	
			}
	    }, resultHandler);		
	}

	public class SystemUser extends AbstractUser{

		private String name;
		
		private JsonObject principal;
		
		private SystemCustomer customer;
		
		public SystemUser(String name, SystemCustomer customer) {
			this.name = name;
			this.customer = customer;
		}

		@Override
		public JsonObject principal() {
		    if (principal == null) {
		        principal = new JsonObject().put("username", name);
		      }
		      return principal;
		}

		@Override
		public void setAuthProvider(AuthProvider authProvider) {
		}

		@Override
		protected void doIsPermitted(String permission,
				Handler<AsyncResult<Boolean>> resultHandler) {
			ServerAuthProvider.this.vertx.executeBlocking(
					fut -> fut.complete(true), resultHandler);
		}

		public String getName() {
			return name;
		}

		public SystemCustomer getCustomer() {
			return customer;
		}
		
	}

}
