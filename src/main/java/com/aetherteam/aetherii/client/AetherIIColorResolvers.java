package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;
import java.util.List;

public class AetherIIColorResolvers {
    public static final int AETHER_GRASS_COLOR = AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get().defaultValue();
    public static final ColorResolver GRASS_COLORS = (biome, x, z) -> biome.getAttributes().applyModifier(AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get(), AETHER_GRASS_COLOR);

    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(GRASS_COLORS);
    }

    public static void registerBlockColor(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(irradiatedLeaves()),
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());

        event.register(List.of(
                grassBlockColor(0, AETHER_GRASS_COLOR, 5.0F, 6.0F),
                grassBlockColor(1, AETHER_GRASS_COLOR, 5.0F, 6.0F),
                grassBlockColor(2, AETHER_GRASS_COLOR, 5.0F, 6.0F)
        ), AetherIIBlocks.AETHER_GRASS_BLOCK.get());

        event.register(List.of(
                grassColor(0, AETHER_GRASS_COLOR, 5.0F, 6.0F),
                grassColor(1, AETHER_GRASS_COLOR, 5.0F, 6.0F),
                grassColor(2, AETHER_GRASS_COLOR, 5.0F, 6.0F)
        ), AetherIIBlocks.SHORT_AETHER_GRASS.get(), AetherIIBlocks.MEDIUM_AETHER_GRASS.get(), AetherIIBlocks.TALL_AETHER_GRASS.get());

        event.register(List.of(
                fernColor(AETHER_GRASS_COLOR)
        ), AetherIIBlocks.AETHER_FERN.get(), AetherIIBlocks.POTTED_AETHER_FERN.get());
    }

    public static BlockTintSource irradiatedLeaves() {
        return new BlockTintSource() {
            private static final int BOTTOM_COLOR = 0xFFF68D;
            private static final int TOP_COLOR = 0xFFFFFF;

            public int color(BlockState state) {
                float shade = state.getValue(IrradiatedLeavesBlock.SHADE);
                float shadeMax = 7.0F;

                Color bottom = new Color(BOTTOM_COLOR);
                Color top = new Color(TOP_COLOR);

                int resultRed = bottom.getRed() + (int) ((shade / shadeMax) * (top.getRed() - bottom.getRed()));
                int resultGreen = bottom.getGreen() + (int) ((shade / shadeMax) * (top.getGreen() - bottom.getGreen()));
                int resultBlue = bottom.getBlue() + (int) ((shade / shadeMax) * (top.getBlue() - bottom.getBlue()));

                return new Color(resultRed, resultGreen, resultBlue).getRGB();
            }

            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return this.color(state);
            }
        };
    }

    public static BlockTintSource grassBlockColor(int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
        return new BlockTintSource() {
            public int color(BlockState state) {
                return createTriTintGrassColor(tintIndex, defaultColor, darkSaturationOffset, lightSaturationOffset);
            }

            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return createTriTintGrassColor(tintIndex, getAverageColor(level, pos, GRASS_COLORS, defaultColor), darkSaturationOffset, lightSaturationOffset);
            }

            @Override
            public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return -1;
            }
        };
    }

    public static BlockTintSource grassColor(int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
        return new BlockTintSource() {
            public int color(BlockState state) {
                return createTriTintGrassColor(tintIndex, defaultColor, darkSaturationOffset, lightSaturationOffset);
            }

            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return createTriTintGrassColor(tintIndex, getAverageColor(level, pos, GRASS_COLORS, defaultColor), darkSaturationOffset, lightSaturationOffset);
            }

            @Override
            public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return BlockTintSource.super.colorAsTerrainParticle(state, level, pos);
            }
        };
    }

    public static BlockTintSource fernColor(int defaultColor) {
        return new BlockTintSource() {
            public int color(BlockState state) {
                return defaultColor;
            }

            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return getAverageColor(level, pos, GRASS_COLORS, defaultColor);
            }
        };
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

    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver, int defaultColor) {
        if (level != null && blockPos != null) {
            try {
                return level.getBlockTint(blockPos, colorResolver);
            } catch (Exception e) {
                AetherII.LOGGER.error("Failed to get Aether Grass color, this is not intended! Ignoring exception and using default color", e);
            }
        }
        return defaultColor;
    }
}
