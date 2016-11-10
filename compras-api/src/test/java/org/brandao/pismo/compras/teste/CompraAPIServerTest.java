package org.brandao.pismo.compras.teste;

import java.math.BigDecimal;
import java.util.Arrays;

import junit.framework.TestCase;
import io.netty.handler.codec.http.HttpHeaders;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.brandao.pismo.compras.teste.entity.Order;
import org.brandao.pismo.produtos.teste.entity.Price;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.teste.client.ComprasClient;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sun.misc.BASE64Encoder;

@RunWith(VertxUnitRunner.class)
public class CompraAPIServerTest {

	private Vertx vertxCompras;

	private Vertx vertxProdutos;
	
	private BASE64Encoder encoder;

	private ComprasClient client;
	
	@Before
	public void setUp(TestContext context) {
		encoder = new BASE64Encoder();
		vertxCompras    = Vertx.vertx();
		vertxProdutos   = Vertx.vertx();
		vertxCompras.deployVerticle(TestComprasAPIServer.class.getName(),
				context.asyncAssertSuccess());
		
		vertxProdutos.deployVerticle(TestProdutosAPIServer.class.getName(),
				context.asyncAssertSuccess());
		this.client = new ComprasClient("localhost", 8080);
	}

	@After
	public void tearDown(TestContext context) {
		vertxProdutos.close(context.asyncAssertSuccess());
		vertxCompras.close(context.asyncAssertSuccess());
	}

	@Test
	public void testRegistryProduct(TestContext context) throws OrderRegistryException {

		Product product = new Product();
		product.setId(1);
		product.setDescription("desc xx");
		product.setName("product x");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		Product product2 = new Product();
		product2.setId(1);
		product2.setDescription("desc xx");
		product2.setName("product x");
		product2.setPrice(new Price(BigDecimal.TEN, "BRL"));
		
		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");
		
		Order order = 
				this.client.registryOrder(customer, Arrays.asList(product, product2));
		
		TestCase.assertNotNull(order);
		TestCase.assertNotNull(order.getId());
		TestCase.assertEquals(2, order.getProducts().size());
		
		TestCase.assertNotNull(order.getProducts().get(0).getProduct());
		TestCase.assertEquals("desc xx", order.getProducts().get(0).getProduct().getDescription());
		TestCase.assertEquals("product x", order.getProducts().get(0).getProduct().getName());
		
		TestCase.assertNotNull(order.getProducts().get(1).getProduct());
		TestCase.assertEquals("desc xx", order.getProducts().get(1).getProduct().getDescription());
		TestCase.assertEquals("product x", order.getProducts().get(1).getProduct().getName());
	}
	  
}
