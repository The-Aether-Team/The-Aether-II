package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MimicModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MimicRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MimicRenderer extends MobRenderer<Mimic, MimicRenderState, MimicModel<MimicRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_crate_mimic/sentry_crate_mimic.png");
    private static final ResourceLocation TEXTURE_EYE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_crate_mimic/sentry_crate_mimic_eye.png");
    private static final ResourceLocation TEXTURE_EMISSIVE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_crate_mimic/sentry_crate_mimic_emissive.png");

    public MimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new MimicModel<>(renderer.bakeLayer(AetherIIModelLayers.MIMIC)), 1.0F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return RenderType.eyes(TEXTURE_EMISSIVE);
            }
        });
    }

    @Override
    public MimicRenderState createRenderState() {
        return new MimicRenderState();
    }

    @Override
    public void extractRenderState(Mimic mimic, MimicRenderState mimicRenderState, float particalTick) {
        super.extractRenderState(mimic, mimicRenderState, particalTick);
        mimicRenderState.spawnAnimationState.copyFrom(mimic.spawnAnimationState);
        mimicRenderState.attackAnimationState.copyFrom(mimic.attackAnimationState);
    }

    @Override
    public void render(MimicRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int p_115313_) {
        super.render(renderState, poseStack, bufferSource, p_115313_);
        poseStack.pushPose();
        float f1 = renderState.scale;
        poseStack.scale(f1, f1, f1);
        this.setupRotations(renderState, poseStack, renderState.bodyRot + 180, f1);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.body.translateAndRotate(poseStack);
        this.model.head.translateAndRotate(poseStack);
        this.model.eye.translateAndRotate(poseStack);
        this.scale(renderState, poseStack);
        poseStack.translate(0.0F, -1.501F, 0.0F);
        PoseStack.Pose posestack$pose = poseStack.last();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE_EYE));
        vertex(vertexconsumer, posestack$pose, p_115313_, -0.5F + (2.5F / 16F), -0.85F, 0, 1);
        vertex(vertexconsumer, posestack$pose, p_115313_, 0.5F + (2.5F / 16F), -0.85F, 1, 1);
        vertex(vertexconsumer, posestack$pose, p_115313_, 0.5F + (2.5F / 16F), 0.15F, 1, 0);
        vertex(vertexconsumer, posestack$pose, p_115313_, -0.5F + (2.5F / 16F), 0.15F, 0, 0);
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, int u, int v) {
        consumer.addVertex(pose, x, y, 0.0F)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    /**
     * If the Lootr mod is installed or if it is Christmas, Mimics will have a custom texture.
     *
     * @param Mimic The {@link Mimic} entity.
     * @return The texture {@link ResourceLocation}.
     */
    @Override
    public ResourceLocation getTextureLocation(MimicRenderState Mimic) {
        return TEXTURE;
    }
}

