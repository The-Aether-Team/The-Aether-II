package com.gildedgames.aether.api.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PosCondition
{

	boolean isMet(World world, BlockPos spawnAt, BlockPos underneath);

}
