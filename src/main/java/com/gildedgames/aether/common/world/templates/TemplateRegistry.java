package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.world.generation.ITemplateRegistry;
import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TemplateRegistry implements ITemplateRegistry
{
	private final BiMap<Integer, TemplateDefinition> idToTemplate = HashBiMap.create();

	@Override
	public int getID(final TemplateDefinition def)
	{
		return this.idToTemplate.inverse().get(def);
	}

	@Override
	public TemplateDefinition get(final int id)
	{
		return this.idToTemplate.get(id);
	}

	@Override
	public void register(final int id, final TemplateDefinition def)
	{
		this.idToTemplate.put(id, def);
	}
}
