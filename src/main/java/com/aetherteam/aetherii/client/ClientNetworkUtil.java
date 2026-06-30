package com.aetherteam.aetherii.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ClientNetworkUtil {
    private ClientNetworkUtil() {
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
