package com.aetherteam.aetherii.client.event.hooks.attachment;

import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;

public class DimensionRenderEffectHooks {
    @Nullable
    public static Triple<Float, Float, Float> renderFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
                ClientLevel.ClientLevelData worldInfo = clientLevel.getLevelData();
                FogType type = camera.getFluidInCamera();

                double f = (camera.getPosition().y() - 64) * worldInfo.getClearColorScale();
                if (f < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
                    if (f < 0.0F) {
                        f = 0.0F;
                    }
                    f *= f;
                    red *= (float) Math.clamp(f, 0.2F, 1.0F);
                    green *= (float) Math.clamp(f, 0.2F, 1.0F);
                    blue *= (float) Math.clamp(f * 1.25F, 0.2F * 1.25F, 1.0F);
                }

                double d0 = (camera.getPosition().y() - (double) clientLevel.getMinBuildHeight()) * worldInfo.getClearColorScale();
                if (d0 < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
                    if (d0 < 0.0) {
                        d0 = 0.0;
                    }
                    d0 *= d0;
                    if (d0 != 0.0) {
                        red /= (float) d0;
                        green /= (float) d0;
                        blue /= (float) d0;
                    }
                }

                return Triple.of(red, green, blue);
            }
        }
        return null;
    }

    @Nullable
    public static Triple<Float, Float, Float> adjustWeatherFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
                FogType fluidState = camera.getFluidInCamera();
                if (fluidState == FogType.NONE) {
                    Vec3 defaultSky = Vec3.fromRGB24(clientLevel.getBiome(camera.getBlockPosition()).value().getModifiedSpecialEffects().getFogColor());
                    if (clientLevel.rainLevel > 0.0) { // Check for rain.
                        float f14 = 1.0F + clientLevel.rainLevel * 0.8F;
                        float f17 = 1.0F + clientLevel.rainLevel * 0.56F;
                        red *= f14;
                        green *= f14;
                        blue *= f17;
                    }
                    if (clientLevel.thunderLevel > 0.0) { // Check for thunder.
                        float f18 = 1.0F + clientLevel.thunderLevel * 0.66F;
                        float f19 = 1.0F + clientLevel.thunderLevel * 0.76F;
                        red *= f18;
                        green *= f18;
                        blue *= f19;
                    }
                    red = (float) Math.min(red, defaultSky.x());
                    green = (float) Math.min(green, defaultSky.y());
                    blue = (float) Math.min(blue, defaultSky.z());
                    return Triple.of(red, green, blue);
                }
            }
        }
        return null;
    }
}
