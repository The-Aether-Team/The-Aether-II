package com.gildedgames.aether.api.capabilites.entity.stats;

import java.util.Optional;

public interface IEntityStat
{
	/**
	 * Removes a modifier from this entity.
	 * @param base The stat base to remove
	 */
	void addModifier(StatBase base);

	/**
	 * Adds a modifier to this entity.
	 * @param base The stat base to add
	 */
	void removeModifier(StatBase base);

	/**
	 * @return Returns the base value of this stat.
	 */
	Optional<StatBase> getBaseStat();

	/**
	 * @return Returns the value of this stat, including the base and modifiers.
	 */
	double getValue();
}
