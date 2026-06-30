package com.aetherteam.aetherii.item;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface SpecialAttackStrengthScale {
    default float getAttackStrengthScale(Level level, Player player, ItemStack stack, float adjustTicks, int attackStrengthTicker) {
        return Mth.clamp((attackStrengthTicker + adjustTicks) / player.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
    }
}
