package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterSpriteSourcesEvent;

public class AetherIISpriteSourceTypes {
    public static void registerSpriteSourceTypes(RegisterSpriteSourcesEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "additive"), Additive.CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "subtractive"), Subtractive.CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "squares"), Squares.CODEC);
    }
}