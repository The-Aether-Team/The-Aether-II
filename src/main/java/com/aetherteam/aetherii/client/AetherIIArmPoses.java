package com.aetherteam.aetherii.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraftforge.client.IArmPoseTransformer;

public class AetherIIArmPoses {
    public static final HumanoidModel.ArmPose DART_SHOOTER = getOrCreate("AETHER_II_DART_SHOOTER_ARM_POSE", true, HumanoidModel.ArmPose.CROSSBOW_HOLD, AetherIIArmPoseTransformers.DART_SHOOTER_TRANSFORMER);
    public static final HumanoidModel.ArmPose GLIDING = getOrCreate("AETHER_II_GLIDING_ARM_POSE", true, HumanoidModel.ArmPose.EMPTY, AetherIIArmPoseTransformers.GLIDING_TRANSFORMER);
    public static final HumanoidModel.ArmPose SKIFF_SAILING = getOrCreate("AETHER_II_SKIFF_SAILING_ARM_POSE", true, HumanoidModel.ArmPose.EMPTY, AetherIIArmPoseTransformers.SKIFF_SAILING_TRANSFORMER);

    private static HumanoidModel.ArmPose getOrCreate(String name, boolean twoHanded, HumanoidModel.ArmPose fallback, IArmPoseTransformer transformer) {
        try {
            return HumanoidModel.ArmPose.valueOf(name);
        } catch (IllegalArgumentException ignored) {
            try {
                return HumanoidModel.ArmPose.create(name, twoHanded, transformer);
            } catch (RuntimeException ignoredCreateFailure) {
                return fallback;
            }
        }
    }
}
