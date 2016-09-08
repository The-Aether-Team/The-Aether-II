package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.common.world.spawning.util.GroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;

import java.util.List;

public class SpawnEntry
{

	private Class<? extends Entity> clazz;

	private int minGroupSize, maxGroupSize;

	private float rarityWeight;

	private List<PosCondition> conditions = Lists.newArrayList();

	private final PositionSelector positionSelector;

	public SpawnEntry(Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize)
	{
		this(clazz, rarityWeight, minGroupSize, maxGroupSize, new GroundPositionSelector());
	}

	public SpawnEntry(Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize, PositionSelector heightSelector)
	{
		this.clazz = clazz;
		this.rarityWeight = rarityWeight;
		this.minGroupSize = minGroupSize;
		this.maxGroupSize = maxGroupSize;
		this.positionSelector = heightSelector;
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

	public float getRarityWeight() { return this.rarityWeight; }

	public PositionSelector getPositionSelector()
	{
		return this.positionSelector;
	}

}
