package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.CloudSkiffModel;
import com.aetherteam.aetherii.client.renderer.entity.state.CloudSkiffRenderState;
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

public class CloudSkiffRenderer extends EntityRenderer<CloudSkiff, CloudSkiffRenderState> {
    private static final ResourceLocation CLOUD_SKIFF_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/cloud_skiff/cloud_skiff.png");
    private final CloudSkiffModel model;

    public CloudSkiffRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CloudSkiffModel(context.bakeLayer(AetherIIModelLayers.CLOUD_SKIFF));
        this.shadowRadius = 0.8F;
    }

    @Override
    public void render(CloudSkiffRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(-0.125F, 1.5F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.yRot));
        float f = renderState.hurtTime;
        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * renderState.damageTime / 10.0F * (float) renderState.hurtDir));
        }
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        this.model.setupAnim(renderState);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(CLOUD_SKIFF_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @Override
    public CloudSkiffRenderState createRenderState() {
        return new CloudSkiffRenderState();
    }

    @Override
    public void extractRenderState(CloudSkiff entity, CloudSkiffRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.hurtTime = (float) entity.getHurtTime() - partialTick;
        reusedState.hurtDir = entity.getHurtDir();
        reusedState.damageTime = Math.max(entity.getDamage() - partialTick, 0.0F);
    }
}
