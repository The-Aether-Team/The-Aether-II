package com.aetherteam.aetherii.item.combat.abilities;

import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.UUID;

public interface ZaniteWeapon extends ZaniteTool, UniqueDamage {
    UUID DAMAGE_MODIFIER_UUID = UUID.fromString("7513C45D-8163-47A6-9829-C9C9FF6E1809");

    default Multimap<Attribute, AttributeModifier> increaseDamage(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_UUID, "Damage modifier", this.calculateIncrease(map, stack), AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

     default int calculateIncrease(Multimap<Attribute, AttributeModifier> map, ItemStack stack) {
        double baseDamage = 0.0;
        for (Iterator<AttributeModifier> it = map.get(Attributes.ATTACK_DAMAGE).stream().iterator(); it.hasNext();) {
            AttributeModifier modifier = it.next();
            baseDamage += modifier.getAmount();
        }
        return this.calculateIncrease(stack, baseDamage);
    }

    default int calculateIncrease(ItemStack stack, double baseDamage) {
        double boostedDamage = this.calculateZaniteBuff(stack, baseDamage);
        boostedDamage -= baseDamage;
        if (boostedDamage < 0.0) {
            boostedDamage = 0.0;
        }
        return (int) Math.round(boostedDamage);
    }
}
