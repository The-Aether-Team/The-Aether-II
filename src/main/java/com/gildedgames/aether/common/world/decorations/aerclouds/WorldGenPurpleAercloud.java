package com.gildedgames.aether.common.world.decorations.aerclouds;

import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.Random;

public class WorldGenPurpleAercloud extends WorldGenAercloud
{
	public WorldGenPurpleAercloud(final IBlockState state, final int numberOfBlocks, final boolean isFlat)
	{
		super(state, numberOfBlocks, isFlat);
	}

	@Override
	public IBlockState getAercloudState(final Random random)
	{
		return this.state.withProperty(BlockAercloud.PROPERTY_FACING,
				EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)]);
	}
}
