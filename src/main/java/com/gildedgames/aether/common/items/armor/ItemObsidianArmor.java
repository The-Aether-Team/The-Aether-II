package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.MaterialsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

public class ItemObsidianArmor extends ItemAetherArmor
{
	public ItemObsidianArmor(EntityEquipmentSlot armorType)
	{
		super(MaterialsAether.OBSIDIAN_ARMOR, "obsidian", armorType);
	}
}
