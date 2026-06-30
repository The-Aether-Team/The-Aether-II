package com.aetherteam.aetherii.item.equipment;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;

import java.util.function.Consumer;

public class BrokenItem extends Item {
    public BrokenItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("aether_ii.tooltip.item.broken").withStyle(ChatFormatting.RED));
    }
}
