package com.gildedgames.aether.common.items.accessories;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
	
	/**
	 * Called when the player kills a living entity while they are wearing this accessory.
	 * @param event The event fired by Forge
	 * @param killedEntity The entity killed by the player
	 * @param aePlayer The player who killed the entity
	 * @param stack The accessory stack being notified
	 * @param type The notified accessory's type
	 */
	void onKillEntity(LivingDropsEvent event, EntityLivingBase killedEntity, PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
	void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type);
	
}
