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

		this.setLightOpacity(3);
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IBlockReader world, BlockPos pos, Direction face)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		BlockState neighborState = world.getBlockState(pos.offset(side));
		if (neighborState.getBlock() == BlocksAether.scatterglass_slab)
		{
			if (state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK)
			{
				return neighborState.getValue(PROPERTY_SLAB_STATE) != EnumSlabPart.FULL_BLOCK;
			}
			return false;
		}
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}
}
