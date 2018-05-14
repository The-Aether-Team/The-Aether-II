package com.gildedgames.aether.api.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.List;

public interface ISpawnEntry
{
	EntityLiving.SpawnPlacementType getPlacementType();

	ISpawnEntry addCondition(PosCondition condition);

	List<PosCondition> getConditions();

	Class<? extends Entity> getEntityClass();

	int getMinGroupSize();

	int getMaxGroupSize();

	float getRarityWeight();

	PositionSelector getPositionSelector();
}
