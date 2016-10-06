package com.gildedgames.aether.api.registry.cooler;

import com.gildedgames.aether.api.capabilites.items.properties.CoolingProperties;
import com.sun.istack.internal.Nullable;
import net.minecraft.item.Item;

public interface ICoolerRegistry
{
    /**
	 * Creates and assigns equipment properties to an item.
	 * @param item The item to assign to
	 * @param coolingProperties The cooling properties for this item
	 */
	void register(Item item, CoolingProperties coolingProperties);

	/**
	 * Returns the cooling properties of an item.
	 * @param item The item
	 * @return The item's registered {@link CoolingProperties}. Returns null if properties are not assigned to the item.
	 */
	@Nullable
	CoolingProperties getProperties(Item item);
}
