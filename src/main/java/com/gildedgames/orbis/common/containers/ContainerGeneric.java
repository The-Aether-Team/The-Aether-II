package com.gildedgames.orbis.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerGeneric extends Container
{

	private final EntityPlayer player;

	public ContainerGeneric(final EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player)
	{
		return true;
	}

}
