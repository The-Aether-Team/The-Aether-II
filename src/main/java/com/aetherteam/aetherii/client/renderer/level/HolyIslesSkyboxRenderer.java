package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.client.AetherIIRenderPipelines;
import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.CustomSkyboxRenderer;
import org.joml.*;

import java.lang.Math;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class HolyIslesSkyboxRenderer implements CustomSkyboxRenderer {
    private static final int CLOUD_COVER_BUFFER_VERTICES = 42;
    private final GpuBuffer cloudCoverBuffer;

    public HolyIslesSkyboxRenderer() {
        this.cloudCoverBuffer = this.buildCloudCover();
    }

    private GpuBuffer buildCloudCover() {
        GpuBuffer buffer;
        try (ByteBufferBuilder builder = ByteBufferBuilder.exactlySized(CLOUD_COVER_BUFFER_VERTICES * DefaultVertexFormat.POSITION_COLOR.getVertexSize())) {
            BufferBuilder bufferBuilder = new BufferBuilder(builder, VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            int centerColor = ARGB.white(1.0F);
            int edgeColor = ARGB.white(0.0F);

            bufferBuilder.addVertex(0.0F, -16.0F, 0.0F).setColor(centerColor);
            for (int i = -180; i <= 180; i += 9) {
                float angle = i * Mth.DEG_TO_RAD;
                bufferBuilder.addVertex(Math.signum(-16.0F) * 512.0F * Mth.cos(angle), -16.0F, 512.0F * Mth.sin(angle)).setColor(edgeColor);
            }

            try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                buffer = RenderSystem.getDevice().createBuffer(() -> "Cloud cover vertex buffer", 32, mesh.vertexBuffer());
            }
        }
        return buffer;
    }

    @Override
    public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4fc modelViewMatrix, Runnable setupFog) {
        SkyRenderer skyRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getSkyRenderer();
        setupFog.run();
        PoseStack poseStack = new PoseStack();
        skyRenderer.renderSkyDisc(skyRenderState.skyColor);
        skyRenderer.renderSunriseAndSunset(poseStack, skyRenderState.sunAngle, skyRenderState.sunriseAndSunsetColor);
        skyRenderer.renderSunMoonAndStars(poseStack, skyRenderState.sunAngle,
                skyRenderState.moonAngle,
                skyRenderState.starAngle,
                skyRenderState.moonPhase,
                skyRenderState.rainBrightness,
                skyRenderState.starBrightness);
        this.renderCloudCoverDisc(levelRenderState, poseStack);
        return true;
    }

    public void renderCloudCoverDisc(LevelRenderState levelRenderState, PoseStack poseStack) {
        int color = levelRenderState.getRenderDataOrDefault(AetherIIDimensionRenderers.DATA_CLOUD_COVER_COLOR_KEY, 0);
        Vector3f colorVec = ARGB.vector3fFromRGB24(color);

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
        Matrix4f pose = poseStack.last().pose();

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.mul(pose);

        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewStack, new Vector4f(colorVec.x(), colorVec.y(), colorVec.z(), 1.0F), new Vector3f(), new Matrix4f());
        GpuTextureView colorTexture = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
        GpuTextureView depthTexture = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();

        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Cloud cover", colorTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
            renderPass.setPipeline(AetherIIRenderPipelines.CLOUD_COVER_SHADER);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", dynamicTransforms);
            renderPass.setVertexBuffer(0, this.cloudCoverBuffer);
            renderPass.draw(0, CLOUD_COVER_BUFFER_VERTICES);
        }
        modelViewStack.popMatrix();

        poseStack.popPose();
    }
}