package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TreasureItem extends Item {
    public TreasureItem(Properties properties) {
        super(properties.rarity(AetherIIItems.AETHER_II_TREASURE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.treasure.description").withStyle(ChatFormatting.GRAY));
    }
}
