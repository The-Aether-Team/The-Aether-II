package com.gildedgames.aether.api.world.spawn;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;

import java.util.List;

public interface ISpawnEntry
{
	PlacementType getPlacementType();

	ISpawnEntry addCondition(IConditionPosition condition);

	List<IConditionPosition> getConditions();

	Class<? extends Entity> getEntityClass();

	int getMinGroupSize();

	int getMaxGroupSize();

	float getRarityWeight();

	IPositionSelector getPositionSelector();
}
