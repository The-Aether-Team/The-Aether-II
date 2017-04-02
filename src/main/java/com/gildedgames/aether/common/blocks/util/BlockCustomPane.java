package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockCustomPane extends BlockPane
{
	private final Block block;

	private final IBlockState base;

	public BlockCustomPane(IBlockState state)
	{
		super(state.getMaterial(), false);

		this.base = state;
		this.block = state.getBlock();

		this.setLightOpacity(0);

		this.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return this.block.getBlockLayer();
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return this.block.getHarvestLevel(state);
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return this.block.getHarvestTool(state);
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		return this.block.getSoundType(state, world, pos, entity);
	}

	@Override
	public Material getMaterial(IBlockState state)
	{
		return this.block.getMaterial(state);
	}


}
