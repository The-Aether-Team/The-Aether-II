package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;

public class ContainerDialogController extends Container
{

	private final PlayerEntity player;

	public ContainerDialogController(final PlayerEntity player)
	{
		this.player = player;
	}

	@Override
	public boolean canInteractWith(final PlayerEntity playerIn)
	{
		return playerIn.getDistanceSq(this.player) <= 64.0D;
	}
}
