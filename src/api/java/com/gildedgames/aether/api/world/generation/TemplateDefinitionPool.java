package com.gildedgames.aether.api.world.generation;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class TemplateDefinitionPool
{

	private final List<TemplateDefinition> definitions;

	public TemplateDefinitionPool(final TemplateDefinition... definitions)
	{
		this.definitions = Lists.newArrayList(definitions);
	}

	public TemplateDefinition getRandomDefinition(final Random rand)
	{
		return this.definitions.get(rand.nextInt(this.definitions.size()));
	}

}
