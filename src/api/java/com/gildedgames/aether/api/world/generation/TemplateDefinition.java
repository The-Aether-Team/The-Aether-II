package com.gildedgames.aether.api.world.generation;

import com.google.common.collect.Lists;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class TemplateDefinition
{

	private final Template template;

	private CenterOffsetProcessor offset;

	private List<PlacementCondition> conditions = Lists.newArrayList();

	private List<PostPlacement> postPlacements = Lists.newArrayList();

	private boolean randomRotation;

	public TemplateDefinition(final Template template)
	{
		this.template = template;
	}

	public TemplateDefinition setPostPlacements(final PostPlacement postPlacement, final PostPlacement... postPlacements)
	{
		this.postPlacements = Lists.newArrayList(postPlacements);

		this.postPlacements.add(postPlacement);

		return this;
	}

	public List<PostPlacement> getPostPlacements()
	{
		return this.postPlacements;
	}

	public TemplateDefinition setConditions(final PlacementCondition condition, final PlacementCondition... conditions)
	{
		this.conditions = Lists.newArrayList(conditions);

		this.conditions.add(condition);

		return this;
	}

	public CenterOffsetProcessor getOffset()
	{
		return this.offset;
	}

	public TemplateDefinition setOffset(final CenterOffsetProcessor offset)
	{
		this.offset = offset;

		return this;
	}

	public List<PlacementCondition> getConditions()
	{
		return this.conditions;
	}

	public Template getTemplate()
	{
		return this.template;
	}

	public TemplateDefinition setRandomRotation(final boolean flag)
	{
		this.randomRotation = flag;

		return this;
	}

	public boolean hasRandomRotation()
	{
		return this.randomRotation;
	}

	@Override
	public TemplateDefinition clone()
	{
		final TemplateDefinition clone = new TemplateDefinition(this.template).setOffset(this.offset).setRandomRotation(this.randomRotation);

		clone.conditions = this.conditions;

		return clone;
	}
}
