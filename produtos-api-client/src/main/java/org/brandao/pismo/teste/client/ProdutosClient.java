package org.brandao.pismo.teste.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
import org.brandao.pismo.produtos.teste.ProductRegistryException;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.brandao.pismo.teste.util.AESCrypt;
import org.brandao.pismo.teste.util.AESCryptException;
import org.brandao.pismo.teste.util.RestUtil;

import sun.misc.BASE64Encoder;

/**
 * Cliente da API produtos.
 * 
 * @author Brandao
 *
 */
public class ProdutosClient {

	private static final String REGISTRY_PRODUCT  = "/api/products";

	private static final String DELETE_PRODUCT    = "/api/products/";
	
	private static final String LOCK_PATH         = "/api/products/lock";
	
	private static final String IS_LOCKED_PATH 	  = "/api/products/islock";

	private static final String UNLOCK_PATH 	  = "/api/products/unlock";
	
	private static final String GET_PRODUCT_STOCK = "/api/products";
	
	private static final String SEARCH            = "/api/products/search";

	private String productHost;

	private int productPort;
	
	private BASE64Encoder encoder;
	
	private CloseableHttpClient httpclient;

	public ProdutosClient(String productHost, int productPort) {
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
	 * Registra ou atualizar um determinado produto.
	 * @param product Produto.
	 * @param customer Usuário do sistema.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao 
	 * tentar registrar ou atualizar o produto.
	 */
	public int registryProduct(Product product, SystemCustomer customer) 
			throws ProductRegistryException {
		try{
			String path = REGISTRY_PRODUCT;
			String response = this.execute(path, product, 201, customer, HttpPost.METHOD_NAME);
			return RestUtil.getEntity(response, Integer.class);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
	}

	/**
	 * Apaga um determinado produto.
	 * @param product Produto.
	 * @param customer Usuário do sistema.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar
	 * apagar o produto.
	 */
	public void deleteProduct(Product product, SystemCustomer customer) 
			throws ProductRegistryException {
		try{
			String path = DELETE_PRODUCT + "/" + product.getId();
			this.execute(path, product, 204, customer, HttpDelete.METHOD_NAME);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
	}

	/**
	 * Bloqueia um produto do estoque para a venda.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no bloqueio.
	 */
	public void lockProduct(String id, SystemCustomer customer, Product product)
			throws ProductRegistryException {
		
		try{
			String path = 
					LOCK_PATH + 
					"/" + id  +
					"/" + product.getId();
			
			this.execute(path, null, 204, customer, HttpPut.METHOD_NAME);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
		
	}

	/**
	 * Verifica se um produto está bloqueado.
	 * @param id Identificação.
	 * @param customer Usuário do sistema.
	 * @return Verdadeiro se o produto está bloqueado. Caso contrário, falso.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar 
	 * verificar o bloqueio.
	 */
	public boolean isLocked(String id, SystemCustomer customer)
			throws ProductRegistryException {
		
		try{
			String path =
					IS_LOCKED_PATH + 
					"/" + id;
			
			String result = 
					this.execute(path, null, 201, customer, HttpPut.METHOD_NAME);
			return "true".equals(result);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
	}

	/**
	 * Desbloqueia um produto do estoque.
	 * @param id Identificação do bloqueio.
	 * @param customer Usuário do sistema.
	 * @param product Produto.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha no desbloqueio.
	 */
	public void unlockProduct(String id, SystemCustomer customer,
			Product product) throws ProductRegistryException {
		try{
			String path =
					UNLOCK_PATH + 
					"/" + id + 
					"/" + product.getId();
			
			this.execute(path, 
					null, 204, customer, HttpPut.METHOD_NAME);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
	}

	/**
	 * Obtém informações do estoque de um determinado produto.
	 * @param product Produto.
	 * @param customer Usuário do sistema.
	 * @return Estoque.
	 * @throws ProductRegistryException Lançada se ocorrer alguma falha ao tentar obter 
	 * as informações sobre o estoque do produto.
	 */
	public ProductStock getProductStock(Product product, SystemCustomer customer)
			throws ProductRegistryException {
		try{
			String path =
					GET_PRODUCT_STOCK + 
					"/" + product.getId();
			
			String response = this.execute(path, 
					null, 201, customer, HttpGet.METHOD_NAME);
			
			return RestUtil.getEntity(response, ProductStock.class);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
		}
	}

	/**
	 * Recupera uma coleção de produtos que adequem-se aos critérios informados. 
	 * @param name Nome.
	 * @param description Descrição.
	 * @param minPrice Preço mínimo.
	 * @param maxPrice Preço máximo.
	 * @param firstResult Define a partir de qual produto encontrado se inicia a coleta. 
	 * @param maxResult Quantidade máxima de produtos a serem coletados.
	 * @param customer Usuário do sistema.
	 * @return Coleção.
	 * @throws ProductRegistryException Lançada se ocorrer alguma 
	 * falha ao tentar coletar os produtos.
	 */
	public List<Product> search(String name, String description,
			BigDecimal minPrice, BigDecimal maxPrice, Integer firstResult,
			Integer maxResult, SystemCustomer customer) throws ProductRegistryException {
		try{
			Object[] values = 
					new Object[]{
						name, 
						description, 
						minPrice, 
						maxPrice, 
						firstResult, 
						maxResult};
			String[] names = 
					new String[]{
						"name", 
						"description", 
						"minPrice", 
						"maxPrice", 
						"firstResult", 
						"maxResult"};

			StringBuilder params = new StringBuilder();
			
			for(int i=0;i<names.length;i++){
				if(params.length() > 0){
					params.append("&");
				}
				
				if(values[i] != null){
					params
						.append(names[i])
						.append("=")
						.append(
								URLEncoder.encode(String.valueOf(values[i]), "UTF-8")
						);
				}
				
			}
			
			String response = this.execute(
					SEARCH + (params.length()>0? "?" + params.toString() : ""), 
					null, 201, customer, HttpGet.METHOD_NAME);
			
			return RestUtil.getCollection(response, Product.class);
		}
		catch(Throwable e){
			throw new ProductRegistryException(e);
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
