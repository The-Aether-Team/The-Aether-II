package com.gildedgames.aether.world;

import com.gildedgames.aether.Aether;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerAether extends WorldChunkManager
{
	private BiomeGenBase biomeGenerator;

	public WorldChunkManagerAether()
	{
		this.biomeGenerator = Aether.getAetherBiome();
	}

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list)
	{
		return list.contains(this.biomeGenerator);
	}

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
	public float[] getRainfall(float listToReuse[], int x, int z, int width, int length)
	{
		int size = width * length;

		if (listToReuse == null || listToReuse.length < size)
		{
			listToReuse = new float[size];
		}

		Arrays.fill(listToReuse, 0, size, 0);

		return listToReuse;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase listToReuse[], int x, int z, int width, int depth)
	{
		int size = width * depth;

		if (listToReuse == null || listToReuse.length < size)
		{
			listToReuse = new BiomeGenBase[size];
		}

		Arrays.fill(listToReuse, 0, size, this.biomeGenerator);

		return listToReuse;
	}
}
