package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.*;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
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
	public ActionResultType onItemUse(final PlayerEntity player, final World world, BlockPos pos, final Hand hand, final Direction facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (world.isRemote)
		{
			return ActionResultType.SUCCESS;
		}
		else if (facing != Direction.UP)
		{
			return ActionResultType.FAIL;
		}
		else
		{
			final BlockState state = world.getBlockState(pos);

			final Block block = state.getBlock();

			final boolean canReplace = block.isReplaceable(world, pos);

			if (!canReplace)
			{
				pos = pos.up();
			}

			final int look = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			final Direction enumfacing = Direction.byHorizontalIndex(look);

			final BlockPos adjPos = pos.offset(enumfacing);

			final ItemStack heldStack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, heldStack) && player.canPlayerEdit(adjPos, facing, heldStack))
			{
				final BlockState adjBlock = world.getBlockState(adjPos);

				final boolean flag1 = adjBlock.getBlock().isReplaceable(world, adjPos);
				final boolean flag2 = canReplace || world.isAirBlock(pos);
				final boolean flag3 = flag1 || world.isAirBlock(adjPos);

				if (flag2 && flag3 && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), Direction.UP) &&
						world.getBlockState(adjPos.down()).isSideSolid(world, adjPos.down(), Direction.UP))
				{
					BlockState otherBed = BlocksAether.skyroot_bed.getDefaultState().withProperty(BedBlock.OCCUPIED, Boolean.FALSE)
							.withProperty(BedBlock.FACING, enumfacing).withProperty(BedBlock.PART, BedBlock.EnumPartType.FOOT);
					world.setBlockState(pos, otherBed, 10);
					world.setBlockState(adjPos, otherBed.withProperty(BedBlock.PART, BedBlock.EnumPartType.HEAD), 10);

					world.notifyNeighborsRespectDebug(pos, block, false);
					world.notifyNeighborsRespectDebug(adjPos, adjBlock.getBlock(), false);

					final SoundType sound = otherBed.getBlock().getSoundType(otherBed, world, pos, player);
					world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

					heldStack.shrink(1);

					return ActionResultType.SUCCESS;
				}
				else
				{
					return ActionResultType.FAIL;
				}
			}
			else
			{
				return ActionResultType.FAIL;
			}
		}
	}
}
