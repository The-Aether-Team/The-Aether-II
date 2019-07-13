package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class BlockOrangeTree extends BlockAetherPlant implements IGrowable
{
	public static final BooleanProperty PROPERTY_IS_TOP_BLOCK = BooleanProperty.create("is_top_block");

	private static final int STAGE_COUNT = 5;

	public static final IntegerProperty PROPERTY_STAGE = IntegerProperty.create("stage", 1, STAGE_COUNT);

	public BlockOrangeTree(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_IS_TOP_BLOCK, false).with(PROPERTY_STAGE, 1));
	}

	@Override
	public float getBlockHardness(BlockState blockState, IBlockReader world, BlockPos pos)
	{
		if (blockState.get(PROPERTY_STAGE) >= STAGE_COUNT)
		{
			return 0.0f;
		}

		return super.getBlockHardness(blockState, world, pos);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		BlockState soilBlock = world.getBlockState(pos.down());

		if (soilBlock.getBlock() == this && soilBlock.get(PROPERTY_IS_TOP_BLOCK))
		{
			return false;
		}

		return this.isSuitableSoilBlock(world, pos, soilBlock);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		BlockPos topBlock = state.get(PROPERTY_IS_TOP_BLOCK) ? pos : pos.up();
		BlockPos bottomBlock = state.get(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos;

		BlockState soilState = world.getBlockState(bottomBlock.down());

		int chance = 10;

		if (soilState.getBlock() == BlocksAether.aether_grass
				&& soilState.get(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED)
		{
			chance /= 2;
		}

		if (rand.nextInt(chance) == 0)
		{
			this.growTree(world, topBlock, bottomBlock, 1);
		}
	}

	@Override
	public boolean validatePosition(World world, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_STAGE) >= 3)
		{
			BlockState adj = world.getBlockState(state.get(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos.up());

			if (adj.getBlock() != this)
			{
				return false;
			}
		}

		return super.validatePosition(world, pos, state);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		this.destroyTree(worldIn, pos, state);

		super.harvestBlock(worldIn, player, pos, state, te, stack);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{
		boolean isRoot = !state.get(PROPERTY_IS_TOP_BLOCK);

		BlockPos adjPos = isRoot ? pos.up() : pos.down();

		if (state.get(PROPERTY_STAGE) == STAGE_COUNT)
		{
			if (player.isCreative())
			{
				this.destroyTree(world, pos, state);

				return true;
			}
			else
			{
				for (ItemStack item : this.getFruitDrops(world, state))
				{
					Block.spawnAsEntity(world, pos, item);
				}
			}

			BlockState adjState = world.getBlockState(adjPos);

			if (adjState.getBlock() == this)
			{
				world.setBlockState(adjPos, adjState.with(PROPERTY_STAGE, 4));
			}

			world.setBlockState(pos, world.getBlockState(pos).with(PROPERTY_STAGE, 4));

			return false;
		}

		this.destroyTree(world, pos, state);

		return true;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_IS_TOP_BLOCK, PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		int stage = state.get(PROPERTY_STAGE);

		return stage < 5;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		int stage = state.get(PROPERTY_STAGE);

		return stage < 5;
	}

	@Override
	public boolean isSuitableSoilBlock(IWorldReader world, BlockPos pos, BlockState state)
	{
		return (state.getBlock() == this && !state.get(PROPERTY_IS_TOP_BLOCK) && state.get(PROPERTY_STAGE) >= 3) || super
				.isSuitableSoilBlock(world, pos, state);
	}

	private ArrayList<ItemStack> getFruitDrops(IBlockReader world, BlockState state)
	{
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		int count = rand.nextInt(3) + 1;

		if (state.getBlock() == BlocksAether.aether_grass && state.get(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED)
		{
			count += 1;
		}

		return Lists.newArrayList(new ItemStack(ItemsAether.orange, count));
	}

	private void destroyTree(World world, BlockPos pos, BlockState state)
	{
		world.removeBlock(pos, false);

		BlockPos adjPos = state.get(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos.up();

		if (world.getBlockState(adjPos).getBlock() == this)
		{
			world.removeBlock(adjPos, false);
		}
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, BlockState state)
	{
		BlockPos topBlock = state.get(PROPERTY_IS_TOP_BLOCK) ? pos : pos.up();
		BlockPos bottomBlock = state.get(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos;

		this.growTree(world, topBlock, bottomBlock, rand.nextInt(3));
	}

	private void growTree(World world, BlockPos topPos, BlockPos bottomPos, int amount)
	{
		BlockState topState = world.getBlockState(bottomPos);
		BlockState bottomState = world.getBlockState(bottomPos);

		if (topState.getBlock() == this && bottomState.getBlock() == this)
		{
			int nextStage = topState.get(PROPERTY_STAGE) + amount;

			if (nextStage <= STAGE_COUNT)
			{
				if (nextStage >= 3)
				{
					if (!world.isAirBlock(topPos) && world.getBlockState(topPos).getBlock() != this)
					{
						return;
					}

					world.setBlockState(topPos, topState.with(PROPERTY_STAGE, nextStage).with(PROPERTY_IS_TOP_BLOCK, true));
				}

				world.setBlockState(bottomPos, topState.with(PROPERTY_STAGE, nextStage).with(PROPERTY_IS_TOP_BLOCK, false));
			}
		}
	}

}
