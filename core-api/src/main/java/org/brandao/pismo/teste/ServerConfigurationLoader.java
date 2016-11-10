package org.brandao.pismo.teste;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Carrega a configuração da aplicação.
 * 
 * @author Brandao.
 *
 */
@Singleton
public class ServerConfigurationLoader { 

	protected Properties configuration;
	
	@Inject
	public ServerConfigurationLoader() throws FileNotFoundException, IOException{
        Properties config = new Properties();
	    config.load(new FileInputStream("./server.properties"));
	    this.configuration = config;
	}

	/**
	 * Obtém a configuração.
	 * @return Configuração.
	 */
	public Properties getConfiguration() {
		return configuration;
	}


	/**
	 * Disponibiliza a configuração para a app.
	 * @return Configuração.
	 * @throws IOException Lançada se ocorrer alguma falha ao tentar
	 * ler os dados.
	 */
	@Produces
	@Singleton
	@Named(AbstractAPIServer.SERVER_CONFIGURATION)
    protected Properties loadConfiguration() throws IOException{
	    return this.configuration;
    }
	
}
