package org.brandao.pismo.teste.persistence;

/**
 * Lançada se ocorrer alguma falha ao tentar manipular uma entidade 
 * persistente.
 * 
 * @author Brandao
 *
 */
public class EntityAccessException 
	extends Exception{

	private static final long serialVersionUID = -3952945865198228976L;

	public EntityAccessException() {
		super();
	}

	public EntityAccessException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public EntityAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EntityAccessException(String arg0) {
		super(arg0);
	}

	public EntityAccessException(Throwable arg0) {
		super(arg0);
	}

}
