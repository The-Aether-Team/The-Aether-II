package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.TempestThunderball;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class TempestThunderballRenderer extends EntityRenderer<TempestThunderball> {
    private static final ResourceLocation TEMPEST_PROJECTILE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/tempest_thunderball.png");

    public TempestThunderballRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TempestThunderball thunderball, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (thunderball.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(thunderball) < 12.25)) {
            poseStack.pushPose();
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix4f = pose.pose();
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(thunderball)));
            vertex(vertexconsumer, matrix4f, pose, packedLight, 0.0F, 0, 0, 1);
            vertex(vertexconsumer, matrix4f, pose, packedLight, 1.0F, 0, 1, 1);
            vertex(vertexconsumer, matrix4f, pose, packedLight, 1.0F, 1, 1, 0);
            vertex(vertexconsumer, matrix4f, pose, packedLight, 0.0F, 1, 0, 0);
            poseStack.popPose();
            super.render(thunderball, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, PoseStack.Pose pose, int uv, float x, int y, int u, int v) {
        consumer.addVertex(matrix4f, x - 0.5F, (float) y - 0.25F, 0.0F)
                .setColor(255, 255, 255, 255)
                .setUv((float) u, (float) v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(u, v)
                .setNormal(pose, 0.0F, 1.5F, 0.0F);
    }

    @Override
    protected int getBlockLightLevel(TempestThunderball entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(TempestThunderball snowball) {
        return TEMPEST_PROJECTILE_TEXTURE;
    }
}
