package com.gildedgames.aether.common.items.miscellaneous;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShardOfLife extends ItemFood
{

	public ItemShardOfLife()
	{
		super(20, false);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer player)
	{
		//Play scary sound
	}

}
