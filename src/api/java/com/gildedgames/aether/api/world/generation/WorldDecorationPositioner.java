package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface WorldDecorationPositioner
{
	BlockPos findPositionToPlace(World world, Random rand, BlockPos pos);
}
