package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IPrepRegistry;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.google.common.collect.Lists;

import java.util.List;

public class PrepRegistry implements IPrepRegistry
{
	private List<IPrepRegistryEntry> entries = Lists.newArrayList();

	@Override
	public void register(IPrepRegistryEntry registration)
	{
		if (!this.entries.contains(registration))
		{
			this.entries.add(registration);
		}
	}

	@Override
	public List<IPrepRegistryEntry> getEntries()
	{
		return this.entries;
	}
}
