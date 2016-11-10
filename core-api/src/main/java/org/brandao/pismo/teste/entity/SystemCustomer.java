package org.brandao.pismo.teste.entity;

/**
 * Representa um usuário do sistema.
 * 
 * @author Brandao.
 * 
 */
public class SystemCustomer {

	private long id;
	
	private String name;
	
	private String password;

	/**
	 * Obtém Identificação.
	 * @return Identificação.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Define a identificação.
	 * @param id Identificação.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtém o nome.
	 * @return Nome.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define o nome.
	 * @param name Nome.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtém a senha.
	 * @return Senha.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Define a senha.
	 * @param password Senha.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
