package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface IItemPropertiesRegistry
{
	/**
	 * Retrieves the properties of {@param item}.
	 * @param item The item to retrieve properties from
	 * @return The item's properties
	 */
	@Nonnull
	IItemProperties getProperties(Item item);

	/**
	 * Helper method to retrieve the equipment properties of {@param item}.
	 * @param item The item to retrieve properties from
	 * @return The item's equipment properties
	 */
	@Nonnull
	Optional<IEquipmentProperties> getEquipmentProperties(Item item);

	void registerItem(Item item, IItemProperties def);
}
