package com.gildedgames.aether.common.containers.guidebook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class EmptyContainer extends Container
{
	public EmptyContainer()
	{
		super(null, -1);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
}
