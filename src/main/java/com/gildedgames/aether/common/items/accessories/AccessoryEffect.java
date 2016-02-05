package com.gildedgames.aether.common.items.accessories;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gildedgames.aether.common.player.PlayerAether;

public interface AccessoryEffect
{

	void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type);

	void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
	/**
	 * Called every tick while the accessory is equipped.
	 * @param aePlayer The player responsible for this tick
	 * @param stack The stack equipped
	 */
	void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
	void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
}
