package com.aetherteam.aetherii.item.equipment.tools.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.components.EquipmentSlotGroup;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.ArrayList;
import java.util.Collection;

public interface ZaniteTool extends ZaniteBuff {
    ResourceLocation MINING_EFFICIENCY_MODIFIER_ID = new ResourceLocation(AetherII.MODID, "zanite_modified_mining_efficiency");

    static void updateToolAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (event.getSlotType() == EquipmentSlot.MAINHAND && stack.getItem() instanceof ZaniteTool zaniteTool) {
            Attribute attribute = AetherIIAttributes.MINING_EFFICIENCY.get();
            Multimap<Attribute, AttributeModifier> modifiers = event.getModifiers();
            Collection<AttributeModifier> attributeModifiers = modifiers.get(attribute);
            double amount = zaniteTool.calculateSpeedIncrease(attribute, 6.0F, MINING_EFFICIENCY_MODIFIER_ID, attributeModifiers, stack);
            for (AttributeModifier modifier : new ArrayList<>(attributeModifiers)) {
                if (ItemAttributeModifiers.id(modifier).equals(MINING_EFFICIENCY_MODIFIER_ID)) {
                    event.removeModifier(attribute, modifier);
                }
            }
            if (amount != 0.0) {
                event.addModifier(attribute, ItemAttributeModifiers.modifier(MINING_EFFICIENCY_MODIFIER_ID, amount, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    default ItemAttributeModifiers.Entry increaseSpeed(ItemAttributeModifiers modifiers, ItemStack stack, double baseValue) {
        Collection<AttributeModifier> attributeModifiers = new ArrayList<>();
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == AetherIIAttributes.MINING_EFFICIENCY.get()) {
                attributeModifiers.add(modifier);
            }
        });
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.MINING_EFFICIENCY, ItemAttributeModifiers.modifier(MINING_EFFICIENCY_MODIFIER_ID, this.calculateSpeedIncrease(AetherIIAttributes.MINING_EFFICIENCY.get(), baseValue, MINING_EFFICIENCY_MODIFIER_ID, attributeModifiers, stack), net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND);
    }

    default double calculateSpeedIncrease(Attribute base, double baseValue, ResourceLocation bonusModifier, Collection<AttributeModifier> modifiers, ItemStack stack) {
        double baseStat = baseValue;
        for (AttributeModifier modifier : modifiers) {
            if (!ItemAttributeModifiers.id(modifier).equals(bonusModifier)) {
                baseStat += modifier.getAmount();
            }
        }
        return this.calculateZaniteBuff(stack, baseStat) - baseStat;
    }
}
