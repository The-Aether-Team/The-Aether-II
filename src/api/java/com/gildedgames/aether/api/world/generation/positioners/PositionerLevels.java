package com.gildedgames.aether.api.world.generation.positioners;

import com.gildedgames.aether.api.world.decoration.WorldDecorationPositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PositionerLevels implements WorldDecorationPositioner
{
	private final int min, max;

	public PositionerLevels(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	@Override
	public BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
	{
		final int width = 16;
		return pos.add(rand.nextInt(width), this.min + rand.nextInt(this.max - this.min), rand.nextInt(width));
	}
}
