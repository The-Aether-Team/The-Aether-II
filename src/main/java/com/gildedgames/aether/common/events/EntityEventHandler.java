package com.gildedgames.aether.common.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {}

	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.getEntity();
//		System.out.println(entity.getName());
		//Effect: id, currentValue, exposed, resistance
	}

	@SubscribeEvent
	public void onEntityLivingDropItems(LivingDropsEvent event) {}

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {}

	@SubscribeEvent
	public void itemToss(ItemTossEvent event) {}

	@SubscribeEvent
	public void onEntityDead(LivingDeathEvent event) {}

}
