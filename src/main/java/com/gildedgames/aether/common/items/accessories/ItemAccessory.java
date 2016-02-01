package com.gildedgames.aether.common.items.accessories;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAccessory extends Item
{
	private final AccessoryType type;

	public ItemAccessory(AccessoryType type)
	{
		this.type = type;

		this.setMaxStackSize(1);
	}

	public void onAccessoryEquipped(PlayerAether aePlayer, ItemStack stack) { }

	public void onAccessoryUnequipped(PlayerAether aePlayer, ItemStack stack) { }

	/**
	 * Called every tick while this accessory is equipped.
	 * @param aePlayer The player responsible for this tick
	 * @param stack The stack equipped
	 */
	public void onAccessoryUpdate(PlayerAether aePlayer, ItemStack stack) { }

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
}
