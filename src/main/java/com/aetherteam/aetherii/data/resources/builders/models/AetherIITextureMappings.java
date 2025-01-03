package com.aetherteam.aetherii.data.resources.builders.models;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AetherIITextureMappings {
    public static TextureMapping portal(Block portal) {
        return new TextureMapping()
                .put(AetherIITextureSlots.PORTAL, TextureMapping.getBlockTexture(portal))
                .copySlot(AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    }

    public static TextureMapping snowyGrass(Block grass, Block dirt) {
        return  new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirt))
                .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(grass, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grass, "_snow"));
    }

    public static TextureMapping vine(ResourceLocation vine) {
        return  new TextureMapping()
                .put(AetherIITextureSlots.VINE, vine)
                .copySlot(AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    }

    public static TextureMapping snowyLeaves(Block leaves) {
        return  new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(leaves))
                .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(leaves))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(leaves, "_snowy"));
    }

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

    public static TextureMapping tintedTallGrass(Block tallGrass) {
        return new TextureMapping()
                .put(TextureSlot.CROSS, TextureMapping.getBlockTexture(tallGrass))
                .copySlot(TextureSlot.CROSS, TextureSlot.PARTICLE)
                .put(AetherIITextureSlots.OVERLAY_1, TextureMapping.getBlockTexture(tallGrass, "_1"))
                .put(AetherIITextureSlots.OVERLAY_2, TextureMapping.getBlockTexture(tallGrass, "_2"))
                .put(AetherIITextureSlots.OVERLAY_3, TextureMapping.getBlockTexture(tallGrass, "_3"));
    }

    public static TextureMapping dirtPath(Block path, Block dirt) {
        return new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirt))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(path, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(path, "_side"));
    }

    public static TextureMapping bushBlock(Block bush) {
        return new TextureMapping()
                .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(bush))
                .copySlot(TextureSlot.TEXTURE, TextureSlot.PARTICLE)
                .put(TextureSlot.CROSS, TextureMapping.getBlockTexture(bush, "_stem"));
    }

    public static TextureMapping pottedBushBlock(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.FLOWERPOT, TextureMapping.getBlockTexture(Blocks.FLOWER_POT))
                .copySlot(AetherIITextureSlots.FLOWERPOT, TextureSlot.PARTICLE)
                .put(TextureSlot.STEM, TextureMapping.getBlockTexture(block, "_stem"))
                .put(AetherIITextureSlots.BUSH, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping flowerbed(Block block) {
        return new TextureMapping()
                .put(TextureSlot.FLOWERBED, TextureMapping.getBlockTexture(block))
                .copySlot(TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    }

    public static TextureMapping tarahespFlowerbed(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureMapping.getBlockTexture(block, "_white"))
                .put(AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, TextureMapping.getBlockTexture(block, "_purple"))
                .copySlot(AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, TextureSlot.PARTICLE);
    }

    public static TextureMapping doorTop(Block block) {
        return new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")).copySlot(TextureSlot.TOP, TextureSlot.PARTICLE);
    }

    public static TextureMapping doorBottom(Block block) {
        return new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom")).copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE);
    }

    public static TextureMapping asymmetricalCross(Block block) {
        return new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block)).put(AetherIITextureSlots.CROSS_OTHER, TextureMapping.getBlockTexture(block, "_other"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping plant(Block block) {
        return new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping particle(TextureMapping textureMapping) {
        return textureMapping.copyForced(TextureSlot.ALL, TextureSlot.PARTICLE);
    }
}