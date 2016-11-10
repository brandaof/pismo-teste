package org.brandao.pismo.produtos.teste;

/**
 * Lançada se ocorrer alguma falha ao tentar
 * manipular um produto.
 * 
 * @author Brandao
 *
 */
public class ProductRegistryException 
	extends Exception{

	public ProductRegistryException() {
		super();
	}

	public ProductRegistryException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ProductRegistryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ProductRegistryException(String arg0) {
		super(arg0);
	}

	public ProductRegistryException(Throwable arg0) {
		super(arg0);
	}

}
