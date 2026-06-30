package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

import java.awt.Color;

public class AetherIIColorResolvers {
    public static final int AETHER_GRASS_COLOR = 0xb5ffd0;
    public static final int AETHER_TALL_GRASS_COLOR = 0xb5ffd0;

    public static final ColorResolver GRASS_COLORS = BiomeHooks::getColor;

    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> irradiatedLeavesColor(state),
                AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(),
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());

        event.register((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, getAverageGrassColor(level, pos, AETHER_GRASS_COLOR), 5.0F, 6.0F),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get());

        event.register((state, level, pos, tintIndex) -> createTriTintGrassColor(tintIndex, getAverageGrassColor(level, pos, AETHER_TALL_GRASS_COLOR), 5.0F, 6.0F),
                AetherIIBlocks.SHORT_AETHER_GRASS.get(),
                AetherIIBlocks.MEDIUM_AETHER_GRASS.get(),
                AetherIIBlocks.TALL_AETHER_GRASS.get());

        event.register((state, level, pos, tintIndex) -> getAverageGrassColor(level, pos, AETHER_TALL_GRASS_COLOR),
                AetherIIBlocks.AETHER_FERN.get(),
                AetherIIBlocks.POTTED_AETHER_FERN.get());
    }

    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> createTriTintGrassColor(tintIndex, AETHER_GRASS_COLOR, 5.0F, 6.0F),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get().asItem(),
                AetherIIBlocks.SHORT_AETHER_GRASS.get().asItem(),
                AetherIIBlocks.MEDIUM_AETHER_GRASS.get().asItem(),
                AetherIIBlocks.TALL_AETHER_GRASS.get().asItem());

        event.register((stack, tintIndex) -> AETHER_TALL_GRASS_COLOR,
                AetherIIBlocks.AETHER_FERN.get().asItem());

        event.register((stack, tintIndex) -> -1,
                AetherIIItems.FLYING_COW_SPAWN_EGG.get(),
                AetherIIItems.SHEEPUFF_SPAWN_EGG.get(),
                AetherIIItems.PHYG_SPAWN_EGG.get(),
                AetherIIItems.AERBUNNY_SPAWN_EGG.get(),
                AetherIIItems.AERWHALE_SPAWN_EGG.get(),
                AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get(),
                AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get(),
                AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get(),
                AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get(),
                AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get(),
                AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get(),
                AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get(),
                AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get(),
                AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get(),
                AetherIIItems.MOA_SPAWN_EGG.get(),
                AetherIIItems.PRISMALLARD_SPAWN_EGG.get(),
                AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get(),
                AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get(),
                AetherIIItems.CARRION_SPROUT_SPAWN_EGG.get(),
                AetherIIItems.GLITTERWING_SPAWN_EGG.get(),
                AetherIIItems.SHROUDWING_SPAWN_EGG.get(),
                AetherIIItems.ZEPHYR_SPAWN_EGG.get(),
                AetherIIItems.TEMPEST_SPAWN_EGG.get(),
                AetherIIItems.COCKATRICE_SPAWN_EGG.get(),
                AetherIIItems.BLUE_SWET_SPAWN_EGG.get(),
                AetherIIItems.GOLDEN_SWET_SPAWN_EGG.get(),
                AetherIIItems.SKEPHID_SPAWN_EGG.get(),
                AetherIIItems.ARKENIUM_TALUTON_SPAWN_EGG.get(),
                AetherIIItems.GRAVITITE_TALUTON_SPAWN_EGG.get(),
                AetherIIItems.DETONATION_SENTRY_SPAWN_EGG.get(),
                AetherIIItems.SENTRY_GOLEM_SPAWN_EGG.get(),
                AetherIIItems.SENTRY_CRATE_MIMIC_SPAWN_EGG.get(),
                AetherIIItems.SLIDER_SPAWN_EGG.get());
    }

    public static int createTriTintGrassColor(int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
        Color midColor = new Color(sanitizeGrassColor(defaultColor, AETHER_GRASS_COLOR));
        float[] hsb = Color.RGBtoHSB(midColor.getRed(), midColor.getGreen(), midColor.getBlue(), null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];

        Color darkColor = Color.getHSBColor(hue, saturation + (darkSaturationOffset / 100.0F), brightness);
        Color lightColor = Color.getHSBColor(hue, saturation - (lightSaturationOffset / 100.0F), brightness);
        return switch (tintIndex) {
            case 0 -> darkColor.getRGB();
            case 1 -> midColor.getRGB();
            case 2 -> lightColor.getRGB();
            default -> defaultColor;
        };
    }

    private static int irradiatedLeavesColor(BlockState state) {
        int bottomColor = 0xFFF68D;
        int topColor = 0xFFFFFF;
        float shade = state.getValue(IrradiatedLeavesBlock.SHADE);
        float shadeMax = 7.0F;
        Color bottom = new Color(bottomColor);
        Color top = new Color(topColor);
        int red = bottom.getRed() + (int) ((shade / shadeMax) * (top.getRed() - bottom.getRed()));
        int green = bottom.getGreen() + (int) ((shade / shadeMax) * (top.getGreen() - bottom.getGreen()));
        int blue = bottom.getBlue() + (int) ((shade / shadeMax) * (top.getBlue() - bottom.getBlue()));
        return new Color(red, green, blue).getRGB();
    }

    private static int getAverageGrassColor(BlockAndTintGetter level, BlockPos pos, int defaultColor) {
        if (level != null && pos != null) {
            try {
                if (level instanceof RenderChunkRegion renderChunkRegion) {
                    return sanitizeGrassColor(BiomeHooks.getColor(renderChunkRegion.level.getBiome(pos).value(), pos.getX(), pos.getZ()), defaultColor);
                }
                if (level instanceof Level world) {
                    return sanitizeGrassColor(BiomeHooks.getColor(world.getBiome(pos).value(), pos.getX(), pos.getZ()), defaultColor);
                }
                return sanitizeGrassColor(level.getBlockTint(pos, GRASS_COLORS), defaultColor);
            } catch (Exception exception) {
                AetherII.LOGGER.error("Failed to get Aether Grass color, using default color", exception);
            }
        }
        return defaultColor;
    }

    private static int sanitizeGrassColor(int color, int defaultColor) {
        int rgb = color & 0x00FFFFFF;
        return rgb == 0 ? defaultColor : rgb;
    }
}
