package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterSpriteSourcesEvent;

public class AetherIISpriteSourceTypes {
    public static void registerSpriteSourceTypes(RegisterSpriteSourcesEvent event) {
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "additive"), Additive.CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "subtractive"), Subtractive.CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "squares"), Squares.CODEC);
    }
}