package com.gildedgames.aether.api.registry.equipment;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public interface IEquipmentRegistry
{
	/**
	 * Creates and assigns equipment properties to an item.
	 * @param item The item to assign to
	 * @param rarity The rarity of the item
	 * @param type The equipment type of the item
	 */
	void register(Item item, ItemRarity rarity, ItemEquipmentType type);

	/**
	 * Returns the equipment properties of an item.
	 * @param item The item
	 * @return The item's registered {@link IEquipmentProperties}. Returns null if properties are not assigned to the item.
	 */
	@Nullable
	IEquipmentProperties getProperties(Item item);
}
