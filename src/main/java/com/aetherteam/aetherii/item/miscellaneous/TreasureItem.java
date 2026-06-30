package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

public class TreasureItem extends Item {
    public TreasureItem(Properties properties) {
        super(properties.rarity(AetherIIItems.AETHER_II_TREASURE));
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.treasure.description").withStyle(ChatFormatting.GRAY));
    }
}
