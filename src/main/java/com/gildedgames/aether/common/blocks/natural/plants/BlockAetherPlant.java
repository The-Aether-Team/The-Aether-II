package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAetherPlant extends Block implements IGrowable
{
	protected static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);

	public BlockAetherPlant(final Material material)
	{
		super(material);
	}

	public boolean isSuitableSoilBlock(World world, BlockPos pos, final IBlockState blockUnderneath)
	{
		return blockUnderneath.getBlock() == BlocksAether.aether_grass || blockUnderneath.getBlock() == BlocksAether.aether_dirt;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		final IBlockState soilBlock = world.getBlockState(pos.down());

		return this.isSuitableSoilBlock(world, pos, soilBlock);
	}

	@Override
	public void neighborChanged(final IBlockState state, final World world, final BlockPos pos, final Block block, final BlockPos fromPos)
	{
		if (!this.validatePosition(world, pos, state))
		{
			this.invalidateBlock(world, pos, state);
		}
	}

	public boolean validatePosition(final World world, final BlockPos pos, final IBlockState state)
	{
		return this.canPlaceBlockAt(world, pos);
	}

	protected void invalidateBlock(final World world, final BlockPos pos, final IBlockState state)
	{
		this.dropBlockAsItem(world, pos, state, 0);

		world.setBlockToAir(pos);
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		return PLANT_AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(final IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient)
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{
		return false;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{

	}

	@Override
	public boolean onBlockActivated(
			final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (playerIn.getHeldItemMainhand().getItem() == ItemsAether.swet_jelly)
		{
			if (!this.canGrow(worldIn, pos, state, true))
			{
				return false;
			}
			if (!playerIn.isCreative())
			{
				playerIn.getHeldItemMainhand().shrink(1);
			}
			this.grow(worldIn, new Random(), pos, state);
			return true;
		}
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
