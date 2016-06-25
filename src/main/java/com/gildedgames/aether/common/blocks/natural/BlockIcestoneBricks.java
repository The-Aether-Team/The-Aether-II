package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIcestoneBricks extends Block
{
	private static final int EFFECT_RADIUS = 3;

	public BlockIcestoneBricks()
	{
		super(Material.ROCK);

		this.setHardness(3f);

		this.setSoundType(SoundType.GLASS);

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

		if (block == Blocks.WATER)
		{
			return state.getValue(BlockLiquid.LEVEL) == 0 ? Blocks.ICE.getDefaultState() : null;
		}
		else if (block == Blocks.LAVA)
		{
			return Blocks.OBSIDIAN.getDefaultState();
		}
		else if (block == Blocks.FLOWING_LAVA || block == Blocks.FLOWING_WATER)
		{
			return Blocks.AIR.getDefaultState();
		}

		return null;
	}

}
