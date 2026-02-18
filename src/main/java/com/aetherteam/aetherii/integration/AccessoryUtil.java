package com.aetherteam.aetherii.integration;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.network.packet.clientbound.BreakItemPacket;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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

    public static InteractionResult equip(Player player, ItemStack stack, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = player.getData(AetherIIDataAttachments.ACCESSORIES);
        int index = getValidSlot(player, slot);
        ItemStack itemstack = container.getItem(index);
        if ((!EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) || player.isCreative()) && !ItemStack.isSameItemSameComponents(stack, itemstack)) {
            if (!player.level().isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            if (stack.getCount() <= 1) {
                ItemStack resultStack = itemstack.isEmpty() ? stack : itemstack.copyAndClear();
                ItemStack insertedStack = player.isCreative() ? stack.copy() : stack.copyAndClear();
                container.getItems().set(index, insertedStack);
                return InteractionResult.SUCCESS.heldItemTransformedTo(resultStack);
            } else {
                ItemStack copiedStack = itemstack.copyAndClear();
                ItemStack insertedStack = stack.consumeAndReturn(1, player);
                container.getItems().set(index, insertedStack);
                if (!player.getInventory().add(copiedStack)) {
                    player.drop(copiedStack, false);
                }
                return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    private static int getValidSlot(Player player, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = player.getData(AetherIIDataAttachments.ACCESSORIES);
        int firstEmptyIndex = -1;
        int firstFullIndex = -1;
        for (int i : slot.getIndex()) {
            if (firstEmptyIndex < 0 && container.getItem(i).isEmpty()) {
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
        tooltip.accept(Component.translatable("aether_ii.tooltip.item.modifiers." + group).withStyle(ChatFormatting.GRAY));

        AttributeUtil.applyTextFor(stack, tooltip, modifiers, ctx);
    }

    public static void breakAccessory(Item item, ItemStack stack, ServerPlayer wearer) {
        PacketDistributor.sendToAllPlayers(new BreakItemPacket(wearer.getId(), stack.copy()));
    }
}
