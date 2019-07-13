package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockCustomCarpet extends Block
{
	private static final VoxelShape CARPET_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	public BlockCustomCarpet(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return CARPET_AABB;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		return super.isValidPosition(state, world, pos) && this.canBlockStay(world, pos);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!this.canBlockStay(world, pos))
		{
			world.destroyBlock(pos, true);
		}
	}

	private boolean canBlockStay(IWorldReader worldIn, BlockPos pos)
	{
		return !worldIn.isAirBlock(pos.down());
	}
}
