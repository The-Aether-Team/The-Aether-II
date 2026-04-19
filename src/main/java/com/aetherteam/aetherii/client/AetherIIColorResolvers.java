package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;
import java.util.List;
import java.util.Set;

public class AetherIIColorResolvers {
    public static final int AETHER_GRASS_COLOR = 0xb5ffd0;
    public static final int AETHER_TALL_GRASS_COLOR = 0xb5ffd0;

    public static final ColorResolver GRASS_COLORS = BiomeHooks::getColor;

    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(GRASS_COLORS);
    }

    public static void registerBlockColor(RegisterColorHandlersEvent.BlockTintSources event) {
/*
        event.register((state, level, pos, tintIndex) -> { //todo
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

 */

        event.register(List.of(BlockTintSources.constant(0), BlockTintSources.constant(1), BlockTintSources.constant(2), createTriTintGrassColor(event.getBlockColors(), AETHER_GRASS_COLOR, 5.0F, 6.0F)), AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        //event.register(List.of(createTriTintGrassColor(1, AETHER_GRASS_COLOR, 5.0F, 6.0F)), AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        //event.register(List.of(createTriTintGrassColor(2, AETHER_GRASS_COLOR, 5.0F, 6.0F)), AetherIIBlocks.AETHER_GRASS_BLOCK.get());

        /*
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_GRASS_COLOR, 5.0F, 6.0F)),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_TALL_GRASS_COLOR, 2.0F, 10.0F)),
                AetherIIBlocks.SHORT_AETHER_GRASS.get(), AetherIIBlocks.MEDIUM_AETHER_GRASS.get(), AetherIIBlocks.TALL_AETHER_GRASS.get());
        event.register(((state, level, pos, tintIndex) ->  level != null && pos != null ? getAverageColor(level, pos, GRASS_COLORS) : AETHER_TALL_GRASS_COLOR),

               AetherIIBlocks.AETHER_FERN.get(), AetherIIBlocks.POTTED_AETHER_FERN.get());

         */
    }



    public static BlockTintSource createTriTintGrassColor(BlockColors blockColors, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
        return new BlockTintSource() {
            public int color(BlockState state) {
                Color midColor = new Color(defaultColor);

                float[] hsb = Color.RGBtoHSB(midColor.getRed(), midColor.getGreen(), midColor.getBlue(), null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                Color darkColor = Color.getHSBColor(hue, saturation + (darkSaturationOffset / 100.0F), brightness);
                Color lightColor = Color.getHSBColor(hue, saturation - (lightSaturationOffset / 100.0F), brightness);

                if (blockColors.getColoringProperties(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) == BlockTintSources.constant(0)) {
                    return darkColor.getRGB();
                }
                if (blockColors.getColoringProperties(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) == BlockTintSources.constant(1)) {
                    return midColor.getRGB();
                }
                if (blockColors.getColoringProperties(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) == BlockTintSources.constant(2)) {
                    return lightColor.getRGB();
                }

                /*
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

                 */
                return defaultColor;
            }

            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return getAverageColor(level, pos, GRASS_COLORS);
            }
        };
    }

    /*
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

     */

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
