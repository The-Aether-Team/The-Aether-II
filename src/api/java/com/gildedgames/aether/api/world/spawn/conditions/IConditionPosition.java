package com.gildedgames.aether.api.world.spawn.conditions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IConditionPosition
{

	boolean isMet(World world, BlockPos spawnAt, BlockPos underneath);

}
