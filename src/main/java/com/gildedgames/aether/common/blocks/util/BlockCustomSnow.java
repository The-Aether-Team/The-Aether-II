package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSnow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
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
		BlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();

		if (block != BlocksAether.highlands_ice && block != BlocksAether.highlands_packed_ice && block != Blocks.BARRIER)
		{
			VoxelShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), Direction.UP);
			return blockfaceshape == VoxelShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down())
					|| block == this && iblockstate.getValue(LAYERS) == 8;
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
