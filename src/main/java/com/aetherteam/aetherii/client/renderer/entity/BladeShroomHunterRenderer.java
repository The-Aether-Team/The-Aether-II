package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.BladeShroomHunterModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BladeShroomHunterRenderState;
import com.aetherteam.aetherii.entity.monster.BladeShroomHunter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class BladeShroomHunterRenderer<T extends BladeShroomHunter> extends MobRenderer<T, BladeShroomHunterRenderState, BladeShroomHunterModel<BladeShroomHunterRenderState>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/bladeshroom_hunter/bladeshroom_hunter.png");

    public BladeShroomHunterRenderer(EntityRendererProvider.Context context) {
        super(context, new BladeShroomHunterModel<>(context.bakeLayer(AetherIIModelLayers.BLADE_SHROOM_HUNTER)), 0.5F);
    }

    @Override
    public BladeShroomHunterRenderState createRenderState() {
        return new BladeShroomHunterRenderState();
    }

    @Override
    public void extractRenderState(T bladeShroom, BladeShroomHunterRenderState renderState, float p_361157_) {
        super.extractRenderState(bladeShroom, renderState, p_361157_);
        renderState.rotations = bladeShroom.getCellRotation();
        renderState.prevRotations = bladeShroom.prevRotation;
        renderState.attachDir = bladeShroom.getAttachFacing();
        renderState.attachChangeProgress = bladeShroom.getAttachAmount(p_361157_);
        renderState.attackAnimationState.copyFrom(bladeShroom.axeAttackAnimationState);
        renderState.buryAnimationState.copyFrom(bladeShroom.buryAnimationState);
        renderState.unburyAnimationState.copyFrom(bladeShroom.unburyAnimationState);
        renderState.rustleAnimationState.copyFrom(bladeShroom.rustleAnimationState);
    }

    @Override
    protected void setupRotations(BladeShroomHunterRenderState entity, PoseStack poseStack, float rotationYaw, float p_320045_) {
        float trans = 8F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachDir == Direction.DOWN) {
                super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
            } else {

                float yaw = (float) Math.toDegrees(Mth.atan2(entity.rotations.x, entity.rotations.z));
                float pitch = (float) -Math.toDegrees(Mth.atan2(entity.rotations.y, Math.sqrt(entity.rotations.x * entity.rotations.x + entity.rotations.z * entity.rotations.z)));
                float prevYaw = (float) Math.toDegrees(Mth.atan2(entity.prevRotations.x, entity.prevRotations.z));
                float prevPitch = (float) -Math.toDegrees(Mth.atan2(entity.prevRotations.y, Math.sqrt(entity.prevRotations.x * entity.prevRotations.x + entity.prevRotations.z * entity.prevRotations.z)));
                float realYaw = yaw;
                float realPitch = pitch;
                poseStack.translate(0.0F, trans, 0.0F);

                poseStack.mulPose(Axis.YP.rotationDegrees(realYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(-90 + realPitch));
                //poseStack.mulPose(Axis.YP.rotationDegrees(realDiff * realYaw));

                poseStack.translate(0.0F, -trans - 4 / 16F, 0.0F);
                super.setupRotations(entity, poseStack, 0.0F, p_320045_);
            }
        } else {
            super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(BladeShroomHunterRenderState renderState) {
        return LOCATION;
    }
}
