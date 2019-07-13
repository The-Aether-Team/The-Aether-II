package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.state.IntegerProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStandingSkyrootSign extends BlockSkyrootSign
{
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 15);

	public BlockStandingSkyrootSign(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.blockState.getBaseState().with(BlockStandingSkyrootSign.ROTATION, 0));
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!world.getBlockState(pos.down()).getMaterial().isSolid())
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.removeBlock(pos, false);
		}

		super.neighborChanged(state, world, pos, block, fromPos, p_220069_6_);
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().with(BlockStandingSkyrootSign.ROTATION, meta);
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		return state.get(BlockStandingSkyrootSign.ROTATION);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockStandingSkyrootSign.ROTATION);
	}
}
