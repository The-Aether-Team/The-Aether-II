package com.aetherteam.aetherii.client.renderer.entity.model.burrukai;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ArcticBurrukaiModel extends AbstractBurrukaiModel {
	public ArcticBurrukaiModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(20, 85).mirror().addBox(-9.0F, -0.8F, -1.0F, 18.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -4.0F, -8.0F));

		PartDefinition body_front = body_main.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(27, 106).mirror().addBox(-6.0F, 7.0F, -1.0F, 12.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_rear = body_main.addOrReplaceChild("body_rear", CubeListBuilder.create().texOffs(31, 129).mirror().addBox(-4.0F, -1.0F, 11.5F, 8.0F, 15.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1396F, 0.0F, 0.0F));

		PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(71, 145).mirror().addBox(-2.0F, 0.4F, -1.0F, 4.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.6F, 23.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition body_plate_rear = body_main.addOrReplaceChild("body_plate_rear", CubeListBuilder.create().texOffs(63, 117).mirror().addBox(-5.0F, 0.0F, 1.2F, 10.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition body_plate_front = body_main.addOrReplaceChild("body_plate_front", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-4.5F, -2.2F, -3.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition body_neck = body_main.addOrReplaceChild("body_neck", CubeListBuilder.create().texOffs(37, 68).mirror().addBox(-3.0F, 2.2F, -1.0F, 6.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition leg_front_left = body_main.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(66, 12).mirror().addBox(-5.0F, -2.0F, -3.0F, 5.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 7.0F, 5.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition leg_front_left_lower = leg_front_left.addOrReplaceChild("leg_front_left_lower", CubeListBuilder.create().texOffs(70, 33).mirror().addBox(-7.5F, 10.5F, 0.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0873F));

		PartDefinition leg_front_left_plate = leg_front_left.addOrReplaceChild("leg_front_left_plate", CubeListBuilder.create().texOffs(71, 48).mirror().addBox(-5.5F, -12.0F, -5.0F, 4.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition leg_front_right = body_main.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(12, 12).mirror().addBox(0.0F, -2.0F, -3.0F, 5.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition leg_front_right_lower = leg_front_right.addOrReplaceChild("leg_front_right_lower", CubeListBuilder.create().texOffs(16, 33).mirror().addBox(-0.5F, 10.5F, 0.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, -0.0873F));

		PartDefinition leg_front_right_plate = leg_front_right.addOrReplaceChild("leg_front_right_plate", CubeListBuilder.create().texOffs(1, 48).mirror().addBox(1.5F, -12.0F, -5.0F, 4.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition leg_rear_right = body_main.addOrReplaceChild("leg_rear_right", CubeListBuilder.create().texOffs(77, 72).mirror().addBox(-2.0F, -2.0F, -1.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 8.0F, 18.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg_rear_right_lower = leg_rear_right.addOrReplaceChild("leg_rear_right_lower", CubeListBuilder.create().texOffs(82, 92).mirror().addBox(-1.0F, 9.0F, 1.5F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition leg_rear_left = body_main.addOrReplaceChild("leg_rear_left", CubeListBuilder.create().texOffs(1, 72).mirror().addBox(-3.0F, -2.0F, -1.5F, 5.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 8.0F, 18.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg_rear_left_lower = leg_rear_left.addOrReplaceChild("leg_rear_left_lower", CubeListBuilder.create().texOffs(6, 92).mirror().addBox(-2.0F, 9.0F, 1.5F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_main = body_main.addOrReplaceChild("head_main", CubeListBuilder.create().texOffs(36, 12).mirror().addBox(-5.0F, -7.0F, -3.0F, 10.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, -3.5F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head_front = head_main.addOrReplaceChild("head_front", CubeListBuilder.create().texOffs(31, 40).mirror().addBox(-3.0F, -7.0F, -11.0F, 6.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.733F, 0.0F, 0.0F));

		PartDefinition head_plate = head_main.addOrReplaceChild("head_plate", CubeListBuilder.create().texOffs(33, 0).mirror().addBox(-4.0F, -8.5F, -3.5F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition antler_right = head_main.addOrReplaceChild("antler_right", CubeListBuilder.create().texOffs(83, 140).mirror().addBox(5.0F, -17.0F, -1.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.75F, 0.2508F, -0.085F, -0.1013F));

		PartDefinition antler_left = head_main.addOrReplaceChild("antler_left", CubeListBuilder.create().texOffs(0, 140).addBox(-15.0F, -17.0F, -1.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.75F, 0.2508F, 0.085F, 0.1013F));

		PartDefinition ear_left = head_main.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(58, 40).mirror().addBox(-4.5F, -0.5F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition ear_right = head_main.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(36, 40).mirror().addBox(3.5F, -0.5F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition head_rear = head_main.addOrReplaceChild("head_rear", CubeListBuilder.create().texOffs(36, 22).mirror().addBox(-3.0F, -1.75F, -6.5F, 6.0F, 9.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mouth = head_main.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(46, 64).mirror().addBox(-1.5F, 0.05F, -0.1F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.2F, -10.8F));

		return LayerDefinition.create(meshdefinition, 107, 156);
	}
}