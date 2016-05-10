package com.gildedgames.aether.player;

import com.gildedgames.aether.player.inventory.IInventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IPlayerAetherCapability
{
	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(LivingDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onJump(LivingJumpEvent event);

	IInventoryEquipment getEquipmentInventory();

	/**
	 * @return The {@link EntityPlayer} this capability is attached to.
	 */
	EntityPlayer getPlayer();

	/**
	 * @return This player's mining speed modifier.
	 */
	float getMiningSpeedMultiplier();
}
