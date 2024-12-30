package com.aetherteam.aetherii.item.equipment.tools.abilities;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteTool {
    ResourceLocation MINING_EFFICIENCY_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite_modified_mining_efficiency");

    default ItemAttributeModifiers increaseSpeed(ItemAttributeModifiers modifiers, ItemStack stack, double baseValue) {
        List<ItemAttributeModifiers.Entry> modifierEntryList = new ArrayList<>(modifiers.modifiers());

        modifierEntryList.removeIf((entry) -> entry.modifier().is(MINING_EFFICIENCY_MODIFIER_ID));
        modifierEntryList.add(new ItemAttributeModifiers.Entry(Attributes.MINING_EFFICIENCY, new AttributeModifier(MINING_EFFICIENCY_MODIFIER_ID, this.calculateSpeedIncrease(Attributes.MINING_EFFICIENCY, baseValue, MINING_EFFICIENCY_MODIFIER_ID, modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : modifierEntryList) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder.build();
    }

    default double calculateSpeedIncrease(Holder<Attribute> base, double baseValue, ResourceLocation bonusModifier, ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseStat = new AtomicReference<>(baseValue);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == base.value() && !modifier.id().equals(bonusModifier)) {
                baseStat.updateAndGet(v -> v + modifier.amount());
            }
        });
        return this.calculateZaniteBuff(stack, baseStat.get()) - baseStat.get();
    }

    default double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }

    default float getDefaultSpeed(ItemStack stack, TagKey<Block> breakableTag) {
        Tool tool = stack.get(DataComponents.TOOL);
        HolderSet<Block> breakable = BuiltInRegistries.BLOCK.getOrThrow(breakableTag);
        if (tool != null) {
            List<Tool.Rule> rules = tool.rules();
            for (Tool.Rule rule : rules) {
                if (rule.blocks().equals(breakable)) {
                    return rule.speed().orElse(-1.0F);
                }
            }
        }
        return -1.0F;
    }
}
