package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
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

    public int getCloudColor(LevelRenderState levelRenderState) { //this might need to be handled differently as it seems to be overwritten
        int i = -1;
        float f = levelRenderState.skyRenderState.rainBrightness;
        if (f > 0.0F) {
            int j = ARGB.scaleRGB(ARGB.greyscale(i), 0.6F);
            i = ARGB.srgbLerp(f * 0.5F, i, j); //reduced darkening
        }

        float f3 = levelRenderState.getRenderDataOrDefault(AetherIIDimensionRenderers.DATA_TIME_OF_DAY_KEY, 0.0F);
        float f1 = Mth.cos(f3 * 6.2831855F) * 2.0F + 0.5F;
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        i = ARGB.multiply(i, ARGB.colorFromFloat(1.0F, f1 * 0.9F + 0.1F, f1 * 0.9F + 0.1F, f1 * 0.85F + 0.15F));
        float f2 = levelRenderState.getRenderDataOrDefault(AetherIIDimensionRenderers.DATA_THUNDER_KEY, 0.0F);
        if (f2 > 0.0F) {
            int k = ARGB.scaleRGB(ARGB.greyscale(i), 0.2F);
            i = ARGB.srgbLerp(f2 * 0.5F, i, k); //reduced darkening
        }

        return i;
    }
}