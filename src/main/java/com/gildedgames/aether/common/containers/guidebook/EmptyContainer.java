package com.gildedgames.aether.common.containers.guidebook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;

public class EmptyContainer extends Container
{
	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
}
