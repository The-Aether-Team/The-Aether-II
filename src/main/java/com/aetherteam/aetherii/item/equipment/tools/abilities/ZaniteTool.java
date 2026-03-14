package com.aetherteam.aetherii.item.equipment.tools.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;

public interface ZaniteTool extends ZaniteBuff {
    ResourceLocation MINING_EFFICIENCY_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_mining_efficiency");

    static void updateToolAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        List<ItemAttributeModifiers.Entry> modifiers = event.getModifiers();

        if (stack.getItem() instanceof ZaniteTool zaniteTool) {
            ItemAttributeModifiers.Entry updatedEntry = null;
            ItemAttributeModifiers.Entry newEntry = zaniteTool.increaseSpeed(stack);
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

    default ItemAttributeModifiers.Entry increaseSpeed(ItemStack stack) {
        return new ItemAttributeModifiers.Entry(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(MINING_EFFICIENCY_MODIFIER_ID, this.calculateZaniteBuff(stack), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
    }
}
