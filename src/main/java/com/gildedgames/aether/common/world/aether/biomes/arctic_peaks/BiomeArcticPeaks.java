package com.gildedgames.aether.common.world.aether.biomes.arctic_peaks;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGeneratorArcticPeaks;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.aether.common.world.aether.island.gen.highlands.IslandGeneratorHighlands;
import com.gildedgames.aether.common.world.templates.TemplateWorldGen;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
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
import java.util.List;
import java.util.Random;

public class BiomeArcticPeaks extends BiomeAetherBase
{

	public BiomeArcticPeaks(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ARCTIC);
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return Blocks.PACKED_ICE.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand)
	{
		int coastHeight = 1 + rand.nextInt(3);
		double coastSpread = rand.nextDouble() * 0.6;

		if (coastHeight == 0)
		{
			coastSpread = 0.0;
		}

		double mountainAmplitude = 4.0;

		boolean hasTerraces = rand.nextInt(30) == 0;

		return new IslandGeneratorHighlands(IslandVariables.build()
				.coastHeight(coastHeight)
				.coastSpread(coastSpread)
				.lakeBlendRange(0.05 + (rand.nextDouble() * 0.5))
				.lakeDepth(rand.nextInt(40) + 5)
				.lakeScale(40.0D + (rand.nextDouble() * 30.0D))
				.lakeThreshold(rand.nextDouble() * 0.3)
				.maxTerrainHeight(80 + rand.nextInt(70))
				.terraces(hasTerraces)
				.lakeConcentrationModifier(0.5 + (rand.nextDouble() * -2.5))
				.heightSampleFilter((heightSample) -> Math.min(1.1, Math.pow(heightSample, mountainAmplitude) * 0.55))
				.snowCaps(!hasTerraces)
				.maxYFilter((bottomMaxY, filteredSample, cutoffPoint, topHeight) -> bottomMaxY + ((filteredSample - (hasTerraces ? cutoffPoint : 0.0))
						* topHeight))
				.lakeBottomValueFilter((lakeBottomValue) -> 0.0));
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
						final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

						final double sample = IslandGeneratorArcticPeaks.interpolate(heightMap, x, z);
						final double heightSample = sample + 1.0 - dist;

						final BlockPos blockpos1 = p.add(0, world.getHeight(posX + x, posZ + z), 0);
						final BlockPos blockpos2 = blockpos1.down();

						if (world.canBlockFreezeWater(blockpos2))
						{
							world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
						}

						if (heightSample > 0.5)
						{
							final IBlockState state = world.getBlockState(blockpos1);
							final Block block = state.getBlock();

							if (world.canSnowAt(blockpos1, true))
							{
								world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
							}
							else if (block instanceof IBlockSnowy)
							{
								final IBlockState newState = state.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE);

								world.setBlockState(blockpos1, newState, 2);
							}
						}
					}
				}
			}
		}

	}

	@Override
	public List<WorldDecoration> createBasicDecorations(Random rand)
	{
		List<WorldDecoration> decorations = Lists.newArrayList();

		decorations.add(new WorldDecorationSimple(2, GenerationAether.short_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2F, GenerationAether.skyroot_twigs));

		decorations.add(new WorldDecorationSimple(6, GenerationAether.holystone_rocks)
		{
			@Override
			public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
			{
				final int x = rand.nextInt(16) + 8;
				final int y = rand.nextInt(128);
				final int z = rand.nextInt(16) + 8;

				return pos.add(x, y, z);
			}
		});

		decorations.add(new WorldDecorationSimple(1, 0.06F, GenerationAether.storm_aercloud)
		{
			@Override
			public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
			{
				final int width = 16;
				final int minY = 90;
				final int maxY = 130;

				return pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));
			}
		});

		return decorations;
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		List<WorldDecoration> treeDecorations = Lists.newArrayList();

		treeDecorations.add(new WorldDecorationSimple(3, new TemplateWorldGen(GenerationAether.blue_skyroot_tree)));
		treeDecorations.add(new WorldDecorationSimple(4, new TemplateWorldGen(GenerationAether.dark_blue_skyroot_tree)));
		treeDecorations.add(new WorldDecorationSimple(3, new TemplateWorldGen(GenerationAether.dark_blue_skyroot_oak)));

		return treeDecorations;
	}

	@Override
	public float createForestTreeCountModifier(Random rand)
	{
		if (rand.nextInt(30) == 0)
		{
			return 0.1F + (rand.nextFloat() * 0.9F);
		}

		return 0.75F + (rand.nextFloat() * 0.25F);
	}

	@Override
	public float createOpenAreaDecorationGenChance(Random rand)
	{
		return 0.15F * rand.nextFloat();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(final float currentTemperature)
	{
		return 0xcbe4eb;
	}

}
