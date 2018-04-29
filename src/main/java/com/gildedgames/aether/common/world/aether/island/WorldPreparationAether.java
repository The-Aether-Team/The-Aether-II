package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

/**
 * This is like a ChunkGenerator, but instead using virtual chunks
 * Here we'll place the island for generation later
 */
public class WorldPreparationAether
{

	private final World world;

	private final IBlockAccessExtended access;

	private final Random rand;

	private final WorldGenAetherCaves caveGenerator;

	private final NoiseGeneratorPerlin surfaceNoise;

	private final OpenSimplexNoise noise;

	private double[] depthBuffer;

	public WorldPreparationAether(final World world, final Random rand)
	{
		this.world = world;
		this.rand = rand;

		this.access = new BlockAccessExtendedWrapper(this.world);

		this.noise = new OpenSimplexNoise(world.getSeed());

		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

		this.caveGenerator = new WorldGenAetherCaves();
	}

	public OpenSimplexNoise getNoise()
	{
		return this.noise;
	}

	public void generateBaseTerrain(Biome[] biomes, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
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

				if (val > 0.2)
				{
					for (int y = 70; y < 70 + ((val - 0.2) * 10); y++)
					{
						primer.setBlockState(x, y, z, BlocksAether.aercloud.getDefaultState());
					}

					for (int y = 70; y > 70 - ((val - 0.2) * 10); y--)
					{
						primer.setBlockState(x, y, z, BlocksAether.aercloud.getDefaultState());
					}
				}
			}
		}

		final IIslandGenerator generator = island.getGenerator();

		generator.genIslandForChunk(biomes, this.noise, this.access, primer, island, chunkX, chunkZ);

		this.replaceBiomeBlocks(island, primer, chunkX, chunkZ, biomes);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, primer);
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
