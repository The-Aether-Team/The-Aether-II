package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.world.ISpawnEntry;
import com.gildedgames.aether.api.world.PosCondition;
import com.gildedgames.aether.api.world.PositionSelector;
import com.gildedgames.aether.common.world.spawning.util.GroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.Collections;
import java.util.List;

public class SpawnEntry implements ISpawnEntry
{

	private final PositionSelector positionSelector;

	private final EntityLiving.SpawnPlacementType placementType;

	private final Class<? extends Entity> clazz;

	private final int minGroupSize, maxGroupSize;

	private final float rarityWeight;

	private final List<PosCondition> conditions = Lists.newArrayList();

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

	@Override
	public EntityLiving.SpawnPlacementType getPlacementType()
	{
		return this.placementType;
	}

	@Override
	public SpawnEntry addCondition(PosCondition condition)
	{
		this.conditions.add(condition);

		return this;
	}

	@Override
	public List<PosCondition> getConditions()
	{
		return Collections.unmodifiableList(this.conditions);
	}

	@Override
	public Class<? extends Entity> getEntityClass()
	{
		return this.clazz;
	}

	@Override
	public int getMinGroupSize()
	{
		return this.minGroupSize;
	}

	@Override
	public int getMaxGroupSize()
	{
		return this.maxGroupSize;
	}

	@Override
	public float getRarityWeight()
	{
		return this.rarityWeight;
	}

	@Override
	public PositionSelector getPositionSelector()
	{
		return this.positionSelector;
	}

}
