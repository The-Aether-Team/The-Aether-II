package com.aetherteam.aetherii.item.miscellaneous.glider;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlueAercloudGliderItem extends AercloudGliderItem {
    public BlueAercloudGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void onParachuteOpen(Level level, Player player, InteractionHand hand, ItemStack stack) {
        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 2.0, 0.0));
    }
}
