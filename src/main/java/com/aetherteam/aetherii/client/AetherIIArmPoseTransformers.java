package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.client.IArmPoseTransformer;

public class AetherIIArmPoseTransformers {
    public static final IArmPoseTransformer DART_SHOOTER_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        humanoidModel.rightArm.yRot = -0.1F + humanoidModel.head.yRot - 0.3F;
        humanoidModel.leftArm.yRot = 0.1F + humanoidModel.head.yRot + 0.3F;
        humanoidModel.rightArm.xRot = -Mth.HALF_PI + humanoidModel.head.xRot;
        humanoidModel.leftArm.xRot = -Mth.HALF_PI + humanoidModel.head.xRot;
    };
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
        float walkPosition = livingEntity.walkAnimation.position();
        float walkSpeed = livingEntity.walkAnimation.speed();
        humanoidModel.rightLeg.xRot = Mth.cos(walkPosition * 0.1662F) * 0.3F * walkSpeed;
        humanoidModel.leftLeg.xRot = Mth.cos(walkPosition * 0.1662F + Mth.PI) * 0.3F * walkSpeed;
    };
    public static final IArmPoseTransformer SKIFF_SAILING_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        if (livingEntity.getVehicle() instanceof CloudSkiff cloudSkiff) {
            CloudSkiff.SteeringState steering = cloudSkiff.getSteeringState();
            HumanoidArm arm = livingEntity.getMainArm();
            if (steering == CloudSkiff.SteeringState.RIGHT) {
                humanoidModel.rightArm.xRot -= (float) (-Math.PI / 8);
            } else if (steering == CloudSkiff.SteeringState.LEFT) {
                humanoidModel.leftArm.xRot -= (float) (-Math.PI / 8);
            } else {
                if (arm == HumanoidArm.RIGHT) {
                    humanoidModel.rightArm.xRot -= (float) (-Math.PI / 8);
                } else if (arm == HumanoidArm.LEFT) {
                    humanoidModel.leftArm.xRot -= (float) (-Math.PI / 8);
                }
            }
        }
    };
}
