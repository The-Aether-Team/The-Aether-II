package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockCustomStairs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockScatterglassStairs extends BlockCustomStairs
{

	public BlockScatterglassStairs(IBlockState state)
	{
		super(state);

		this.setLightOpacity(3);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = world.getBlockState(pos.offset(side));
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
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

}
