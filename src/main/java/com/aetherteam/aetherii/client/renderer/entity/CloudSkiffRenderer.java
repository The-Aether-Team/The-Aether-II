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
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

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
        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - renderState.yRot));
        float f = renderState.hurtTime;
        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * renderState.damageTime / 10.0F * (float) renderState.hurtDir));
        }
        if (!renderState.isUnderWater && !Mth.equal(renderState.bubbleAngle, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(renderState.bubbleAngle * Mth.DEG_TO_RAD, 1.0F, 0.0F, 1.0F));
        }
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
        poseStack.translate(0.0F, 0.0F, -0.125F);
        this.model.setupAnim(renderState);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(CLOUD_SKIFF_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @Override
    protected AABB getBoundingBoxForCulling(CloudSkiff minecraft) {
        return super.getBoundingBoxForCulling(minecraft).inflate(0.3);
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
        reusedState.bubbleAngle = entity.getBubbleAngle(partialTick);
        reusedState.isUnderWater = entity.isUnderWater();
        reusedState.rowingTimeLeft = entity.getRowingTime(0, partialTick);
        reusedState.rowingTimeRight = entity.getRowingTime(1, partialTick);
        reusedState.steering = Mth.lerp(partialTick, entity.steeringO * Mth.DEG_TO_RAD, entity.steering * Mth.DEG_TO_RAD);
        reusedState.wingLift = Mth.lerp(partialTick, entity.wingLiftO * Mth.DEG_TO_RAD, entity.wingLift * Mth.DEG_TO_RAD);
    }
}
