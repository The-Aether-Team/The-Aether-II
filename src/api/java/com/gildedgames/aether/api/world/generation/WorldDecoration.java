package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.gildedgames.orbis.api.world.IWorldGen;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public interface WorldDecoration
{

	boolean shouldGenerate(Random random);

	/**
	 * @return Number of times this decoration should attempt to generate per chunk.
	 */
	int getGenerationCount();

	IWorldGen getGenerator(Random rand);

	BlockPos findPositionToPlace(IBlockAccessExtended blockAccess, Random rand, BlockPos pos);

}
