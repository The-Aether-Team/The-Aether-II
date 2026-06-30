package com.aetherteam.aetherii.item.equipment.weapons.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.components.EquipmentSlotGroup;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public interface ZaniteWeapon extends ZaniteBuff {
    ResourceLocation DAMAGE_MODIFIER_ID = new ResourceLocation(AetherII.MODID, "zanite_modified_attack_damage");
    ResourceLocation SLASH_DAMAGE_MODIFIER_ID = new ResourceLocation(AetherII.MODID, "zanite_modified_slash_attack_damage");
    ResourceLocation IMPACT_DAMAGE_MODIFIER_ID = new ResourceLocation(AetherII.MODID, "zanite_modified_impact_attack_damage");
    ResourceLocation PIERCE_DAMAGE_MODIFIER_ID = new ResourceLocation(AetherII.MODID, "zanite_modified_pierce_attack_damage");

    Map<RegistryObject<Attribute>, ResourceLocation> DAMAGE_TYPES = Map.ofEntries(
            Map.entry(AetherIIAttributes.SLASH_DAMAGE, SLASH_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.IMPACT_DAMAGE, IMPACT_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.PIERCE_DAMAGE, PIERCE_DAMAGE_MODIFIER_ID)
    );

    static void updateWeaponAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (event.getSlotType() == EquipmentSlot.MAINHAND && stack.getItem() instanceof ZaniteWeapon zaniteWeapon) {
            zaniteWeapon.applyDamageIncrease(event, zaniteWeapon.getDamageType().get(), DAMAGE_TYPES.get(zaniteWeapon.getDamageType()), stack);
            zaniteWeapon.applyDamageIncrease(event, Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID, stack);
        }
    }

    default void applyDamageIncrease(ItemAttributeModifierEvent event, Attribute attribute, ResourceLocation modifierId, ItemStack stack) {
        Collection<AttributeModifier> modifiers = event.getModifiers().get(attribute);
        int amount = this.calculateDamageIncrease(attribute, modifierId, modifiers, stack);
        for (AttributeModifier modifier : new ArrayList<>(modifiers)) {
            if (ItemAttributeModifiers.id(modifier).equals(modifierId)) {
                event.removeModifier(attribute, modifier);
            }
        }
        if (amount != 0) {
            event.addModifier(attribute, ItemAttributeModifiers.modifier(modifierId, amount, AttributeModifier.Operation.ADDITION));
        }
    }

    default List<ItemAttributeModifiers.Entry> increaseDamage(RegistryObject<Attribute> typeAttribute, ItemAttributeModifiers modifiers, ItemStack stack) {
        List<ItemAttributeModifiers.Entry> modifierEntryList = new ArrayList<>();
        ResourceLocation typeModifierId = DAMAGE_TYPES.get(typeAttribute);

        modifierEntryList.add(new ItemAttributeModifiers.Entry(typeAttribute, ItemAttributeModifiers.modifier(typeModifierId, this.calculateDamageIncrease(typeAttribute.get(), typeModifierId, collectModifiers(modifiers, typeAttribute.get()), stack), net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND));
        modifierEntryList.add(new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, ItemAttributeModifiers.modifier(DAMAGE_MODIFIER_ID, this.calculateDamageIncrease(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID, collectModifiers(modifiers, Attributes.ATTACK_DAMAGE), stack), net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND));

        return modifierEntryList;
    }

    private static Collection<AttributeModifier> collectModifiers(ItemAttributeModifiers modifiers, Attribute attribute) {
        Collection<AttributeModifier> attributeModifiers = new ArrayList<>();
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (entryAttribute, modifier) -> {
            if (entryAttribute.value() == attribute) {
                attributeModifiers.add(modifier);
            }
        });
        return attributeModifiers;
    }

     default int calculateDamageIncrease(Attribute base, ResourceLocation bonusModifier, Collection<AttributeModifier> modifiers, ItemStack stack) {
        double baseStat = 0.0;
        for (AttributeModifier modifier : modifiers) {
            if (!ItemAttributeModifiers.id(modifier).equals(bonusModifier)) {
                baseStat += modifier.getAmount();
            }
        }
        return this.calculateDamageIncrease(stack, baseStat);
    }

    default int calculateDamageIncrease(ItemStack stack, double baseDamage) {
        double boostedDamage = this.calculateZaniteBuff(stack, baseDamage);
        boostedDamage -= baseDamage;
        if (boostedDamage < 0.0) {
            boostedDamage = 0.0;
        }
        return (int) Math.round(boostedDamage);
    }

    RegistryObject<Attribute> getDamageType();
}
