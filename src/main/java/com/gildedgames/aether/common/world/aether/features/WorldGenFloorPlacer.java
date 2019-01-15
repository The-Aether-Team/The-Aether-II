package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.world.IWorldGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class WorldGenFloorPlacer extends WorldGenerator implements IWorldGen
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
	public boolean generate(final World world, final Random rand, final BlockPos pos)
	{
		return this.generate(new BlockAccessExtendedWrapper(world), world, rand, pos, false);
	}

	@Override
	public boolean generate(final IBlockAccessExtended access, final World world, final Random rand, BlockPos pos, final boolean centered)
	{
		pos = access.getTopPos(pos);

		int count = 0;

		BlockPos.PooledMutableBlockPos randomPos = BlockPos.PooledMutableBlockPos.retain();
		BlockPos.PooledMutableBlockPos randomPosDown = BlockPos.PooledMutableBlockPos.retain();

		for (int attempts = 0; attempts < 128; attempts++)
		{
			randomPos.setPos(
					pos.getX() + (rand.nextInt(16) - 8),
					pos.getY() + (rand.nextInt(8) - 4),
					pos.getZ() + (rand.nextInt(16) - 8)
			);

			final IBlockState chosen = this.states[rand.nextInt(this.states.length)];

			if (access.isAirBlock(randomPos))
			{
				randomPosDown.setPos(randomPos);
				randomPosDown.setY(randomPosDown.getY() - 1);

				final IBlockState below = access.getBlockState(randomPosDown);

				if (((this.statesCanPlaceOn.length == 0 && below.getBlock() == BlocksAether.aether_grass) || ArrayUtils.contains(this.statesCanPlaceOn, below)))
				{
					access.setBlockState(randomPos, chosen, 2 | 16);

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
		}

		randomPos.release();
		randomPosDown.release();

		return true;
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}
}
