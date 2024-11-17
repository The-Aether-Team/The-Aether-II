package com.aetherteam.aetherii.client;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class AetherIIArmPoseTransformers {
    public static final IArmPoseTransformer GLIDING_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        humanoidModel.rightArm.resetPose();
        humanoidModel.leftArm.resetPose();
        humanoidModel.rightArm.z = 0.0F;
        humanoidModel.rightArm.x = -5.0F;
        humanoidModel.leftArm.z = 0.0F;
        humanoidModel.leftArm.x = 5.0F;
        humanoidModel.rightArm.xRot = (Mth.cos(0.6662F) * 0.25F);
        humanoidModel.leftArm.xRot = (Mth.cos(0.6662F) * 0.25F);
        humanoidModel.rightArm.zRot = 2.3561945F;
        humanoidModel.leftArm.zRot = -2.3561945F;
        humanoidModel.rightArm.yRot = 0.0F;
        humanoidModel.leftArm.yRot = 0.0F;
    };
}
