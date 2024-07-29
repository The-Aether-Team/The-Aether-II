package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
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
//        event.register((state, level, pos, tintIndex) -> {
//            int shade = state.getValue(IrradiatedLeavesBlock.SHADE);
//            int shadeMax = 7;
//
//            Color bottom = new Color(0xEFE553);
//
//            Color top = new Color(0xF2EFE6);
//
//            int resultRed = bottom.getRed() + (shade / shadeMax) * (top.getRed() - top.getRed());
//            int resultGreen = bottom.getGreen() + (shade / shadeMax) * (top.getGreen() - top.getGreen());
//            int resultBlue = bottom.getBlue() + (shade / shadeMax) * (top.getBlue() - top.getBlue());
//
//            AetherII.LOGGER.info(String.valueOf(new Color(resultRed, resultGreen, resultBlue).getRGB()));
//
//            return new Color(resultRed, resultGreen, resultBlue).getRGB();
//        }, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(level, pos, tintIndex, level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_GRASS_COLOR, 5.0F, 6.0F)),
                AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        event.register(((state, level, pos, tintIndex) -> createTriTintGrassColor(level, pos, tintIndex, level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_TALL_GRASS_COLOR, 2.0F, 10.0F)),
                AetherIIBlocks.AETHER_SHORT_GRASS.get(), AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), AetherIIBlocks.AETHER_LONG_GRASS.get());
    }

    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider == 0 ? -1 : DyedItemColor.getOrDefault(color, -3150087),
                AetherIIItems.TAEGORE_HIDE_HELMET.get(), AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get(), AetherIIItems.TAEGORE_HIDE_LEGGINGS.get(), AetherIIItems.TAEGORE_HIDE_BOOTS.get(), AetherIIItems.TAEGORE_HIDE_GLOVES.get());
        event.register((color, itemProvider) -> itemProvider == 0 ? -1 : DyedItemColor.getOrDefault(color, -10380096),
                AetherIIItems.BURRUKAI_PELT_HELMET.get(), AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get(), AetherIIItems.BURRUKAI_PELT_LEGGINGS.get(), AetherIIItems.BURRUKAI_PELT_BOOTS.get(), AetherIIItems.BURRUKAI_PELT_GLOVES.get());
        event.register((color, itemProvider) -> {
            BlockState blockstate = ((BlockItem) color.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, itemProvider);
        }, AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_SHORT_GRASS.get(), AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), AetherIIBlocks.AETHER_LONG_GRASS.get());
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
