package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAetherArmor extends ItemArmor
{
	private EnumAetherArmorVariant armorVariant;

	public ItemAetherArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material.getArmorMaterial(), renderIndex, armorType);

		this.armorVariant = material;

		this.setCreativeTab(AetherCreativeTabs.tabArmor);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return this.armorVariant.getResourceForSlot(slot);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return this.armorVariant.getRepairItem() == stack.getItem();
	}
}
