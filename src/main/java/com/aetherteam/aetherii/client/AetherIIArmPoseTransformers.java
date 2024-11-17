package com.aetherteam.aetherii.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class AetherIIArmPoseTransformers {
    public static final IArmPoseTransformer GLIDING_TRANSFORMER = (humanoidModel, livingEntity, humanoidArm) -> {
        float ageInTicks = livingEntity.tickCount + DeltaTracker.ONE.getGameTimeDeltaPartialTick(!Minecraft.getInstance().level.tickRateManager().isEntityFrozen(livingEntity));
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
        AnimationUtils.bobModelPart(humanoidModel.rightArm, ageInTicks, -1.0F);
        AnimationUtils.bobModelPart(humanoidModel.leftArm, ageInTicks, 1.0F);
    };
}
