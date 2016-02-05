package com.gildedgames.aether.common.items.accessories;

import net.minecraft.item.ItemStack;

import com.gildedgames.aether.common.player.PlayerAether;

public interface AccessoryEffect
{

	void onAccessoryEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type);

	void onAccessoryUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
	/**
	 * Called every tick while the accessory is equipped.
	 * @param aePlayer The player responsible for this tick
	 * @param stack The stack equipped
	 */
	void onAccessoryUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
}
