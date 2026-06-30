package com.aetherteam.aetherii.integration;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.network.packet.clientbound.BreakItemPacket;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.PacketDistributor;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.common.util.FakePlayer;

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
        List<ItemStack> items = new ArrayList<>();
        if (!(livingEntity instanceof FakePlayer)) {
            AccessoryContainer container = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.ACCESSORIES);
            for (int i : slot.getIndex()) {
                ItemStack itemStack = container.getItem(i);
                if (!itemStack.isEmpty()) {
                    items.add(itemStack);
                }
            }
        }
        return items;
    }

    public static InteractionResult equip(Player player, InteractionHand hand, ItemStack stack, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ACCESSORIES);
        int index = getValidSlot(player, stack,  slot);
        ItemStack itemstack = container.getItem(index);
        if (!ItemStack.isSameItemSameTags(stack, itemstack)) {
            if (!player.level().isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            if (stack.getCount() <= 1) {
                ItemStack resultStack = itemstack.isEmpty() ? stack : itemstack.copyAndClear();
                ItemStack insertedStack = player.isCreative() ? stack.copy() : stack.copyAndClear();
                container.setItemWithEquip(player, index, insertedStack);
                player.setItemInHand(hand, resultStack);
                return InteractionResult.SUCCESS;
            } else {
                ItemStack copiedStack = itemstack.copyAndClear();
                ItemStack insertedStack = stack.copyWithCount(1);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                container.setItemWithEquip(player, index, insertedStack);
                if (!copiedStack.isEmpty() && !player.getInventory().add(copiedStack)) {
                    player.drop(copiedStack, false);
                }
                player.setItemInHand(hand, stack);
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    private static int getValidSlot(Player player, ItemStack stack, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ACCESSORIES);
        int firstEmptyIndex = -1;
        int firstFullIndex = -1;
        for (int i : slot.getIndex()) {
            if (firstEmptyIndex < 0 && container.getItem(i).isEmpty() && !container.hasAnyMatching((otherStack) -> otherStack.getItem() == stack.getItem())) {
                firstEmptyIndex = i;
            }
            if (firstFullIndex < 0 && !container.getItem(i).isEmpty()) {
                firstFullIndex = i;
            }
        }
        if (firstEmptyIndex >= 0) {
            return firstEmptyIndex;
        } else {
            return firstFullIndex;
        }
    }

    public static void addAttributeTooltips(ItemStack stack, Consumer<Component> tooltip, AttributeTooltipContext ctx, Multimap<Holder<Attribute>, AttributeModifier> modifiers, String group) {
        if (modifiers.isEmpty()) {
            return;
        }

        // Add an empty line, then the name of the group, then the modifiers.
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("aether_ii.tooltip.item.modifiers." + group).withStyle(ChatFormatting.GRAY));

        modifiers.forEach((attribute, modifier) -> tooltip.accept(formatAttributeModifier(attribute, modifier)));
    }

    public static void addPotionTooltip(List<Pair<Holder<Attribute>, AttributeModifier>> modifiers, Consumer<Component> tooltip) {
        for (Pair<Holder<Attribute>, AttributeModifier> pair : modifiers) {
            tooltip.accept(formatAttributeModifier(pair.getFirst(), pair.getSecond()));
        }
    }

    private static Component formatAttributeModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
        double amount = modifier.getAmount();
        boolean positive = amount >= 0.0;
        MutableComponent value = Component.literal(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(amount)));
        return Component.translatable(positive ? "attribute.modifier.plus.0" : "attribute.modifier.take.0", value, Component.translatable(attribute.value().getDescriptionId()))
                .withStyle(positive ? ChatFormatting.BLUE : ChatFormatting.RED);
    }

    public static void breakAccessory(Item item, ItemStack stack, ServerPlayer wearer) {
        PacketDistributor.sendToAllPlayers(new BreakItemPacket(wearer.getId(), stack.copy()));
    }
}
