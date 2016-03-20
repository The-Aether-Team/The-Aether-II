package com.gildedgames.aether.common.blocks.construction.aether_walls;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Only exists because Aerogel walls are translucent.
public class BlockAerogelWall extends BlockAetherWall
{
	public BlockAerogelWall(Block block, float hardness, float resistance)
	{
		super(block, hardness, resistance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState state = world.getBlockState(pos);
		IBlockState neighborState = world.getBlockState(pos.offset(side.getOpposite()));

		return state != neighborState && super.shouldSideBeRendered(world, pos, side);
	}
}
