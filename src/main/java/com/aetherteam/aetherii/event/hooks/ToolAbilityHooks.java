package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.item.tools.abilities.HolystoneTool;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ToolAbilityHooks {
    public static void handleHolystoneToolAbility(Player player, Level level, BlockPos pos, ItemStack stack, BlockState blockState) {
        if (stack.getItem() instanceof HolystoneTool holystoneTool) {
            holystoneTool.dropAmbrosium(player, level, pos, stack, blockState);
        }
    }

    public static float handleZaniteToolAbility(ItemStack stack, float speed) {
        if (stack.getItem() instanceof ZaniteTool zaniteTool) {
            return zaniteTool.increaseSpeed(stack, speed);
        }
        return speed;
    }
}
