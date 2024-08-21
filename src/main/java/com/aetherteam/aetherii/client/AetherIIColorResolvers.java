package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

public class AetherIIColorResolvers {
    private static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    private static final int AETHER_TALL_GRASS_COLOR = 0xB1FFCB;

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
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(level, pos, tintIndex, level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_GRASS_COLOR, 5.0F, 6.0F)),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(level, pos, tintIndex, level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_TALL_GRASS_COLOR, 2.0F, 10.0F)),
                AetherIIBlocks.AETHER_SHORT_GRASS.get(), AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), AetherIIBlocks.AETHER_LONG_GRASS.get());
        event.register(((state, level, pos, tintIndex) ->  level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_TALL_GRASS_COLOR),
                AetherIIBlocks.HIGHLAND_FERN.get());
    }

    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider == 0 ? -1 : DyedItemColor.getOrDefault(color, -3150087),
                AetherIIItems.TAEGORE_HIDE_HELMET.get(), AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get(), AetherIIItems.TAEGORE_HIDE_LEGGINGS.get(), AetherIIItems.TAEGORE_HIDE_BOOTS.get(), AetherIIItems.TAEGORE_HIDE_GLOVES.get());
        event.register((color, itemProvider) -> itemProvider == 0 ? -1 : DyedItemColor.getOrDefault(color, -10380096),
                AetherIIItems.BURRUKAI_PELT_HELMET.get(), AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get(), AetherIIItems.BURRUKAI_PELT_LEGGINGS.get(), AetherIIItems.BURRUKAI_PELT_BOOTS.get(), AetherIIItems.BURRUKAI_PELT_GLOVES.get());
        event.register((color, itemProvider) -> {
            BlockState blockstate = ((BlockItem) color.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, itemProvider);
        }, AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_SHORT_GRASS.get(), AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), AetherIIBlocks.AETHER_LONG_GRASS.get(), AetherIIBlocks.HIGHLAND_FERN.get());
    }

    private static int createTriTintGrassColor(BlockAndTintGetter level, BlockPos pos, int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) {
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
}
