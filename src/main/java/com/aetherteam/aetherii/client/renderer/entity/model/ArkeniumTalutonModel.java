package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.monster.ArkeniumTaluton;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ArkeniumTalutonModel extends HierarchicalModel<ArkeniumTaluton> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart rearLeg;
    private final ModelPart rearLeg2;
    private final ModelPart leftLeg;
    private final ModelPart leftLeg2;
    private final ModelPart rightLeg;
    private final ModelPart rightLeg2;
    private final ModelPart leftArm;
    private final ModelPart leftArm2;
    private final ModelPart rightArm;
    private final ModelPart rightArm2;
    private final ModelPart rightArm3;

    public ArkeniumTalutonModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.rearLeg = this.body.getChild("leg_rear");
        this.rearLeg2 = this.rearLeg.getChild("leg_rear_2");
        this.leftLeg = this.body.getChild("leg_left");
        this.leftLeg2 = this.leftLeg.getChild("leg_left_2");
        this.rightLeg = this.body.getChild("leg_right");
        this.rightLeg2 = this.rightLeg.getChild("leg_right_2");
        this.leftArm = this.body.getChild("arm_left");
        this.leftArm2 = this.leftArm.getChild("arm_left_2");
        this.rightArm = this.body.getChild("arm_right");
        this.rightArm2 = this.rightArm.getChild("arm_right_2");
        this.rightArm3 = this.rightArm2.getChild("arm_right_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(31, 0).addBox(-17.0F, -8.0F, -1.0F, 18.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(38, 20).addBox(-14.5F, 0.0F, 0.0F, 13.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(42, 34).addBox(-12.5F, 4.0F, 0.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 6.0F, -5.0F));

        PartDefinition body_spike_2_r1 = body.addOrReplaceChild("body_spike_2_r1", CubeListBuilder.create().texOffs(97, 3).addBox(-1.0F, -0.5F, -1.8F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3F, -8.5F, 10.3F, -0.2618F, -0.0436F, 0.5236F));

        PartDefinition body_spike_1_r1 = body.addOrReplaceChild("body_spike_1_r1", CubeListBuilder.create().texOffs(80, 0).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.3F, -8.2F, 10.0F, -0.3491F, -0.1309F, 0.1745F));

        PartDefinition leg_rear = body.addOrReplaceChild("leg_rear", CubeListBuilder.create().texOffs(52, 55).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 10.5F, 8.0F));

        PartDefinition leg_rear_2 = leg_rear.addOrReplaceChild("leg_rear_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition leg_rear_2_r1 = leg_rear_2.addOrReplaceChild("leg_rear_2_r1", CubeListBuilder.create().texOffs(53, 64).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_left = body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(80, 55).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, 10.5F, 1.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition leg_left_spike_r1 = leg_left.addOrReplaceChild("leg_left_spike_r1", CubeListBuilder.create().texOffs(99, 57).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.5F, 4.0F, -0.1745F, -0.1309F, -0.2182F));

        PartDefinition leg_left_2 = leg_left.addOrReplaceChild("leg_left_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition leg_left_2_r1 = leg_left_2.addOrReplaceChild("leg_left_2_r1", CubeListBuilder.create().texOffs(81, 64).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_right = body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(24, 55).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.2F, 10.5F, 1.3F, 0.0F, -2.3562F, 0.0F));

        PartDefinition leg_right_2 = leg_right.addOrReplaceChild("leg_right_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition leg_right_2_r1 = leg_right_2.addOrReplaceChild("leg_right_2_r1", CubeListBuilder.create().texOffs(25, 64).addBox(-2.4F, -2.0F, -0.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 5.0F));

        PartDefinition arm_left_spike_r1 = arm_left.addOrReplaceChild("arm_left_spike_r1", CubeListBuilder.create().texOffs(105, 13).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.1F, -0.3927F, 0.1745F, 0.829F));

        PartDefinition arm_left_1_r1 = arm_left.addOrReplaceChild("arm_left_1_r1", CubeListBuilder.create().texOffs(92, 21).addBox(-2.0F, -3.0F, -2.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition arm_left_2 = arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create().texOffs(91, 29).addBox(-3.5F, -0.5F, -3.5F, 7.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 4.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create(), PartPose.offset(-16.0F, -4.0F, 5.0F));

        PartDefinition arm_right_1_r1 = arm_right.addOrReplaceChild("arm_right_1_r1", CubeListBuilder.create().texOffs(1, 13).addBox(-6.0F, -6.0F, -2.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create().texOffs(9, 28).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 3.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition arm_right_spike_r1 = arm_right_2.addOrReplaceChild("arm_right_spike_r1", CubeListBuilder.create().texOffs(0, 31).addBox(-3.0F, -4.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.5F, 1.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition arm_right_3 = arm_right_2.addOrReplaceChild("arm_right_3", CubeListBuilder.create().texOffs(4, 38).addBox(-3.0F, 3.0F, -1.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 2.0F, -2.5F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(ArkeniumTaluton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float f = entity.getAttackAnimationTick() > 0 ? entity.getAttackAnimationTick() - (ageInTicks - entity.tickCount) : 0.0F;
        float f1 = limbSwing;
        float f2 = limbSwingAmount;

        if (f > 0.0F) {
            this.rightArm.xRot = -2.0F + 1.5F * Mth.triangleWave(f, 10.0F);
            this.leftArm.xRot = -2.0F + 1.5F * Mth.triangleWave(f, 10.0F);
        } else {
            this.rightArm.xRot = (-0.2F + 1.5F * Mth.triangleWave(f1, 13.0F)) * f2;
            this.leftArm.xRot = (-0.2F - 1.5F * Mth.triangleWave(f1, 13.0F)) * f2;
        }


        this.rightLeg.xRot = Mth.cos(f1 * 0.6662F + Mth.HALF_PI) * 0.3F * f2;
        this.leftLeg.xRot = Mth.cos(f1 * 0.6662F) * 0.3F * f2;
        this.rearLeg.xRot = Mth.cos(f1 * 0.6662F + Mth.PI) * 0.3F * f2;

        this.rightLeg.yRot = Mth.PI + (45 * Mth.DEG_TO_RAD) + Mth.cos(f1 * 0.6662F) * 0.8F * f2;
        this.leftLeg.yRot = Mth.PI - (45 * Mth.DEG_TO_RAD) + Mth.cos(f1 * 0.6662F + Mth.HALF_PI) * 0.8F * f2;
        this.rearLeg.yRot = Mth.cos(f1 * 0.6662F + Mth.PI) * 0.8F * f2;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
