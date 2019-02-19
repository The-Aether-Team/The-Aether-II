package com.gildedgames.aether.api.world.generation.positioners;

import com.gildedgames.aether.api.world.generation.WorldDecorationPositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PositionerSurface implements WorldDecorationPositioner
{
	@Override
	public BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
	{
		final int x = rand.nextInt(16) + 8;
		final int z = rand.nextInt(16) + 8;

		return world.getTopSolidOrLiquidBlock(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
	}
}
