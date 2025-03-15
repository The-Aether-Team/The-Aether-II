package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;

public class AetherIISpriteSourceTypes {
    public static final SpriteSourceType OVERLAID = new SpriteSourceType(Overlaid.CODEC);

    public static void registerSpriteSourceTypes(RegisterSpriteSourceTypesEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "overlaid"), OVERLAID);
    }
}
