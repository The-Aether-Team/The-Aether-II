package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.SentryGolemAnimation;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class SentryGolemModel extends HierarchicalModel<SentryGolem> implements ArmedModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart left_arm_bottom;
    private final ModelPart right_arm;
    private final ModelPart right_arm_bottom;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public SentryGolemModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.torso = this.body.getChild("torso");
        this.head = this.torso.getChild("head");
        this.left_arm = this.torso.getChild("left_arm");
        this.left_arm_bottom = this.left_arm.getChild("left_arm_bottom");
        this.right_arm = this.torso.getChild("right_arm");
        this.right_arm_bottom = this.right_arm.getChild("right_arm_bottom");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-7.0F, -0.5F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 23).addBox(-7.5F, -4.5F, -5.0F, 15.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-5.5F, -6.5F, -5.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-2.0F, -5.5F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 44).addBox(-2.0F, 3.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 44).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(-5.0F, 6.0F, -4.0F, 10.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 2.5F, 0.0F));

        PartDefinition rotator = torso.addOrReplaceChild("rotator", CubeListBuilder.create().texOffs(0, 41).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition left_shoulder = torso.addOrReplaceChild("left_shoulder", CubeListBuilder.create(), PartPose.offset(7.0F, -2.5F, 0.0F));

        PartDefinition left_shoulder_guard_r1 = left_shoulder.addOrReplaceChild("left_shoulder_guard_r1", CubeListBuilder.create().texOffs(50, 0).mirror().addBox(0.0F, -4.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 14).mirror().addBox(0.5F, -3.0F, -3.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.2182F));

        PartDefinition right_shoulder = torso.addOrReplaceChild("right_shoulder", CubeListBuilder.create(), PartPose.offset(-7.0F, -2.5F, 0.0F));

        PartDefinition right_shoulder_r1 = right_shoulder.addOrReplaceChild("right_shoulder_r1", CubeListBuilder.create().texOffs(88, 14).addBox(-7.5F, -3.0F, -3.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, -0.2182F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(50, 25).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 25).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -1.5F, 0.5F, 0.0873F, 0.0F, -0.1309F));

        PartDefinition left_arm_bottom = left_arm.addOrReplaceChild("left_arm_bottom", CubeListBuilder.create().texOffs(50, 34).mirror().addBox(-1.5F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 36).addBox(0.5F, 6.0F, -2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(66, 30).mirror().addBox(2.5F, -4.0F, -2.0F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(88, 25).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 25).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -1.5F, 0.5F, 0.0873F, 0.0F, 0.1309F));

        PartDefinition right_arm_bottom = right_arm.addOrReplaceChild("right_arm_bottom", CubeListBuilder.create().texOffs(88, 34).addBox(-2.5F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 30).addBox(-2.5F, -4.0F, -2.0F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition hand = right_arm_bottom.addOrReplaceChild("hand", CubeListBuilder.create().texOffs(114, 31).addBox(-1.25F, -1.25F, -1.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(114, 37).addBox(-0.25F, -1.25F, -2.75F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, 7.25F, 0.25F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(50, 45).mirror().addBox(-1.5F, -2.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(70, 46).mirror().addBox(-0.5F, 3.5F, -0.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 48).mirror().addBox(1.0F, 8.0F, -4.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(72, 59).mirror().addBox(-3.0F, 8.0F, 2.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(11.5F, 12.5F, -1.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(88, 45).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(108, 46).addBox(-2.5F, 3.5F, -0.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(88, 48).addBox(-1.0F, 8.0F, -4.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(110, 59).addBox(-5.0F, 8.0F, 2.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 12.5F, -1.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(SentryGolem golem, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * Mth.DEG_TO_RAD + 0.0873F;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.animateWalk(SentryGolemAnimation.walk, limbSwing, limbSwingAmount, 1.0F, 1.0F);
        this.animateWalk(SentryGolemAnimation.run, limbSwing, limbSwingAmount, 1.0F, 1.0F);
        this.animateWalk(SentryGolemAnimation.idle, ageInTicks, ageInTicks, 1.0F, 1.0F);
        this.animate(golem.checkSelfAnimationState, SentryGolemAnimation.check_self, ageInTicks, 1.0F);
        this.animate(golem.lookAroundAnimationState, SentryGolemAnimation.look_around, ageInTicks, 1.0F);
        this.animate(golem.attackAnimationState, SentryGolemAnimation.swing_melee_weapon, ageInTicks, 1.0F);
        this.animate(golem.attackReadyAnimationState, SentryGolemAnimation.ready_melee_weapon, ageInTicks, 1.0F);
        this.animate(golem.attackRangeAnimationState, SentryGolemAnimation.fire_ranged_weapon, ageInTicks, 1.0F);
        this.animate(golem.attackRangeReadyAnimationState, SentryGolemAnimation.ready_ranged_weapon, ageInTicks, 1.0F);
    }

    private ModelPart getArmBottom(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_arm_bottom : this.right_arm_bottom;
    }

    private ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        this.body.translateAndRotate(poseStack);
        this.torso.translateAndRotate(poseStack);
        this.getArm(side).translateAndRotate(poseStack);
        this.getArmBottom(side).translateAndRotate(poseStack);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
