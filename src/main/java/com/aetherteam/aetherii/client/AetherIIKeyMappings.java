package com.aetherteam.aetherii.client;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class AetherIIKeyMappings {
    public final static KeyMapping ALLOW_DISMOUNTING_PASSENGER = new KeyMapping("key.aether_ii.allow_dismounting_passenger.desc", GLFW.GLFW_KEY_LEFT_SHIFT, "key.aether_ii.category");

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ALLOW_DISMOUNTING_PASSENGER);
    }
}
