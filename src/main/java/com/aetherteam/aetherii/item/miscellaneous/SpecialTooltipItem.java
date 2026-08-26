package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class SpecialTooltipItem extends Item {
    public final TooltipTemplate tooltip;

    public SpecialTooltipItem(TooltipTemplate tooltip, Properties properties) {
        super(properties);
        this.tooltip = tooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        this.tooltip.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
