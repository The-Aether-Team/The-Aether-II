package com.gildedgames.aether.common.blocks.natural.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBlueberryBush extends BlockAetherPlant implements IGrowable
{
	public static final int
			BERRY_BUSH_STEM = 0,
			BERRY_BUSH_RIPE = 1;

	public static final BooleanProperty PROPERTY_HARVESTABLE = BooleanProperty.create("harvestable");

	private static final VoxelShape BUSH_AABB = Block.makeCuboidShape(0.2D, 0.0D, 0.2D, 0.8D, 0.8D, 0.8D);

	public BlockBlueberryBush(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public float getBlockHardness(final BlockState blockState, final IBlockReader worldIn, final BlockPos pos)
	{
		if (blockState.get(PROPERTY_HARVESTABLE))
		{
			return 0.0f;
		}

		return super.getBlockHardness(blockState, worldIn, pos);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{
		if (state.get(PROPERTY_HARVESTABLE))
		{
			if (player.isCreative())
			{
				world.removeBlock(pos, false);

				return false;
			}

			world.setBlockState(pos, state.with(PROPERTY_HARVESTABLE, false));

			return false;
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!state.get(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
				if (rand.nextInt(60) == 0)
				{
					world.setBlockState(pos, state.with(PROPERTY_HARVESTABLE, true));
				}
			}
		}
	}

	@Override
	public boolean canGrow(final IBlockReader worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return !state.get(PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return !state.get(PROPERTY_HARVESTABLE);
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final BlockState state)
	{
		world.setBlockState(pos, state.with(PROPERTY_HARVESTABLE, true));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_HARVESTABLE);
	}
}
