package org.brandao.pismo.teste.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * Auxilia no processamento das solicitações e respostas.
 * 
 * @author Brandao
 *
 */
public class RestUtil {

	public static final String RESPONSE_CONTENT_TYPE = "application/json; charset=utf-8";
	
	/**
	 * Eniva uma entidade ao solicitante.
	 * @param routingContext Contexto.
	 * @param entity Entidade
	 * @param responseCode Código de resultado que será enviado ao solicitante.
	 */
	public static void send(RoutingContext routingContext, Object entity, int responseCode){
		
		if(entity != null){
			String content = Json.encodePrettily(entity);
			routingContext.response()
				.setStatusCode(entity == null? 204 : responseCode)
				.putHeader("content-type", RESPONSE_CONTENT_TYPE)
				.end(content);
		}
		else{
			routingContext.response()
			.setStatusCode(204)
			.end();
		}
	}

	/**
	 * Converte uma entidade em Json.
	 * @param entity Entidade
	 * @return Entidade codificada.
	 */
	public static String toJson(Object entity){
		
		if(entity != null){
			return Json.encodePrettily(entity);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Obtém uma entidade do solicitante.
	 * @param value Dados.
	 * @param requestType Tipo da entidade.
	 * @return Entidade.
	 */
	public static <T> T getEntity(String value, Class<T> type){
		
		if(value == null){
			return null;
		}
		
		return Json.decodeValue(value, type);
	}

	/**
	 * Obtém uma coleção de entidades do solicitante.
	 * @param value Dados.
	 * @param type Tipo da entidade
	 * @return Coleção.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> List<T> getCollection(String value, 
			Class<T> type){
		
		if(value == null){
			return null;
		}
		
		Object arrayTmpType = Array.newInstance(type, 0);
		Class<?> collectionType = arrayTmpType.getClass();
		Object array = Json.decodeValue(value, collectionType);
		
		List<T> list = new ArrayList<T>();
		int size = Array.getLength(array);
		for(int i=0;i<size;i++){
			list.add((T)Array.get(array, i));
		}
		
		return list;
		
	}
	
}
