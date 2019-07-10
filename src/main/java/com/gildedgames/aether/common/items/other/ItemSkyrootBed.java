package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
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

public class ItemSkyrootBed extends Item implements IDropOnDeath
{
	public ItemSkyrootBed()
	{

	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, BlockPos pos, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
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
			final IBlockState state = world.getBlockState(pos);

			final Block block = state.getBlock();

			final boolean canReplace = block.isReplaceable(world, pos);

			if (!canReplace)
			{
				pos = pos.up();
			}

			final int look = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			final EnumFacing enumfacing = EnumFacing.byHorizontalIndex(look);

			final BlockPos adjPos = pos.offset(enumfacing);

			final ItemStack heldStack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, heldStack) && player.canPlayerEdit(adjPos, facing, heldStack))
			{
				final IBlockState adjBlock = world.getBlockState(adjPos);

				final boolean flag1 = adjBlock.getBlock().isReplaceable(world, adjPos);
				final boolean flag2 = canReplace || world.isAirBlock(pos);
				final boolean flag3 = flag1 || world.isAirBlock(adjPos);

				if (flag2 && flag3 && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) &&
						world.getBlockState(adjPos.down()).isSideSolid(world, adjPos.down(), EnumFacing.UP))
				{
					IBlockState otherBed = BlocksAether.skyroot_bed.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.FALSE)
							.withProperty(BlockBed.FACING, enumfacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
					world.setBlockState(pos, otherBed, 10);
					world.setBlockState(adjPos, otherBed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);

					world.notifyNeighborsRespectDebug(pos, block, false);
					world.notifyNeighborsRespectDebug(adjPos, adjBlock.getBlock(), false);

					final SoundType sound = otherBed.getBlock().getSoundType(otherBed, world, pos, player);
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
