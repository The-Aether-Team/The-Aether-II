package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.blocks.util.BlockCustomSlab;
import com.gildedgames.aether.common.blocks.util.BlockCustomSlab.EnumSlabPart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockCustomSlab extends ItemBlock
{
	public ItemBlockCustomSlab(Block block)
	{
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		BlockPos target = pos;

		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == this.block && state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE) != EnumSlabPart.FULL_BLOCK)
		{
			EnumSlabPart part = state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE);

			if ((part == EnumSlabPart.BOTTOM_HALF && facing == EnumFacing.UP) ||
					(part == EnumSlabPart.TOP_HALF && facing == EnumFacing.DOWN))
			{
				IBlockState newState = state.withProperty(BlockCustomSlab.PROPERTY_SLAB_STATE, EnumSlabPart.FULL_BLOCK);

				if (player.canPlayerEdit(target, facing, stack) && world.setBlockState(target, newState, 11))
				{
					stack.shrink(1);

					return EnumActionResult.SUCCESS;
				}
			}
		}
		else
		{
			target = pos.offset(facing);
			state = world.getBlockState(target);

			if (state.getBlock() == this.block)
			{
				EnumSlabPart part = state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE);

				if (part != EnumSlabPart.FULL_BLOCK)
				{
					IBlockState newState = state.withProperty(BlockCustomSlab.PROPERTY_SLAB_STATE, EnumSlabPart.FULL_BLOCK);

					if (player.canPlayerEdit(target, facing, stack) && world.setBlockState(target, newState, 11))
					{
						stack.shrink(1);

						return EnumActionResult.SUCCESS;
					}
				}
			}
		}

		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		BlockPos blockpos = pos;

		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == this.block && state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE) != EnumSlabPart.FULL_BLOCK)
		{
			boolean flag = state.getValue(BlockCustomSlab.PROPERTY_SLAB_STATE) == EnumSlabPart.TOP_HALF;

			if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag) && state.getBlock() == this.block)
			{
				return true;
			}
		}

		pos = pos.offset(side);

		IBlockState offsetState = world.getBlockState(pos);

		return offsetState.getBlock() == this.block || super.canPlaceBlockOnSide(world, blockpos, side, player, stack);
	}

}
