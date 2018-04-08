package com.gildedgames.aether.api.world.generation;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class WorldDecorationUtil
{

	public static void generateDecorations(List<WorldDecoration> decorations, IBlockAccessExtended blockAccess, Random rand, BlockPos pos)
	{
		for (final WorldDecoration decoration : decorations)
		{
			for (int count = 0; count < decoration.getGenerationCount(); count++)
			{
				if (decoration.shouldGenerate(rand))
				{
					final BlockPos placeAt = decoration.findPositionToPlace(blockAccess, rand, pos);

					decoration.getGenerator(rand).generate(blockAccess, blockAccess.getWorld(), rand, placeAt);
				}
			}
		}
	}

	public static void generateDecorationsWithNoise(List<WorldDecoration> decorations, IBlockAccessExtended blockAccess, Random rand, BlockPos pos,
			OpenSimplexNoise noise, float openAreaChance, float clumpedDecorationCountModifier)
	{
		for (final WorldDecoration decoration : decorations)
		{
			for (int count = 0; count < decoration.getGenerationCount() * clumpedDecorationCountModifier; count++)
			{
				if (decoration.shouldGenerate(rand))
				{
					final BlockPos placeAt = decoration.findPositionToPlace(blockAccess, rand, pos);

					boolean isClumped = noise.eval(placeAt.getX() / 100D, placeAt.getZ() / 100D) + 1.0F < rand.nextFloat();
					boolean override = rand.nextFloat() < openAreaChance;

					if (isClumped || override)
					{
						decoration.getGenerator(rand).generate(blockAccess, blockAccess.getWorld(), rand, placeAt);

						if (!isClumped)
						{
							break;
						}
					}
				}
			}
		}
	}

}
