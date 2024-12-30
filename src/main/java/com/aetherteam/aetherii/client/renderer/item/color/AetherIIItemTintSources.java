package com.aetherteam.aetherii.client.renderer.item.color;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class AetherIIItemTintSources {
    public static void registerTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aether_grass"), AetherGrassColorSource.MAP_CODEC);
    }
}
