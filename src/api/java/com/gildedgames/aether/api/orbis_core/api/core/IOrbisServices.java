package com.gildedgames.aether.api.orbis_core.api.core;

import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;

import javax.annotation.Nullable;

public interface IOrbisServices
{

	/**
	 * Searches for the definition registry linked with the
	 * provided registry id. If it cannot find it, it will
	 * return null.
	 * @param registryId The unique registry id associated
	 *                   with the definition registry you're
	 *                   attempting to find.
	 */
	@Nullable
	IOrbisDefinitionRegistry findDefinitionRegistry(String registryId);

	/**
	 *
	 * @param registry The registry you're registering.
	 */
	void register(IOrbisDefinitionRegistry registry);

}
