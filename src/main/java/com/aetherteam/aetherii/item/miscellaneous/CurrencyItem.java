package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.currency.description").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.currency.amount", this.currencyAmount).withColor(8158399));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        CurrencyAttachment attachment = player.getData(AetherIIDataAttachments.CURRENCY);
        attachment.setAmount(attachment.getAmount() + this.currencyAmount);
        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public int getCurrencyAmount() {
        return this.currencyAmount;
    }
}
