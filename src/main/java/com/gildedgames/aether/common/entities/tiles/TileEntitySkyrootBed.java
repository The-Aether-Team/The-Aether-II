package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBed;

public class TileEntitySkyrootBed extends TileEntityBed
{

	@Override
	public ItemStack getItemStack()
	{
		return new ItemStack(ItemsAether.skyroot_bed, 1, 0);
	}

}
