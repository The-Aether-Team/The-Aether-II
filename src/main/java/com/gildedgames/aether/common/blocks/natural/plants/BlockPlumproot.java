package com.gildedgames.aether.common.blocks.natural.plants;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPlumproot extends Block
{
	public BlockPlumproot()
	{
		super(Material.GOURD, MapColor.ADOBE);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(1.0F);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
	}
}
