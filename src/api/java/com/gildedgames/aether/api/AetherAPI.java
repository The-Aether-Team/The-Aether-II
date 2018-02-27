package com.gildedgames.aether.api;

import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.world.instances.IInstanceRegistry;

public class AetherAPI
{
	private static IAetherServices services;

	public static IContentRegistry content()
	{
		return AetherAPI.services().content();
	}

	public static IInstanceRegistry instances()
	{
		return AetherAPI.services().instances();
	}

	public static void registerProvider(final IAetherServices services)
	{
		if (AetherAPI.services != null)
		{
			throw new IllegalStateException("The Aether API provider is already initialized");
		}

		AetherAPI.services = services;
	}

	public static IAetherServices services()
	{
		return AetherAPI.services;
	}
}
