package com.gildedgames.aether.api.world.decoration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface WorldDecoration
{
	boolean shouldGenerate(Random random);

	/**
	 * @return Number of times this decoration should attempt to generate per chunk.
	 */
	int getGenerationCount();

	WorldDecorationGenerator getGenerator(Random rand);

	BlockPos findPositionToPlace(World world, Random rand, BlockPos pos);
}
