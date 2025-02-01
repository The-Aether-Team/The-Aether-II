package com.aetherteam.aetherii.integration;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.client.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class AccessoryUtil {
    public static Optional<ItemStack> getFirst(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        return get(livingEntity, slot, 0);
    }

    public static Optional<ItemStack> get(LivingEntity livingEntity, AccessoryContainer.SlotType slot, int index) {
        List<ItemStack> itemStacks = get(livingEntity, slot);
        return itemStacks.isEmpty() ? Optional.empty() : Optional.of(itemStacks.get(index));
    }

    public static List<ItemStack> get(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = livingEntity.getData(AetherIIDataAttachments.ACCESSORIES);
        List<ItemStack> items = new ArrayList<>();
        for (int i : slot.getIndex()) {
            ItemStack itemStack = container.getItem(i);
            if (!itemStack.isEmpty()) {
                items.add(itemStack);
            }
        }
        return items;
    }

    public static void addAttributeTooltips(ItemStack stack, Consumer<Component> tooltip, AttributeTooltipContext ctx, Multimap<Holder<Attribute>, AttributeModifier> modifiers, String group) {
        var event = NeoForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, ctx));
        if (event.isSkippingAll()) {
            return;
        }

        // Remove any skipped modifiers before doing any logic
        modifiers.values().removeIf(m -> event.isSkipped(m.id()));

        if (modifiers.isEmpty()) {
            return;
        }

        // Add an empty line, then the name of the group, then the modifiers.
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.modifiers." + group).withStyle(ChatFormatting.GRAY));

        AttributeUtil.applyTextFor(stack, tooltip, modifiers, ctx);
    }
}
