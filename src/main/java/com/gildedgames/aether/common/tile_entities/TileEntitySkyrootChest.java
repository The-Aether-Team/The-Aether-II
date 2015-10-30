package com.gildedgames.aether.common.tile_entities;

import net.minecraft.tileentity.TileEntityChest;

public class TileEntitySkyrootChest extends TileEntityChest
{
	@Override
	public String getCommandSenderName()
	{
		return this.hasCustomName() ? super.getCommandSenderName() : "container.skyroot_chest";
	}
}
