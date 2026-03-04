package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.SkephidWebbingBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SkephidWebbingBallRenderer extends EntityRenderer<SkephidWebbingBall, EntityRenderState> {
    private static final Identifier ZEPHYR_PROJECTILE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/zephyr_webbing_ball.png");
    private static final RenderType RENDER_TYPE = RenderTypes.entityCutoutNoCull(ZEPHYR_PROJECTILE_TEXTURE);

    public SkephidWebbingBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState p_451076_) {
        poseStack.pushPose();
        poseStack.mulPose(p_451076_.orientation);

        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, vertexConsumer) -> {

            vertex(vertexConsumer, pose, entityRenderState.lightCoords, 0.0F, 0, 0, 1);
            vertex(vertexConsumer, pose, entityRenderState.lightCoords, 1.0F, 0, 1, 1);
            vertex(vertexConsumer, pose, entityRenderState.lightCoords, 1.0F, 1, 1, 0);
            vertex(vertexConsumer, pose, entityRenderState.lightCoords, 0.0F, 1, 0, 0);

        });
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, p_451076_);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, (float) y - 0.25F, 0.0F)
                .setColor(-1)
                .setUv((float) u, (float) v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}