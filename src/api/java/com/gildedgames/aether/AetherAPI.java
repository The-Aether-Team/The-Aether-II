package com.gildedgames.aether;

import com.gildedgames.aether.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.registry.equipment.IEquipmentRegistry;

public class AetherAPI
{
	private static IAetherServices services;

	private static boolean isInitialized = false;

	public static IAltarRecipeRegistry altarRegistry()
	{
		return AetherAPI.services().getAltarRecipeRegistry();
	}

	public static IEquipmentRegistry equipmentRegistry()
	{
		return AetherAPI.services().getEquipmentRegistry();
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
