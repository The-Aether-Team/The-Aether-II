package com.gildedgames.aether.api.items.equipment.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;

import java.util.Collection;

/**
 * This class contains the instance of an effect for an entity.
 */
public interface IEffectInstance
{
	/**
	 * Called when the entity updates.
	 * @param player The player entity that is updating
	 */
	void onEntityUpdate(IPlayerAether player);

	/**
	 * Adds effect tooltips to {@param label}.
	 * @param label The {@link Collection} to add to
	 */
	void addItemInformation(Collection<String> label);
}
