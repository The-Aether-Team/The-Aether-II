package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIcestoneBricks extends Block
{
	private static final int EFFECT_RADIUS = 3;

	public BlockIcestoneBricks(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		for (int x = pos.getX() - EFFECT_RADIUS; x <= (pos.getX() + EFFECT_RADIUS); x++)
		{
			for (int y = pos.getY() - EFFECT_RADIUS; y <= (pos.getY() + EFFECT_RADIUS); y++)
			{
				for (int z = pos.getZ() - EFFECT_RADIUS; z <= (pos.getZ() + EFFECT_RADIUS); z++)
				{
					BlockPos newPos = new BlockPos(x, y, z);

					BlockState frozenState = this.getFrozenBlock(world.getFluidState(newPos));

					if (frozenState != null)
					{
						world.setBlockState(newPos, frozenState);
					}
				}
			}
		}
	}

	private BlockState getFrozenBlock(IFluidState state)
	{
		Fluid fluid = state.getFluid();

		if (fluid == Fluids.WATER)
		{
			return state.getLevel() == 0 ? Blocks.ICE.getDefaultState() : null;
		}
		else if (fluid == Fluids.LAVA)
		{
			return Blocks.OBSIDIAN.getDefaultState();
		}
		else if (fluid == Fluids.FLOWING_LAVA || fluid == Fluids.FLOWING_WATER)
		{
			return Blocks.AIR.getDefaultState();
		}

		return null;
	}

}
