package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CurrencyItem extends Item {
    private final int currencyAmount;

    public CurrencyItem(int currencyAmount, Properties properties) {
        super(properties.rarity(AetherIIItems.AETHER_II_CURRENCY));
        this.currencyAmount = currencyAmount;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.add(Component.translatable("aether_ii.tooltip.item.currency.description").withStyle(ChatFormatting.GRAY));
        tooltipAdder.add(Component.translatable("aether_ii.tooltip.item.currency.amount", this.currencyAmount).withStyle(style -> style.withColor(8158399)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        CurrencyAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.CURRENCY);
        attachment.setAmount(attachment.getAmount() + this.currencyAmount);
        AetherIIDataAttachments.sync(player, AetherIIDataAttachments.CURRENCY);
        if (player instanceof ServerPlayer serverPlayer) {
            AetherIIAdvancementTriggers.CURRENCY.get().trigger(serverPlayer, attachment.getAmount());
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (level.isClientSide()) {
            player.displayClientMessage(Component.translatable("aether_ii.tooltip.item.currency.amount", attachment.getAmount()).withStyle(style -> style.withColor(15066623)), true);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public int getCurrencyAmount() {
        return this.currencyAmount;
    }
}
