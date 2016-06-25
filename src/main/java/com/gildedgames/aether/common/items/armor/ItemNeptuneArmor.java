package com.gildedgames.aether.common.items.armor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "neptune", armorType);
	}

	@Override
	protected void applyFullSetBonus(World world, EntityPlayer player)
	{
		if (player.isInsideOfMaterial(Material.WATER))
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("water_breathing"), 2, 0, false, false));
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}
}
