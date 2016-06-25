package com.gildedgames.aether.common.world.dungeon.labyrinth.dim;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BiomeProviderSliderLabyrinth extends BiomeProvider
{
	private final BiomeSliderLabyrinth biomeGenerator = new BiomeSliderLabyrinth();

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list)
	{
		return list.contains(this.biomeGenerator);
	}

	@Override
	public Biome[] getBiomesForGeneration(Biome[] array, int x, int z, int width, int depth)
	{
		IntCache.resetIntCache();

		int size = width * depth;

		if (array == null || array.length < size)
		{
			array = new Biome[size];
		}

		Arrays.fill(array, 0, size, this.biomeGenerator);

		return array;
	}

	@Override
	public BlockPos findBiomePosition(int x, int z, int range, List biomes, Random random)
	{
		if (biomes.contains(this.biomeGenerator))
		{
			return new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1));
		}

		return null;
	}
}