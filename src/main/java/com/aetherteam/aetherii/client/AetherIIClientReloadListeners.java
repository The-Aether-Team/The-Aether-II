package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;

public class AetherIIClientReloadListeners {
    public static void registerReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(Identifier.fromNamespaceAndPath(AetherII.MODID, "cloud_renderer"), AetherIIDimensionRenderers.CLOUDS_RENDERER);
    }
}
