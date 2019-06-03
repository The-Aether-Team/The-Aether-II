package com.gildedgames.aether.api.player.conditions.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface ISeeEntityEventsListener
{
	void onSeeEntity(Entity entity, EntityPlayer player);
}
