package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

// Blame Hugo
public class ItemEnchantedWyndberry extends ItemFood
{
	public ItemEnchantedWyndberry()
	{
		super(8, false);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if (!worldIn.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("resistance"), 200, 1));
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("dig_speed"), 200, 1));
		}

		super.onFoodEaten(stack, worldIn, player);
	}

}
