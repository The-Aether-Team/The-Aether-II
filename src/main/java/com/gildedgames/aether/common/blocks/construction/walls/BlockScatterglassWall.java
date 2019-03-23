package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Only exists because Aerogel walls are translucent.
public class BlockScatterglassWall extends BlockCustomWall
{
	public BlockScatterglassWall(IBlockState state, float hardness, float resistance)
	{
		super(state, hardness, resistance);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState neighborState = world.getBlockState(pos.offset(side));
		Block block = neighborState.getBlock();

		return block != this;
	}
}
