package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.util.BlockCustomSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockScatterglassSlab extends BlockCustomSlab
{
	public BlockScatterglassSlab(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IBlockReader world, BlockPos pos, Direction face)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}
