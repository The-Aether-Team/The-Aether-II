package com.aetherteam.aetherii.client.renderer.item.color;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class AetherIIItemTintSources {
    public static void registerTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "aether_grass"), AetherGrassColorSource.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "effect_buildup"), EffectBuildupColorSource.MAP_CODEC);
    }
}
