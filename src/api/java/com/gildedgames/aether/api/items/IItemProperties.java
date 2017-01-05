package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;

import java.util.Optional;

/**
 * An immutable container for optional item properties, such as rarities and equipment.
 */
public interface IItemProperties
{
	/**
	 * @return The equipment properties of this item, or empty if the item is
	 * not equipment.
	 */
	Optional<IEquipmentProperties> getEquipmentProperties();

	/**
	 * @return The rarity of this item, or empty if this item does not have a
	 * rarity.
	 */
	Optional<ItemRarity> getRarity();
}
