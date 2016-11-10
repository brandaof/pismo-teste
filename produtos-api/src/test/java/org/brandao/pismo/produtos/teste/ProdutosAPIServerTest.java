package org.brandao.pismo.produtos.teste;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.brandao.pismo.produtos.teste.entity.Price;
import org.brandao.pismo.produtos.teste.entity.Product;
import org.brandao.pismo.produtos.teste.entity.ProductStock;
import org.brandao.pismo.teste.client.ProdutosClient;
import org.brandao.pismo.teste.entity.SystemCustomer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ProdutosAPIServerTest {

	private Vertx vertx;

	private ProdutosClient client;

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		vertx.deployVerticle(
				TestProdutosAPIServer.class.getName(),
				context.asyncAssertSuccess());

		this.client = new ProdutosClient("localhost", 8085);
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void testRegistryProduct(TestContext context)
			throws ProductRegistryException {

		Product product = new Product();

		product.setDescription("prod 00");
		product.setName("product 00");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		int id = client.registryProduct(product, customer);
		TestCase.assertTrue(id != 0);

		List<Product> search = client.search("product 00", null, null, null,
				null, null, customer);

		TestCase.assertNotNull(search);
		TestCase.assertEquals(1, search.size());
		TestCase.assertEquals(search.get(0).getDescription(), "prod 00");
		TestCase.assertEquals(search.get(0).getName(), "product 00");

		TestCase.assertNotNull(search.get(0).getPrice());
		TestCase.assertEquals(search.get(0).getPrice().getCurrency(), "BRL");
		TestCase.assertEquals(search.get(0).getPrice().getValue(),
				BigDecimal.TEN);
	}

	@Test
	public void testDeleteProduct(TestContext context)
			throws ProductRegistryException {
		Product product = new Product();

		product.setDescription("prod 10");
		product.setName("product 10");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		client.registryProduct(product, customer);

		List<Product> search = client.search("product 10", null, null, null,
				null, null, customer);

		TestCase.assertNotNull(search);
		TestCase.assertEquals(1, search.size());
		TestCase.assertEquals(search.get(0).getDescription(), "prod 10");
		TestCase.assertEquals(search.get(0).getName(), "product 10");

		client.deleteProduct(search.get(0), customer);

		search = client.search("product 10", null, null, null, null, null, customer);

		TestCase.assertNotNull(search);
		TestCase.assertEquals(0, search.size());
	}

	@Test
	public void lockProduct(TestContext context)
			throws ProductRegistryException {
		Product product = new Product();

		product.setDescription("prod 11");
		product.setName("product 11");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		int id = client.registryProduct(product, customer);
		product.setId(id);

		String lockid = UUID.randomUUID().toString();
		client.lockProduct(lockid, customer, product);

		boolean locked = client.isLocked(lockid, customer);
		TestCase.assertTrue(locked);
	}

	@Test
	public void isLockedProduct(TestContext context)
			throws ProductRegistryException {
		Product product = new Product();

		product.setDescription("prod 12");
		product.setName("product 12");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		int id = client.registryProduct(product, customer);
		product.setId(id);

		String lockid = UUID.randomUUID().toString();
		client.lockProduct(lockid, customer, product);

		boolean locked = client.isLocked(lockid, customer);
		TestCase.assertTrue(locked);
	}

	@Test
	public void unlockProduct(TestContext context)
			throws ProductRegistryException {
		Product product = new Product();

		product.setDescription("prod 12");
		product.setName("product 12");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		int id = client.registryProduct(product, customer);
		product.setId(id);

		String lockid = UUID.randomUUID().toString();
		client.lockProduct(lockid, customer, product);

		boolean locked = client.isLocked(lockid, customer);
		TestCase.assertTrue(locked);

		client.unlockProduct(lockid, customer, product);

		locked = client.isLocked(lockid, customer);
		TestCase.assertFalse(locked);
	}

	@Test
	public void getProductStock(TestContext context)
			throws ProductRegistryException {
		Product product = new Product();

		product.setDescription("prod 14");
		product.setName("product 14");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		int id = client.registryProduct(product, customer);
		product.setId(id);

		ProductStock stock = client.getProductStock(product, customer);
		TestCase.assertEquals(id, stock.getId());
		TestCase.assertTrue(stock.getAvailable() > 0);

		TestCase.assertEquals(id, stock.getProduct().getId());
		TestCase.assertEquals("prod 14", stock.getProduct().getDescription());
	}

	@Test
	public void search(TestContext context) throws ProductRegistryException {
		SystemCustomer customer = new SystemCustomer();
		customer.setName("teste");
		customer.setPassword("jUj9bKW8652XRQLotzx1kA==");

		Product product = new Product();
		product.setDescription("prod 00");
		product.setName("product 00");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		int id = client.registryProduct(product, customer);

		product = new Product();
		product.setDescription("prod xx");
		product.setName("product xx");
		product.setPrice(new Price(BigDecimal.TEN, "BRL"));

		client.registryProduct(product, customer);

		TestCase.assertTrue(id != 0);

		List<Product> search = client.search("product", null, null, null, null,
				null, customer);

		TestCase.assertNotNull(search);
		TestCase.assertEquals(2, search.size());
		TestCase.assertEquals(search.get(0).getDescription(), "prod 00");
		TestCase.assertEquals(search.get(0).getName(), "product 00");

		TestCase.assertNotNull(search.get(0).getPrice());
		TestCase.assertEquals(search.get(0).getPrice().getCurrency(), "BRL");
		TestCase.assertEquals(search.get(0).getPrice().getValue(),
				BigDecimal.TEN);

		TestCase.assertEquals(search.get(1).getDescription(), "prod xx");
		TestCase.assertEquals(search.get(1).getName(), "product xx");

		TestCase.assertNotNull(search.get(1).getPrice());
		TestCase.assertEquals(search.get(1).getPrice().getCurrency(), "BRL");
		TestCase.assertEquals(search.get(1).getPrice().getValue(),
				BigDecimal.TEN);
	}

}
