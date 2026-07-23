package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;

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
                    farDistanceGoal = farDistance / 5.0F;
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
}