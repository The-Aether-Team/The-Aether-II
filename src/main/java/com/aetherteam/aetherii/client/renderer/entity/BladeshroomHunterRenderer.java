package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.BladeshroomHunterModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BladeshroomHunterRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.BladeshroomHunter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class BladeshroomHunterRenderer<T extends BladeshroomHunter> extends MobRenderer<T, BladeshroomHunterRenderState, BladeshroomHunterModel<BladeshroomHunterRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/bladeshroom_hunter/bladeshroom_hunter.png");

    public BladeshroomHunterRenderer(EntityRendererProvider.Context context) {
        super(context, new BladeshroomHunterModel<>(context.bakeLayer(AetherIIModelLayers.BLADESHROOM_HUNTER)), 0.5F);
    }

    @Override
    public BladeshroomHunterRenderState createRenderState() {
        return new BladeshroomHunterRenderState();
    }

    @Override
    public void extractRenderState(T bladeshroomHunter, BladeshroomHunterRenderState renderState, float p_361157_) {
        super.extractRenderState(bladeshroomHunter, renderState, p_361157_);
        renderState.rotations = bladeshroomHunter.getCellRotation();
        renderState.prevRotations = bladeshroomHunter.prevRotation;
        renderState.attachDir = bladeshroomHunter.getAttachFacing();
        renderState.attachChangeProgress = bladeshroomHunter.getAttachAmount(p_361157_);
        renderState.attackAnimationState.copyFrom(bladeshroomHunter.axeAttackAnimationState);
        renderState.buryAnimationState.copyFrom(bladeshroomHunter.buryAnimationState);
        renderState.unburyAnimationState.copyFrom(bladeshroomHunter.unburyAnimationState);
        renderState.rustleAnimationState.copyFrom(bladeshroomHunter.rustleAnimationState);
    }

    @Override
    protected void setupRotations(BladeshroomHunterRenderState entity, PoseStack poseStack, float rotationYaw, float p_320045_) {
        float trans = 8F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachDir == Direction.DOWN) {
                super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
            } else {

                float yaw = (float) Math.toDegrees(Mth.atan2(entity.rotations.x(), entity.rotations.z()));
                float pitch = (float) -Math.toDegrees(Mth.atan2(entity.rotations.y(), Math.sqrt(entity.rotations.x() * entity.rotations.x() + entity.rotations.z() * entity.rotations.z())));
                float prevYaw = (float) Math.toDegrees(Mth.atan2(entity.prevRotations.x(), entity.prevRotations.z()));
                float prevPitch = (float) -Math.toDegrees(Mth.atan2(entity.prevRotations.y(), Math.sqrt(entity.prevRotations.x() * entity.prevRotations.x() + entity.prevRotations.z() * entity.prevRotations.z())));
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
    public Identifier getTextureLocation(BladeshroomHunterRenderState renderState) {
        return LOCATION;
    }
}
