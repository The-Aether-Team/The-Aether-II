package com.gildedgames.aether.api;

import com.gildedgames.aether.api.capabilites.instances.IInstanceRegistry;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.cooler.ITemperatureRegistry;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;

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

	public static ITemperatureRegistry temperature()
	{
		return AetherAPI.services().getTemperatureRegistry();
	}

	public static ITabRegistry tabs() { return AetherAPI.services().getTabRegistry(); }

	public static IInstanceRegistry instances() { return AetherAPI.services().getInstanceRegistry(); }

	public static ISimpleCraftingRegistry crafting() { return AetherAPI.services().getSimpleCraftingRegistry(); }

	public static ISimpleCraftingRegistry masonry() { return AetherAPI.services().getMasonryRegistry(); }

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
				throw new RuntimeException("Couldn't get the running instances of the Aether", e);
			}

			if (AetherAPI.services == null)
			{
				throw new IllegalStateException("The Aether is not initialized yet");
			}
		}

		return AetherAPI.services;
	}
}
