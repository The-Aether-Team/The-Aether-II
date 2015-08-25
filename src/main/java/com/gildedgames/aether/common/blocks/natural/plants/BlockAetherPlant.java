package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAetherPlant extends Block
{
	public BlockAetherPlant(Material material)
	{
		super(material);

		this.setBlockBounds(0.3f, 0.0F, 0.3f, 0.7f, 0.6f, 0.7f);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		Block soilBlock = world.getBlockState(pos.down()).getBlock();

		return this.isSuitableSoilBlock(soilBlock);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		this.validatePosition(world, pos, state);
	}

	public void validatePosition(World world, BlockPos pos, IBlockState state)
	{
		if (!this.canPlaceBlockAt(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.setBlockToAir(pos);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	public boolean isSuitableSoilBlock(Block soilBlock)
	{
		return soilBlock == BlocksAether.aether_grass || soilBlock == BlocksAether.aether_dirt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
}
