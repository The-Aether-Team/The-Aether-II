package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.islands.IVirtualChunk;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.aether.island.data.virtual.VirtualChunkFunnel;
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

	private final WorldGenAetherCaves caveGenerator;

	private final NoiseGeneratorPerlin surfaceNoise;

	private final OpenSimplexNoise noise;

	private double[] depthBuffer;

	public WorldPreparationAether(final World world, final Random rand)
	{
		this.world = world;
		this.rand = rand;

		this.noise = new OpenSimplexNoise(world.getSeed());

		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

		this.caveGenerator = new WorldGenAetherCaves();
	}

	public OpenSimplexNoise getNoise()
	{
		return this.noise;
	}

	public void generateBaseTerrain(final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final Biome[] biomes = this.world.getBiomeProvider().getBiomesForGeneration(null, chunkX * 16, chunkZ * 16, 16, 16);

		final int worldX = chunkX * 16;
		final int worldZ = chunkZ * 16;

		//TODO: Interpolate this! Can increase performance a lot
		for (int x = 0; x < 16; x++)
		{
			final double nx = (worldX + x) / 70D;

			for (int z = 0; z < 16; z++)
			{
				final double nz = (worldZ + z) / 70D;

				final double val = NoiseUtil.normalise(NoiseUtil.something(this.noise, nx, nz));

				if (val > 0.0)
				{
					for (int y = 70; y < 70 + (val * 10); y++)
					{
						if (y > 72)
						{
							primer.setBlockState(x, y, z, BlocksAether.aercloud.getDefaultState());
						}
					}
				}
			}
		}

		final IIslandGenerator generator = island.getGenerator();

		generator.genIslandForChunk(this.noise, this.world, primer, island, chunkX, chunkZ);

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
		// Penetration depth evalNormalised generation
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
				int top;

				boolean searchingSolid = true;

				// Find top-most block
				for (int y = island.getBounds().getMaxY(); y > island.getBounds().getMinY(); y--)
				{
					if (!searchingSolid)
					{
						if (primer.getBlockState(x, y, z).getBlock() == Blocks.AIR)
						{
							searchingSolid = true;
						}

						continue;
					}

					if (primer.getBlockState(x, y, z).getBlock() != Blocks.AIR)
					{
						top = y;

						// Penetrate ground and set biome blocks
						for (int y1 = top; pentration <= depth & y1 > 0; y1--)
						{
							final IBlockState state = primer.getBlockState(x, y1, z);

							if (state == BlocksAether.holystone.getDefaultState() || state.getBlock() == BlocksAether.ferrosite)
							{
								primer.setBlockState(x, y1, z, pentration < 1 ? biome.topBlock : biome.fillerBlock);
							}

							pentration++;
						}

						searchingSolid = false;
						pentration = 0;
					}
				}
			}
		}
	}

}
