package com.gildedgames.aether.common.world.features.aerclouds;

import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.Random;

public class WorldGenPurpleAercloud extends WorldGenAercloud
{
	public WorldGenPurpleAercloud(IBlockState state, int numberOfBlocks, boolean isFlat)
	{
		super(state, numberOfBlocks, isFlat);
	}

	@Override
	public IBlockState getAercloudState(Random random)
	{
		return this.state.withProperty(BlockAercloud.PROPERTY_FACING,
				EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)]);
	}
}
