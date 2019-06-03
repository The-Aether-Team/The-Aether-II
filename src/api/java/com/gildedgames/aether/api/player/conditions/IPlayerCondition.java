package com.gildedgames.aether.api.player.conditions;

import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionListener;

/**
 * This MUST implement hashCode() as it is heavily
 * used in maps.
 */
public interface IPlayerCondition
{
	/**
	 * This identifier must be globally unique, representing both the
	 * base condition and the unique data that this particular condition
	 * is handling.
	 *
	 * E.G: Let's say you have a "SeeEntity" condition, and the entity
	 * that this particular condition is looking for is a "Pig". The unique
	 * identifier could be:
	 *
	 * seeEntity:entity.pig
	 * @return The unique identifier for this condition and its sub data.
	 */
	String getUniqueIdentifier();

	void listen(IPlayerConditionListener listener);

	void unlisten(IPlayerConditionListener listener);

	/**
	 * Called when this condition is no longer tracked by the
	 * {@link IPlayerConditionTracker}
	 *
	 * Should subscribe to any events needed.
	 */
	void onTracked();

	/**
	 * Called when this condition is tracked by the
	 * {@link IPlayerConditionTracker}
	 *
	 * Should unsubscribe from any events needed.
	 */
	void onUntracked();
}
