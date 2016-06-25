package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.biome.BiomeAether;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

import java.util.List;
import java.util.Random;

public class BiomeProviderAether extends BiomeProvider
{
	private final Biome biomeGenerator = new BiomeAether(AetherCore.CONFIG.getAetherBiomeID());

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list)
	{
		return list.contains(this.biomeGenerator);
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

	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
	{
		IntCache.resetIntCache();

		if (biomes == null || biomes.length < width * height)
		{
			biomes = new Biome[width * height];
		}

		for (int i = 0; i < width * height; ++i)
		{
			biomes[i] = biomeGenerator;
		}

		return biomes;
	}
}
