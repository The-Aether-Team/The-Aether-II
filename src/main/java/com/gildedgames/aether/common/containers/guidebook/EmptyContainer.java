package com.gildedgames.aether.common.containers.guidebook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EmptyContainer extends Container
{
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
