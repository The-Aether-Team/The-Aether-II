package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.items.properties.ItemPropertiesBuilder;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public interface IItemPropertiesRegistry
{
	/**
	 * Retrieves the properties of {@param item}.
	 *
	 * @param item The item to retrieve properties from
	 * @return The item's properties
	 */
	@Nonnull
	IItemProperties getProperties(Item item);

	/**
	 * Registers an item with the registry, throwing an {@link IllegalArgumentException} if
	 * it is already registered.
	 *  @param item The item to register
	 * @param def The {@link IItemProperties} to register
	 */
	void registerItem(Item item, ItemPropertiesBuilder def);
}
