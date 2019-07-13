package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ItemAetherArmor extends ArmorItem
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("a8896424-20ea-4d0a-b0d1-d961dbdcb309"),
			UUID.fromString("48712e68-2b7e-4c3e-95ae-dba24d7e9e84"), UUID.fromString("13552bc9-9a27-4b8f-879b-0d7e68417486"),
			UUID.fromString("fbaef69d-8d56-43e4-ac35-af3a25b28330") };

	private double slashLevel, pierceLevel, impactLevel;

	public ItemAetherArmor(final IArmorMaterial material, final EquipmentSlotType slot, final Item.Properties builder)
	{
		super(material, slot, builder);
	}

	public ItemAetherArmor setSlashDefense(int level)
	{
		this.slashLevel = level;

		return this;
	}

	public ItemAetherArmor setPierceDefense(int level)
	{
		this.pierceLevel = level;

		return this;
	}

	public ItemAetherArmor setImpactDefense(int level)
	{
		this.impactLevel = level;

		return this;
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
	public String getArmorTexture(final ItemStack stack, final Entity entity, final EquipmentSlotType slot, final String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.material.getName() + "_layer_" + (slot == EquipmentSlotType.LEGS ? 2 :
				1 + (AetherCore.CONFIG.hasCutoutHelmets() ? "b" : "")) + ".png");
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);

		int index = equipmentSlot.getIndex();
		String suffix = " defense level modifier";

		if (this.slot == equipmentSlot)
		{
			multimap.put(DamageTypeAttributes.SLASH_DEFENSE_LEVEL.getName(), new AttributeModifier(ARMOR_MODIFIERS[index], "Slash" + suffix, this.slashLevel, AttributeModifier.Operation.ADDITION));
			multimap.put(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL.getName(), new AttributeModifier(ARMOR_MODIFIERS[index], "Pierce" + suffix, this.pierceLevel, AttributeModifier.Operation.ADDITION));
			multimap.put(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL.getName(), new AttributeModifier(ARMOR_MODIFIERS[index], "Impact" + suffix, this.impactLevel, AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}
}
