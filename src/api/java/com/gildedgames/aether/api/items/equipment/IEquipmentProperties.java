package com.gildedgames.aether.api.items.equipment;

import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IEquipmentProperties
{
	/**
	 * @return The equipment slot this item uses.
	 */
	@Nonnull
	ItemEquipmentSlot getSlot();

	/**
	 * @return The collection of effect instances this item provides.
	 */
	Collection<IEffectProvider> getEffectInstances();
}
