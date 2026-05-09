package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.CloudSkiffModel;
import com.aetherteam.aetherii.client.renderer.entity.state.CloudSkiffRenderState;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

public class CloudSkiffRenderer extends EntityRenderer<CloudSkiff, CloudSkiffRenderState> {
    private static final Identifier CLOUD_SKIFF_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/cloud_skiff/cloud_skiff.png");
    private final CloudSkiffModel model;

    public CloudSkiffRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CloudSkiffModel(context.bakeLayer(AetherIIModelLayers.CLOUD_SKIFF));
        this.shadowRadius = 0.8F;
    }

    @Override
    public void submit(CloudSkiffRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.animationTick > 1) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.375F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.yRot));
            float f = renderState.hurtTime;
            if (f > 0.0F) {
                poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * renderState.damageTime / 10.0F * (float) renderState.hurtDir));
            }
            if (!renderState.isUnderWater && !Mth.equal(renderState.bubbleAngle, 0.0F)) {
                poseStack.mulPose(new Quaternionf().setAngleAxis(renderState.bubbleAngle * Mth.DEG_TO_RAD, 1.0F, 0.0F, 1.0F));
            }
            poseStack.translate(0.0F, 1.125F, 0.0F);
            poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
            poseStack.translate(0.0F, 0.0F, -0.125F);
            this.model.setupAnim(renderState);
            submitNodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(CLOUD_SKIFF_TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);

            poseStack.popPose();

            super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
        }
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
        reusedState.unfoldAnimationState.copyFrom(entity.unfoldAnimationState);
        reusedState.foldAnimationState.copyFrom(entity.foldAnimationState);
        reusedState.animationTick = entity.tickCount;
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
