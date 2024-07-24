package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.CockatriceAnimation;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CockatriceModel<T extends Cockatrice> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart body_front;
    private final ModelPart neck;
    private final ModelPart head;

    public CockatriceModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.body_front = this.body.getChild("body_front");
        this.neck = this.body_front.getChild("neck");
        this.head = this.neck.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 0.5F));

        PartDefinition body_rear_r1 = body.addOrReplaceChild("body_rear_r1", CubeListBuilder.create().texOffs(42, 96).addBox(-5.0F, -5.0F, -7.5F, 10.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_spines_right_r1 = body_front.addOrReplaceChild("body_spines_right_r1", CubeListBuilder.create().texOffs(42, 81).addBox(-2.1F, -8.0F, -11.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.2182F, -0.0436F));

        PartDefinition body_spines_left_r1 = body_front.addOrReplaceChild("body_spines_left_r1", CubeListBuilder.create().texOffs(82, 81).addBox(0.1F, -8.0F, -11.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, -0.2182F, 0.0436F));

        PartDefinition body_front_r1 = body_front.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(52, 77).addBox(-4.0F, -8.0F, -10.5F, 8.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition neck = body_front.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -8.5F));

        PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(59, 61).addBox(-2.0F, -14.0F, -11.5F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 8.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(57, 0).addBox(-2.5F, -5.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(53, 12).addBox(-2.5F, -5.5F, -11.5F, 5.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(62, 36).addBox(-1.5F, -1.5F, -4.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 25).addBox(-2.5F, -1.5F, -11.5F, 5.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 1.5F, 0.0873F, 0.0F, 0.0F));

        PartDefinition head_feather_right_4_r1 = head.addOrReplaceChild("head_feather_right_4_r1", CubeListBuilder.create().texOffs(25, 5).addBox(0.71F, 3.45F, 1.25F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -1.5F, -1.5F, 1.7453F, -0.1309F, -0.0436F));

        PartDefinition head_feather_right_3_r1 = head.addOrReplaceChild("head_feather_right_3_r1", CubeListBuilder.create().texOffs(31, 3).addBox(1.2182F, 2.0683F, 0.1541F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -1.5F, -1.5F, 1.2828F, -0.3316F, 0.0349F));

        PartDefinition head_feather_right_2_r1 = head.addOrReplaceChild("head_feather_right_2_r1", CubeListBuilder.create().texOffs(41, 3).addBox(0.9F, 1.65F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -1.5F, -1.5F, 0.829F, -0.3491F, 0.2618F));

        PartDefinition head_feather_right_1_r1 = head.addOrReplaceChild("head_feather_right_1_r1", CubeListBuilder.create().texOffs(51, 5).addBox(0.0F, 0.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -1.5F, -1.5F, 0.0F, 0.0F, 0.3054F));

        PartDefinition head_feather_left_4_r1 = head.addOrReplaceChild("head_feather_left_4_r1", CubeListBuilder.create().texOffs(103, 5).addBox(-1.71F, 3.45F, 1.25F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.5F, -1.5F, 1.7453F, 0.1309F, 0.0436F));

        PartDefinition head_feather_left_3_r1 = head.addOrReplaceChild("head_feather_left_3_r1", CubeListBuilder.create().texOffs(93, 3).addBox(-2.2182F, 2.0683F, 0.1541F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.5F, -1.5F, 1.2828F, 0.3316F, -0.0349F));

        PartDefinition head_feather_left_2_r1 = head.addOrReplaceChild("head_feather_left_2_r1", CubeListBuilder.create().texOffs(83, 3).addBox(-1.9F, 1.65F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.5F, -1.5F, 0.829F, 0.3491F, -0.2618F));

        PartDefinition head_feather_left_1_r1 = head.addOrReplaceChild("head_feather_left_1_r1", CubeListBuilder.create().texOffs(77, 5).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.5F, -1.5F, 0.0F, 0.0F, -0.3054F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(58, 41).addBox(-1.5F, 0.6F, -7.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition jaw_right_r1 = jaw.addOrReplaceChild("jaw_right_r1", CubeListBuilder.create().texOffs(42, 41).addBox(-2.5F, 1.1F, -6.5F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0349F, -0.1745F, 0.2618F));

        PartDefinition jaw_left_r1 = jaw.addOrReplaceChild("jaw_left_r1", CubeListBuilder.create().texOffs(76, 41).addBox(0.5F, 1.1F, -6.5F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0349F, 0.1745F, -0.2618F));

        PartDefinition jaw_front_r1 = jaw.addOrReplaceChild("jaw_front_r1", CubeListBuilder.create().texOffs(59, 51).addBox(-2.0F, 1.0F, -10.2F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition arm_left = body_front.addOrReplaceChild("arm_left", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -7.0F, -6.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition arm_left_1_r1 = arm_left.addOrReplaceChild("arm_left_1_r1", CubeListBuilder.create().texOffs(107, 12).addBox(-1.0F, -0.9F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition arm_left_2 = arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create().texOffs(98, 24).addBox(-2.2F, -2.0F, -8.5F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(101, 48).addBox(0.78F, 2.5F, -6.0F, 1.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 4.5F, 0.0F, 0.5672F, 0.1745F, -0.3491F));

        PartDefinition arm_left_feather_5_r1 = arm_left_2.addOrReplaceChild("arm_left_feather_5_r1", CubeListBuilder.create().texOffs(125, 48).addBox(-0.71F, -0.3F, -1.5F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.0F, 1.8F, 0.9163F, 0.0F, 0.0F));

        PartDefinition arm_left_feather_4_r1 = arm_left_2.addOrReplaceChild("arm_left_feather_4_r1", CubeListBuilder.create().texOffs(117, 48).addBox(-0.72F, -1.0F, -1.0F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 3.0F, 0.8F, 0.4363F, 0.0F, 0.0F));

        PartDefinition arm_left_feather_3_r1 = arm_left_2.addOrReplaceChild("arm_left_feather_3_r1", CubeListBuilder.create().texOffs(109, 48).addBox(-0.71F, -1.0F, -1.3F, 1.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 3.0F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition arm_left_feather_1_r1 = arm_left_2.addOrReplaceChild("arm_left_feather_1_r1", CubeListBuilder.create().texOffs(93, 48).addBox(-0.71F, -1.0F, -0.7F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 3.0F, -8.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition hand_left = arm_left_2.addOrReplaceChild("hand_left", CubeListBuilder.create().texOffs(106, 40).addBox(-0.4F, -4.5F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(100, 42).addBox(-0.4F, -4.5F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -7.0F, 0.0F, 0.1309F, -0.0175F));

        PartDefinition arm_right = body_front.addOrReplaceChild("arm_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -7.0F, -6.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition arm_right_1_r1 = arm_right.addOrReplaceChild("arm_right_1_r1", CubeListBuilder.create().texOffs(15, 12).addBox(-2.0F, -0.9F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create().texOffs(6, 24).addBox(-1.8F, -2.0F, -8.5F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(25, 48).addBox(-1.78F, 2.5F, -6.0F, 1.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 4.5F, 0.0F, 0.5672F, -0.1745F, 0.3491F));

        PartDefinition arm_right_feather_6_r1 = arm_right_2.addOrReplaceChild("arm_right_feather_6_r1", CubeListBuilder.create().texOffs(1, 48).addBox(-0.29F, -0.3F, -1.5F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.0F, 1.8F, 0.9163F, 0.0F, 0.0F));

        PartDefinition arm_right_feather_5_r1 = arm_right_2.addOrReplaceChild("arm_right_feather_5_r1", CubeListBuilder.create().texOffs(9, 48).addBox(-0.28F, -1.0F, -1.0F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 3.0F, 0.8F, 0.4363F, 0.0F, 0.0F));

        PartDefinition arm_right_feather_4_r1 = arm_right_2.addOrReplaceChild("arm_right_feather_4_r1", CubeListBuilder.create().texOffs(17, 48).addBox(-0.29F, -1.0F, -1.3F, 1.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 3.0F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition arm_right_feather_1_r1 = arm_right_2.addOrReplaceChild("arm_right_feather_1_r1", CubeListBuilder.create().texOffs(33, 48).addBox(-0.29F, -1.0F, -0.7F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 3.0F, -8.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition hand_right = arm_right_2.addOrReplaceChild("hand_right", CubeListBuilder.create().texOffs(14, 40).addBox(-2.6F, -4.5F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 42).addBox(-0.6F, -4.5F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -7.0F, 0.0F, -0.1309F, 0.0175F));

        PartDefinition leg_left = body.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 3.0F));

        PartDefinition leg_left_1_r1 = leg_left.addOrReplaceChild("leg_left_1_r1", CubeListBuilder.create().texOffs(101, 63).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, -0.3491F));

        PartDefinition leg_left_2 = leg_left.addOrReplaceChild("leg_left_2", CubeListBuilder.create(), PartPose.offset(3.0F, 4.0F, 0.0F));

        PartDefinition leg_left_2_r1 = leg_left_2.addOrReplaceChild("leg_left_2_r1", CubeListBuilder.create().texOffs(107, 80).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, -0.1309F));

        PartDefinition foot_left = leg_left_2.addOrReplaceChild("foot_left", CubeListBuilder.create(), PartPose.offset(0.5F, 9.0F, -1.5F));

        PartDefinition talon_left_outer_r1 = foot_left.addOrReplaceChild("talon_left_outer_r1", CubeListBuilder.create().texOffs(120, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, -0.3665F, -0.4363F, 0.0F));

        PartDefinition talon_left_inner_r1 = foot_left.addOrReplaceChild("talon_left_inner_r1", CubeListBuilder.create().texOffs(92, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, -0.3665F, 0.4363F, 0.0F));

        PartDefinition talon_left_rear_r1 = foot_left.addOrReplaceChild("talon_left_rear_r1", CubeListBuilder.create().texOffs(122, 93).addBox(-1.0F, -0.7F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3752F, 0.0F, 0.0F));

        PartDefinition talon_left_mid_r1 = foot_left.addOrReplaceChild("talon_left_mid_r1", CubeListBuilder.create().texOffs(106, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.3665F, 0.0F, 0.0F));

        PartDefinition foot_left_r1 = foot_left.addOrReplaceChild("foot_left_r1", CubeListBuilder.create().texOffs(104, 92).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

        PartDefinition leg_right = body.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 3.0F));

        PartDefinition leg_right_1_r1 = leg_right.addOrReplaceChild("leg_right_1_r1", CubeListBuilder.create().texOffs(9, 63).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.3491F));

        PartDefinition leg_right_2 = leg_right.addOrReplaceChild("leg_right_2", CubeListBuilder.create(), PartPose.offset(-3.0F, 4.0F, 0.0F));

        PartDefinition leg_right_3_r1 = leg_right_2.addOrReplaceChild("leg_right_3_r1", CubeListBuilder.create().texOffs(15, 80).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.1309F));

        PartDefinition foot_right = leg_right_2.addOrReplaceChild("foot_right", CubeListBuilder.create(), PartPose.offset(-0.5F, 9.0F, -1.5F));

        PartDefinition talon_right_outer_r1 = foot_right.addOrReplaceChild("talon_right_outer_r1", CubeListBuilder.create().texOffs(0, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, -0.3665F, 0.4363F, 0.0F));

        PartDefinition talon_right_inner_r1 = foot_right.addOrReplaceChild("talon_right_inner_r1", CubeListBuilder.create().texOffs(28, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, -0.3665F, -0.4363F, 0.0F));

        PartDefinition talon_right_rear_r1 = foot_right.addOrReplaceChild("talon_right_rear_r1", CubeListBuilder.create().texOffs(2, 93).addBox(-1.0F, -0.7F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3752F, 0.0F, 0.0F));

        PartDefinition talon_right_mid_r1 = foot_right.addOrReplaceChild("talon_right_mid_r1", CubeListBuilder.create().texOffs(14, 99).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.3665F, 0.0F, 0.0F));

        PartDefinition foot_right_r1 = foot_right.addOrReplaceChild("foot_right_r1", CubeListBuilder.create().texOffs(12, 92).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition tail_feathers = body.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -3.8F, 7.95F));

        PartDefinition tail_feather_right_1_r1 = tail_feathers.addOrReplaceChild("tail_feather_right_1_r1", CubeListBuilder.create().texOffs(55, 121).addBox(-2.6F, -0.1F, -1.15F, 3.0F, 19.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.6545F, -0.3491F, 0.0F));

        PartDefinition tail_feather_left_1_r1 = tail_feathers.addOrReplaceChild("tail_feather_left_1_r1", CubeListBuilder.create().texOffs(71, 121).addBox(-0.4F, -0.1F, -1.15F, 3.0F, 19.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.6545F, 0.3491F, 0.0F));

        PartDefinition tail_feather_mid_r1 = tail_feathers.addOrReplaceChild("tail_feather_mid_r1", CubeListBuilder.create().texOffs(63, 121).addBox(-1.5F, 0.0F, -0.95F, 3.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition rear_feather_left = body.addOrReplaceChild("rear_feather_left", CubeListBuilder.create(), PartPose.offset(4.5F, -3.5F, 7.0F));

        PartDefinition rear_feather_left_r1 = rear_feather_left.addOrReplaceChild("rear_feather_left_r1", CubeListBuilder.create().texOffs(79, 121).addBox(-1.4F, 0.0F, -0.1F, 3.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.6545F, -1.8326F, 0.0F));

        PartDefinition rear_feather_right = body.addOrReplaceChild("rear_feather_right", CubeListBuilder.create(), PartPose.offset(-4.5F, -3.5F, 7.0F));

        PartDefinition rear_feather_right_r1 = rear_feather_right.addOrReplaceChild("rear_feather_right_r1", CubeListBuilder.create().texOffs(46, 121).addBox(-1.6F, 0.0F, -0.1F, 3.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.6545F, 1.8326F, 0.0F));

        return LayerDefinition.create(meshdefinition, 168, 168);
    }


    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = (headPitch * Mth.DEG_TO_RAD) + 0.0873F;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.animateWalk(CockatriceAnimation.RUN, limbSwing, limbSwingAmount, 1.0F, 1.5F);
        this.animate(entity.attackAnimationState, CockatriceAnimation.CLAW_ATTACK, ageInTicks, 2.0F);
        this.animate(entity.shootAnimationState, CockatriceAnimation.ATTACK, ageInTicks, 1.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}