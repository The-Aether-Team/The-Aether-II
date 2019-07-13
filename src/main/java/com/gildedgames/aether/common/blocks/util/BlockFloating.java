package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFloating extends Block
{
	public BlockFloating(Properties properties)
	{
		super(properties);
	}

	public static boolean canFallInto(World world, BlockPos pos)
	{
		if (world.isAirBlock(pos))
		{
			return true;
		}

		BlockState state = world.getBlockState(pos);

		Material material = state.getMaterial();

		return state.getBlock() == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
	}

	@Override
	public void onBlockAdded(BlockState state1, World world, BlockPos pos, BlockState state2, boolean isMoving)
	{
		world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (!world.isRemote())
		{
			this.checkFallable(world, pos);
		}
	}

	private void checkFallable(World world, BlockPos pos)
	{
		if (canFallInto(world, pos.up()) && pos.getY() >= 0)
		{
			if (!world.isRemote())
			{
				EntityFloatingBlock entity = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos));
				world.addEntity(entity);
			}
		}
	}

	@Override
	public int tickRate(IWorldReader worldIn)
	{
		return 2;
	}
}
