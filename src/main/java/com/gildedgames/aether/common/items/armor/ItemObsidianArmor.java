package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherMaterials;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

public class ItemObsidianArmor extends ItemAetherArmor
{
	public ItemObsidianArmor(EntityEquipmentSlot armorType)
	{
		super(AetherMaterials.OBSIDIAN_ARMOR, "obsidian", armorType);
	}

	@Override
	protected void applyFullSetBonus(World world, EntityPlayer player)
	{
		player.setSprinting(false);
	}
}
