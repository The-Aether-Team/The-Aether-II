package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.items.misc.ItemAetherFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStomperPop extends ItemAetherFood
{

	public ItemStomperPop()
	{
		super(20, false);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer player)
	{
		//Play scary sound
	}

}
