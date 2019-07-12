package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;

public class ContainerLoadingScreen extends Container
{
	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
}
