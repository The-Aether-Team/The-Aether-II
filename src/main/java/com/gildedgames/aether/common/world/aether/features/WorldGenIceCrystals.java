package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.api.world.generation.WorldDecorationGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockIceCrystal;
import com.gildedgames.orbis.lib.world.WorldSlice;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class WorldGenIceCrystals implements WorldDecorationGenerator
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
	public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
	{
		int count = 0;

		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos = pos.add(
					rand.nextInt(8) - rand.nextInt(8),
					rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));


			final IBlockState above = slice.getBlockState(randomPos.up());
			final IBlockState below = slice.getBlockState(randomPos.down());

			if (slice.isAirBlock(randomPos))
			{
				if (this.statesCanPlaceOn.isEmpty() || this.statesCanPlaceOn.contains(below))
				{
					int type = rand.nextInt(3);
                    slice.setBlockState(randomPos,
							this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.PROPERTY_VARIANT.fromMeta(type)));
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
					int type = rand.nextInt(3);
					type = type + 3;
					slice.setBlockState(randomPos,
							this.crystalState.withProperty(BlockIceCrystal.PROPERTY_VARIANT, BlockIceCrystal.PROPERTY_VARIANT.fromMeta(type)));
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
