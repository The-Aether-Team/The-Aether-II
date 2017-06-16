package com.gildedgames.aether.common.analytics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GAEnvironment
{
	/**
	 * Returns the version of the currently running Java environment. Avoid
	 * calling this method frequently.
	 * @return The version of the currently running Java environment
	 */
	public static String getJavaEnvironment()
	{
		return System.getProperty("java.version");
	}

	/**
	 * Returns the vendor's name of the running Java environment (i.e. Oracle). Avoid
	 * calling this method frequently.
	 * @return The vendor of the currently running Java environment
	 */
	public static String getJavaVendor()
	{
		String name = System.getProperty("java.vendor");

		if (name.length() > 64)
		{
			return name.substring(0, 63);
		}

		return name;
	}

	/**
	 * Returns the platform of the currently running Java environment. Avoid
	 * calling this method frequently.
	 * @return The platform's name
	 */
	public static String getPlatform()
	{
		String name = System.getProperty("os.name").toLowerCase();

		if (name.startsWith("windows"))
		{
			return "windows";
		}
		else if (name.startsWith("mac"))
		{
			return "mac_osx";
		}
		else
		{
			return "linux";
		}
	}

	/**
	 * Returns the operating system on the device this reporter is running on. Avoid
	 * calling this method frequently.
	 * @return The operating system's name and version
	 */
	public static String getOperatingSystem()
	{
		// We can only have so many significant digits, and we need to strip extra guff
		Matcher matcher = Pattern.compile("[0-9]{0,5}(\\.[0-9]{0,5}){0,2}").matcher(System.getProperty("os.version"));

		// We default to an empty space just to pass GameAnalytic's regex weirdness
		String version = " ";

		// Find the most important group of digits
		if (matcher.find())
		{
			version += matcher.group(0);
		}

		return GAEnvironment.getPlatform() + version;
	}
}
