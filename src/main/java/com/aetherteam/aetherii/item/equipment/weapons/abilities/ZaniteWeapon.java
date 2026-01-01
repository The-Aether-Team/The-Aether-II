package com.aetherteam.aetherii.item.equipment.weapons.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteWeapon extends ZaniteBuff {
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_attack_damage");
    ResourceLocation SLASH_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_slash_attack_damage");
    ResourceLocation IMPACT_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_impact_attack_damage");
    ResourceLocation PIERCE_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_pierce_attack_damage");

    Map<Holder<Attribute>, ResourceLocation> DAMAGE_TYPES = Map.ofEntries(
            Map.entry(AetherIIAttributes.SLASH_DAMAGE, SLASH_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.IMPACT_DAMAGE, IMPACT_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.PIERCE_DAMAGE, PIERCE_DAMAGE_MODIFIER_ID)
    );

    static void updateWeaponAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        ItemAttributeModifiers defaultModifiers = event.getDefaultModifiers();
        List<ItemAttributeModifiers.Entry> modifiers = event.getModifiers();

        if (stack.getItem() instanceof ZaniteWeapon zaniteWeapon) {
            Set<ItemAttributeModifiers.Entry> updatedEntries = new HashSet<>();
            Set<ItemAttributeModifiers.Entry> newEntries = new HashSet<>(zaniteWeapon.increaseDamage(AetherIIAttributes.SLASH_DAMAGE, defaultModifiers, stack));
            for (ItemAttributeModifiers.Entry newEntry : newEntries) {
                boolean flag = true;
                for (ItemAttributeModifiers.Entry oldEntry : modifiers) {
                    double newAmount = newEntry.modifier().amount();
                    double oldAmount = oldEntry.modifier().amount();
                    if (oldEntry.matches(newEntry.attribute(), newEntry.modifier().id())) {
                        if (oldAmount != newAmount) {
                            updatedEntries.add(newEntry);
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    updatedEntries.add(newEntry);
                }
            }
            for (ItemAttributeModifiers.Entry updatedEntry : updatedEntries) {
                event.replaceModifier(updatedEntry.attribute(), updatedEntry.modifier(), updatedEntry.slot());
            }
        }
    }

    default List<ItemAttributeModifiers.Entry> increaseDamage(Holder<Attribute> typeAttribute, ItemAttributeModifiers modifiers, ItemStack stack) {
        List<ItemAttributeModifiers.Entry> modifierEntryList = new ArrayList<>();

        modifierEntryList.add(new ItemAttributeModifiers.Entry(typeAttribute, new AttributeModifier(DAMAGE_TYPES.get(typeAttribute), this.calculateDamageIncrease(typeAttribute, DAMAGE_TYPES.get(typeAttribute), modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));
        modifierEntryList.add(new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, this.calculateDamageIncrease(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID, modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));

        return modifierEntryList;
    }

     default int calculateDamageIncrease(Holder<Attribute> base, ResourceLocation bonusModifier, ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseStat = new AtomicReference<>(0.0);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == base.value() && !modifier.id().equals(bonusModifier)) {
                baseStat.updateAndGet(v -> v + modifier.amount());
            }
        });
        return this.calculateDamageIncrease(stack, baseStat.get());
    }

    default int calculateDamageIncrease(ItemStack stack, double baseDamage) {
        double boostedDamage = this.calculateZaniteBuff(stack, baseDamage);
        boostedDamage -= baseDamage;
        if (boostedDamage < 0.0) {
            boostedDamage = 0.0;
        }
        return (int) Math.round(boostedDamage);
    }
}
