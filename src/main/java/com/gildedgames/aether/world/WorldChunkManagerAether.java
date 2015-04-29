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
	public BlockPos findBiomePosition(int i, int j, int k, List list, Random random)
	{
		if (list.contains(this.biomeGenerator))
		{
			return new BlockPos(i - k + random.nextInt(k * 2 + 1), 0, j - k + random.nextInt(k * 2 + 1));
		}

		return null;
	}

	@Override
	public float[] getRainfall(float af[], int i, int j, int k, int l)
	{
		if (af == null || af.length < k * l)
		{
			af = new float[k * l];
		}

		Arrays.fill(af, 0, k * l, 0);

		return af;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase listToReuse[], int i, int j, int k, int l)
	{
		if (listToReuse == null || listToReuse.length < k * l)
		{
			listToReuse = new BiomeGenBase[k * l];
		}

		Arrays.fill(listToReuse, 0, k * l, this.biomeGenerator);

		return listToReuse;
	}
}
