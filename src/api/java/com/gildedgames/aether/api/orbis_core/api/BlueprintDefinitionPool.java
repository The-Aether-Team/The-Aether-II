package com.gildedgames.aether.api.orbis_core.api;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class BlueprintDefinitionPool
{

	private final List<BlueprintDefinition> definitions;

	private boolean hasRandomRotation = true;

	public BlueprintDefinitionPool(final BlueprintDefinition... definitions)
	{
		this.definitions = Lists.newArrayList(definitions);
	}

	public BlueprintDefinition getRandomDefinition(final Random rand)
	{
		return this.definitions.get(rand.nextInt(this.definitions.size()));
	}

	public boolean hasRandomRotation()
	{
		return this.hasRandomRotation;
	}

	public BlueprintDefinitionPool setRandomRotation(final boolean flag)
	{
		this.hasRandomRotation = flag;

		return this;
	}

}
