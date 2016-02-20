package com.gildedgames.aether.common.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos.MutableBlockPos;

public class BlockPosUtil
{
	public static MutableBlockPos add(MutableBlockPos pos, BlockPos add)
	{
		return BlockPosUtil.add(pos, add.getX(), add.getY(), add.getZ());
	}

	public static MutableBlockPos add(MutableBlockPos pos, int x, int y, int z)
	{
		pos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

		return pos;
	}

	public static MutableBlockPos down(MutableBlockPos pos, int n)
	{
		pos.set(pos.getX(), pos.getY() - n, pos.getZ());

		return pos;
	}

	public static MutableBlockPos set(MutableBlockPos pos, int x, int y, int z)
	{
		pos.set(x, y, z);

		return pos;
	}

	public static MutableBlockPos convert(BlockPos pos)
	{
		return new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
	}
}
