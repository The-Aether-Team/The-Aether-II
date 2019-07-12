package com.gildedgames.aether.common.world.decorations.aerclouds;

import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;

import java.util.Random;

public class WorldGenPurpleAercloud extends WorldGenAercloud
{
	public WorldGenPurpleAercloud(final BlockState state, final int numberOfBlocks, final boolean isFlat)
	{
		super(state, numberOfBlocks, isFlat);
	}

	@Override
	public BlockState getAercloudState(final Random random)
	{
		return this.state.withProperty(BlockAercloud.PROPERTY_FACING,
				Direction.HORIZONTALS[random.nextInt(Direction.HORIZONTALS.length)]);
	}
}
