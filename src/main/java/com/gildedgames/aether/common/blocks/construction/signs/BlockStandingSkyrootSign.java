package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStandingSkyrootSign extends BlockSkyrootSign
{
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 15);

	public BlockStandingSkyrootSign(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(BlockStandingSkyrootSign.ROTATION, 0));
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!world.getBlockState(pos.down()).getMaterial().isSolid())
		{
			world.destroyBlock(pos, true);
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockStandingSkyrootSign.ROTATION);
	}
}
