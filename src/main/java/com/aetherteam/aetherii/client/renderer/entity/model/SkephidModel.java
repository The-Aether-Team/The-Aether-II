package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.SkephidAnimations;
import com.aetherteam.aetherii.entity.monster.Skephid;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SkephidModel<T extends Skephid> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart mouth;

    public SkephidModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.mouth = this.head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 25).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -3.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(20, 34).addBox(-6.25F, -4.0F, -6.25F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -6.5F));

        PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(29, 46).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offset(3.0F, -4.5F, -2.0F));

        PartDefinition leg_front_left_1_r1 = leg_front_left.addOrReplaceChild("leg_front_left_1_r1", CubeListBuilder.create().texOffs(49, 42).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_front_left_2 = leg_front_left.addOrReplaceChild("leg_front_left_2", CubeListBuilder.create(), PartPose.offset(1.5F, 0.0F, -1.5F));

        PartDefinition leg_front_left_2_r1 = leg_front_left_2.addOrReplaceChild("leg_front_left_2_r1", CubeListBuilder.create().texOffs(49, 49).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, -0.7854F, 0.0F));

        PartDefinition leg_front_left_3 = leg_front_left_2.addOrReplaceChild("leg_front_left_3", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2.0F, -2.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_front_left_3_r1 = leg_front_left_3.addOrReplaceChild("leg_front_left_3_r1", CubeListBuilder.create().texOffs(44, 55).addBox(-1.5F, -1.5F, -6.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3526F, 0.0F, 0.0F));

        PartDefinition leg_rear_left = body.addOrReplaceChild("leg_rear_left", CubeListBuilder.create(), PartPose.offset(3.0F, -4.5F, 2.0F));

        PartDefinition leg_rear_left_1_r1 = leg_rear_left.addOrReplaceChild("leg_rear_left_1_r1", CubeListBuilder.create().texOffs(49, 16).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_rear_left_2 = leg_rear_left.addOrReplaceChild("leg_rear_left_2", CubeListBuilder.create(), PartPose.offset(1.5F, 0.0F, 1.5F));

        PartDefinition leg_rear_left_2_r1 = leg_rear_left_2.addOrReplaceChild("leg_rear_left_2_r1", CubeListBuilder.create().texOffs(49, 23).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.7854F, 0.0F));

        PartDefinition leg_rear_left_3 = leg_rear_left_2.addOrReplaceChild("leg_rear_left_3", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2.0F, 2.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_rear_left_3_r1 = leg_rear_left_3.addOrReplaceChild("leg_rear_left_3_r1", CubeListBuilder.create().texOffs(44, 29).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offset(-3.0F, -4.5F, -2.0F));

        PartDefinition leg_front_right_1_r1 = leg_front_right.addOrReplaceChild("leg_front_right_1_r1", CubeListBuilder.create().texOffs(5, 42).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_front_right_2 = leg_front_right.addOrReplaceChild("leg_front_right_2", CubeListBuilder.create(), PartPose.offset(-1.5F, 0.0F, -1.5F));

        PartDefinition leg_front_right_2_r1 = leg_front_right_2.addOrReplaceChild("leg_front_right_2_r1", CubeListBuilder.create().texOffs(5, 49).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.7854F, 0.0F));

        PartDefinition leg_front_right_3 = leg_front_right_2.addOrReplaceChild("leg_front_right_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2.0F, -2.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_front_right_3_r1 = leg_front_right_3.addOrReplaceChild("leg_front_right_3_r1", CubeListBuilder.create().texOffs(0, 55).addBox(-1.5F, -1.5F, -6.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3526F, 0.0F, 0.0F));

        PartDefinition leg_rear_right = body.addOrReplaceChild("leg_rear_right", CubeListBuilder.create(), PartPose.offset(-3.0F, -4.5F, 2.0F));

        PartDefinition leg_rear_right_1_r1 = leg_rear_right.addOrReplaceChild("leg_rear_right_1_r1", CubeListBuilder.create().texOffs(5, 16).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_rear_right_2 = leg_rear_right.addOrReplaceChild("leg_rear_right_2", CubeListBuilder.create(), PartPose.offset(-1.5F, 0.0F, 1.5F));

        PartDefinition leg_rear_right_2_r1 = leg_rear_right_2.addOrReplaceChild("leg_rear_right_2_r1", CubeListBuilder.create().texOffs(5, 23).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, -0.7854F, 0.0F));

        PartDefinition leg_rear_right_3 = leg_rear_right_2.addOrReplaceChild("leg_rear_right_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2.0F, 2.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_rear_right_3_r1 = leg_rear_right_3.addOrReplaceChild("leg_rear_right_3_r1", CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

        PartDefinition body_joint = body.addOrReplaceChild("body_joint", CubeListBuilder.create().texOffs(24, 17).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 2.0F));

        PartDefinition abdomen = body_joint.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(18, 4).addBox(-4.0F, -7.0F, -3.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(29, 2).addBox(-1.5F, -9.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition arm_left = abdomen.addOrReplaceChild("arm_left", CubeListBuilder.create(), PartPose.offset(3.0F, -4.0F, 0.0F));

        PartDefinition arm_left_2_r1 = arm_left.addOrReplaceChild("arm_left_2_r1", CubeListBuilder.create().texOffs(47, 0).addBox(-5.0F, -2.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition arm_left_1_r1 = arm_left.addOrReplaceChild("arm_left_1_r1", CubeListBuilder.create().texOffs(52, 4).addBox(0.0F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition arm_right = abdomen.addOrReplaceChild("arm_right", CubeListBuilder.create(), PartPose.offset(-3.0F, -4.0F, 0.0F));

        PartDefinition arm_right_2_r1 = arm_right.addOrReplaceChild("arm_right_2_r1", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, -2.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition arm_right_1_r1 = arm_right.addOrReplaceChild("arm_right_1_r1", CubeListBuilder.create().texOffs(9, 5).addBox(-1.0F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.animateWalk(SkephidAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 4.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
