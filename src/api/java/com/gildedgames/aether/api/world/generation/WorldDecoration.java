package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.world.IWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public interface WorldDecoration
{

	DecorateBiomeEvent.Decorate.EventType getDecorateType();

	boolean shouldGenerate(Random random);

	/**
	 * @return Number of times this decoration should attempt to generate per chunk.
	 */
	int getGenerationCount();

	IWorldGen getGenerator(Random rand);

	BlockPos findPositionToPlace(IBlockAccessExtended blockAccess, Random rand, BlockPos pos);

}
