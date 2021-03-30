package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRockGlass extends Block
{
	public BlockRockGlass()
	{
		super(Material.ROCK);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);

		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.crude_scatterglass_shard;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		BlockPos offset = pos.offset(side);
		IBlockState iblockstate = blockAccess.getBlockState(offset);

		return iblockstate.getBlock() != this && (iblockstate.getBlock().isAir(iblockstate, blockAccess, offset) || !iblockstate.isFullCube());
	}
}
