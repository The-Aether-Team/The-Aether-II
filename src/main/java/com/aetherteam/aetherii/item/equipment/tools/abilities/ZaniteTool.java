package com.aetherteam.aetherii.item.equipment.tools.abilities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public interface ZaniteTool {
    static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) { //todo convert to attachments like zaniteweapon
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        if (!event.isCanceled()) {
            float speed = event.getNewSpeed();
            if (itemStack.getItem() instanceof ZaniteTool zaniteTool) {
                speed = zaniteTool.increaseSpeed(itemStack, speed);
                event.setNewSpeed(speed);
            }
        }
    }

    default float increaseSpeed(ItemStack stack, float speed) {
        return (float) this.calculateZaniteBuff(stack, speed);
    }

    default double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }
}
