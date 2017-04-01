package com.gildedgames.aether.api.capabilites.entity.stats;

import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Optional;

public interface IEntityStatContainer
{
	/**
	 * Adds a stat modifier to the container.
	 */
	void addStatProvider(IEntityStatProvider modifier);

	/**
	 * Removes a stat modifier from this container.
	 */
	void removeStatProvider(IEntityStatProvider modifier);

	/**
	 * Returns all the stats for this container.
	 */
	Collection<IEntityStat> getAllStats();

	/**
	 * Returns a {@link StatBase} for the provided resource ID.
	 * @param id The resource ID of the stat
	 * @return The {@link StatBase}
	 */
	Optional<IEntityStat> getStat(ResourceLocation id);
}
