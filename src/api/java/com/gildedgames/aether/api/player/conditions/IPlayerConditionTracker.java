package com.gildedgames.aether.api.player.conditions;

import java.util.Collection;

public interface IPlayerConditionTracker
{
	/**
	 * Should only register conditions that aren't already registered.
	 *
	 * @param conditions The conditions you want to actively register
	 *                   to the manager.
	 * @return The unique identifiers for the registered conditions.
	 */
	void trackConditions(Collection<IPlayerCondition> conditions);

	/**
	 * Should untrack all conditions within.
	 */
	void unload();
}
