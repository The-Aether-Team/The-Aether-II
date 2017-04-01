package com.gildedgames.aether.api.capabilites.entity.stats;

import java.util.Collection;

public interface IEntityStatProvider
{
	/**
	 * Returns the collection of modifiers this object provides to an entity.
	 * @return An immutable collection of modifiers
	 */
	Collection<StatBase> getStats();
}
