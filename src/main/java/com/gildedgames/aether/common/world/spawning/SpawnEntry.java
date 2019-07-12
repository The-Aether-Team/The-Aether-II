package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.world.spawn.IPositionSelector;
import com.gildedgames.aether.api.world.spawn.ISpawnEntry;
import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import com.gildedgames.aether.common.world.spawning.util.GroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.MobEntity;

import java.util.Collections;
import java.util.List;

public class SpawnEntry implements ISpawnEntry
{

	private final IPositionSelector positionSelector;

	private final PlacementType placementType;

	private final Class<? extends Entity> clazz;

	private final int minGroupSize, maxGroupSize;

	private final float rarityWeight;

	private final List<IConditionPosition> conditions = Lists.newArrayList();

	public SpawnEntry(PlacementType placementType, Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize)
	{
		this(placementType, clazz, rarityWeight, minGroupSize, maxGroupSize, new GroundPositionSelector());
	}

	public SpawnEntry(PlacementType placementType, Class<? extends Entity> clazz, float rarityWeight, int minGroupSize, int maxGroupSize,
			IPositionSelector heightSelector)
	{
		this.placementType = placementType;
		this.clazz = clazz;
		this.rarityWeight = rarityWeight;
		this.minGroupSize = minGroupSize;
		this.maxGroupSize = maxGroupSize;
		this.positionSelector = heightSelector;
	}

	@Override
	public PlacementType getPlacementType()
	{
		return this.placementType;
	}

	@Override
	public SpawnEntry addCondition(IConditionPosition condition)
	{
		this.conditions.add(condition);

		return this;
	}

	@Override
	public List<IConditionPosition> getConditions()
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
	public IPositionSelector getPositionSelector()
	{
		return this.positionSelector;
	}

}
