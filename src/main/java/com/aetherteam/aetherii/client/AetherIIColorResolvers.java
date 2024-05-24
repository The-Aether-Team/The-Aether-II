package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

public class AetherIIColorResolvers {
    private static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    private static final int ENCHANTED_GRASS_COLOR = 0xFCEA64;

    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register(((state, level, pos, tintIndex) -> {
            int hex = level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_GRASS_COLOR;
            Color midColor = new Color(hex);

            float[] hsb = Color.RGBtoHSB(midColor.getRed(), midColor.getGreen(), midColor.getBlue(), null);
            float hue = hsb[0];
            float saturation = hsb[1];
            float brightness = hsb[2];

            Color darkColor = Color.getHSBColor(hue, saturation + (5.0F / 100.0F), brightness);
            Color lightColor = Color.getHSBColor(hue, saturation - (8.0F / 100.0F), brightness);

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
                    return hex;
                }
            }
        }), AetherIIBlocks.AETHER_GRASS_BLOCK.get());
    }

    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> {
            BlockState blockstate = ((BlockItem)color.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, itemProvider);
        }, AetherIIBlocks.AETHER_GRASS_BLOCK.get());
    }
}
