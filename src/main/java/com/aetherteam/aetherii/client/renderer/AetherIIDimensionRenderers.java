package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;

import javax.annotation.Nullable;

public class AetherIIDimensionRenderers {
    public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE.location(), new HolyIslesEffects());
    }

    public static float timeOfDay(long dayTime) {
        double d0 = Mth.frac((double) dayTime / (double) 24000.0F - (double) 0.25F);
        double d1 = (double) 0.5F - Math.cos(d0 * Math.PI) / (double) 2.0F;
        return (float) (d0 * (double) 2.0F + d1) / 3.0F;
    }

    public static boolean isSunriseOrSunset(float timeOfDay) {
        float f = Mth.cos(timeOfDay * Mth.TWO_PI);
        return f >= -0.4F && f <= 0.4F;
    }

    public static int getSunriseOrSunsetColor(float timeOfDay) {
        float f = Mth.cos(timeOfDay * Mth.TWO_PI);
        float f1 = f / 0.4F * 0.5F + 0.5F;
        float f2 = Mth.square(1.0F - (1.0F - Mth.sin(f1 * Mth.PI)) * 0.99F);
        return com.aetherteam.aetherii.util.ARGB.colorFromFloat(f2, f1 * 0.3F + 0.65F, f1 * f1 * 0.7F + 0.25F, 0.4F);
    }

    public static class HolyIslesEffects extends DimensionSpecialEffects {
        public HolyIslesEffects() {
            super(192.0F, true, SkyType.NORMAL, false, false);
        }

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
            return fogColor.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
        }

        @Override
        public boolean isFoggyAt(int x, int z) {
            return false;
        }

        @Nullable
        @Override
        public float[] getSunriseColor(float timeOfDay, float partialTick) {
            if (!isSunriseOrSunset(timeOfDay)) {
                return null;
            }
            int color = getSunriseOrSunsetColor(timeOfDay);
            return new float[]{
                    com.aetherteam.aetherii.util.ARGB.redFloat(color),
                    com.aetherteam.aetherii.util.ARGB.greenFloat(color),
                    com.aetherteam.aetherii.util.ARGB.blueFloat(color),
                    com.aetherteam.aetherii.util.ARGB.alphaFloat(color)
            };
        }
    }
}
