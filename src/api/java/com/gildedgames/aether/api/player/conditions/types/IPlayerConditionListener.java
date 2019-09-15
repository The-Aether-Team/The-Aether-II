package com.gildedgames.aether.api.player.conditions.types;

import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerConditionListener
{
	void onTriggered(IPlayerCondition condition, EntityPlayer player);
}
