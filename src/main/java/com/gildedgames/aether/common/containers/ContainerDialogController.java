package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDialogController extends Container
{

	private final EntityPlayer player;

	public ContainerDialogController(final EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return playerIn.getDistanceSq(this.player) <= 64.0D;
	}
}
