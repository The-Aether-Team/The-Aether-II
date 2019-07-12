package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEnchantedWyndberry extends ItemAetherFood
{
	public ItemEnchantedWyndberry()
	{
		super(8, false);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player)
	{
		if (!worldIn.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Effects.RESISTANCE, 200, 1));
			player.addPotionEffect(new PotionEffect(Effects.HASTE, 200, 1));
		}

		super.onFoodEaten(stack, worldIn, player);
	}

}
