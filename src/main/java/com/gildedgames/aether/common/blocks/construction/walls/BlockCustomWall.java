package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.state.StateContainer;

public class BlockCustomWall extends WallBlock
{
	public BlockCustomWall(final Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(UP, Boolean.FALSE).with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE)
				.with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockCustomWall.UP, BlockCustomWall.NORTH, BlockCustomWall.EAST, BlockCustomWall.WEST, BlockCustomWall.SOUTH);
	}
}
