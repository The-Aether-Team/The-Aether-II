package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockIceCrystal;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenIceCrystals extends WorldGenerator
{

	private final IBlockState crystalState;

	private List<IBlockState> statesCanPlaceOn = Lists.newArrayList();

	private final int max;

	public WorldGenIceCrystals(final int max)
	{
		this.crystalState = BlocksAether.ice_crystal.getDefaultState();
		this.max = max;
	}

	public void setStatesToPlaceOn(final IBlockState... states)
	{
		this.statesCanPlaceOn = Lists.newArrayList(states);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		if (!world.isBlockLoaded(position))
		{
			return false;
		}

		int count = 0;

		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos =
					position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!world.isBlockLoaded(randomPos))
			{
				return false;
			}

			if (!world.isBlockLoaded(randomPos.down()) || !world.isBlockLoaded(randomPos.up()))
			{
				return false;
			}

			final IBlockState above = world.getBlockState(randomPos.up());
			final IBlockState below = world.getBlockState(randomPos.down());

			if (world.isAirBlock(randomPos))
			{
				if (this.statesCanPlaceOn.isEmpty() || this.statesCanPlaceOn.contains(below))
				{
					world.setBlockState(randomPos, this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.STALAGMITE), 2 | 16);

					if (this.max > 0)
					{
						if (count < this.max)
						{
							count++;
						}
						else
						{
							return true;
						}
					}
				}

				if (this.statesCanPlaceOn.isEmpty() || this.statesCanPlaceOn.contains(above))
				{
					world.setBlockState(randomPos, this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.STALACTITE), 2 | 16);

					if (this.max > 0)
					{
						if (count < this.max)
						{
							count++;
						}
						else
						{
							return true;
						}
					}
				}
			}
		}

		return true;
	}
}
