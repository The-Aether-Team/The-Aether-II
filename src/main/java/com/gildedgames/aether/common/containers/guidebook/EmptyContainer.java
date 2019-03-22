package com.gildedgames.aether.common.containers.guidebook;

import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.inventory.ContainerPlayer;

public class EmptyContainer extends ContainerPlayer
{
	public EmptyContainer(IPlayerAether aePlayer)
	{
		super(aePlayer.getEntity().inventory, false, aePlayer.getEntity());
	}
}
