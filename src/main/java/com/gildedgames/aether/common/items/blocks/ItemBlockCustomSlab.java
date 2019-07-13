package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.util.BlockCustomSlab;
import com.gildedgames.aether.common.blocks.util.BlockCustomSlab.EnumSlabPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBlockCustomSlab extends BlockItem
{
	public ItemBlockCustomSlab(Block block)
	{
		super(block);
	}

	@Override
	public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing,
			float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		BlockPos target = pos;

		BlockState state = world.getBlockState(pos);

		if (state.getBlock() == this.block && state.get(BlockCustomSlab.PROPERTY_SLAB_STATE) != EnumSlabPart.FULL_BLOCK)
		{
			EnumSlabPart part = state.get(BlockCustomSlab.PROPERTY_SLAB_STATE);

			if ((part == EnumSlabPart.BOTTOM_HALF && facing == Direction.UP) ||
					(part == EnumSlabPart.TOP_HALF && facing == Direction.DOWN))
			{
				BlockState newState = state.with(BlockCustomSlab.PROPERTY_SLAB_STATE, EnumSlabPart.FULL_BLOCK);

				if (player.canPlayerEdit(target, facing, stack) && world.setBlockState(target, newState, 11))
				{
					stack.shrink(1);

					return ActionResultType.SUCCESS;
				}
			}
		}
		else
		{
			target = pos.offset(facing);
			state = world.getBlockState(target);

			if (state.getBlock() == this.block)
			{
				EnumSlabPart part = state.get(BlockCustomSlab.PROPERTY_SLAB_STATE);

				if (part != EnumSlabPart.FULL_BLOCK)
				{
					BlockState newState = state.with(BlockCustomSlab.PROPERTY_SLAB_STATE, EnumSlabPart.FULL_BLOCK);

					if (player.canPlayerEdit(target, facing, stack) && world.setBlockState(target, newState, 11))
					{
						stack.shrink(1);

						return ActionResultType.SUCCESS;
					}
				}
			}
		}

		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, Direction side, PlayerEntity player, ItemStack stack)
	{
		BlockPos blockpos = pos;

		BlockState state = world.getBlockState(pos);

		if (state.getBlock() == this.block && state.get(BlockCustomSlab.PROPERTY_SLAB_STATE) != EnumSlabPart.FULL_BLOCK)
		{
			boolean flag = state.get(BlockCustomSlab.PROPERTY_SLAB_STATE) == EnumSlabPart.TOP_HALF;

			if ((side == Direction.UP && !flag || side == Direction.DOWN && flag) && state.getBlock() == this.block)
			{
				return true;
			}
		}

		pos = pos.offset(side);

		BlockState offsetState = world.getBlockState(pos);

		return offsetState.getBlock() == this.block || super.canPlaceBlockOnSide(world, blockpos, side, player, stack);
	}

}
