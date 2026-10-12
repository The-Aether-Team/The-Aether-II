package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.client.AetherIIRenderPipelines;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.CloudRendererAccessor;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.CustomCloudsRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class HolyIslesCloudsRenderer implements CustomCloudsRenderer {
    @Override
    public boolean renderClouds(LevelRenderState levelRenderState, Vec3 camPos, CloudStatus cloudStatus, int cloudColor, float cloudHeight, int cloudRange, Matrix4fc modelViewMatrix) {
        if (!AetherIIConfig.CLIENT.disable_custom_clouds.get()) {
            CloudRendererAccessor cloudRendererAccessor = (CloudRendererAccessor) Minecraft.getInstance().levelRenderer.getCloudRenderer();
            long gameTime = levelRenderState.gameTime;
            float partialTicks = DeltaTracker.ONE.getGameTimeDeltaPartialTick(false);

            if (cloudRendererAccessor.aether_ii$getTexture() != null) {
                int radiusBlocks = cloudRange * 16;
                int radiusCells = Mth.ceil(radiusBlocks / 12.0F);
                int utbSize = CloudRendererAccessor.callGetSizeForCloudDistance(radiusCells);
                if (cloudRendererAccessor.aether_ii$getUtb() == null || cloudRendererAccessor.aether_ii$getUtb().currentBuffer().size() != utbSize) {
                    if (cloudRendererAccessor.aether_ii$getUtb() != null) {
                        cloudRendererAccessor.aether_ii$getUtb().close();
                    }
                    cloudRendererAccessor.aether_ii$setUtb(new MappableRingBuffer(() -> "Cloud UTB", 258, utbSize));
                }

                float relativeBottomY = (float) (cloudHeight - camPos.y);
                float relativeTopY = relativeBottomY + 4.0F;
                CloudRenderer.RelativeCameraPos relativeCameraPos;
                if (relativeTopY < 0.0F) {
                    relativeCameraPos = CloudRenderer.RelativeCameraPos.ABOVE_CLOUDS;
                } else if (relativeBottomY > 0.0F) {
                    relativeCameraPos = CloudRenderer.RelativeCameraPos.BELOW_CLOUDS;
                } else {
                    relativeCameraPos = CloudRenderer.RelativeCameraPos.INSIDE_CLOUDS;
                }

                float cloudOffset = (float) (gameTime % (cloudRendererAccessor.aether_ii$getTexture().width() * 400L)) + partialTicks;
                double cloudX = camPos.x + (cloudOffset * 0.030000001);
                double cloudZ = camPos.z + 3.96;
                double textureWidthBlocks = cloudRendererAccessor.aether_ii$getTexture().width() * 12.0;
                double textureHeightBlocks = cloudRendererAccessor.aether_ii$getTexture().height() * 12.0;
                cloudX -= Mth.floor(cloudX / textureWidthBlocks) * textureWidthBlocks;
                cloudZ -= Mth.floor(cloudZ / textureHeightBlocks) * textureHeightBlocks;
                int cellX = Mth.floor(cloudX / 12.0);
                int cellZ = Mth.floor(cloudZ / 12.0);
                float xInCell = (float) (cloudX - (cellX * 12.0F));
                float zInCell = (float) (cloudZ - (cellZ * 12.0F));
                boolean fancyClouds = cloudStatus == CloudStatus.FANCY;
                RenderPipeline renderPipeline = fancyClouds ? AetherIIRenderPipelines.CLOUDS_SHADER : AetherIIRenderPipelines.FLAT_CLOUDS_SHADER;
                if (cloudRendererAccessor.aether_ii$getNeedsRebuild() || cellX != cloudRendererAccessor.aether_ii$getPrevCellX() || cellZ != cloudRendererAccessor.aether_ii$getPrevCellZ() || relativeCameraPos != cloudRendererAccessor.aether_ii$getPrevRelativeCameraPos() || cloudStatus != cloudRendererAccessor.aether_ii$getPrevCloudStatus()) {
                    cloudRendererAccessor.aether_ii$setNeedsRebuild(false);
                    cloudRendererAccessor.aether_ii$setPrevCellX(cellX);
                    cloudRendererAccessor.aether_ii$setPrevCellZ(cellZ);
                    cloudRendererAccessor.aether_ii$setPrevRelativeCameraPos(relativeCameraPos);
                    cloudRendererAccessor.aether_ii$setPrevCloudStatus(cloudStatus);
                    cloudRendererAccessor.aether_ii$getUtb().rotate();

                    try (GpuBuffer.MappedView view = RenderSystem.getDevice().createCommandEncoder().mapBuffer(cloudRendererAccessor.aether_ii$getUtb().currentBuffer(), false, true)) {
                        cloudRendererAccessor.callBuildMesh(relativeCameraPos, view.data(), cellX, cellZ, fancyClouds, radiusCells);
                        cloudRendererAccessor.aether_ii$setQuadCount(view.data().position() / 3);
                    }
                }

                if (cloudRendererAccessor.aether_ii$getQuadCount() != 0) {
                    try (GpuBuffer.MappedView view = RenderSystem.getDevice().createCommandEncoder().mapBuffer(cloudRendererAccessor.aether_ii$getUbo().currentBuffer(), false, true)) {
                        Std140Builder.intoBuffer(view.data()).putVec4(ARGB.vector4fFromARGB32(cloudColor)).putVec3(-xInCell, relativeBottomY, -zInCell).putVec3(12.0F, 4.0F, 12.0F);
                    }

                    GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), new Vector3f(), new Matrix4f());
                    RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
                    RenderTarget cloudTarget = Minecraft.getInstance().levelRenderer.getCloudsTarget();
                    RenderSystem.AutoStorageIndexBuffer indices = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
                    GpuBuffer indexBuffer = indices.getBuffer(6 * cloudRendererAccessor.aether_ii$getQuadCount());
                    GpuTextureView colorTexture;
                    GpuTextureView depthTexture;
                    if (cloudTarget != null) {
                        colorTexture = cloudTarget.getColorTextureView();
                        depthTexture = cloudTarget.getDepthTextureView();
                    } else {
                        colorTexture = mainRenderTarget.getColorTextureView();
                        depthTexture = mainRenderTarget.getDepthTextureView();
                    }

                    try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Clouds", colorTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
                        renderPass.setPipeline(renderPipeline);
                        RenderSystem.bindDefaultUniforms(renderPass);
                        renderPass.setUniform("DynamicTransforms", dynamicTransforms);
                        renderPass.setIndexBuffer(indexBuffer, indices.type());
                        renderPass.setUniform("CloudInfo", cloudRendererAccessor.aether_ii$getUbo().currentBuffer());
                        renderPass.setUniform("CloudFaces", cloudRendererAccessor.aether_ii$getUtb().currentBuffer());
                        renderPass.drawIndexed(0, 0, 6 * cloudRendererAccessor.aether_ii$getQuadCount(), 1);
                    }
                }
            }
            return true;
        }
        return false;
    }
}