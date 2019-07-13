package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSnow;
import net.minecraft.block.*;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCustomSnow extends SnowBlock implements IBlockWithItem
{
	public BlockCustomSnow(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		BlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();

		if (block != BlocksAether.highlands_ice && block != BlocksAether.highlands_packed_ice && block != Blocks.BARRIER)
		{
			VoxelShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), Direction.UP);
			return blockfaceshape == VoxelShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down())
					|| block == this && iblockstate.get(LAYERS) == 8;
		}
		else
		{
			return false;
		}
	}

	@Override
	public BlockItem createItemBlock()
	{
		return new ItemBlockCustomSnow(this);
	}
}
