package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemAetherArmor extends ItemArmor
{
	private final String name;

	public ItemAetherArmor(final ArmorMaterial material, final String name, final EntityEquipmentSlot armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(CreativeTabsAether.ARMOR);
	}

	@Override
	public boolean getIsRepairable(final ItemStack target, final ItemStack stack)
	{
		return false;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	public float getExtraDamageReduction(final ItemStack stack)
	{
		return 0.0f;
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png");
	}
}
