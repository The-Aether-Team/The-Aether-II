package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEnchantedWyndberry extends ItemAetherFood
{
	public ItemEnchantedWyndberry()
	{
		super(5, 0.6F, false);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if (!worldIn.isRemote)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 200, 1));
		}

		super.onFoodEaten(stack, worldIn, player);
	}

}