package com.gildedgames.aether.common.world.dimensions.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenFloorPlacer extends WorldGenerator
{

	private final IBlockState[] states;

	private final List<IBlockState> statesCanPlaceOn = Lists.newArrayList();

	private final int amount;

	public WorldGenFloorPlacer(IBlockState... states)
	{
		this(-1, states);
	}

	public WorldGenFloorPlacer(int amount, IBlockState... states)
	{
		this.states = states;
		this.amount = amount;

		this.statesCanPlaceOn.add(BlocksAether.aether_grass.getDefaultState());
	}

	public List<IBlockState> getStatesCanPlaceOn()
	{
		return this.statesCanPlaceOn;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		pos = world.getTopSolidOrLiquidBlock(pos);

		int count = 0;

		for (int attempts = 0; attempts < 128; attempts++)
		{
			BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!world.isBlockLoaded(randomPos))
			{
				return false;
			}

			IBlockState chosen = this.states[rand.nextInt(this.states.length)];

			IBlockState below = world.getBlockState(randomPos.down());

			if (world.isAirBlock(randomPos) && (this.statesCanPlaceOn.isEmpty() || this.statesCanPlaceOn.contains(below)))
			{
				world.setBlockState(randomPos, chosen, 2);

				if (this.amount > 0)
				{
					if (count < this.amount)
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

		return true;
	}
}
