package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.common.world.spawning.util.GroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.List;

public class SpawnEntry
{

	private final PositionSelector positionSelector;

	private final EntityLiving.SpawnPlacementType placementType;

	private Class<? extends Entity> clazz;

	private int minGroupSize, maxGroupSize;

	private float rarityWeight;

	private List<PosCondition> conditions = Lists.newArrayList();

	public SpawnEntry(EntityLiving.SpawnPlacementType placementType, Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize)
	{
		this(placementType, clazz, rarityWeight, minGroupSize, maxGroupSize, new GroundPositionSelector());
	}

	public SpawnEntry(EntityLiving.SpawnPlacementType placementType, Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize,
			PositionSelector heightSelector)
	{
		this.placementType = placementType;
		this.clazz = clazz;
		this.rarityWeight = rarityWeight;
		this.minGroupSize = minGroupSize;
		this.maxGroupSize = maxGroupSize;
		this.positionSelector = heightSelector;
	}

	public EntityLiving.SpawnPlacementType getPlacementType()
	{
		return this.placementType;
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

	public Class<? extends Entity> getEntityClass()
	{
		return this.clazz;
	}

	public int getMinGroupSize()
	{
		return this.minGroupSize;
	}

	public int getMaxGroupSize()
	{
		return this.maxGroupSize;
	}

	public float getRarityWeight()
	{
		return this.rarityWeight;
	}

	public PositionSelector getPositionSelector()
	{
		return this.positionSelector;
	}

}
