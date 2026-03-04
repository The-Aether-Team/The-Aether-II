package com.aetherteam.aetherii.item.equipment.tools.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteTool extends ZaniteBuff {
    Identifier MINING_EFFICIENCY_MODIFIER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_mining_efficiency");

    static void updateToolAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        ItemAttributeModifiers defaultModifiers = event.getDefaultModifiers();
        List<ItemAttributeModifiers.Entry> modifiers = event.getModifiers();

        if (stack.getItem() instanceof ZaniteTool zaniteTool) {
            ItemAttributeModifiers.Entry updatedEntry = null;
            ItemAttributeModifiers.Entry newEntry = zaniteTool.increaseSpeed(defaultModifiers, stack, 6.0F);
            double newAmount = newEntry.modifier().amount();
            boolean flag = true;
            for (ItemAttributeModifiers.Entry oldEntry : modifiers) {
                double oldAmount = oldEntry.modifier().amount();
                if (oldEntry.matches(newEntry.attribute(), newEntry.modifier().id())) {
                    if (oldAmount != newAmount) {
                        updatedEntry = newEntry;
                    }
                    flag = false;
                }
            }
            if (flag) {
                updatedEntry = newEntry;
            }
            if (updatedEntry != null) {
                event.replaceModifier(updatedEntry.attribute(), updatedEntry.modifier(), updatedEntry.slot());
            }
        }
    }

    default ItemAttributeModifiers.Entry increaseSpeed(ItemAttributeModifiers modifiers, ItemStack stack, double baseValue) {
        return new ItemAttributeModifiers.Entry(Attributes.MINING_EFFICIENCY, new AttributeModifier(MINING_EFFICIENCY_MODIFIER_ID, this.calculateSpeedIncrease(Attributes.MINING_EFFICIENCY, baseValue, MINING_EFFICIENCY_MODIFIER_ID, modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    default double calculateSpeedIncrease(Holder<Attribute> base, double baseValue, Identifier bonusModifier, ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseStat = new AtomicReference<>(baseValue);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == base.value() && !modifier.id().equals(bonusModifier)) {
                baseStat.updateAndGet(v -> v + modifier.amount());
            }
        });
        return this.calculateZaniteBuff(stack, baseStat.get()) - baseStat.get();
    }
}
