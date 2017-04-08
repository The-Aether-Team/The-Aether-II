package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSkyrootBed extends Item
{
	public ItemSkyrootBed()
	{

	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return EnumActionResult.SUCCESS;
		}
		else if (facing != EnumFacing.UP)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState state = world.getBlockState(pos);

			Block block = state.getBlock();

			boolean canReplace = block.isReplaceable(world, pos);

			if (!canReplace)
			{
				pos = pos.up();
			}

			int look = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			EnumFacing enumfacing = EnumFacing.getHorizontal(look);

			BlockPos adjPos = pos.offset(enumfacing);

			ItemStack heldStack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, heldStack) && player.canPlayerEdit(adjPos, facing, heldStack))
			{
				IBlockState adjBlock = world.getBlockState(adjPos);

				boolean flag1 = adjBlock.getBlock().isReplaceable(world, adjPos);
				boolean flag2 = canReplace || world.isAirBlock(pos);
				boolean flag3 = flag1 || world.isAirBlock(adjPos);

				if (flag2 && flag3 && world.getBlockState(pos.down()).isFullyOpaque() && world.getBlockState(adjPos.down()).isFullyOpaque())
				{
					IBlockState otherBed = BlocksAether.skyroot_bed.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.FALSE)
							.withProperty(BlockBed.FACING, enumfacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);

					world.setBlockState(pos, otherBed, 10);
					world.setBlockState(adjPos, otherBed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);

					world.notifyNeighborsRespectDebug(pos, block, false);
					world.notifyNeighborsRespectDebug(adjPos, adjBlock.getBlock(), false);

					SoundType sound = otherBed.getBlock().getSoundType(otherBed, world, pos, player);
					world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

					heldStack.shrink(1);

					return EnumActionResult.SUCCESS;
				}
				else
				{
					return EnumActionResult.FAIL;
				}
			}
			else
			{
				return EnumActionResult.FAIL;
			}
		}
	}
}
