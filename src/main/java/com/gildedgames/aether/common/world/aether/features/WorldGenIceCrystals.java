package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockIceCrystal;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.world.IWorldGen;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenIceCrystals extends WorldGenerator implements IWorldGen
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
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{

		return this.generate(new BlockAccessExtendedWrapper(worldIn), worldIn, rand, position, false);
	}

	@Override
	public boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position, boolean centered)
	{
		if (!blockAccess.canAccess(position))
		{
			return false;
		}

		int count = 0;
		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos =
					position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!blockAccess.canAccess(randomPos))
			{
				return false;
			}

			if (!blockAccess.canAccess(randomPos.down()) || !blockAccess.canAccess(randomPos.up()))
			{
				return false;
			}

			final IBlockState above = blockAccess.getBlockState(randomPos.up());
			final IBlockState below = blockAccess.getBlockState(randomPos.down());

			if (blockAccess.isAirBlock(randomPos))
			{
				if (this.statesCanPlaceOn.isEmpty() || this.statesCanPlaceOn.contains(below))
				{
					blockAccess.setBlockState(randomPos, this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.STALAGMITE), 2 | 16);
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
					blockAccess.setBlockState(randomPos, this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.STALACTITE), 2 | 16);
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

	@Override
	public boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}
}
