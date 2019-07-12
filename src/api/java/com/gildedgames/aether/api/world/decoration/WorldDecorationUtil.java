package com.gildedgames.aether.api.world.decoration;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldDecorationUtil
{

	public static void generateDecorations(List<WorldDecoration> decorations, World world, Random rand, BlockPos pos)
	{
		ChunkPos chunkPos = new ChunkPos(pos);

		WorldSlice slice = new WorldSlice(world, chunkPos);

		for (final WorldDecoration decoration : decorations)
		{
			for (int count = 0; count < decoration.getGenerationCount(); count++)
			{
				if (decoration.shouldGenerate(rand)/* && TerrainGen.decorate(world, rand, chunkPos, decoration.getDecorateType())*/)
				{
					final BlockPos placeAt = decoration.findPositionToPlace(world, rand, pos);

					decoration.getGenerator(rand).generate(slice, rand, placeAt);
				}
			}
		}
	}

	public static void generateDecorationsWithNoise(List<WorldDecoration> decorations, World world, Random rand, BlockPos pos,
			OpenSimplexNoise noise, float openAreaChance, float clumpedDecorationCountModifier)
	{
		ChunkPos chunkPos = new ChunkPos(pos);

		WorldSlice slice = new WorldSlice(world, chunkPos);

		for (final WorldDecoration decoration : decorations)
		{
			for (int count = 0; count < decoration.getGenerationCount() * clumpedDecorationCountModifier; count++)
			{
				if (decoration.shouldGenerate(rand)/* && TerrainGen.decorate(world, rand, chunkPos, decoration.getDecorateType())*/)
				{
					final BlockPos placeAt = decoration.findPositionToPlace(world, rand, pos);

					boolean isClumped = noise.eval(placeAt.getX() / 100D, placeAt.getZ() / 100D) + 1.0F < rand.nextFloat();
					boolean override = rand.nextFloat() < openAreaChance;

					if (isClumped || override)
					{
						decoration.getGenerator(rand).generate(slice, rand, placeAt);

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
