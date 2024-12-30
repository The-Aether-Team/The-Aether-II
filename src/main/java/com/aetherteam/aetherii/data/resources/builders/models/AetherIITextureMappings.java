package com.aetherteam.aetherii.data.resources.builders.models;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

public class AetherIITextureMappings {
    public static TextureMapping tintedGrass(Block grass, Block dirt) {
        return new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirt))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(grass, "_top"))
                .put(AetherIITextureSlots.TOP_1, TextureMapping.getBlockTexture(grass, "_top_1"))
                .put(AetherIITextureSlots.TOP_2, TextureMapping.getBlockTexture(grass, "_top_2"))
                .put(AetherIITextureSlots.TOP_3, TextureMapping.getBlockTexture(grass, "_top_3"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grass, "_side"))
                .put(AetherIITextureSlots.SIDE_OVERLAY_1, TextureMapping.getBlockTexture(grass, "_side_overlay_1"))
                .put(AetherIITextureSlots.SIDE_OVERLAY_2, TextureMapping.getBlockTexture(grass, "_side_overlay_2"))
                .put(AetherIITextureSlots.SIDE_OVERLAY_3, TextureMapping.getBlockTexture(grass, "_side_overlay_3"));
    }
}
