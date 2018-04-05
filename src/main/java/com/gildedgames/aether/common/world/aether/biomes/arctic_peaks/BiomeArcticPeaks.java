package com.gildedgames.aether.common.world.aether.biomes.arctic_peaks;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGeneratorArcticPeaks;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGeneratorHighlands;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BiomeArcticPeaks extends BiomeAetherBase
{

	public BiomeArcticPeaks(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ARCTIC);

		this.setDefaultSubBiome(new SubBiomeArctic());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return Blocks.PACKED_ICE.getDefaultState();
	}

	@Override
	public IIslandGenerator getIslandGenerator()
	{
		return IslandGenerators.ARCTIC_PEAKS;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Collections.emptyList();
	}

	@Override
	public float getRarityWeight()
	{
		return 1.0F;
	}

	@Override
	public void postDecorate(final World world, final Random rand, final BlockPos pos)
	{
		final int chunkX = pos.getX() >> 4;
		final int chunkZ = pos.getZ() >> 4;

		final ISectorAccess access = IslandSectorHelper.getAccess(world);
		final ISector sector = access.provideSector(chunkX, chunkZ);

		// TODO: support multiple islands in same chunk
		final IIslandData island = sector.getIslandsForRegion(pos.getX(), 0, pos.getZ(), 16, 255, 16)
				.stream().findFirst().orElse(null);

		if (island == null)
		{
			return;
		}

		final IChunkGenerator generator = ((WorldServer) world).getChunkProvider().chunkGenerator;

		if (generator instanceof ChunkGeneratorAether)
		{
			final ChunkGeneratorAether aetherGen = (ChunkGeneratorAether) generator;

			final OpenSimplexNoise noise = aetherGen.getPreparation().getNoise();

			final double[] heightMap = IslandGeneratorArcticPeaks.generateNoise(noise, island, chunkX, chunkZ);

			final int posX = pos.getX() + 8;
			final int posZ = pos.getZ() + 8;

			final double centerX = island.getBounds().getCenterX();
			final double centerZ = island.getBounds().getCenterZ();

			final double radiusX = island.getBounds().getWidth() / 2.0;
			final double radiusZ = island.getBounds().getLength() / 2.0;

			for (int x = 0; x < 16; x++)
			{
				for (int z = 0; z < 16; z++)
				{
					final BlockPos p = new BlockPos(posX + x, 0, posZ + z);

					if (world.isBlockLoaded(p))
					{
						final int worldX = posX + x;
						final int worldZ = posZ + z;

						final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
						final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

						// Get distance from center of Island
						final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.1D;

						final double sample = IslandGeneratorHighlands.interpolate(heightMap, x, z);
						final double heightSample = sample + 1.0 - dist;

						final BlockPos blockpos1 = p.add(0, world.getHeight(posX + x, posZ + z), 0);
						final BlockPos blockpos2 = blockpos1.down();

						if (world.canBlockFreezeWater(blockpos2))
						{
							world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
						}

						if (heightSample > 0.5)
						{
							if (world.canSnowAt(blockpos1, true))
							{
								world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
							}
							else if (world.getBlockState(blockpos1).getBlock() instanceof BlockAetherFlower)
							{
								IBlockState state = world.getBlockState(blockpos1);
								int id = state.getBlock().getMetaFromState(state);

								if (id < BlockAetherFlower.SNOW_START)
								{
									world.setBlockState(blockpos1, state.withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PROPERTY_VARIANT.fromMeta(id + BlockAetherFlower.SNOW_START)), 0);
								}
							}
						}
					}
				}
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(final float currentTemperature)
	{
		return 0xcbe4eb;
	}

}
