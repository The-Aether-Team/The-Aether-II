package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAetherPlant extends Block implements IGrowable
{
	protected static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);

	public BlockAetherPlant(final Material material)
	{
		super(material);
	}

	public boolean isSuitableSoilBlock(World world, BlockPos pos, final BlockState blockUnderneath)
	{
		return blockUnderneath.getBlock() == BlocksAether.aether_grass || blockUnderneath.getBlock() == BlocksAether.aether_dirt;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		final BlockState soilBlock = world.getBlockState(pos.down());

		return this.isSuitableSoilBlock(world, pos, soilBlock);
	}

	@Override
	public void neighborChanged(final BlockState state, final World world, final BlockPos pos, final Block block, final BlockPos fromPos)
	{
		if (!this.validatePosition(world, pos, state))
		{
			this.invalidateBlock(world, pos, state);
		}
	}

	public boolean validatePosition(final World world, final BlockPos pos, final BlockState state)
	{
		return this.canPlaceBlockAt(world, pos);
	}

	protected void invalidateBlock(final World world, final BlockPos pos, final BlockState state)
	{
		this.dropBlockAsItem(world, pos, state, 0);

		world.setBlockToAir(pos);
	}

	@Override
	public AxisAlignedBB getBoundingBox(final BlockState state, final IBlockReader source, final BlockPos pos)
	{
		return PLANT_AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(final BlockState blockState, final IBlockReader worldIn, final BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(final BlockState state)
	{
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return false;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{

	}

	@Override
	public boolean onBlockActivated(
			final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity playerIn, final Hand hand, final Direction facing,
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
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}
}
