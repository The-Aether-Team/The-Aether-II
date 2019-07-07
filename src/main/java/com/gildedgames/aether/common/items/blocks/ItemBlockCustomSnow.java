package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockCustomSnow extends ItemBlock
{
	public ItemBlockCustomSnow(Block block)
	{
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldStack = player.getHeldItem(hand);

		if (!heldStack.isEmpty() && player.canPlayerEdit(pos, facing, heldStack))
		{
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			BlockPos otherPos = pos;

			if ((facing != EnumFacing.UP || block != this.block) && !block.isReplaceable(world, pos))
			{
				otherPos = pos.offset(facing);

				state = world.getBlockState(otherPos);
				block = state.getBlock();
			}

			if (block == this.block)
			{
				int i = state.getValue(BlockSnow.LAYERS);

				if (i < 8)
				{
					IBlockState modifiedState = state.withProperty(BlockSnow.LAYERS, i + 1);

					AxisAlignedBB aabb = modifiedState.getCollisionBoundingBox(world, otherPos);

					if (aabb != Block.NULL_AABB && world.checkNoEntityCollision(aabb.offset(otherPos)) && world.setBlockState(otherPos, modifiedState, 10))
					{
						SoundType soundtype = this.block.getSoundType(modifiedState, world, pos, player);
						world.playSound(player, otherPos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
								soundtype.getPitch() * 0.8F);

						if (player instanceof EntityPlayerMP)
						{
							CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, heldStack);
						}

						heldStack.shrink(1);

						return EnumActionResult.SUCCESS;
					}
				}
			}

			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		IBlockState state = world.getBlockState(pos);

		return (state.getBlock() == BlocksAether.highlands_snow_layer && state.getValue(BlockSnow.LAYERS) <= 7) || super
				.canPlaceBlockOnSide(world, pos, side, player, stack);
	}
}
