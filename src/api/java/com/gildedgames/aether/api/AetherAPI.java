package com.gildedgames.aether.api;

import com.gildedgames.aether.api.capabilites.instances.IInstanceRegistry;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;

// TODO:
// Modular functionality
public class AetherAPI
{
	private static IAetherServiceLocator services;

	public static IAltarRecipeRegistry altar()
	{
		return AetherAPI.services().getAltarRecipeRegistry();
	}

	public static ITabRegistry tabs()
	{
		return AetherAPI.services().getTabRegistry();
	}

	public static IInstanceRegistry instances()
	{
		return AetherAPI.services().getInstanceRegistry();
	}

	public static ISimpleCraftingRegistry crafting()
	{
		return AetherAPI.services().getSimpleCraftingRegistry();
	}

	public static ISimpleCraftingRegistry masonry()
	{
		return AetherAPI.services().getMasonryRegistry();
	}

	public static IItemPropertiesRegistry items()
	{
		return AetherAPI.services().getItemPropertiesRegistry();
	}

	public static IEquipmentRegistry equipment()
	{
		return AetherAPI.services().getEquipmentRegistry();
	}

	public static void registerProvider(IAetherServiceLocator services)
	{
		if (AetherAPI.services != null)
		{
			throw new IllegalStateException("The Aether API provider is already initialized");
		}

		AetherAPI.services = services;
	}

	public static IAetherServiceLocator services()
	{
		return AetherAPI.services;
	}
}
