package com.aetherteam.aetherii.client.renderer.level;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.CustomCloudsRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class AetherCloudsRenderer implements CustomCloudsRenderer {

    @Override
    public boolean renderClouds(LevelRenderState levelRenderState, Vec3 camPos, CloudStatus cloudStatus, int cloudColor, float cloudHeight, int cloudRange, Matrix4fc modelViewMatrix) {
        if (levelRenderState.customCloudsRenderer != null) {
            Minecraft.getInstance().levelRenderer.getCloudRenderer().render(cloudColor, cloudStatus, cloudHeight, cloudRange, camPos, levelRenderState.gameTime, DeltaTracker.ONE.getGameTimeDeltaPartialTick(false));
        }
        return true;
    }
}