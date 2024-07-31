package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.TempestThunderball;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class TempestThunderballRenderer extends EntityRenderer<TempestThunderball> {
    private static final ResourceLocation TEMPEST_PROJECTILE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/tempest_thunderball.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEMPEST_PROJECTILE_TEXTURE);

    public TempestThunderballRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TempestThunderball thunderball, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, pose, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, pose, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, pose, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(thunderball, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, (float) y - 0.25F, 0.0F)
                .setColor(-1)
                .setUv((float) u, (float) v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    protected int getBlockLightLevel(TempestThunderball entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(TempestThunderball thunderball) {
        return TEMPEST_PROJECTILE_TEXTURE;
    }
}
