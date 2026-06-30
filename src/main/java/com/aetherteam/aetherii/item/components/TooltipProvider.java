package com.aetherteam.aetherii.item.components;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public interface TooltipProvider {
    void addToTooltip(Object context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter);
}
