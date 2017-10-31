package com.gildedgames.aether.api.orbis_core.api.registry;

import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinition;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Default implementation for an IOrbisDefinitionRegistry.
 *
 * Use if you don't want a specific implementation of your definition
 * registry. This should work for most cases.
 */
public class OrbisDefinitionRegistry implements IOrbisDefinitionRegistry
{
	private final BiMap<Integer, BlueprintDefinition> idToTemplate = HashBiMap.create();

	private final String registryId;

	/**
	 * @param registryId Has to be unique. Recommended to use MOD_ID.
	 */
	public OrbisDefinitionRegistry(final String registryId)
	{
		this.registryId = registryId;
	}

	@Override
	public String getRegistryId()
	{
		return this.registryId;
	}

	@Override
	public int getID(final BlueprintDefinition def)
	{
		return this.idToTemplate.inverse().get(def);
	}

	@Override
	public BlueprintDefinition get(final int id)
	{
		return this.idToTemplate.get(id);
	}

	@Override
	public void register(final int id, final BlueprintDefinition def)
	{
		this.idToTemplate.put(id, def);
	}
}
