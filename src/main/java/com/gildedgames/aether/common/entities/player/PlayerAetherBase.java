package com.gildedgames.aether.common.entities.player;

import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface PlayerAetherBase
{
	void onUpdate(LivingUpdateEvent event);

	void onDeath(LivingDeathEvent event);

	void onDrops(LivingDropsEvent event);

	void onHurt(LivingHurtEvent event);

	void onFall(LivingFallEvent event);

	void onJump(LivingJumpEvent event);

	InventoryEquipment getEquipmentInventory();

	float getMiningSpeedMultiplier();

	EntityPlayer getPlayer();
}
