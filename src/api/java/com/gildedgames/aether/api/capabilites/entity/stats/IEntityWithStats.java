package com.gildedgames.aether.api.capabilites.entity.stats;

import java.util.Collection;

public interface IEntityWithStats
{
	/**
	 * Registers this entity's stats with the container. Will be re-created each time the
	 * entity is initialized.
	 * @param stats The collection of base stats belonging to the entity
	 */
	void registerStats(Collection<StatBase> stats);
}
