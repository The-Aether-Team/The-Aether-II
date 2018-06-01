package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSnow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCustomSnow extends BlockSnow implements IBlockWithItem
{
	public BlockCustomSnow()
	{
		super();

		this.setHardness(0.1F);
		this.setSoundType(SoundType.SNOW);
		this.setLightOpacity(0);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();

		if (block != BlocksAether.highlands_ice && block != BlocksAether.highlands_packed_ice && block != Blocks.BARRIER)
		{
			BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
			return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down())
					|| block == this && iblockstate.getValue(LAYERS).intValue() == 8;
		}
		else
		{
			return false;
		}
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockCustomSnow(this);
	}
}
