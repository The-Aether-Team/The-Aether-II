package com.gildedgames.aether.common.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class BlockPosUtil
{
	public static AxisAlignedBB bounds(BlockPos start, BlockPos end)
	{
		return new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public static MutableBlockPos add(MutableBlockPos pos, BlockPos add)
	{
		return BlockPosUtil.add(pos, add.getX(), add.getY(), add.getZ());
	}

	public static MutableBlockPos add(MutableBlockPos pos, int x, int y, int z)
	{
		pos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

		return pos;
	}

	public static MutableBlockPos down(MutableBlockPos pos, int n)
	{
		pos.setPos(pos.getX(), pos.getY() - n, pos.getZ());

		return pos;
	}

	public static MutableBlockPos set(MutableBlockPos pos, int x, int y, int z)
	{
		pos.setPos(x, y, z);

		return pos;
	}

	public static MutableBlockPos toMutable(BlockPos pos)
	{
		return new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
	}
}
