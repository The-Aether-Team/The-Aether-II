package com.aetherteam.aetherii.network;

public final class ClientPacketDistributor {
    private ClientPacketDistributor() {
    }

    public static void sendToServer(Object payload) {
        AetherIINetwork.sendToServer(payload);
    }
}
