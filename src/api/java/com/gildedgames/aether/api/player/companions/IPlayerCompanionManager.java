package com.gildedgames.aether.api.player.companions;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IPlayerCompanionManager
{
	Entity getCompanionEntity();

	ItemStack getEquippedCompanionItem();
}
