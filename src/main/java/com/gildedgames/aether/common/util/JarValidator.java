package com.gildedgames.aether.common.util;

import com.gildedgames.aether.common.AetherCore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class JarValidator
{
	private static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";

	/**
	 * Performs additional validation to ensure that the classpath of the current Aether II installation
	 * comes from a file which has an expected name. It's very common for third-party distributors to
	 * change the file name.
	 * @param clazz The main mod's class.
	 * @return True if the extended validation succeeded, otherwise false.
	 */
	public static boolean validate(final File file)
	{
		Manifest prop;

		try (JarInputStream stream = new JarInputStream(new BufferedInputStream(new FileInputStream(file))))
		{
			prop = stream.getManifest();
		}
		catch (IOException e)
		{
			AetherCore.LOGGER.warn("Couldn't open and decode JAR manifest information", e);

			return true;
		}

		String expectedFileName = prop.getMainAttributes().getValue("SignedFileName");

		if (expectedFileName == null)
		{
			AetherCore.LOGGER.warn("JAR manifest does not contain attributes for extended validation");

			return true;
		}

		return file.getName().equals(expectedFileName);
	}
}
