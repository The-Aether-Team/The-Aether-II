package com.aetherteam.aetherii;

import com.aetherteam.aetherii.client.AetherIIArmPoseTransformers;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class AetherIIEnumExtensions {
    public static final EnumProxy<Rarity> AETHER_II_CURRENCY_RARITY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "aether_ii:currency", (UnaryOperator<Style>) (style) -> AetherIIItems.CURRENCY_NAME_COLOR
    );

    public static final EnumProxy<Rarity> AETHER_II_TREASURE_RARITY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "aether_ii:treasure", (UnaryOperator<Style>) (style) -> AetherIIItems.TREASURE_NAME_COLOR
    );

    public static final EnumProxy<Rarity> AETHER_II_UPGRADED_RARITY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "aether_ii:upgraded", (UnaryOperator<Style>) (style) -> AetherIIItems.UPGRADED_WEAPON_COLOR
    );

    private static String prefix(String id) {
        return AetherII.MODID + ":" + id;
    }

    public static class Client {
        public static final EnumProxy<HumanoidModel.ArmPose> AETHER_II_DART_SHOOTER_ARM_POSE_PROXY = new EnumProxy<>(
                HumanoidModel.ArmPose.class, true, true, AetherIIArmPoseTransformers.DART_SHOOTER_TRANSFORMER
        );
        public static final EnumProxy<HumanoidModel.ArmPose> AETHER_II_GLIDING_ARM_POSE_PROXY = new EnumProxy<>(
                HumanoidModel.ArmPose.class, true, true, AetherIIArmPoseTransformers.GLIDING_TRANSFORMER
        );
        public static final EnumProxy<HumanoidModel.ArmPose> AETHER_II_SKIFF_SAILING_ARM_POSE_PROXY = new EnumProxy<>(
                HumanoidModel.ArmPose.class, true, true, AetherIIArmPoseTransformers.SKIFF_SAILING_TRANSFORMER
        );

        public static final EnumProxy<EquipmentClientInfo.LayerType> AETHER_II_HUMANOID_GLOVES_LAYER_TYPE_PROXY = new EnumProxy<>(
                EquipmentClientInfo.LayerType.class, "aether_ii:humanoid_gloves"
        );
        public static final EnumProxy<EquipmentClientInfo.LayerType> AETHER_II_HUMANOID_ACCESSORY_LAYER_TYPE_PROXY = new EnumProxy<>(
                EquipmentClientInfo.LayerType.class, "aether_ii:humanoid_accessory"
        );
    }
}