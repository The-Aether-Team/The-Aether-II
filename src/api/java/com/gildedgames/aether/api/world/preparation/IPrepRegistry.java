package com.gildedgames.aether.api.world.preparation;

import java.util.List;

public interface IPrepRegistry
{

	void register(IPrepRegistryEntry registration);

	List<IPrepRegistryEntry> getEntries();

}
