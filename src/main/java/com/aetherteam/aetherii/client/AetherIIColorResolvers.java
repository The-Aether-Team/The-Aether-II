package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

public class AetherIIColorResolvers {
    public static final int AETHER_GRASS_COLOR = 0xb5ffd0;
    public static final int AETHER_TALL_GRASS_COLOR = 0xb5ffd0;

    public static final ColorResolver GRASS_COLORS = BiomeHooks::getColor;

    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(GRASS_COLORS);
    }

    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            float shade = state.getValue(IrradiatedLeavesBlock.SHADE);
            float shadeMax = 7.0F;

            Color bottom = new Color(0xFFF68D);
            Color top = new Color(0xFFFFFF);

            int resultRed = bottom.getRed() + (int) ((shade / shadeMax) * (top.getRed() - bottom.getRed()));
            int resultGreen = bottom.getGreen() + (int) ((shade / shadeMax) * (top.getGreen() - bottom.getGreen()));
            int resultBlue = bottom.getBlue() + (int) ((shade / shadeMax) * (top.getBlue() - bottom.getBlue()));

            return new Color(resultRed, resultGreen, resultBlue).getRGB();
        },
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_GRASS_COLOR, 5.0F, 6.0F)),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_TALL_GRASS_COLOR, 2.0F, 10.0F)),
                AetherIIBlocks.AETHER_SHORT_GRASS.get(), AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), AetherIIBlocks.AETHER_LONG_GRASS.get());
        event.register(((state, level, pos, tintIndex) ->  level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_TALL_GRASS_COLOR),
                AetherIIBlocks.HIGHLAND_FERN.get(), AetherIIBlocks.POTTED_HIGHLAND_FERN.get());
    }

    public static int createTriTintGrassColor(int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
        Color midColor = new Color(defaultColor);

        float[] hsb = Color.RGBtoHSB(midColor.getRed(), midColor.getGreen(), midColor.getBlue(), null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];

        Color darkColor = Color.getHSBColor(hue, saturation + (darkSaturationOffset / 100.0F), brightness);
        Color lightColor = Color.getHSBColor(hue, saturation - (lightSaturationOffset / 100.0F), brightness);

        switch (tintIndex) {
            case 0 -> {
                return darkColor.getRGB();
            }
            case 1 -> {
                return midColor.getRGB();
            }
            case 2 -> {
                return lightColor.getRGB();
            }
            default -> {
                return defaultColor;
            }
        }
    }

    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver) {
        if (level != null && blockPos != null) {
            try {
                return level.getBlockTint(blockPos, colorResolver);
            } catch (Exception e) {
                AetherII.LOGGER.error("Failed to get Aether Grass color, this is not intended! Ignoring exception and using default color", e);
            }
        }
        return AETHER_GRASS_COLOR;
    }
}
