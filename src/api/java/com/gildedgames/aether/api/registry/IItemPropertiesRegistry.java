package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.IItemProperties;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public interface IItemPropertiesRegistry
{
	/**
	 * Retrieves the properties of {@param item}.
	 * @param item The item to retrieve properties from
	 * @return The item's properties
	 */
	@Nonnull
	IItemProperties getProperties(Item item);

	void registerItem(Item item, IItemProperties def);
}
