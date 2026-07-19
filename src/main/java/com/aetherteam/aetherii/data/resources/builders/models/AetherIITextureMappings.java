package com.aetherteam.aetherii.data.resources.builders.models;

import com.aetherteam.aetherii.AetherII;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public class AetherIITextureMappings {
    public static TextureMapping emissive(Material texture) {
        return new TextureMapping()
                .put(TextureSlot.TEXTURE, texture)
                .put(AetherIITextureSlots.EMISSIVE, new Material(texture.sprite().withSuffix("_emissive")));
    }

    public static TextureMapping cubeEmissive(Material texture) {
        return new TextureMapping()
                .put(TextureSlot.ALL, texture)
                .put(AetherIITextureSlots.EMISSIVE, new Material(texture.sprite().withSuffix("_emissive")));
    }

    public static TextureMapping cubeColumnEmissive(Identifier side, Identifier end) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, new Material(side))
                .put(TextureSlot.END, new Material(end))
                .put(AetherIITextureSlots.EMISSIVE_SIDE, new Material(side.withSuffix("_emissive")))
                .put(AetherIITextureSlots.EMISSIVE_END, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/blank")));
    }

    public static TextureMapping block(Block block) {
        return block(TextureMapping.getBlockTexture(block).sprite());
    }

    public static TextureMapping block(Block block, String suffix) {
        return block(TextureMapping.getBlockTexture(block).sprite(), suffix);
    }

    public static TextureMapping block(Identifier texture) {
        return block(texture, "");
    }

    public static TextureMapping block(Identifier texture, String suffix) {
        return new TextureMapping()
            .put(TextureSlot.NORTH, new Material(texture.withSuffix("_front" + suffix)))
            .put(TextureSlot.SOUTH, new Material(texture.withSuffix("_back" + suffix)))
            .put(TextureSlot.EAST, new Material(texture.withSuffix("_right" + suffix)))
            .put(TextureSlot.WEST, new Material(texture.withSuffix("_left" + suffix)))
            .put(TextureSlot.UP, new Material(texture.withSuffix("_top" + suffix)))
            .put(TextureSlot.DOWN, new Material(texture.withSuffix("_bottom" + suffix)));
    }

    public static TextureMapping portal(Block portal) {
        return new TextureMapping()
                .put(AetherIITextureSlots.PORTAL, TextureMapping.getBlockTexture(portal))
                .copySlot(AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    }

    public static TextureMapping snowyGrass(Block grass, Block dirt) {
        return new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirt))
                .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(grass, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grass, "_snow"));
    }

    public static TextureMapping vine(Identifier vine) {
        return new TextureMapping()
                .put(AetherIITextureSlots.VINE, new Material(vine))
                .copySlot(AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    }

    public static TextureMapping mossyTopped(Block top, Block moss, String suffix) {
        return new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(top))
                .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(moss))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(top, "_" + suffix))
                .put(AetherIITextureSlots.OVERLAY, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/" + suffix + "_overlay")));
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
                .put(TextureSlot.STEM, TextureMapping.getBlockTexture(block, "_stem"))
                .put(AetherIITextureSlots.BUSH, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping flowerbed(Block block) {
        return new TextureMapping()
                .put(TextureSlot.FLOWERBED, TextureMapping.getBlockTexture(block))
                .copySlot(TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    }

    public static TextureMapping flowerbedAndStem(Block block) {
        return flowerbed(block).put(TextureSlot.STEM, TextureMapping.getBlockTexture(block, "_stem"));
    }

    public static TextureMapping tarahespFlowerbed(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureMapping.getBlockTexture(block, "_white"))
                .put(AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, TextureMapping.getBlockTexture(block, "_purple"))
                .copySlot(AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, TextureSlot.PARTICLE);
    }

    public static TextureMapping doorTop(Block block) {
        return new TextureMapping()
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_end"));
    }

    public static TextureMapping doorBottom(Block block) {
        return new TextureMapping()
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_end"));
    }

    public static TextureMapping asymmetricalCross(Block block) {
        return new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block)).put(AetherIITextureSlots.CROSS_OTHER, TextureMapping.getBlockTexture(block, "_other"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping plant(Block block) {
        return new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping itemPlant(Block block) {
        return new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem()));
    }

    public static TextureMapping pluracian(Block block) {
        return new TextureMapping()
                .put(TextureSlot.STEM, TextureMapping.getBlockTexture(block, "_stem"))
                .put(AetherIITextureSlots.LEAVES1, TextureMapping.getBlockTexture(block, "_leaves_1"))
                .put(AetherIITextureSlots.LEAVES2, TextureMapping.getBlockTexture(block, "_leaves_2"))
                .put(AetherIITextureSlots.PETAL_TOP, TextureMapping.getBlockTexture(block, "_petal_top"))
                .put(AetherIITextureSlots.PETAL_BOTTOM, TextureMapping.getBlockTexture(block, "_petal_bottom"))
                .put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem()));
    }

    public static TextureMapping lilichime(Block block) {
        return lilichime(block, false);
    }

    public static TextureMapping pottedLilichime(Block block) {
        return lilichime(block, true);
    }

    public static TextureMapping lilichime(Block block, boolean potted) {
        return new TextureMapping()
            .put(TextureSlot.STEM, TextureMapping.getBlockTexture(block, potted ? "_stem_potted" : "_stem"))
            .put(AetherIITextureSlots.PETALS, TextureMapping.getBlockTexture(block, "_petals"))
            .put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem()));
    }

    public static TextureMapping lockedBlockInventory(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.FACE, TextureMapping.getBlockTexture(block))
                .put(AetherIITextureSlots.OVERLAY, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/dungeon_lock")));
    }

    public static TextureMapping barsWithDifferentEdge(Block block, Block edgeBlock, String suffix) {
        return new TextureMapping()
                .put(TextureSlot.BARS, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.EDGE, TextureMapping.getBlockTexture(edgeBlock, suffix));
    }

    public static TextureMapping campfire(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.LOG, TextureMapping.getBlockTexture(block, "_log_lit"))
                .put(AetherIITextureSlots.STONE, TextureMapping.getBlockTexture(block, "_stone"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_log"));
    }

    public static TextureMapping campfireOff(Block block) {
        return new TextureMapping()
                .put(AetherIITextureSlots.LOG, TextureMapping.getBlockTexture(block, "_log"))
                .put(AetherIITextureSlots.STONE, TextureMapping.getBlockTexture(block, "_stone"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_log"));
    }

    public static TextureMapping particle(TextureMapping textureMapping) {
        return textureMapping.copyForced(TextureSlot.ALL, TextureSlot.PARTICLE);
    }

    public static TextureMapping amberHourglass(Block block) {
        return amberHourglass(block, "");
    }

    public static TextureMapping amberHourglass(Block block, String suffix) {
        return new TextureMapping()
                .put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block, "_cross"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top" + suffix))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.INNER_TOP, TextureMapping.getBlockTexture(block, "_inner_top"))
                .put(AetherIITextureSlots.INNER_BOTTOM, TextureMapping.getBlockTexture(block, "_inner_bottom"));
    }

    public static TextureMapping altar(Block block) {
        return altar(block, "");
    }

    public static TextureMapping altar(Block block, String suffix) {
        return new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top" + suffix))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom" + suffix))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side" + suffix))
                .put(AetherIITextureSlots.BASE_TOP, TextureMapping.getBlockTexture(block, "_base_top" + suffix))
                .put(AetherIITextureSlots.BASE_BOTTOM, TextureMapping.getBlockTexture(block, "_base_bottom" + suffix));
    }

    public static TextureMapping arkeniumForge(Block block) {
        return arkeniumForge(block, "");
    }

    public static TextureMapping arkeniumForge(Block block, String suffix) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side" + suffix))
                .put(AetherIITextureSlots.BASE_TOP, TextureMapping.getBlockTexture(block, "_top" + suffix))
                .put(AetherIITextureSlots.ANVIL_FRONT, TextureMapping.getBlockTexture(block, "_anvil_front" + suffix))
                .put(AetherIITextureSlots.ANVIL_SIDE, TextureMapping.getBlockTexture(block, "_anvil_side" + suffix))
                .put(AetherIITextureSlots.ANVIL_BOTTOM, TextureMapping.getBlockTexture(block, "_anvil_bottom" + suffix));
    }

    public static TextureMapping artisansBench(Block block) {
        return block(block)
                .put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(block, "_inside"))
                .put(AetherIITextureSlots.SAW, TextureMapping.getBlockTexture(block, "_saw"));
    }
}