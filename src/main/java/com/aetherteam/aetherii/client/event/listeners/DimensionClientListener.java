package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesSkyboxRenderer;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector3fc;

public class DimensionClientListener {
    private static Float modifiedNearDistance = null;
    private static Float modifiedFarDistance = null;

    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        FogType fogMode = event.getType();
        float nearDistance = event.getNearPlaneDistance();
        float farDistance = event.getFarPlaneDistance();

        if (camera.entity().level() instanceof ClientLevel clientLevel && clientLevel.getBiome(camera.blockPosition()).is(AetherIITags.Biomes.THE_AETHER)) {
            Holder<Biome> biome = clientLevel.getBiome(camera.blockPosition());
            FogType fluidState = camera.getFluidInCamera();
            if (fogMode == FogType.ATMOSPHERIC && fluidState == FogType.NONE) {
                if (modifiedNearDistance == null) {
                    modifiedNearDistance = nearDistance;
                }
                if (modifiedFarDistance == null) {
                    modifiedFarDistance = farDistance;
                }

                float nearDistanceGoal = farDistance / 20.0F;
                float farDistanceGoal = farDistance;

                if (biome.is(AetherIITags.Biomes.ARCTIC)) {
                    nearDistanceGoal = farDistance / 20.0F;
                    farDistanceGoal = farDistance / 2.5F;
                } else if (biome.is(AetherIITags.Biomes.MAGNETIC_FOG)) {
                    nearDistanceGoal = farDistance / 80.0F;
                    farDistanceGoal = farDistance / 5.0F;
                } else if (biome.is(AetherIITags.Biomes.IRRADIATED)) {
                    nearDistanceGoal = farDistance / 60.0F;
                    farDistanceGoal = farDistance / 7.5F;
                }

                if (clientLevel.isRaining()) {
                    nearDistanceGoal = -15.0F;
                }
                if (clientLevel.isThundering()) {
                    nearDistanceGoal = -30.0F;
                }

                modifiedNearDistance = Mth.lerp(0.05F, modifiedNearDistance, nearDistanceGoal);
                modifiedFarDistance = Mth.lerp(0.05F, modifiedFarDistance, farDistanceGoal);

                event.setNearPlaneDistance(modifiedNearDistance);
                event.setFarPlaneDistance(modifiedFarDistance);
            } else {
                modifiedNearDistance = null;
                modifiedFarDistance = null;
            }
        }
    }

    public static void onFogColorComputed(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        DeltaTracker deltaTracker = DeltaTracker.ONE;
        float f = deltaTracker.getGameTimeDeltaPartialTick(false);

        if (camera.entity().level() instanceof ClientLevel clientLevel) {
            if (clientLevel.dimensionTypeRegistration().is(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE)) {
                int i = getBaseFogColor(clientLevel, camera, event.getRenderer().getMinecraft().options.getEffectiveRenderDistance(), f);
                i = adjustHeightBasedFogColors(clientLevel, camera, camera.getFluidInCamera(), i);
                event.setRed(ARGB.redFloat(i));
                event.setGreen(ARGB.greenFloat(i));
                event.setBlue(ARGB.blueFloat(i));
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment#getBaseColor(ClientLevel, Camera, int, float)}.
     */
    public static int getBaseFogColor(ClientLevel clientLevel, Camera camera, int effectiveRenderDistance, float partialTick) {
        float timeOfDay = timeOfDay(clientLevel.getDefaultClockTime());
        int i = camera.attributeProbe().getValue(EnvironmentAttributes.FOG_COLOR, partialTick);
        float f4;
        if (new HolyIslesSkyboxRenderer().isSunriseOrSunset(timeOfDay)) {
            if (effectiveRenderDistance >= 4) {
                float f = camera.attributeProbe().getValue(EnvironmentAttributes.SUN_ANGLE, partialTick) * 0.017453292F;
                f4 = Mth.sin(f) > 0.0F ? -1.0F : 1.0F;
                Vector3fc vector3fc = camera.isPanoramicMode() ? camera.panoramicForwards() : camera.forwardVector();
                float f2 = vector3fc.dot(f4, 0.0F, 0.0F);
                if (f2 > 0.0F) {
                    int j = new HolyIslesSkyboxRenderer().getSunriseOrSunsetColor(timeOfDay); //Modifies the sunrise/sunset fog colors to use the Aether's sunrise/sunset fog colors
                    float f3 = ARGB.alphaFloat(j);
                    if (f3 > 0.0F) {
                        i = ARGB.srgbLerp(f2 * f3, i, ARGB.opaque(j));
                    }
                }
            }
        }

        int skyColor = camera.attributeProbe().getValue(EnvironmentAttributes.SKY_COLOR, partialTick);
        skyColor  = applyWeatherDarken(skyColor , clientLevel.getRainLevel(partialTick), clientLevel.getThunderLevel(partialTick));
        f4 = Math.min(camera.attributeProbe().getValue(EnvironmentAttributes.SKY_FOG_END_DISTANCE, partialTick) / 16.0F, (float) effectiveRenderDistance);
        float f5 = Mth.clampedLerp(f4 / 32.0F, 0.25F, 1.0F);
        f5 = 1.0F - (float) Math.pow(f5, 0.25);
        return ARGB.srgbLerp(f5, i, skyColor);
    }

    private static int applyWeatherDarken(int skyColor, float rainLevel, float thunderLevel) {
        if (rainLevel > 0.0F) {
            float f = 1.0F - rainLevel * 0.5F;
            float f1 = 1.0F - rainLevel * 0.4F;
            skyColor = ARGB.scaleRGB(skyColor, f, f, f1);
        }

        if (thunderLevel > 0.0F) {
            skyColor = ARGB.scaleRGB(skyColor, 1.0F - thunderLevel * 0.5F);
        }

        return skyColor;
    }

    public static int adjustHeightBasedFogColors(ClientLevel clientLevel, Camera camera, FogType type, int i) {
        double f = (camera.position().y() - 64) * 0.03125F;
        if (f < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
            if (f < 0.0F) {
                f = 0.0F;
            }
            f *= f;

            int multiplier = ARGB.colorFromFloat(1.0F, (float) Math.clamp(f, 0.2F, 1.0F), (float) Math.clamp(f, 0.2F, 1.0F), (float) Math.clamp(f * 1.25F, 0.2F * 1.25F, 1.0F));
            i = ARGB.multiply(i, multiplier);
        }
        double d0 = (camera.position().y() - (double) clientLevel.getMinY()) * 0.03125F;
        if (d0 < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
            if (d0 < 0.0) {
                d0 = 0.0;
            }
            d0 *= d0;
            if (d0 != 0.0) {
                int multiplier = ARGB.colorFromFloat(1.0F, (float) d0, (float) d0, (float) d0);
                i = ARGB.multiply(i, multiplier);
            }
        }
        return i;
    }

    public static float timeOfDay(long dayTime) {
        double d0 = Mth.frac((double) dayTime / (double) 24000.0F - (double) 0.25F);
        double d1 = (double) 0.5F - Math.cos(d0 * Math.PI) / (double) 2.0F;
        return (float) (d0 * (double) 2.0F + d1) / 3.0F;
    }
}