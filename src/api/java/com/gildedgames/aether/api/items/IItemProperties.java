package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * An immutable container for optional item properties, such as rarities and equipment.
 */
public interface IItemProperties
{
	/**
	 * @return The equipment slot this item uses, empty if it's not equipment.
	 */
	Optional<ItemEquipmentSlot> getEquipmentSlot();

	/**
	 * @return The rarity of this item, or empty if this item does not have a rarity.
	 */
	Optional<ItemRarity> getRarity();

	/**
	 * @return The collection of effect instances this item provides, empty if it provides none.
	 */
	Collection<IEffectProvider> getEffectProviders();
}
