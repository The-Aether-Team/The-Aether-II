package com.gildedgames.aether.common.world.decorations;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecorationGenerator;
import com.gildedgames.aether.common.blocks.natural.BlockIceCrystal;
import com.gildedgames.orbis.lib.world.WorldSlice;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class WorldGenIceCrystals implements WorldDecorationGenerator
{

	private final BlockState crystalState;

	private List<BlockState> statesCanPlaceOn = Lists.newArrayList();

	private final int max;

	public WorldGenIceCrystals(final int max)
	{
		this.crystalState = BlocksAether.highlands_ice_crystal.getDefaultState();
		this.max = max;
	}

	public void setStatesToPlaceOn(final BlockState... states)
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


			final BlockState above = slice.getBlockState(randomPos.up());
			final BlockState below = slice.getBlockState(randomPos.down());

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
