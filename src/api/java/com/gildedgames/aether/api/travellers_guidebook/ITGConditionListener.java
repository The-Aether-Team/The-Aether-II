package com.gildedgames.aether.api.travellers_guidebook;

import net.minecraft.entity.player.EntityPlayer;

public interface ITGConditionListener
{
	void onTriggered(ITGCondition condition, EntityPlayer player);
}
