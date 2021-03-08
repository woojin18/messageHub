package kr.co.uplus.cloud.sample.crypto;

import yoyozo.security.AES;

/**
 * <pre>
 * AesEncryptor
 *
 * </pre>
 *
 * @author  : savrani (Son Wang Gu)
 * @Date    : 2020. 12. 18.
 * @Version : 1.0
 * Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
public final class AesEncryptor {
	
	private AES aes;
	private final String aesKey = "azh0G1LOgSZwFvP35Wd+mGM6J7/zwwcQnvXCmsQ/PoE=";
	
	public AesEncryptor() {
		aes = new AES();
		aes.setKey(aesKey);
	}
	
	public String encrypt(String data) {
		return aes.encryptToBase64(data);
	}
	
	public String decrypt(String base64EncodedData) {
		return aes.decrypt(base64EncodedData);
	}
}
