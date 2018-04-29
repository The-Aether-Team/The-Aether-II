package com.gildedgames.aether.common.world.aether.prep;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.aether.island.data.IslandBounds;
import com.gildedgames.aether.common.world.aether.island.data.IslandData;
import com.gildedgames.orbis_api.preparation.IPrepChunkManager;
import com.gildedgames.orbis_api.preparation.IPrepRegistryEntry;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public class PrepAether implements IPrepRegistryEntry
{
	public static final ResourceLocation UNIQUE_ID = AetherCore.getResource("islands");

	@Override
	public ResourceLocation getUniqueId()
	{
		return UNIQUE_ID;
	}

	@Override
	public int getSectorChunkArea()
	{
		return 60;
	}

	@Override
	public boolean shouldAttachTo(World world)
	{
		return world.provider.getDimensionType() == DimensionsAether.AETHER;
	}

	@Override
	public void postSectorDataCreate(World world, IPrepSectorData sectorData, IPrepChunkManager iPrepChunkManager)
	{
		if (sectorData instanceof PrepSectorDataAether)
		{
			PrepSectorDataAether sectorDataAether = (PrepSectorDataAether) sectorData;

			IIslandData islandData = sectorDataAether.getIslandData();
			Biome biome = islandData.getBiome();

			if (biome instanceof BiomeAetherBase)
			{
				BiomeAetherBase biomeAether = (BiomeAetherBase) biome;

				islandData.addComponents(biomeAether.createIslandComponents(islandData));

				biomeAether.getBiomeDecorator().prepareDecorationsWholeIsland(world, islandData, sectorData, new Random(islandData.getSeed()));
			}
		}
	}

	@Override
	public IPrepSectorData createData(World world, long seed, int sectorX, int sectorY)
	{
		final int sectorArea = this.getSectorChunkArea() * 16;

		final int width = 960;
		final int height = 255;
		final int length = 960;

		final int x = (sectorArea * sectorX);
		final int y = 0;
		final int z = (sectorArea * sectorY);

		final IIslandBounds bounds = new IslandBounds(x, y, z, x + width, y + height, z + length);

		final long islandSeed = seed ^ ((long) x * 341873128712L + (long) z * 132897987541L);

		final Random rand = new Random(islandSeed);

		final BiomeAetherBase chosen;

		if (sectorX == 0 && sectorY == 0)
		{
			chosen = BiomesAether.HIGHLANDS;
		}
		else
		{
			chosen = BiomesAether.fetchRandomBiome(rand);
		}

		final IslandData island = new IslandData(world, bounds, chosen, islandSeed);

		return new PrepSectorDataAether(world, island, sectorX, sectorY);
	}

	@Override
	public IPrepSectorData createDataAndRead(World world, NBTTagCompound tag)
	{
		return new PrepSectorDataAether(world, tag);
	}

	@Override
	public void threadSafeGenerateChunk(World world, IPrepSectorData sectorData, Biome[] biomes, ChunkPrimer chunkPrimer, int chunkX, int chunkZ)
	{
		IChunkGenerator generator = world.provider.createChunkGenerator();

		if (generator instanceof ChunkGeneratorAether && sectorData instanceof PrepSectorDataAether)
		{
			ChunkGeneratorAether aetherGen = (ChunkGeneratorAether) generator;
			PrepSectorDataAether aetherData = (PrepSectorDataAether) sectorData;

			IIslandData islandData = aetherData.getIslandData();

			aetherGen.threadSafeGenerateChunk(biomes, chunkPrimer, islandData, chunkX, chunkZ);
		}
	}
}
