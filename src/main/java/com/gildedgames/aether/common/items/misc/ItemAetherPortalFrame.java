package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.AetherCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemAetherPortalFrame extends Item
{
	
	public ItemAetherPortalFrame()
	{
		this.maxStackSize = 64;
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
				//int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
				
				boolean old = AetherCore.getTeleporter().createPortal;
				
				AetherCore.getTeleporter().createPortal = true;
				
				AetherCore.getTeleporter().createPortalFrame(world, pos.getX(), pos.getY(), pos.getZ());

				AetherCore.getTeleporter().createPortal = old;
				
				--stack.stackSize;
				
				return EnumActionResult.SUCCESS;
			}
		}
	}
}