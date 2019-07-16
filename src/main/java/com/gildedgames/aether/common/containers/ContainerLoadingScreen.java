package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class ContainerLoadingScreen extends Container
{
	protected ContainerLoadingScreen(@Nullable ContainerType<?> type, int id)
	{
		super(type, id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
}
