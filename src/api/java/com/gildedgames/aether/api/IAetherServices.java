package com.gildedgames.aether.api;

import com.gildedgames.aether.api.net.IGildedGamesAccountApi;
import com.gildedgames.aether.api.registry.IContentRegistry;

public interface IAetherServices
{
	IContentRegistry content();

	IGildedGamesAccountApi gildedGamesAccountApi();
}
