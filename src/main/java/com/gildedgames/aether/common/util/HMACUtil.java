package com.gildedgames.aether.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMACUtil
{
	/**
	 * Crates an HMAC-SHA256 for the byte array using the provided key.
	 *
	 * @param key The key to use
	 * @param data The data to hash
	 * @return The HMAC as a String with base64 encoding
	 * @throws NoSuchAlgorithmException If a provider for HMAC-SHA256 does not exist
	 * @throws InvalidKeyException If the provided key is invalid for HMAC-SHA256
	 */
	public static String hmacSha256(byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");

		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(keySpec);

		return new String(Base64.encodeBase64(mac.doFinal(data)));
	}
}
