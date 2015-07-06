package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockIcestone extends Block
{
	private static final int EFFECT_RADIUS = 3;

	public BlockIcestone()
	{
		super(Material.rock);

		this.setHardness(3f);

		this.setStepSound(Block.soundTypeGlass);

		this.setTickRandomly(true);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		for (int x = pos.getX() - EFFECT_RADIUS; x <= (pos.getX() + EFFECT_RADIUS); x++)
		{
			for (int y = pos.getY() - EFFECT_RADIUS; y <= (pos.getY() + EFFECT_RADIUS); y++)
			{
				for (int z = pos.getZ() - EFFECT_RADIUS; z <= (pos.getZ() + EFFECT_RADIUS); z++)
				{
					BlockPos newPos = new BlockPos(x, y, z);
					IBlockState newState = world.getBlockState(newPos);

					IBlockState frozenState = getFrozenBlock(newState);

					if (frozenState != null)
					{
						world.setBlockState(newPos, frozenState);
					}
				}
			}
		}
	}

	private IBlockState getFrozenBlock(IBlockState state)
	{
		Block block = state.getBlock();

		if (block == Blocks.water)
		{
			return Blocks.ice.getDefaultState();
		}
		else if (block == Blocks.lava)
		{
			return Blocks.obsidian.getDefaultState();
		}
		else if (block == Blocks.flowing_lava || block == Blocks.flowing_water)
		{
			return Blocks.air.getDefaultState();
		}

		return null;
	}

}
