package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BlockCustomSnow extends SnowBlock
{
	public BlockCustomSnow(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState belowState = worldIn.getBlockState(pos.down());
		Block belowBlock = belowState.getBlock();

		if (belowBlock != Blocks.ICE && belowBlock != Blocks.PACKED_ICE && belowBlock != Blocks.BARRIER)
		{
			return Block.doesSideFillSquare(belowState.getCollisionShape(worldIn, pos.down()), Direction.UP) || belowBlock == this && belowState.get(LAYERS) == 8;
		}

		return false;
	}
}
