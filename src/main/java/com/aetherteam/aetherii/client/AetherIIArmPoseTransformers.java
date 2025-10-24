package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class AetherIIArmPoseTransformers {
    public static final IArmPoseTransformer GLIDING_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        humanoidModel.rightArm.z = 0.0F;
        humanoidModel.rightArm.x = -5.0F;
        humanoidModel.leftArm.z = 0.0F;
        humanoidModel.leftArm.x = 5.0F;
        humanoidModel.rightArm.xRot = 0.0F;
        humanoidModel.leftArm.xRot = 0.0F;
        humanoidModel.rightArm.zRot = 145.0F * Mth.DEG_TO_RAD;
        humanoidModel.leftArm.zRot = -145.0F * Mth.DEG_TO_RAD;
        humanoidModel.rightArm.yRot = 0.0F;
        humanoidModel.leftArm.yRot = 0.0F;
        AnimationUtils.bobModelPart(humanoidModel.rightArm, livingEntity.ageInTicks, -1.0F);
        AnimationUtils.bobModelPart(humanoidModel.leftArm, livingEntity.ageInTicks, 1.0F);
        humanoidModel.rightLeg.xRot = Mth.cos(livingEntity.walkAnimationPos * 0.1662F) * 0.3F * livingEntity.walkAnimationSpeed / livingEntity.speedValue;
        humanoidModel.leftLeg.xRot = Mth.cos(livingEntity.walkAnimationPos * 0.1662F + Mth.PI) * 0.3F * livingEntity.walkAnimationSpeed / livingEntity.speedValue;
    };
    public static final IArmPoseTransformer SKIFF_SAILING_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        Boolean riding = livingEntity.getRenderData(AetherIIRenderers.RIDING_SKIFF_KEY);
        if (riding != null && riding) {
            Float steering = livingEntity.getRenderData(AetherIIRenderers.SKIFF_STEERING_KEY);
            if (steering != null) {
                HumanoidArm arm = livingEntity.mainArm;
                if (steering > 0) {
                    humanoidModel.rightArm.xRot -= (float) (-Math.PI / 8);
                } else if (steering < 0) {
                    humanoidModel.leftArm.xRot -= (float) (-Math.PI / 8);
                } else {
                    if (arm == HumanoidArm.RIGHT) {
                        humanoidModel.rightArm.xRot -= (float) (-Math.PI / 8);
                    } else if (arm == HumanoidArm.LEFT) {
                        humanoidModel.leftArm.xRot -= (float) (-Math.PI / 8);
                    }
                }
            }
        }
    };
}
