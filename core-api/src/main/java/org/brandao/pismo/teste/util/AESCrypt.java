package org.brandao.pismo.teste.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Utilitário que permite criptografar dados.
 * 
 * @author Brandao
 *
 */
public class AESCrypt {

	
	private static final byte[] KEY;
	
	private static final BASE64Encoder encoder = new BASE64Encoder();
	
	private static final BASE64Decoder decoder = new BASE64Decoder();

	static{
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			KEY = decoder.decodeBuffer("PdM8HbNrbY8I1F6T5TQYaQ==");
		} 
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Criptografa um texto.
	 * @param text Texto.
	 * @return Texto criptografado.
	 * @throws AESCryptException Lançada se ocorrer alguma falha ao 
	 * tentar criptografar o texto.
	 */
	public static String crypt(String text) throws AESCryptException{
		try {
			byte[] key = KEY;
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
			cipher.init(
				Cipher.ENCRYPT_MODE, 
				new SecretKeySpec(key, "AES"),ivspec);
			
			byte[] textoCifrado = 
					cipher.doFinal(text.getBytes("utf-8"));
			
			return encoder.encode(textoCifrado);
		}
		catch (Throwable e) {
			throw new AESCryptException(text, e);
		} 
	}
	
	/**
	 * Criptografa um texto.
	 * @param text Texto.
	 * @return Texto criptografado.
	 * @throws AESCryptException Lançada se ocorrer alguma falha ao 
	 * tentar criptografar o texto.
	 */
	
	/**
	 * Descriptografa um texto.
	 * @param value Texto criptografado.
	 * @return Texto
	 * @throws AESCryptException Lançada se ocorrer alguma falha ao
	 * tentar descriptografar o texto.
	 */
	public static String decrypt(String value) throws AESCryptException{
		try{
			byte[] key = KEY;
			byte[] text = decoder.decodeBuffer(value);
			
			Cipher chipher = 
				Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			IvParameterSpec ivspec = 
					new IvParameterSpec(new byte[16]);
			
			chipher.init(
				Cipher.DECRYPT_MODE,
			    new SecretKeySpec(key, "AES"), 
			    ivspec);
			
			byte[] decodedText = chipher.doFinal(text);
			return new String(decodedText,"UTF-8");
		}
		catch (Throwable e) {
			throw new AESCryptException(value , e);
		}
	}	
	
}
