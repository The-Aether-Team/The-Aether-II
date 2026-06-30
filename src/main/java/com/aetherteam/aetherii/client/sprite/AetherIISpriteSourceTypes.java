package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;

public final class AetherIISpriteSourceTypes {
    public static final SpriteSourceType ADDITIVE = SpriteSources.register(AetherII.MODID + ":additive", Additive.CODEC);
    public static final SpriteSourceType SUBTRACTIVE = SpriteSources.register(AetherII.MODID + ":subtractive", Subtractive.CODEC);
    public static final SpriteSourceType SQUARES = SpriteSources.register(AetherII.MODID + ":squares", Squares.CODEC);

    private AetherIISpriteSourceTypes() {
    }

    public static void init() {
    }
}
