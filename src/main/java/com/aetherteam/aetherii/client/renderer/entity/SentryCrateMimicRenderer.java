package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryCrateMimicModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryCrateMimicRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryCrateMimic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SentryCrateMimicRenderer extends MobRenderer<SentryCrateMimic, SentryCrateMimicRenderState, SentryCrateMimicModel<SentryCrateMimicRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic.png");
    private static final Identifier TEXTURE_EYE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic_eye.png");
    private static final Identifier TEXTURE_EMISSIVE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic_emissive.png");

    public SentryCrateMimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new SentryCrateMimicModel<>(renderer.bakeLayer(AetherIIModelLayers.MIMIC)), 1.0F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return RenderTypes.eyes(TEXTURE_EMISSIVE);
            }
        });
    }

    @Override
    public SentryCrateMimicRenderState createRenderState() {
        return new SentryCrateMimicRenderState();
    }

    @Override
    public void extractRenderState(SentryCrateMimic mimic, SentryCrateMimicRenderState sentryCrateMimicRenderState, float particalTick) {
        super.extractRenderState(mimic, sentryCrateMimicRenderState, particalTick);
        sentryCrateMimicRenderState.spawnAnimationState.copyFrom(mimic.spawnAnimationState);
        sentryCrateMimicRenderState.attackAnimationState.copyFrom(mimic.attackAnimationState);
    }

    @Override
    public void submit(SentryCrateMimicRenderState renderState, PoseStack poseStack, SubmitNodeCollector p_433768_, CameraRenderState cameraRenderState) {
        super.submit(renderState, poseStack, p_433768_, cameraRenderState);

        if (renderState.deathTime <= 0.0F) {
            poseStack.pushPose();
            poseStack.scale(0.45F, 0.45F, 0.45F);
            poseStack.translate(0.0F, 2.25F, 0.0F);
            poseStack.mulPose(cameraRenderState.orientation);
            p_433768_.submitCustomGeometry(poseStack, RenderTypes.entityCutout(TEXTURE_EYE), (pose, vertexConsumer) -> {

                vertex(vertexConsumer, pose, renderState.lightCoords, -0.5F, -0.5F, 0, 1);
                vertex(vertexConsumer, pose, renderState.lightCoords, 0.5F, -0.5F, 1, 1);
                vertex(vertexConsumer, pose, renderState.lightCoords, 0.5F, 0.5F, 1, 0);
                vertex(vertexConsumer, pose, renderState.lightCoords, -0.5F, 0.5F, 0, 0);
            });
            poseStack.popPose();
        }
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
     * @return The texture {@link }.
     */
    @Override
    public Identifier getTextureLocation(SentryCrateMimicRenderState Mimic) {
        return TEXTURE;
    }
}

