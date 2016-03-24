package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockFloating extends Block
{
	public BlockFloating(Material material)
	{
		super(material);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			this.checkFallable(world, pos);
		}
	}

	private void checkFallable(World world, BlockPos pos)
	{
		boolean floatInstantly = BlockSand.fallInstantly;

		if (canFallInto(world, pos.up()) && pos.getY() >= 0)
		{
			byte b0 = 32;

			if (!floatInstantly && world.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
			{
				if (!world.isRemote)
				{
					List<ItemStack> drops = this.getDrops(world, pos, world.getBlockState(pos), 0);

					EntityFloatingBlock entity = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos), drops);
					world.spawnEntityInWorld(entity);
				}
			}
			else
			{
				world.setBlockToAir(pos);

				BlockPos bottomPos = pos.down();

				while (canFallInto(world, bottomPos) && bottomPos.getY() > 0)
				{
					bottomPos = bottomPos.down();
				}

				if (bottomPos.getY() > 0)
				{
					world.setBlockState(bottomPos.up(), this.getDefaultState());
				}
			}
		}
	}

	public static boolean canFallInto(World world, BlockPos pos)
	{
		if (world.isAirBlock(pos))
		{
			return true;
		}

		Block block = world.getBlockState(pos).getBlock();
		Material material = block.getMaterial();

		return block == Blocks.fire || material == Material.air || material == Material.water || material == Material.lava;
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 2;
	}
}
