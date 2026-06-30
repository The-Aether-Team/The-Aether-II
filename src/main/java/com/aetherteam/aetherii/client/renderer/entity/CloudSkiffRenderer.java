package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.CloudSkiffModel;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class CloudSkiffRenderer extends EntityRenderer<CloudSkiff> {
    private static final ResourceLocation CLOUD_SKIFF_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/cloud_skiff/cloud_skiff.png");

    private final CloudSkiffModel model;

    public CloudSkiffRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CloudSkiffModel(context.bakeLayer(AetherIIModelLayers.CLOUD_SKIFF));
        this.shadowRadius = 0.8F;
    }

    @Override
    public void render(CloudSkiff cloudSkiff, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (cloudSkiff.tickCount <= 1) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float hurtTime = cloudSkiff.getHurtTime() - partialTick;
        float damage = Math.max(cloudSkiff.getDamage() - partialTick, 0.0F);
        if (hurtTime > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 10.0F * cloudSkiff.getHurtDir()));
        }

        float bubbleAngle = cloudSkiff.getBubbleAngle(partialTick);
        if (!Mth.equal(bubbleAngle, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(bubbleAngle * Mth.DEG_TO_RAD, 1.0F, 0.0F, 1.0F));
        }

        poseStack.translate(0.0F, 1.125F, 0.0F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
        poseStack.translate(0.0F, 0.0F, -0.125F);
        this.model.setupAnim(cloudSkiff, 0.0F, 0.0F, cloudSkiff.tickCount + partialTick, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(CLOUD_SKIFF_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(cloudSkiff, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CloudSkiff cloudSkiff) {
        return CLOUD_SKIFF_TEXTURE;
    }
}
