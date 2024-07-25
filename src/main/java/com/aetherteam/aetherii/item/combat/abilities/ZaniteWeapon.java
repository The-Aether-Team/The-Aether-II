package com.aetherteam.aetherii.item.combat.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteWeapon extends ZaniteTool, UniqueDamage { //todo replace with ItemAttributeModifierEvent
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_attack_damage");

    default ItemAttributeModifiers increaseDamage(ItemAttributeModifiers modifiers, ItemStack stack) {
        List<ItemAttributeModifiers.Entry> mutableList = Lists.newArrayList(modifiers.modifiers().listIterator());
        mutableList.removeIf((entry) -> entry.modifier().is(DAMAGE_MODIFIER_ID));
        mutableList.add(new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, this.calculateIncrease(modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : mutableList) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder.build();
    }

     default int calculateIncrease(ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseDamage = new AtomicReference<>(0.0);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == Attributes.ATTACK_DAMAGE.value()) {
                baseDamage.updateAndGet(v -> v + modifier.amount());
            }
        });
        return this.calculateIncrease(stack, baseDamage.get());
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
