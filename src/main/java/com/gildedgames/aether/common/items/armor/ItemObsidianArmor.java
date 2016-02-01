package com.gildedgames.aether.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Stack;

public class ItemObsidianArmor extends ItemAetherArmor
{
	public ItemObsidianArmor(ArmorMaterial material, int armorType)
	{
		super(material, "obsidian", armorType);
	}

	@Override
	protected void applyFullSetBonus(World world, EntityPlayer player)
	{
		player.setSprinting(false);
	}
}
