package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class LevelClientListener {

    /**
     * @see LevelClientHooks#renderDungeonBlockOverlays(RenderLevelStageEvent.Stage, PoseStack, Camera, Frustum, Minecraft)
     */
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Frustum frustum = event.getFrustum();
        Minecraft minecraft = Minecraft.getInstance();
        LevelClientHooks.renderDungeonBlockOverlays(stage, poseStack, camera, frustum, minecraft);
    }
}