package com.gildedgames.aether.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPhoenixArmor extends ItemAetherArmor
{
	public ItemPhoenixArmor(int armorType)
	{
		super(ArmorMaterial.DIAMOND, "phoenix", armorType);
	}

	@Override
	protected void applyFullSetBonus(World world, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 2, 0, false, false));

		player.extinguish();
	}
}
