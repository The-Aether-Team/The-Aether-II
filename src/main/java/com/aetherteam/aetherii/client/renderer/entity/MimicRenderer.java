package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MimicModel;
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

public class MimicRenderer extends MobRenderer<Mimic, MimicModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic.png");
    private static final ResourceLocation TEXTURE_EYE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic_eye.png");
    private static final ResourceLocation TEXTURE_EMISSIVE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/mimic/sentry_crate_mimic_emissive.png");

    public MimicRenderer(EntityRendererProvider.Context context) {
        super(context, new MimicModel(context.bakeLayer(AetherIIModelLayers.MIMIC)), 1.0F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return RenderType.eyes(TEXTURE_EMISSIVE);
            }
        });
    }

    @Override
    public void render(Mimic mimic, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(mimic, entityYaw, partialTick, poseStack, buffer, packedLight);
        if (mimic.deathTime <= 0) {
            poseStack.pushPose();

            poseStack.translate(0.0F, 1.0125F, 0.0F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.scale(0.45F, 0.45F, 0.45F);

            PoseStack.Pose pose = poseStack.last();
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_EYE));

            vertex(consumer, pose, packedLight, -0.5F, -0.5F, 0, 1);
            vertex(consumer, pose, packedLight, 0.5F, -0.5F, 1, 1);
            vertex(consumer, pose, packedLight, 0.5F, 0.5F, 1, 0);
            vertex(consumer, pose, packedLight, -0.5F, 0.5F, 0, 0);

            poseStack.popPose();
        }
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, int u, int v) {
        consumer.vertex(pose.pose(), x, y, 0.0F)
                .color(255, 255, 255, 255)
                .uv((float) u, (float) v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(Mimic mimic) {
        return TEXTURE;
    }
}
