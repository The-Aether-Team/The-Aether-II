package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockWallSkyrootSign;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootSign;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemAetherPortalFrame extends Item
{
	
	public ItemAetherPortalFrame()
	{
		this.maxStackSize = 64;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (side == EnumFacing.DOWN)
		{
			return false;
		}
		else
		{
			pos = pos.offset(side);

			if (!player.canPlayerEdit(pos, side, stack))
			{
				return false;
			}
			else if (world.isRemote)
			{
				return true;
			}
			else
			{
				//int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
				
				boolean old = AetherCore.getTeleporter().createPortal;
				
				AetherCore.getTeleporter().createPortal = true;
				
				AetherCore.getTeleporter().createPortalFrame(world, pos.getX(), pos.getY(), pos.getZ());

				AetherCore.getTeleporter().createPortal = old;
				
				--stack.stackSize;
				
				return true;
			}
		}
	}
}