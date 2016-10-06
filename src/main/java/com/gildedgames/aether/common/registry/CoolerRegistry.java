package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.capabilites.items.properties.CoolingProperties;
import com.gildedgames.aether.api.registry.cooler.ICoolerRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;

public class CoolerRegistry implements ICoolerRegistry
{
	private final HashMap<Item, CoolingProperties> registeredEntries = new HashMap<>();

	@Override
	public void register(Item item, CoolingProperties coolingProperties)
	{
		this.registeredEntries.put(item, coolingProperties);
	}

	@Override
	public CoolingProperties getProperties(Item item)
	{
		return this.registeredEntries.get(item);
	}

}
