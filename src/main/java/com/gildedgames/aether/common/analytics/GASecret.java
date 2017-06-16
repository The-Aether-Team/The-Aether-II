package com.gildedgames.aether.common.analytics;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Stores a secret token and allows HMAC-SHA256 signing with it.
 */
public class GASecret
{
	private static final String ALGORITHM = "HmacSHA256";

	private final Mac mac;

	public GASecret(String secret)
	{
		try
		{
			SecretKeySpec key = new SecretKeySpec(secret.getBytes("UTF-8"), ALGORITHM);

			this.mac = Mac.getInstance(ALGORITHM);
			this.mac.init(key);
		}
		catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e)
		{
			throw new RuntimeException("Couldn't initialize MAC", e);
		}
	}

	/**
	 * Crates an HMAC-SHA256 for the provided data using the secret key stored.
	 *
	 * @param data The data to create an HMAC for
	 * @return The HMAC-SHA256 as a String with base64 encoding
	 */
	public String createHmac(byte[] data)
	{
		byte[] hmac = this.mac.doFinal(data);

		byte[] base64 = Base64.encodeBase64(hmac);

		return new String(base64);
	}
}
