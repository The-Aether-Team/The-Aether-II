package com.gildedgames.aether.common.items.accessories;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.aether.common.player.PlayerAether;

public class ItemAccessory extends Item
{
	
	private final AccessoryType type;
	
	private final List<AccessoryEffect> effects = new ArrayList<AccessoryEffect>();

	public ItemAccessory(AccessoryType type)
	{
		this.type = type;

		this.setMaxStackSize(1);
	}
	
	public ItemAccessory add(AccessoryEffect effect)
	{
		this.effects.add(effect);
		
		return this;
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
	
	public List<AccessoryEffect> getEffects()
	{
		return this.effects;
	}
	
}
