package com.gildedgames.aether.api;

import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.cooler.ICoolerRegistry;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;

public class AetherAPI
{

	private static IAetherServices services;

	private static boolean isInitialized = false;

	public static IAltarRecipeRegistry altar()
	{
		return AetherAPI.services().getAltarRecipeRegistry();
	}

	public static IEquipmentRegistry equipment()
	{
		return AetherAPI.services().getEquipmentRegistry();
	}

	public static ICoolerRegistry cooler()
	{
		return AetherAPI.services().getCoolerRegistry();
	}

	public static void init(IAetherServices services)
	{
		AetherAPI.services = services;
	}

	public static IAetherServices services()
	{
		if (!AetherAPI.isInitialized)
		{
			try
			{
				AetherAPI.services = (IAetherServices) Class.forName("com.gildedgames.aether.common.AetherCore").getDeclaredField("INSTANCE").get(null);
			}
			catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e)
			{
				throw new RuntimeException("Couldn't get the running instance of the Aether", e);
			}

			if (AetherAPI.services == null)
			{
				throw new IllegalStateException("The Aether is not initialized yet");
			}
		}

		return AetherAPI.services;
	}
}
