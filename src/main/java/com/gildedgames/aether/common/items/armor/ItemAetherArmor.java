package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemAetherArmor extends ItemArmor
{
	private Map<StatusEffect, Double> statusEffects = new HashMap<>();

	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("a8896424-20ea-4d0a-b0d1-d961dbdcb309"),
			UUID.fromString("48712e68-2b7e-4c3e-95ae-dba24d7e9e84"), UUID.fromString("13552bc9-9a27-4b8f-879b-0d7e68417486"),
			UUID.fromString("fbaef69d-8d56-43e4-ac35-af3a25b28330") };

	private final String name;

	public ItemAetherArmor(final ArmorMaterial material, final String name, final EntityEquipmentSlot armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(CreativeTabsAether.TAB_ARMOR);

		this.damageReduceAmount = material.getDamageReductionAmount(armorType);
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
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 :
				1 + (AetherCore.CONFIG.cutoutHelmets ? "b" : "")) + ".png");
	}

	public <T extends ItemAetherArmor> T addStatusEffectResistance(StatusEffect effect, double resistance)
	{
		this.statusEffects.put(effect, resistance);

		return (T) this;
	}

	public Map<StatusEffect, Double> getStatusEffects()
	{
		return this.statusEffects;
	}
}
