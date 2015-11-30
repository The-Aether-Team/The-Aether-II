package com.gildedgames.aether.common.tile_entities;

import net.minecraft.tileentity.TileEntityChest;

public class TileEntitySkyrootChest extends TileEntityChest
{
	@Override
	public String getName()
	{
		return this.hasCustomName() ? super.getName() : "container.skyroot_chest";
	}
}
