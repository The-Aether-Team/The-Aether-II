package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStomperPop extends ItemAetherFood
{

	public ItemStomperPop()
	{
		super(20, 0.5F, false);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity player)
	{
		//Play scary sound
	}

}
