package com.gildedgames.aether.common.analytics;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.IOException;

public class GAStorage
{
	/**
	 * Loads the user's GameAnalytics data from disk if present, otherwise creates ands saves a
	 * new user.
	 * @return The user's GameAnalytics data
	 */
	public static GAUser init()
	{
		GAUser user = GAStorage.loadFromDisk();

		if (user == null)
		{
			user = AetherCore.ANALYTICS.createUser();

			GAStorage.saveToDisk(user);
		}

		return user;
	}

	private static GAUser loadFromDisk()
	{
		File file = GAStorage.getDataFile();

		if (file.exists())
		{
			try
			{
				return AetherCore.ANALYTICS.loadUser(CompressedStreamTools.read(file));
			}
			catch (IOException e)
			{
				AetherCore.LOGGER.error("Couldn't load analytics.nbt", e);
			}
		}

		return null;
	}

	private static void saveToDisk(GAUser user)
	{
		try
		{
			CompressedStreamTools.write(user.write(), GAStorage.getDataFile());
		}
		catch (IOException e)
		{
			AetherCore.LOGGER.error("Couldn't save analytics.nbt", e);
		}
	}

	private static File getDataFile()
	{
		return new File(AetherCore.PROXY.getConfigDir(), "analytics.nbt");
	}
}
