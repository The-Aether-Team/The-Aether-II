package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.entity.EntityCharacter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class ContainerDialogController extends Container
{
	private final EntityCharacter character;

	protected ContainerDialogController(@Nullable ContainerType<?> type, int id, EntityCharacter character)
	{
		super(type, id);

		this.character = character;
	}

	@Override
	public boolean canInteractWith(final PlayerEntity playerIn)
	{
		return playerIn.getDistanceSq(this.character) <= 25.0D;
	}
}
