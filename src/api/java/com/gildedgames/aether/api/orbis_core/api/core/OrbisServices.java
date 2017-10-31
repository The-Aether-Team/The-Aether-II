package com.gildedgames.aether.api.orbis_core.api.core;

import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Map;

public class OrbisServices implements IOrbisServices
{
	private final Map<String, IOrbisDefinitionRegistry> idToRegistry = Maps.newHashMap();

	@Nullable
	@Override
	public IOrbisDefinitionRegistry findDefinitionRegistry(final String registryId)
	{
		final IOrbisDefinitionRegistry registry = this.idToRegistry.get(registryId);

		return registry;
	}

	@Override
	public void register(final IOrbisDefinitionRegistry registry)
	{
		if (registry == null)
		{
			throw new RuntimeException("Registry provided is null! Aborting.");
		}

		this.idToRegistry.put(registry.getRegistryId(), registry);
	}
}
