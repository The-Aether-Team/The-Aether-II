package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * An immutable container for optional item properties, such as rarities and equipment effects.
 */
public interface IItemProperties
{
	/**
	 * @return The equipment slot this item uses.
	 */
	@Nonnull
	ItemEquipmentSlot getEquipmentSlot();

	/**
	 * @return The rarity of this item.
	 */
	@Nonnull
	ItemRarity getRarity();

	/**
	 * @return The collection of effect instances this item provides, empty if it provides none.
	 */
	@Nonnull
	Collection<IEffectProvider> getEffectProviders();
}
