package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockRockGlassTranslucent extends BlockBreakable
{
	public BlockRockGlassTranslucent()
	{
		super(Material.GLASS, false);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);

		this.setSoundType(SoundType.GLASS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
	{
		BlockPos offset = pos.offset(side);
		BlockState iblockstate = blockAccess.getBlockState(offset);

		return iblockstate.getBlock() != this && (iblockstate.getBlock().isAir(iblockstate, blockAccess, offset) || !iblockstate.isFullCube());
	}
}
