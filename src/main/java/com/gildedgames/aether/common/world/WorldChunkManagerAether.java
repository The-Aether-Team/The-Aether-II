package com.gildedgames.aether.common.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.gildedgames.aether.common.AetherConfig;
import com.gildedgames.aether.common.world.biome.BiomeGenAether;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManagerAether extends WorldChunkManager
{
	// TODO: Config option to change biome ID.
	private final BiomeGenBase biomeGenerator = new BiomeGenAether(AetherConfig.AetherBiomeID);

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list)
	{
		return list.contains(this.biomeGenerator);
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
	{
		return this.loadBlockGeneratorData(listToReuse, x, z, width, length);
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
	public float[] getRainfall(float[] arrayToReuse, int x, int z, int width, int length)
	{
		int size = width * length;

		if (arrayToReuse == null || arrayToReuse.length < size)
		{
			arrayToReuse = new float[size];
		}

		Arrays.fill(arrayToReuse, 0, size, 0);

		return arrayToReuse;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] arrayToReuse, int x, int z, int width, int depth)
	{
		int size = width * depth;

		if (arrayToReuse == null || arrayToReuse.length < size)
		{
			arrayToReuse = new BiomeGenBase[size];
		}

		Arrays.fill(arrayToReuse, 0, size, this.biomeGenerator);

		return arrayToReuse;
	}
}
