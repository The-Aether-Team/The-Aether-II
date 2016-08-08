package com.gildedgames.aether.common.items;

import com.gildedgames.aether.common.AetherCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemAetherPortalFrame extends Item
{
	
	public ItemAetherPortalFrame()
	{
		this.maxStackSize = 1;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (facing == EnumFacing.DOWN)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			pos = pos.offset(facing);

			if (!player.canPlayerEdit(pos, facing, stack))
			{
				return EnumActionResult.FAIL;
			}
			else if (world.isRemote)
			{
				return EnumActionResult.PASS;
			}
			else
			{
				Rotation rotation;

				if (player.getHorizontalFacing() == EnumFacing.NORTH)
				{
					rotation = Rotation.CLOCKWISE_90;
				}
				else if (player.getHorizontalFacing() == EnumFacing.EAST)
				{
					rotation = Rotation.CLOCKWISE_180;
				}
				else if (player.getHorizontalFacing() == EnumFacing.SOUTH)
				{
					rotation = Rotation.COUNTERCLOCKWISE_90;
				}
				else
				{
					rotation = Rotation.NONE;
				}
				
				boolean old = AetherCore.TELEPORTER.createPortal;

				AetherCore.TELEPORTER.createPortal = true;

				AetherCore.TELEPORTER.createPortalFrame(world, pos.getX(), pos.getY(), pos.getZ(), rotation);

				AetherCore.TELEPORTER.createPortal = old;
				
				--stack.stackSize;
				
				return EnumActionResult.SUCCESS;
			}
		}
	}
}
