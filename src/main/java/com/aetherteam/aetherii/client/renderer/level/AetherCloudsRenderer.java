package com.aetherteam.aetherii.client.renderer.level;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.CustomCloudsRenderer;
import org.joml.Matrix4f;

public class AetherCloudsRenderer implements CustomCloudsRenderer {

    @Override
    public boolean renderClouds(LevelRenderState levelRenderState, Vec3 camPos, CloudStatus cloudStatus, int cloudColor, float cloudHeight, Matrix4f modelViewMatrix) {
        if (levelRenderState.customCloudsRenderer != null) {
            Minecraft.getInstance().levelRenderer.getCloudRenderer().render(cloudColor, cloudStatus, cloudHeight, camPos, levelRenderState.gameTime, DeltaTracker.ONE.getGameTimeDeltaPartialTick(false));
        }
        return true;
    }
}