package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDialogController extends Container
{

	private EntityPlayer player;

	public ContainerDialogController(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

}
