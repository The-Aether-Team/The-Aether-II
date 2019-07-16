package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import com.gildedgames.aether.api.world.spawn.conditions.IConditionWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.World;

public class CheckDimension implements IConditionPosition, IConditionWorld
{

	private final DimensionType dimensionTypeToCheckFor;

	public CheckDimension(DimensionType dimensionTypeToCheckFor)
	{
		this.dimensionTypeToCheckFor = dimensionTypeToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return this.isMet(world);
	}

	@Override
	public boolean isMet(World world)
	{
		return world.getDimension().getType() == this.dimensionTypeToCheckFor;
	}
}
