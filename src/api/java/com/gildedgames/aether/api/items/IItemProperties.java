package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPrecondition;
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
	 * @return A collection of effect preconditions that are checked before applying effects
	 * to the player.
	 */
	Collection<IEffectPrecondition> getEffectPreconditions();

	/**
	 * @return The collection of effect instances this item provides, empty if it provides none.
	 */
	@Nonnull
	Collection<IEffectProvider> getEffectProviders();

	/**
	 * @return The collection of effect activators that this item provides. This defines what activates the effects this item has.
	 */
	@Nonnull
	Collection<EffectActivator> getEffectActivators();
}
