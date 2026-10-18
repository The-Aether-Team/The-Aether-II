package com.aetherteam.aetherii.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class LykonModel<T extends LivingEntityRenderState> extends EntityModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final ModelPart body_rear;
    private final ModelPart tail;
    private final ModelPart tail_end;
    private final ModelPart leg_rear_left;
    private final ModelPart foot_rear_left;
    private final ModelPart leg_rear_right;
    private final ModelPart foot_rear_right;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_left_2;
    private final ModelPart foot_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_front_right_2;
    private final ModelPart foot_front_right;

    public LykonModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.ear_left = this.head.getChild("ear_left");
        this.ear_right = this.head.getChild("ear_right");
        this.body_rear = this.body.getChild("body_rear");
        this.tail = this.body_rear.getChild("tail");
        this.tail_end = this.tail.getChild("tail_end");
        this.leg_rear_left = this.body_rear.getChild("leg_rear_left");
        this.foot_rear_left = this.leg_rear_left.getChild("foot_rear_left");
        this.leg_rear_right = this.body_rear.getChild("leg_rear_right");
        this.foot_rear_right = this.leg_rear_right.getChild("foot_rear_right");
        this.leg_front_left = this.body.getChild("leg_front_left");
        this.leg_front_left_2 = this.leg_front_left.getChild("leg_front_left_2");
        this.foot_front_left = this.leg_front_left_2.getChild("foot_front_left");
        this.leg_front_right = this.body.getChild("leg_front_right");
        this.leg_front_right_2 = this.leg_front_right.getChild("leg_front_right_2");
        this.foot_front_right = this.leg_front_right_2.getChild("foot_front_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 1.0F));

        PartDefinition body_plate_r1 = body.addOrReplaceChild("body_plate_r1", CubeListBuilder.create().texOffs(12, 25).addBox(-3.0F, 0.8868F, -0.6894F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.9F, -7.8F, 0.2618F, 0.0F, 0.0F));

        PartDefinition body_front_r1 = body.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(29, 25).addBox(-6.0F, 0.0F, -9.0F, 12.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(35, 12).addBox(-4.0F, -2.5F, -7.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.2F, -8.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition head_snout_r1 = head.addOrReplaceChild("head_snout_r1", CubeListBuilder.create().texOffs(66, 7).addBox(-2.0F, -0.5343F, -5.2386F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.3F, 0.0349F, 0.0F, 0.0F));

        PartDefinition head_fur_r1 = head.addOrReplaceChild("head_fur_r1", CubeListBuilder.create().texOffs(7, 16).addBox(-4.0F, -2.0F, -4.0F, 14.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.4F, 0.7F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(68, 15).addBox(-1.5F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 20).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -7.0F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offset(1.02F, -2.45F, -4.34F));

        PartDefinition ear_left_r1 = ear_left.addOrReplaceChild("ear_left_r1", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, -8.1F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.18F, 0.15F, 2.04F, -0.6981F, 0.0F, 0.48F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offset(-1.02F, -2.5F, -4.34F));

        PartDefinition ear_right_r1 = ear_right.addOrReplaceChild("ear_right_r1", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -8.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.18F, 0.2F, 2.04F, -0.6981F, 0.0F, -0.48F));

        PartDefinition body_rear = body.addOrReplaceChild("body_rear", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body_rear_r1 = body_rear.addOrReplaceChild("body_rear_r1", CubeListBuilder.create().texOffs(32, 41).addBox(-6.0F, 0.0F, 0.0F, 10.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail = body_rear.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 55).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.6F, 6.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition tail_end = tail.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(36, 62).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1F, 4.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition tail_fur_2_r1 = tail_end.addOrReplaceChild("tail_fur_2_r1", CubeListBuilder.create().texOffs(11, 81).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5847F));

        PartDefinition tail_fur_1_r1 = tail_end.addOrReplaceChild("tail_fur_1_r1", CubeListBuilder.create().texOffs(39, 81).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5847F));

        PartDefinition leg_rear_left = body_rear.addOrReplaceChild("leg_rear_left", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 5.5F, 5.4F, -0.6109F, 0.0F, 0.0F));

        PartDefinition leg_rear_left_r1 = leg_rear_left.addOrReplaceChild("leg_rear_left_r1", CubeListBuilder.create().texOffs(77, 61).addBox(-1.0F, -1.0F, -1.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1066F, -1.024F, 0.0F, 0.0F, -0.2618F));

        PartDefinition foot_rear_left = leg_rear_left.addOrReplaceChild("foot_rear_left", CubeListBuilder.create(), PartPose.offset(2.5F, 4.5F, 2.5F));

        PartDefinition foot_rear_left_r1 = foot_rear_left.addOrReplaceChild("foot_rear_left_r1", CubeListBuilder.create().texOffs(77, 73).addBox(-1.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 2.7066F, -1.524F, 0.6109F, 0.0F, 0.0F));

        PartDefinition leg_rear_right = body_rear.addOrReplaceChild("leg_rear_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 5.5F, 5.4F, -0.6109F, 0.0F, 0.0F));

        PartDefinition leg_rear_right_r1 = leg_rear_right.addOrReplaceChild("leg_rear_right_r1", CubeListBuilder.create().texOffs(5, 61).addBox(-3.0F, -1.0F, -1.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1066F, -1.024F, 0.0F, 0.0F, 0.2618F));

        PartDefinition foot_rear_right = leg_rear_right.addOrReplaceChild("foot_rear_right", CubeListBuilder.create(), PartPose.offset(-2.5F, 4.5F, 2.5F));

        PartDefinition foot_rear_right_r1 = foot_rear_right.addOrReplaceChild("foot_rear_right_r1", CubeListBuilder.create().texOffs(5, 73).addBox(-2.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 2.7066F, -1.524F, 0.6109F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 0.0F, -6.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition leg_front_left_1_r1 = leg_front_left.addOrReplaceChild("leg_front_left_1_r1", CubeListBuilder.create().texOffs(78, 34).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.2588F, 0.4659F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_front_left_2 = leg_front_left.addOrReplaceChild("leg_front_left_2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.6F, 3.0F, 1.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition leg_front_left_plate_r1 = leg_front_left_2.addOrReplaceChild("leg_front_left_plate_r1", CubeListBuilder.create().texOffs(92, 42).addBox(1.0F, -0.6527F, -1.6378F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.6588F, 0.9341F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_front_left_2_r1 = leg_front_left_2.addOrReplaceChild("leg_front_left_2_r1", CubeListBuilder.create().texOffs(80, 43).addBox(-1.0F, -0.6527F, -1.6378F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, 0.2588F, -1.0659F, 0.0F, 0.0F, -0.5236F));

        PartDefinition foot_front_left = leg_front_left_2.addOrReplaceChild("foot_front_left", CubeListBuilder.create().texOffs(79, 51).addBox(-2.4F, 0.1F, -2.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(86, 54).addBox(0.6F, 0.1F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 54).addBox(-1.4F, 0.1F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.8F, 3.4F, -1.2F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, 0.0F, -6.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition leg_front_right_1_r1 = leg_front_right.addOrReplaceChild("leg_front_right_1_r1", CubeListBuilder.create().texOffs(6, 34).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.2588F, 0.4659F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_front_right_2 = leg_front_right.addOrReplaceChild("leg_front_right_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.6F, 3.0F, 1.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition leg_front_right_plate_r1 = leg_front_right_2.addOrReplaceChild("leg_front_right_plate_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -0.6527F, -1.6378F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.6588F, 0.9341F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_front_right_2_r1 = leg_front_right_2.addOrReplaceChild("leg_front_right_2_r1", CubeListBuilder.create().texOffs(8, 43).addBox(-2.0F, -0.6527F, -1.6378F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.2588F, -1.0659F, 0.0F, 0.0F, 0.5236F));

        PartDefinition foot_front_right = leg_front_right_2.addOrReplaceChild("foot_front_right", CubeListBuilder.create().texOffs(7, 51).addBox(-1.6F, 0.1F, -2.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 54).addBox(-0.6F, 0.1F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 54).addBox(1.4F, 0.1F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.8F, 3.4F, -1.2F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity) {

    }

}