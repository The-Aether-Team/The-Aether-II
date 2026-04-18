package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.GuiAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class CurrencyItem extends Item {
    private final int currencyAmount;

    public CurrencyItem(int currencyAmount, Properties properties) {
        super(properties.rarity(AetherIIItems.AETHER_II_CURRENCY));
        this.currencyAmount = currencyAmount;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable("aether_ii.tooltip.item.currency.description").withStyle(ChatFormatting.GRAY));
        tooltipAdder.accept(Component.translatable("aether_ii.tooltip.item.currency.amount", this.currencyAmount).withColor(8158399));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        CurrencyAttachment attachment = player.getData(AetherIIDataAttachments.CURRENCY);
        attachment.setAmount(attachment.getAmount() + this.currencyAmount);
        player.syncData(AetherIIDataAttachments.CURRENCY);
        if (player instanceof ServerPlayer serverPlayer) {
            AetherIIAdvancementTriggers.CURRENCY.get().trigger(serverPlayer, attachment.getAmount());
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        if (level.isClientSide()) {
            GuiAccessor gui = (GuiAccessor) Minecraft.getInstance().gui;
            player.sendOverlayMessage(Component.translatable("aether_ii.tooltip.item.currency.amount", attachment.getAmount()).withColor(15066623));
            if (gui.aether$getOverlayMessageString() != null && gui.aether$getOverlayMessageTime() > 0) {
                gui.aether$setOverlayMessageTime(30);
            }
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }

    public int getCurrencyAmount() {
        return this.currencyAmount;
    }
}
