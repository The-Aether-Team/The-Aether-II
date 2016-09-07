package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.common.world.spawning.util.GroundHeightSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.List;

public class SpawnEntry
{

	private Class<? extends Entity> clazz;

	private int minGroupSize, maxGroupSize, scatterSize;

	private List<PosCondition> conditions = Lists.newArrayList();

	private final HeightSelector heightSelector;

	public SpawnEntry(Class<? extends Entity> clazz, int minGroupSize, int maxGroupSize)
	{
		this(clazz, minGroupSize, maxGroupSize, 4);
	}

	public SpawnEntry(Class<? extends Entity> clazz, int minGroupSize, int maxGroupSize, int scatterSize)
	{
		this(clazz, minGroupSize, maxGroupSize, scatterSize, new GroundHeightSelector());
	}

	public SpawnEntry(Class<? extends Entity> clazz, int minGroupSize, int maxGroupSize, int scatterSize, HeightSelector heightSelector)
	{
		this.clazz = clazz;
		this.minGroupSize = minGroupSize;
		this.maxGroupSize = maxGroupSize;
		this.scatterSize = scatterSize;
		this.heightSelector = heightSelector;
	}

	public SpawnEntry conditiion(PosCondition condition)
	{
		this.conditions.add(condition);

		return this;
	}

	public List<PosCondition> getConditions()
	{
		return this.conditions;
	}

	public Class<? extends Entity> getEntityClass() { return this.clazz; }

	public int getMinGroupSize()
	{
		return this.minGroupSize;
	}

	public int getMaxGroupSize()
	{
		return this.maxGroupSize;
	}

	public int getScatterSize()
	{
		return this.scatterSize;
	}

	public HeightSelector getHeightSelector()
	{
		return this.heightSelector;
	}

}
