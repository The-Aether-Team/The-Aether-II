package com.aetherteam.aetherii.integration;

import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public record AttributeTooltipContext(Object player, Level level, TooltipDisplay display, TooltipFlag flag) {
    public static AttributeTooltipContext of(Object player, Level level, TooltipDisplay display, TooltipFlag flag) {
        return new AttributeTooltipContext(player, level, display, flag);
    }
}
