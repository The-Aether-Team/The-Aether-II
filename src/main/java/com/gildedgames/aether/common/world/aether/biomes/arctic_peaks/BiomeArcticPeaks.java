package com.gildedgames.aether.common.world.aether.biomes.arctic_peaks;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.highlands.SubBiomeCrystalHighlands;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BiomeArcticPeaks extends BiomeAetherBase
{

	public BiomeArcticPeaks(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ARCTIC);

		this.setDefaultSubBiome(new SubBiomeCrystalHighlands());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT);
	}

	@Override
	public IIslandGenerator getIslandGenerator()
	{
		return IslandGenerators.HIGHLANDS;
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
		final int posX = pos.getX() + 8;
		final int posZ = pos.getZ() + 8;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final BlockPos p = new BlockPos(posX + x, 0, posZ + z);

				if (world.isBlockLoaded(p))
				{
					final BlockPos blockpos1 = p.add(0, world.getHeight(posX + x, posZ + z), 0);
					final BlockPos blockpos2 = blockpos1.down();

					if (world.canBlockFreezeWater(blockpos2))
					{
						world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
					}

					if (world.canSnowAt(blockpos1, true))
					{
						world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
					}
				}
			}
		}
	}

}
