package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class WorldGenFloorPlacer extends WorldGenerator
{
	private final IBlockState[] states;

	private final int amount;

	private IBlockState[] statesCanPlaceOn = new IBlockState[0];

	public WorldGenFloorPlacer(final IBlockState... states)
	{
		this(-1, states);
	}

	public WorldGenFloorPlacer(final int amount, final IBlockState... states)
	{
		this.states = states;
		this.amount = amount;
	}

	public void setStatesToPlaceOn(final IBlockState... states)
	{
		this.statesCanPlaceOn = states;
	}

	@Override
	public boolean generate(final World world, final Random rand, BlockPos pos)
	{
		pos = world.getTopSolidOrLiquidBlock(pos);

		int count = 0;

		WorldSlice slice = new WorldSlice(world, new ChunkPos(pos));

		BlockPos.MutableBlockPos randomPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos randomPosDown = new BlockPos.MutableBlockPos();

		for (int attempts = 0; attempts < 128; attempts++)
		{
			randomPos.setPos(
					pos.getX() + (rand.nextInt(16) - 8),
					pos.getY() + (rand.nextInt(8) - 4),
					pos.getZ() + (rand.nextInt(16) - 8)
			);

			if (!slice.isAirBlock(randomPos))
			{
				continue;
			}

			randomPosDown.setPos(randomPos);
			randomPosDown.setY(randomPosDown.getY() - 1);

			final IBlockState below = slice.getBlockState(randomPosDown);

			if (((this.statesCanPlaceOn.length == 0 && below.getBlock() == BlocksAether.aether_grass) || ArrayUtils.contains(this.statesCanPlaceOn, below)))
			{
				final IBlockState chosen = this.states[rand.nextInt(this.states.length)];

				slice.setBlockState(randomPos, chosen);

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
