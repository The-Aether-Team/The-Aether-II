package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.api.world.IChunkInfoAether;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandChunkInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.preparation.IChunkMask;
import com.gildedgames.aether.api.world.preparation.IChunkMaskTransformer;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.BiomesAetherInit;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.world.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.generators.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.island.BlockAccessIsland;
import com.gildedgames.aether.common.world.island.IslandBounds;
import com.gildedgames.aether.common.world.island.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.island.IslandData;
import com.gildedgames.orbis.lib.util.random.XoRoShiRoRandom;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public class PrepAether implements IPrepRegistryEntry<IChunkInfoAether>
{
	public static final ResourceLocation UNIQUE_ID = AetherCore.getResource("islands");

	private final IslandChunkMaskTransformer chunkMaskTransformer = new IslandChunkMaskTransformer();

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
		return world.getDimension().getType() == DimensionsAether.AETHER;
	}

	@Override
	public void postSectorDataCreate(World world, IPrepSectorData sectorData)
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

				biomeAether.getBiomeDecorator().prepareDecorationsWholeIsland(world,
						new BlockAccessIsland(world, islandData, sectorData, this), islandData,
						new XoRoShiRoRandom(islandData.getSeed()));
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
			chosen = (BiomeAetherBase) BiomesAether.HIGHLANDS;
		}
		else
		{
			chosen = BiomesAetherInit.fetchRandomBiome(rand);
		}

		final PrepSectorDataAether data = new PrepSectorDataAether(world, sectorX, sectorY);
		final IslandData island = new IslandData(data, bounds, chosen, islandSeed);

		data.setIslandData(island);

		return data;
	}

	@Override
	public IPrepSectorData createDataAndRead(World world, CompoundNBT tag)
	{
		return new PrepSectorDataAether(world, tag);
	}

	@Override
	public void threadSafeGenerateMask(IChunkInfoAether info, World world, IPrepSectorData sectorData, IChunkMask mask, int x, int z)
	{
		IChunkGenerator generator = world.provider.createChunkGenerator();

		if (generator instanceof ChunkGeneratorAether && sectorData instanceof PrepSectorDataAether)
		{
			ChunkGeneratorAether aetherGen = (ChunkGeneratorAether) generator;
			PrepSectorDataAether aetherData = (PrepSectorDataAether) sectorData;

			IIslandData islandData = aetherData.getIslandData();

			aetherGen.generateBaseTerrain(info, mask, islandData, x, z);
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer()
	{
		return this.chunkMaskTransformer;
	}

	@Override
	public ChunkInfoAether generateChunkColumnInfo(World world, IPrepSectorData sectorData, int chunkX, int chunkZ)
	{
		IChunkGenerator generator = world.provider.createChunkGenerator();

		if (generator instanceof ChunkGeneratorAether && sectorData instanceof PrepSectorDataAether)
		{
			ChunkGeneratorAether aetherGen = (ChunkGeneratorAether) generator;
			PrepSectorDataAether aetherData = (PrepSectorDataAether) sectorData;

			IIslandData islandData = aetherData.getIslandData();

			IIslandChunkInfo obj = aetherGen.generateChunkColumnInfo(islandData, chunkX, chunkZ);

			ChunkInfoAether info = new ChunkInfoAether(1);
			info.setIslandData(0, obj);

			return info;
		}

		return null;
	}
}
