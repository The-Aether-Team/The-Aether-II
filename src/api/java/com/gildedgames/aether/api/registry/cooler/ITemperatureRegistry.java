package com.gildedgames.aether.api.registry.cooler;

import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import javax.annotation.Nullable;
import net.minecraft.item.Item;

public interface ITemperatureRegistry
{
    /**
	 * Creates and assigns equipment properties to an item.
	 * @param item The item to assign to
	 * @param coolingProperties The cooling properties for this item
	 */
	void register(Item item, TemperatureProperties coolingProperties);

	/**
	 * Returns the cooling properties of an item.
	 * @param item The item
	 * @return The item's registered {@link TemperatureProperties}. Returns null if properties are not assigned to the item.
	 */
	@Nullable
    TemperatureProperties getProperties(Item item);
}
