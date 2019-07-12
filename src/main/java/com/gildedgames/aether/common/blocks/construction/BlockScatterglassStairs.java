package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockCustomStairs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockScatterglassStairs extends BlockCustomStairs
{

	public BlockScatterglassStairs(BlockState state)
	{
		super(state);

		this.setLightOpacity(3);
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IBlockReader world, BlockPos pos, Direction face)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		BlockState iblockstate = world.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		return block != this;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}

}
