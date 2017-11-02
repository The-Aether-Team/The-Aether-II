package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.world.generation.CenterOffsetProcessor;
import com.google.common.collect.Lists;

import java.util.List;

public class BlueprintDefinition
{

	private final BlueprintData data;

	private IOrbisDefinitionRegistry registry;

	private CenterOffsetProcessor offset;

	private List<PlacementCondition> conditions = Lists.newArrayList();

	private List<PostPlacement> postPlacements = Lists.newArrayList();

	private boolean randomRotation = true;

	public BlueprintDefinition(final BlueprintData data)
	{
		this.data = data;
	}

	public BlueprintDefinition setPostPlacements(final PostPlacement postPlacement, final PostPlacement... postPlacements)
	{
		this.postPlacements = Lists.newArrayList(postPlacements);

		this.postPlacements.add(postPlacement);

		return this;
	}

	public List<PostPlacement> getPostPlacements()
	{
		return this.postPlacements;
	}

	public BlueprintDefinition setConditions(final PlacementCondition condition, final PlacementCondition... conditions)
	{
		this.conditions = Lists.newArrayList(conditions);

		this.conditions.add(condition);

		return this;
	}

	public CenterOffsetProcessor getOffset()
	{
		return this.offset;
	}

	public BlueprintDefinition setOffset(final CenterOffsetProcessor offset)
	{
		this.offset = offset;

		return this;
	}

	public List<PlacementCondition> getConditions()
	{
		return this.conditions;
	}

	public BlueprintDefinition setConditions(final PlacementCondition[] conditions)
	{
		this.conditions = Lists.newArrayList(conditions);

		return this;
	}

	public BlueprintData getData()
	{
		return this.data;
	}

	public BlueprintDefinition setRandomRotation(final boolean flag)
	{
		this.randomRotation = flag;

		return this;
	}

	public boolean hasRandomRotation()
	{
		return this.randomRotation;
	}

	public IOrbisDefinitionRegistry getRegistry()
	{
		return this.registry;
	}

	public BlueprintDefinition setRegistry(final IOrbisDefinitionRegistry registry)
	{
		this.registry = registry;

		return this;
	}

	@Override
	public BlueprintDefinition clone()
	{
		final BlueprintDefinition clone = new BlueprintDefinition(this.data).setRegistry(this.registry).setOffset(this.offset)
				.setRandomRotation(this.randomRotation);

		clone.conditions = this.conditions;

		return clone;
	}
}
