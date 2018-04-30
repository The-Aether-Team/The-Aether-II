package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerLoadingScreen extends Container
{
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
