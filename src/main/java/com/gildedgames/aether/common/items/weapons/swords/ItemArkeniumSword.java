package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemArkeniumSword extends ItemAetherSword
{
	public ItemArkeniumSword()
	{
		super(MaterialsAether.ARKENIUM_TOOL, ItemAbilityType.PASSIVE);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();

		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 7D, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3D, 0));
		}

		return multimap;
	}
}
