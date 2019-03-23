package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.BlockCustomSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockScatterglassSlab extends BlockCustomSlab
{
	public BlockScatterglassSlab(Material material)
	{
		super(material);

		this.setLightOpacity(3);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState neighborState = world.getBlockState(pos.offset(side));
		if (neighborState.getBlock() == BlocksAether.scatterglass_slab)
		{
			if (state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK)
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
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
