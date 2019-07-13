package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockAetherPlant extends Block implements IGrowable
{
	protected static final VoxelShape PLANT_AABB = Block.makeCuboidShape(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);

	public BlockAetherPlant(final Properties properties)
	{
		super(properties.doesNotBlockMovement());
	}

	public boolean isSuitableSoilBlock(IWorldReader world, BlockPos pos, final BlockState blockUnderneath)
	{
		return blockUnderneath.getBlock() == BlocksAether.aether_grass || blockUnderneath.getBlock() == BlocksAether.aether_dirt;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		final BlockState soilBlock = world.getBlockState(pos.down());

		return this.isSuitableSoilBlock(world, pos, soilBlock);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!this.validatePosition(world, pos, state))
		{
			world.destroyBlock(pos, true);
		}
	}

	public boolean validatePosition(final World world, final BlockPos pos, final BlockState state)
	{
		return this.isValidPosition(state, world, pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return PLANT_AABB;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
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
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (player.getHeldItemMainhand().getItem() == ItemsAether.swet_jelly)
		{
			if (!this.canGrow(world, pos, state, true))
			{
				return false;
			}
			if (!player.isCreative())
			{
				player.getHeldItemMainhand().shrink(1);
			}
			this.grow(world, new Random(), pos, state);
			return true;
		}
		return false;
	}
}
