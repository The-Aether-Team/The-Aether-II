package com.aetherteam.aetherii.client.renderer.model;

import com.aetherteam.aetherii.client.renderer.model.animation.PhygAnimation;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PhygModel<T extends Phyg> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final ModelPart wing_left;
    private final ModelPart wing_right;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_left;
    private final ModelPart leg_back_right;
    private final ModelPart tail;

    public PhygModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.ear_left = this.head.getChild("ear_left");
        this.ear_right = this.head.getChild("ear_right");
        this.wing_left = this.body.getChild("wing_left");
        this.wing_right = this.body.getChild("wing_right");
        this.leg_front_left = this.body.getChild("leg_front_left");
        this.leg_front_right = this.body.getChild("leg_front_right");
        this.leg_back_left = this.body.getChild("leg_back_left");
        this.leg_back_right = this.body.getChild("leg_back_right");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -13.0F, -5.0F, 10.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition spines_r1 = body.addOrReplaceChild("spines_r1", CubeListBuilder.create().texOffs(32, 19).addBox(-11.0F, -2.0F, 0.0F, 13.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 8).addBox(-2.5F, 0.0F, -7.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -3.0F, -5.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -6.0F));

        PartDefinition tusk_right_r1 = head.addOrReplaceChild("tusk_right_r1", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-1.5F, -1.25F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.25F, 2.0F, -6.25F, 0.0F, 0.0F, -0.1745F));

        PartDefinition tusk_left_r1 = head.addOrReplaceChild("tusk_left_r1", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.25F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 2.0F, -6.25F, 0.0F, 0.0F, 0.1745F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(42, 0).addBox(-0.5F, -1.0F, -3.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -3.0F, -1.0F, -0.1396F, 0.3142F, -0.4363F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(30, 0).addBox(-1.5F, -1.0F, -3.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -3.0F, -1.0F, -0.1396F, -0.3142F, 0.4363F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition feather_left_5_r1 = wing_left.addOrReplaceChild("feather_left_5_r1", CubeListBuilder.create().texOffs(16, 59).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 4.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition feather_left_4_r1 = wing_left.addOrReplaceChild("feather_left_4_r1", CubeListBuilder.create().texOffs(12, 59).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition feather_left_3_r1 = wing_left.addOrReplaceChild("feather_left_3_r1", CubeListBuilder.create().texOffs(8, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition feather_left_2_r1 = wing_left.addOrReplaceChild("feather_left_2_r1", CubeListBuilder.create().texOffs(4, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -1.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition feather_left_1_r1 = wing_left.addOrReplaceChild("feather_left_1_r1", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -4.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(20, 42).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition feather_right_6_r1 = wing_right.addOrReplaceChild("feather_right_6_r1", CubeListBuilder.create().texOffs(32, 59).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 4.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition feather_right_5_r1 = wing_right.addOrReplaceChild("feather_right_5_r1", CubeListBuilder.create().texOffs(36, 59).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition feather_right_4_r1 = wing_right.addOrReplaceChild("feather_right_4_r1", CubeListBuilder.create().texOffs(28, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition feather_right_3_r1 = wing_right.addOrReplaceChild("feather_right_3_r1", CubeListBuilder.create().texOffs(24, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -1.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition feather_right__r1 = wing_right.addOrReplaceChild("feather_right__r1", CubeListBuilder.create().texOffs(20, 59).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -4.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(52, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.5F, -3.25F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(40, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -4.5F, -3.25F));

        PartDefinition leg_back_left = body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(52, 41).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -6.0F, 6.5F));

        PartDefinition leg_back_right = body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(40, 41).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -6.0F, 6.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -9.0F, 7.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition tail_1_r1 = tail.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(44, 26).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition tail_2_r1 = tail.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(44, 16).addBox(0.0F, -2.2018F, -2.69F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 2.0F, -0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.leg_back_right.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg_back_left.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg_front_right.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg_front_left.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


        if (!Minecraft.getInstance().isPaused()) {
            float aimingForFold;
            if (entity.isEntityOnGround()) {
                aimingForFold = 0.1F;
            } else {
                aimingForFold = 1.0F;
            }
            entity.setWingAngle(entity.getWingFold() * Mth.sin(ageInTicks / 15.9F));
            entity.setWingFold(entity.getWingFold() + ((aimingForFold - entity.getWingFold()) / 37.5F));
            float wingBend = -((float) Math.acos(entity.getWingFold()));
            this.wing_left.zRot = -(entity.getWingAngle() + wingBend + Mth.HALF_PI);
            this.wing_right.zRot = -this.wing_left.zRot;
        }
        if (entity.isBaby()) {
            this.applyStatic(PhygAnimation.BABY);
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}