package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IVirtualChunk;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.aether.island.data.virtual.VirtualChunkFunnel;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * This is like a ChunkGenerator, but instead using virtual chunks
 * Here we'll place the island for generation later
 */
public class WorldPreparationAether
{

	private final World world;

	private final Random rand;

	private final IslandGenerator islandGenerator;

	private final WorldGenAetherCaves caveGenerator;

	private final NoiseGeneratorPerlin surfaceNoise;

	private double[] depthBuffer;

	public WorldPreparationAether(final World world, final Random rand)
	{
		this.world = world;
		this.rand = rand;

		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

		this.islandGenerator = new IslandGenerator(world);
		this.caveGenerator = new WorldGenAetherCaves();
	}

	public void generateBaseTerrain(final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final Biome[] biomes = this.world.getBiomeProvider().getBiomesForGeneration(null, chunkX * 16, chunkZ * 16, 16, 16);

		this.islandGenerator.genIslandForChunk(primer, island, chunkX, chunkZ);
		this.replaceBiomeBlocks(island, primer, chunkX, chunkZ, biomes);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, primer);
	}

	private void prepare(final IIslandData island)
	{
		if (island.getVirtualDataManager().isPreparing())
		{
			return;
		}

		island.getVirtualDataManager().setPreparing(true);

		final int minChunkX = island.getBounds().getMinX() / 16;
		final int minChunkZ = island.getBounds().getMinZ() / 16;

		final int maxChunkX = island.getBounds().getMaxX() / 16;
		final int maxChunkZ = island.getBounds().getMaxZ() / 16;

		for (int chunkX = minChunkX; chunkX < maxChunkX; chunkX++)
		{
			for (int chunkZ = minChunkZ; chunkZ < maxChunkZ; chunkZ++)
			{
				if (this.world.isChunkGeneratedAt(chunkX, chunkZ))
				{
					continue;
				}

				final VirtualChunkFunnel funnel = new VirtualChunkFunnel(island.getVirtualDataManager().getChunk(chunkX, chunkZ));

				this.generateBaseTerrain(funnel, island, chunkX, chunkZ);
			}
		}

		if (island.getBiome() instanceof BiomeAetherBase)
		{
			final BiomeAetherBase aetherBiome = (BiomeAetherBase) island.getBiome();

			aetherBiome.getBiomeDecorator().prepareDecorationsWholeIsland(this.world, island, this.rand);
		}

		for (int chunkX = minChunkX; chunkX < maxChunkX; chunkX++)
		{
			for (int chunkZ = minChunkZ; chunkZ < maxChunkZ; chunkZ++)
			{
				final IVirtualChunk chunk = island.getVirtualDataManager().getChunk(chunkX, chunkZ);

				this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

				chunk.prepareThisAndNearbyChunks(this.world, island, this.rand);
			}
		}

		island.getVirtualDataManager().markPrepared();
		island.getVirtualDataManager().setPreparing(false);
		island.getVirtualDataManager().dropAllChunks();
	}

	public void checkAndPrepareIfAvailable(final int chunkX, final int chunkZ)
	{
		for (final IIslandData island : this.getIslands(chunkX, chunkZ))
		{
			if (!island.getVirtualDataManager().isPrepped())
			{
				this.prepare(island);
			}
		}
	}

	public Collection<IIslandData> getIslands(final int chunkX, final int chunkZ)
	{
		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final ISectorAccess access = IslandSectorHelper.getAccess(this.world);
		final ISector sector = access.provideSector(chunkX, chunkZ);

		if (sector == null)
		{
			return Collections.emptyList();
		}

		return sector.getIslandsForRegion(posX, 0, posZ, 16, 255, 16);
	}

	// Calculate max penetration depth
	public void replaceBiomeBlocks(final IIslandData island, final ChunkPrimer primer, final int chunkX, final int chunkZ, final Biome[] biomes)
	{
		// Penetration depth noise generation
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer,
				(double) (chunkX * 16), (double) (chunkZ * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final Biome biome = biomes[x + (z * 16)];

				final double val = this.depthBuffer[x + (z * 16)];

				// Calculate max penetration depth
				final int depth = (int) (val / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);

				int pentration = 0;
				int top = 0;

				// Find top-most block
				for (int y = island.getBounds().getMaxY(); y > island.getBounds().getMinY(); y--)
				{
					if (primer.getBlockState(x, y, z).getBlock() != Blocks.AIR)
					{
						top = y;

						break;
					}
				}

				// Penetrate ground and set biome blocks
				for (int y = top; pentration <= depth & y > 0; y--)
				{
					final IBlockState state = primer.getBlockState(x, y, z);

					if (state.getBlock() == BlocksAether.holystone)
					{
						primer.setBlockState(x, y, z, pentration < 1 ? biome.topBlock : biome.fillerBlock);
					}

					pentration++;
				}
			}
		}
	}

}
