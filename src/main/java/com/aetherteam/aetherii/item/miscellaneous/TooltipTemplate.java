package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

@FunctionalInterface
public interface TooltipTemplate {
    void appendHoverText(ItemStack stack, Item.TooltipContext context, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag);
}
