package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.ItemStack;

public class ToolAbilityHooks {
    public static float handleZaniteToolAbility(ItemStack stack, float speed) {
        if (stack.getItem() instanceof ZaniteTool zaniteTool) {
            return zaniteTool.increaseSpeed(stack, speed);
        }
        return speed;
    }
}
