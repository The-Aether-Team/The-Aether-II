package com.gildedgames.aether.api;

import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.world.instances.IInstanceRegistry;

public interface IAetherServices
{
	IContentRegistry content();

	IInstanceRegistry instances();
}
