package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CreativeTabsAether;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherArmor extends ItemArmor
{
	private final String name;

	public ItemAetherArmor(ArmorMaterial material, String name, EntityEquipmentSlot armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(CreativeTabsAether.tabArmor);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	public float getExtraDamageReduction(ItemStack stack)
	{
		return 0.0f;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.BLUE + I18n.format("item.aether.armor." + this.name + ".ability.desc"));

		if (!this.isAbilityPassive())
		{
			tooltip.add(TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use") + ": " +
					TextFormatting.WHITE + I18n.format("item.aether.armor." + this.name + ".use.desc"));
		}
	}
}
