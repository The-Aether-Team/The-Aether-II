package com.aetherteam.aetherii.entity;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerRideableInstantJumping extends PlayerRideable {
    void onPlayerJump();

    boolean canJump();

    default int getJumpCooldown() {
        return 0;
    }

}

