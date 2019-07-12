package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Only exists because Aerogel walls are translucent.
public class BlockScatterglassWall extends BlockCustomWall
{
	public BlockScatterglassWall(BlockState state, float hardness, float resistance)
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
	public boolean shouldSideBeRendered(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		BlockState neighborState = world.getBlockState(pos.offset(side));
		Block block = neighborState.getBlock();

		return block != this;
	}
}
