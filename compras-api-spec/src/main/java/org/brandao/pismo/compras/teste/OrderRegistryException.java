package org.brandao.pismo.compras.teste;

/**
 * Lançada se ocorrer alguma falha na manipulação de um pedido.
 * 
 * @author Brandao.
 *
 */
public class OrderRegistryException 
	extends Exception{

	private static final long serialVersionUID = 6285702998467631442L;

	public OrderRegistryException() {
		super();
	}

	public OrderRegistryException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrderRegistryException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderRegistryException(String message) {
		super(message);
	}

	public OrderRegistryException(Throwable cause) {
		super(cause);
	}

}
