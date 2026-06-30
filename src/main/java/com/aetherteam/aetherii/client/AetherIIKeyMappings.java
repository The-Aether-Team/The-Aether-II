package com.aetherteam.aetherii.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class AetherIIKeyMappings {
    public static final KeyMapping ALLOW_DISMOUNTING_PASSENGER = new KeyMapping(
            "key.aether_ii.allow_dismounting_passenger",
            InputConstants.KEY_LSHIFT,
            "key.categories.aether_ii");

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ALLOW_DISMOUNTING_PASSENGER);
    }
}
