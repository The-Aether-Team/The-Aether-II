package com.gildedgames.aether.api.items.equipment.effects;

import com.gildedgames.aether.api.player.IPlayerAether;

import java.util.Collection;

/**
 * Implementor of an effect for an entity. Effect instances are re-created
 * when the active {@link IEffectProvider} are changed, and are not persistent.
 *
 * Implementors should never store data passed to them, such as {@link IPlayerAether}.
 */
public abstract class EffectInstance
{
	/**
	 * Called each tick when the entity this instance belongs to updates.
	 *
	 * @param player The player entity that is updating
	 */
	public void onEntityUpdate(IPlayerAether player) { }

	/**
	 * Called when this instance is removed from a player. This is only called once per entity
	 * for the duration of the instance. In the event a player logs out or the entity containing
	 * this instance is destroyed, this method will not be called.
	 *
	 * @param player The player this instance was removed from
	 */
	public void onInstanceRemoved(IPlayerAether player) { }

	/**
	 * Called when this instance is added to a player. This is guaranteed to called exactly once,
	 * when the instance is attached to an entity.
	 *
	 * @param player The player this instance was added to
	 */
	public void onInstanceAdded(IPlayerAether player) { }

	/**
	 * Adds information about this instance to {@param label}, such as the stats
	 * it provides or a short description.
	 *
	 * @param label The {@link Collection} to add to
	 */
	public abstract void addInformation(Collection<String> label);
}
