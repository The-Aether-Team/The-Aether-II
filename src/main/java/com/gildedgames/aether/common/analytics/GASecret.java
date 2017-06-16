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
	private final SecretKeySpec key;

	public GASecret(String key)
	{
		try
		{
			this.key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Invalid key format", e);
		}
	}

	/**
	 * Crates an HMAC-SHA256 for the provided data.
	 *
	 * @param data The data to create an HMAC for
	 * @return The HMAC-SHA256 as a String with base64 encoding
	 */
	public String signMessage(byte[] data)
	{
		byte[] hmac;

		try
		{
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(this.key);

			hmac = mac.doFinal(data);
		}
		catch (NoSuchAlgorithmException | InvalidKeyException e)
		{
			throw new RuntimeException("Couldn't create HMAC", e);
		}

		byte[] base64 = Base64.encodeBase64(hmac);

		return new String(base64);
	}
}
