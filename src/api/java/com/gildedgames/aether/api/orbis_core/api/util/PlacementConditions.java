package com.gildedgames.aether.api.orbis_core.api.util;

import com.gildedgames.aether.api.orbis_core.api.PlacementCondition;
import com.gildedgames.aether.api.orbis_core.block.BlockData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.util.BlockUtil;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class PlacementConditions
{

	public static PlacementCondition onSpecificBlock(final int floorHeight, final Block... blocks)
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
			{
				if (pos.getY() == placedAt.getY() + floorHeight && block.getBlockState().getBlock() != Blocks.AIR
						&& block.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = pos.down();

					if (!world.canAccess(down))
					{
						return false;
					}

					final Block blockDown = world.getBlockState(down).getBlock();

					for (final Block s : blocks)
					{
						if (s == blockDown)
						{
							return true;
						}
					}

					return false;
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

	public static PlacementCondition replaceableGround()
	{
		return replaceable(true, Material.GROUND, Material.GRASS, Material.AIR);
	}

	public static PlacementCondition replaceable(final Material... acceptedMaterials)
	{
		return replaceable(true, acceptedMaterials);
	}

	public static PlacementCondition replaceable(final boolean isCriticalWithCheck, final Material... acceptedMaterials)
	{
		return new PlacementCondition()
		{
			List<Material> materials = Lists.newArrayList(acceptedMaterials);

			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
			{
				if (block.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
				{
					if (!world.canAccess(pos))
					{
						return false;
					}

					final IBlockState state = world.getBlockState(pos);

					if ((BlockUtil.isSolid(block.getBlockState()) || block.getBlockState().getMaterial() == Material.PORTAL
							|| block.getBlockState() == Blocks.AIR.getDefaultState()) && (BlueprintUtil.isReplaceable(world, pos)
							|| this.materials.contains(state.getMaterial())))
					{
						return true;
					}

					if ((isCriticalWithCheck ? block.getBlockState() == state : block.getBlockState().getBlock() == state.getBlock())
							|| this.materials.contains(state.getMaterial()))
					{
						return true;
					}

					if (!world.isAirBlock(pos))
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

	public static PlacementCondition insideGround(final Block inside)
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
			{
				if (pos.getY() == placedAt.getY() + 1 && block.getBlockState().getBlock() != Blocks.AIR
						&& block.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = pos.down();

					if (!world.canAccess(down))
					{
						return false;
					}

					final IBlockState state = world.getBlockState(down);

					if (state.getBlock() != inside)
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

	public static PlacementCondition flatGround()
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
			{
				if (pos.getY() == placedAt.getY() && block.getBlockState().getBlock() != Blocks.AIR
						&& block.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = pos.down();

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

	public static PlacementCondition ignoreBlock(final int floorHeight, final IBlockState s)
	{
		return new PlacementCondition()
		{
			@Override
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
			{
				if (pos.getY() == placedAt.getY() + floorHeight && block.getBlockState().getBlock() != Blocks.AIR
						&& block.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
				{
					final BlockPos down = pos.down();

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
			public boolean canPlace(final BlueprintData data, final IBlockAccessExtended world, final BlockPos placedAt, final BlockData block,
					final BlockPos pos)
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

					if (y == placedAt.getY() && info.getBlockState().getBlock() != Blocks.AIR && info.getBlockState().getBlock() != Blocks.STRUCTURE_VOID)
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
