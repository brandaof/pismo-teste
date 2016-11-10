package org.brandao.pismo.teste.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.brandao.pismo.compras.teste.OrderRegistryException;
import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.util.AESCrypt;
import org.brandao.pismo.teste.util.AESCryptException;
import org.brandao.pismo.teste.util.RestUtil;

import sun.misc.BASE64Encoder;

/**
 * Cliente da API compras.
 * 
 * @author Brandao
 *
 */
public class ComprasClient {

	private static final String REGISTRY_ORDER  = "/api/order";

	private static final String UPDATE_ORDER    = "/api/order/";
	
	private static final String CANCEL_ORDER         = "/api/order/";
	
	private static final String GET_ORDER 	  = "/api/order/";

	private String productHost;

	private int productPort;
	
	private BASE64Encoder encoder;
	
	private CloseableHttpClient httpclient;

	public ComprasClient(String productHost, int productPort) {
		this.productHost   = productHost;
		this.productPort   = productPort;
		this.encoder       = new BASE64Encoder();
		
		RequestConfig.Builder requestBuilder = 
				RequestConfig.custom()
					.setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000);
		
		
		this.httpclient = 
				HttpClientBuilder
					.create().setDefaultRequestConfig(requestBuilder.build())
					.setRedirectStrategy(new LaxRedirectStrategy())
					.build();
		
	}

	/**
	 * Registra uma novo pedido.
	 * @param customer Usuário.
	 * @param list Produtos.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar registrar o pedido.
	 */
	public Order registryOrder(SystemCustomer customer, List<Product> list) throws OrderRegistryException{
		try{
			String path = REGISTRY_ORDER;
			String response = this.execute(path, list, 201, customer, HttpPost.METHOD_NAME);
			return RestUtil.getEntity(response, Order.class);
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}

	/**
	 * Atualiza um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	public void updateOrder(Order order, SystemCustomer customer) throws OrderRegistryException{
		try{
			String path = UPDATE_ORDER + 
					"/" + order.getId();
			this.execute(path, order, 204, customer, HttpPost.METHOD_NAME);
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}

	/**
	 * Cancela um pedido
	 * @param order Pedido
	 * @param customer Usuário.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar atualizar o pedido.
	 */
	public void cancelOrder(Order order, SystemCustomer customer) throws OrderRegistryException{
		try{
			String path = CANCEL_ORDER + 
					"/" + order.getId();
			this.execute(path, order, 204, customer, HttpDelete.METHOD_NAME);
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}

	/**
	 * Obtém um pedido.
	 * @param id Identificação.
	 * @param customer Usuário.
	 * @return Pedido.
	 * @throws OrderRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar recuperar o pedido.
	 */
	public Order getOrder(String id, SystemCustomer customer) throws OrderRegistryException{
		try{
			String path = GET_ORDER + 
					"/" + id;
			String response = this.execute(path, null, 201, customer, HttpDelete.METHOD_NAME);
			return RestUtil.getEntity(response, Order.class);
		}
		catch(Throwable e){
			throw new OrderRegistryException(e);
		}
	}
	
	protected String execute(String resource, Object entity, int expectedResultCode, 
			SystemCustomer customer, String methodName) throws ClientProtocolException, IOException, AESCryptException{
		
		String url = 
				"http://" + this.productHost + ":" + this.productPort + resource;

		String pass = AESCrypt.decrypt(customer.getPassword());
		String data = customer.getName() + ":" + pass;
		String auth = encoder.encode(data.getBytes());
		
		HttpUriRequest method;
		
		switch (methodName) {
		case HttpGet.METHOD_NAME:
			method = new HttpGet(url);
			break;
		case HttpPut.METHOD_NAME:
			method = new HttpPut(url);
			if(entity!= null){
				String content = RestUtil.toJson(entity);
				((HttpPut)method).setEntity(new ByteArrayEntity(content.getBytes()));
			}
			break;
		case HttpPost.METHOD_NAME:
			method = new HttpPost(url);
			if(entity!= null){
				String content = RestUtil.toJson(entity);
				((HttpPost)method).setEntity(new ByteArrayEntity(content.getBytes()));
			}
			break;
		case HttpDelete.METHOD_NAME:
			method = new HttpDelete(url);
			if(entity!= null){
				String content = RestUtil.toJson(entity);
				((HttpPost)method).setEntity(new ByteArrayEntity(content.getBytes()));
			}
			break;
		default:
			throw new IllegalStateException("methodName");
		}
		
		method.addHeader("Authorization", "Basic " + auth);
		
		CloseableHttpResponse response = httpclient.execute(method);
		
		StatusLine status              = response.getStatusLine();
		String body                    = null;
		
		if(status.getStatusCode() != HttpStatus.SC_NO_CONTENT){
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			body = response == null? null : responseHandler.handleResponse(response);
		}
		
		if(status.getStatusCode() != expectedResultCode){
			throw new IllegalStateException(body);
		}
		else{
			return body;
		}
	}
	
}
