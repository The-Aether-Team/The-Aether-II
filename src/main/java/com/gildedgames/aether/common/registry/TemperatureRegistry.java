package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import com.gildedgames.aether.api.registry.cooler.ITemperatureRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;

public class TemperatureRegistry implements ITemperatureRegistry
{
	private final HashMap<Item, TemperatureProperties> registeredEntries = new HashMap<>();

	@Override
	public void register(Item item, TemperatureProperties coolingProperties)
	{
		this.registeredEntries.put(item, coolingProperties);
	}

	@Override
	public TemperatureProperties getProperties(Item item)
	{
		return this.registeredEntries.get(item);
	}

}
