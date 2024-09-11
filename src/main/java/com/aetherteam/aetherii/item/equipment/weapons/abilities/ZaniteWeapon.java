package com.aetherteam.aetherii.item.equipment.weapons.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteWeapon extends ZaniteTool { //todo replace with ItemAttributeModifierEvent
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_attack_damage");
    ResourceLocation SLASH_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_slash_attack_damage");
    ResourceLocation IMPACT_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_impact_attack_damage");
    ResourceLocation PIERCE_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_pierce_attack_damage");

    Map<Holder<Attribute>, ResourceLocation> DAMAGE_TYPES = Map.ofEntries(
            Map.entry(AetherIIAttributes.SLASH_DAMAGE, SLASH_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.IMPACT_DAMAGE, IMPACT_DAMAGE_MODIFIER_ID),
            Map.entry(AetherIIAttributes.PIERCE_DAMAGE, PIERCE_DAMAGE_MODIFIER_ID)
    );

    default ItemAttributeModifiers increaseDamage(Holder<Attribute> typeAttribute, ItemAttributeModifiers modifiers, ItemStack stack) {
        List<ItemAttributeModifiers.Entry> modifierEntryList = Lists.newArrayList(modifiers.modifiers().listIterator());

        modifierEntryList.removeIf((entry) -> entry.modifier().is(DAMAGE_TYPES.get(typeAttribute)));
        modifierEntryList.add(new ItemAttributeModifiers.Entry(typeAttribute, new AttributeModifier(DAMAGE_TYPES.get(typeAttribute), this.calculateDamageIncrease(typeAttribute, DAMAGE_TYPES.get(typeAttribute), modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));

        modifierEntryList.removeIf((entry) -> entry.modifier().is(DAMAGE_MODIFIER_ID));
        modifierEntryList.add(new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, this.calculateDamageIncrease(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID, modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : modifierEntryList) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder.build();
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
//         AetherII.LOGGER.info(String.valueOf(boostedDamage));
        return (int) Math.round(boostedDamage);
    }
}
