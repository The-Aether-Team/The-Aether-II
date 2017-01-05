package com.gildedgames.aether.api.items.equipment;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

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
	Collection<IEffectInstance> getEffectInstances();
}
