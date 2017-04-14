package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOrangeTree extends BlockAetherPlant implements IGrowable
{
	private static final int STAGE_COUNT = 5;

	public static final PropertyBool PROPERTY_IS_TOP_BLOCK = PropertyBool.create("is_top_block");

	public static final PropertyInteger PROPERTY_STAGE = PropertyInteger.create("stage", 1, STAGE_COUNT);

	public BlockOrangeTree()
	{
		super(Material.PLANTS);

		this.setHardness(1f);

		this.setTickRandomly(true);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_IS_TOP_BLOCK, false).withProperty(PROPERTY_STAGE, 1));
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		if (blockState.getValue(PROPERTY_IS_TOP_BLOCK) && blockState.getValue(PROPERTY_STAGE) >= STAGE_COUNT)
		{
			return 0.0f;
		}

		return super.getBlockHardness(blockState, worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		IBlockState soilBlock = world.getBlockState(pos.down());

		if (soilBlock.getBlock() == this && soilBlock.getValue(PROPERTY_IS_TOP_BLOCK))
		{
			return false;
		}

		return this.isSuitableSoilBlock(soilBlock);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		BlockPos topBlock = state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos : pos.up();
		BlockPos bottomBlock = state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos;

		IBlockState soilState = world.getBlockState(bottomBlock.down());

		int chance = 10;

		if (soilState.getBlock() == BlocksAether.aether_grass
				&& soilState.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED)
		{
			chance /= 2;
		}

		if (random.nextInt(chance) == 0)
		{
			this.growTree(world, topBlock, bottomBlock, 1);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.025F);
	}

	@Override
	protected void invalidateBlock(World world, BlockPos pos, IBlockState state)
	{
		BlockPos adjPos = state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos.up();

		if (world.getBlockState(adjPos).getBlock() == this)
		{
			this.destroyTree(world, pos, adjPos, true);
		}
		else
		{
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		boolean isRoot = !state.getValue(PROPERTY_IS_TOP_BLOCK);

		BlockPos adjPos = isRoot ? pos.up() : pos.down();

		if (state.getValue(PROPERTY_STAGE) == STAGE_COUNT)
		{
			if (player.capabilities.isCreativeMode)
			{
				world.setBlockToAir(pos);
				world.setBlockToAir(adjPos);
				return false;
			}
			else
			{
				this.dropOranges(world, pos);
			}

			IBlockState adjState = world.getBlockState(adjPos);

			if (adjState.getBlock() == this)
			{
				world.setBlockState(adjPos, adjState.withProperty(PROPERTY_STAGE, 4));
			}

			world.setBlockState(pos, world.getBlockState(pos).withProperty(PROPERTY_STAGE, 4));

			return false;
		}

		this.destroyTree(world, pos, adjPos, !player.capabilities.isCreativeMode);

		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		int isTop = (meta / STAGE_COUNT);
		int stage = ((meta - (STAGE_COUNT * isTop)) % STAGE_COUNT) + 1;

		return this.getDefaultState().withProperty(PROPERTY_IS_TOP_BLOCK, isTop > 0).withProperty(PROPERTY_STAGE, stage);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int top = state.getValue(PROPERTY_IS_TOP_BLOCK) ? STAGE_COUNT : 0;
		int stage = state.getValue(PROPERTY_STAGE);

		return top + stage;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_IS_TOP_BLOCK, PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		int stage = state.getValue(PROPERTY_STAGE);

		return stage < 5;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		int stage = state.getValue(PROPERTY_STAGE);

		return stage < 5;
	}

	@Override
	public boolean isSuitableSoilBlock(IBlockState state)
	{
		return (state.getBlock() == this && !state.getValue(PROPERTY_IS_TOP_BLOCK) && state.getValue(PROPERTY_STAGE) >= 3) || super.isSuitableSoilBlock(state);
	}

	private void dropOranges(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);

		int count = world.rand.nextInt(3) + 1;

		if (state.getBlock() == BlocksAether.aether_grass
				&& state.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED)
		{
			count += 1;
		}

		Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.orange, count));
	}

	private void destroyTree(World world, BlockPos pos, BlockPos adjPos, boolean doDrops)
	{
		if (doDrops)
		{
			Block.spawnAsEntity(world, pos, new ItemStack(BlocksAether.orange_tree));

			if (world.getBlockState(pos).getValue(PROPERTY_STAGE) >= STAGE_COUNT)
			{
				this.dropOranges(world, pos);
			}
		}

		world.setBlockToAir(pos);

		if (world.getBlockState(adjPos).getBlock() == this)
		{
			world.setBlockToAir(adjPos);
		}
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		BlockPos topBlock = state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos : pos.up();
		BlockPos bottomBlock = state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos;

		this.growTree(world, topBlock, bottomBlock, rand.nextInt(3));
	}

	private void growTree(World world, BlockPos topPos, BlockPos bottomPos, int amount)
	{
		IBlockState topState = world.getBlockState(bottomPos);
		IBlockState bottomState = world.getBlockState(bottomPos);

		if (topState.getBlock() == this && bottomState.getBlock() == this)
		{
			int nextStage = topState.getValue(PROPERTY_STAGE) + amount;

			if (nextStage <= STAGE_COUNT)
			{
				if (nextStage >= 3)
				{
					if (!world.isAirBlock(topPos) && world.getBlockState(topPos).getBlock() != this)
					{
						return;
					}

					world.setBlockState(topPos, topState.withProperty(PROPERTY_STAGE, nextStage).withProperty(PROPERTY_IS_TOP_BLOCK, true));
				}

				world.setBlockState(bottomPos, topState.withProperty(PROPERTY_STAGE, nextStage).withProperty(PROPERTY_IS_TOP_BLOCK, false));
			}
		}
	}

}
