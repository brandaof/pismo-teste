package org.brandao.pismo.produtos.teste;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

import org.brandao.pismo.teste.AbstractAPIServer;
import org.brandao.pismo.teste.ServerConfigurationLoader;

public class Configuration extends ServerConfigurationLoader{

	public Configuration() throws FileNotFoundException, IOException {
		//super();
		this.configuration = new Properties();
		configuration.put("hibernate.connection.username","SA");
		configuration.put("hibernate.connection.password","");
		configuration.put("hibernate.connection.url","jdbc:hsqldb:mem:testdb");
		configuration.put("hibernate.dialect","org.hsqldb.jdbcDriver");
	}

	@Produces 
	@Singleton
	@Named(AbstractAPIServer.SERVER_CONFIGURATION)
    protected Properties loadConfiguration() throws IOException{
		return super.loadConfiguration();
   }
	
}
