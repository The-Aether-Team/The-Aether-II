package com.aetherteam.aetherii.network;

import net.minecraft.world.entity.player.Player;

public interface AetherPayloadContext {
    Player player();

    default void enqueueWork(Runnable runnable) {
        runnable.run();
    }
}
