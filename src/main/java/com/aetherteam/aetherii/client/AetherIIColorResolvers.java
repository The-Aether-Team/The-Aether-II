package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

public class AetherIIColorResolvers {
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register(((state, level, pos, tintIndex) -> {
            int hex = level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();
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
}
