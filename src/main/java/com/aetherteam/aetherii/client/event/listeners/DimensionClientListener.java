package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class DimensionClientListener {
    private static Float modifiedNearDistance = null;
    private static Float modifiedFarDistance =  null;

    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        FogRenderer.FogMode fogMode = event.getMode();
        float nearDistance = event.getNearPlaneDistance();
        float farDistance = event.getFarPlaneDistance();

        if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
            Holder<Biome> biome = clientLevel.getBiome(camera.getBlockPosition());
            if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
                FogType fluidState = camera.getFluidInCamera();
                if (fogMode == FogRenderer.FogMode.FOG_TERRAIN && fluidState == FogType.NONE) {
                    if (modifiedNearDistance == null) {
                        modifiedNearDistance = nearDistance;
                    }
                    if (modifiedFarDistance == null) {
                        modifiedFarDistance = farDistance;
                    }

                    float nearDistanceGoal = farDistance / 2.0F;
                    float farDistanceGoal = farDistance;

                    if (biome.is(AetherIITags.Biomes.ARCTIC)) {
                        nearDistanceGoal = farDistance / 4.0F;
                    } else if (biome.is(AetherIITags.Biomes.MAGNETIC_FOG)) {
                        nearDistanceGoal = farDistance / 16.0F;
                    } else if (biome.is(AetherIITags.Biomes.IRRADIATED)) {
                        nearDistanceGoal = farDistance / 16.0F;
                        farDistanceGoal = farDistance / 2.0F;
                    }

                    if (clientLevel.isRaining()) {
                        nearDistanceGoal /= 1.5F;
                    }
                    if (clientLevel.isThundering()) {
                        nearDistanceGoal /= 1.5F;
                    }

                    modifiedNearDistance = Mth.lerp(0.05F, modifiedNearDistance, nearDistanceGoal);
                    modifiedFarDistance = Mth.lerp(0.05F, modifiedFarDistance, farDistanceGoal);

                    if (!event.isCanceled()) {
                        event.setNearPlaneDistance(modifiedNearDistance);
                        event.setFarPlaneDistance(modifiedFarDistance);
                        event.setCanceled(true);
                    }
                }
            } else {
                modifiedNearDistance = null;
                modifiedFarDistance = null;
            }
        }
    }
}
