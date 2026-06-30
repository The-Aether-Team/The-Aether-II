package com.aetherteam.aetherii.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public final class PacketDistributor {
    private PacketDistributor() {
    }

    public static void sendToPlayer(ServerPlayer player, Object payload) {
        AetherIINetwork.sendToPlayer(player, payload);
    }

    public static void sendToAllPlayers(Object payload) {
        AetherIINetwork.sendToAllPlayers(payload);
    }

    public static void sendToPlayersInDimension(ServerLevel level, Object payload) {
        AetherIINetwork.sendToPlayersInDimension(level, payload);
    }

    public static void sendToPlayersNear(ServerLevel level, ServerPlayer excluded, double x, double y, double z, double radius, Object payload) {
        AetherIINetwork.sendToPlayersNear(level, excluded, x, y, z, radius, payload);
    }
}
