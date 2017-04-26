package com.gildedgames.aether.api.items.equipment.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;

import java.util.Collection;

/**
 * This class contains the instance of an effect for an entity.
 */
public abstract class EffectInstance
{
	/**
	 * Called when the entity updates.
	 * @param player The player entity that is updating
	 */
	public void onEntityUpdate(IPlayerAether player) { }

	public void onInstanceRemoved(IPlayerAether player) { }

	public void onInstanceAdded(IPlayerAether player) { }

	/**
	 * Builds a status tooltip to {@param label}.
	 * @param label The {@link Collection} to add to
	 */
	public abstract void addItemInformation(Collection<String> label);
}
