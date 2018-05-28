package com.gildedgames.aether.common.world.util;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class GenUtil
{
	public static BlockPos rotate(final BlockPos origin, final BlockPos pos, final Rotation rotation)
	{
		final int i = pos.getX() - origin.getX();
		final int j = pos.getY() - origin.getY();
		final int k = pos.getZ() - origin.getZ();

		switch (rotation)
		{
			case COUNTERCLOCKWISE_90:
				return new BlockPos(origin.getX() + k, pos.getY(), origin.getZ() - i);
			case CLOCKWISE_90:
				return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() + i);
			case CLOCKWISE_180:
				return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() - k);
			default:
				return pos;
		}
	}
}
