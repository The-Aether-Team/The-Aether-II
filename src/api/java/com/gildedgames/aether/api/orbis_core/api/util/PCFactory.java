package com.gildedgames.aether.api.orbis_core.api.util;

import com.gildedgames.aether.api.orbis_core.api.PlacementCondition;
import com.gildedgames.aether.api.orbis_core.block.BlockData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.util.BlockUtil;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class PCFactory
{

	public static PlacementCondition flatGround()
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block, final int x,
					final int y, final int z)
			{
				if (y == placedAt.getY() && block.getBlock() != Blocks.AIR && block.getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = new BlockPos(x, y - 1, z);

					if (!world.canAccess(down))
					{
						return false;
					}

					final IBlockState state = world.getBlockState(down);

					if (!BlockUtil.isSolid(state, world, down))
					{
						return false;
					}
				}

				return true;
			}

			@Override
			public boolean canPlaceCheckAll(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt,
					final BlockDataContainer blocks)
			{
				return true;
			}
		};
	}

	public static PlacementCondition ignoreBlock(final IBlockState s)
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block, final int x,
					final int y, final int z)
			{
				if (y == placedAt.getY() && block.getBlock() != Blocks.AIR && block.getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = new BlockPos(x, y - 1, z);

					if (!world.canAccess(down))
					{
						return false;
					}

					final IBlockState state = world.getBlockState(down);

					if (s == state)
					{
						return false;
					}
				}

				return true;
			}

			@Override
			public boolean canPlaceCheckAll(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt,
					final BlockDataContainer blocks)
			{
				return true;
			}
		};
	}

	public static PlacementCondition insideGroundAtSource(final Block block)
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block, final int x,
					final int y, final int z)
			{
				return true;
			}

			@Override
			public boolean canPlaceCheckAll(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt,
					final BlockDataContainer blocks)
			{
				if (!world.canAccess(placedAt))
				{
					return false;
				}

				IBlockState state = world.getBlockState(placedAt);

				if (state.getBlock() != block)
				{
					return false;
				}

				int index = 0;

				for (final BlockData info : blocks)
				{
					final int x = blocks.getX(index) + placedAt.getX();
					final int y = blocks.getY(index) + placedAt.getY();
					final int z = blocks.getZ(index) + placedAt.getZ();

					if (y == placedAt.getY() && info.getBlock() != Blocks.AIR && info.getBlock() != Blocks.STRUCTURE_VOID)
					{
						final BlockPos down = new BlockPos(x, y - 1, z);

						if (!world.canAccess(down))
						{
							return false;
						}

						state = world.getBlockState(down);

						if (!BlockUtil.isSolid(state))
						{
							return false;
						}
					}

					index++;
				}

				return true;
			}
		};
	}

}
