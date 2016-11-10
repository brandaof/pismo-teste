package org.brandao.pismo.teste.util;

/**
 * Lançada se ocorrer alguma falha ao tentar 
 * manipular dados criptografados.
 * 
 * @author Brandao.
 *
 */
public class AESCryptException 
	extends Exception{

	private static final long serialVersionUID = -8697155896651627962L;

	public AESCryptException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AESCryptException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AESCryptException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AESCryptException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AESCryptException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
