package com.gildedgames.aether.common.items.accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.aether.common.player.PlayerAether;

public class ItemAccessory extends Item
{
	
	private final AccessoryType type;
	
	private final AccessoryEffect[] effects;

	public ItemAccessory(AccessoryType type, AccessoryEffect... effects)
	{
		this.type = type;

		this.setMaxStackSize(1);
		
		this.effects = effects;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		PlayerAether aePlayer = PlayerAether.get(player);

		int nextEmptySlot = aePlayer.getInventoryAccessories().getNextEmptySlotForType(this.getType());

		if (nextEmptySlot != -1)
		{
			aePlayer.getInventoryAccessories().setInventorySlotContents(nextEmptySlot, stack.copy());

			stack.stackSize = 0;
		}

		return stack;
	}

	public AccessoryType getType()
	{
		return this.type;
	}
	
	public AccessoryEffect[] getEffects()
	{
		return this.effects;
	}
	
}
