package com.gildedgames.aether.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
