package com.aetherteam.aetherii.client.renderer.entity.model.taegore;

import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TaegoreModel extends HierarchicalModel<Taegore> {
	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart head_main;
	private final ModelPart head_lower;
	private final ModelPart head_upper;
	private final ModelPart crest_mid;
	private final ModelPart crest_right;
	private final ModelPart crest_left;
	private final ModelPart snout;
	private final ModelPart ear_right;
	private final ModelPart ear_left;
	private final ModelPart body_front;
	private final ModelPart plate_front_right;
	private final ModelPart plate_rear_right;
	private final ModelPart body_rear;
	private final ModelPart plate_rear_left;
	private final ModelPart plate_front_left;
	private final ModelPart leg_front_right;
	private final ModelPart leg_front_right_lower;
	private final ModelPart leg_front_left;
	private final ModelPart leg_front_left_lower;
	private final ModelPart leg_rear_right;
	private final ModelPart leg_rear_right_lower;
	private final ModelPart leg_rear_left;
	private final ModelPart leg_rear_left_lower;
	private final ModelPart tail;
	private final ModelPart spines;

	public TaegoreModel(ModelPart root) {
		this.root = root;
		this.body_main = root.getChild("body_main");
		this.head_main = body_main.getChild("head_main");
		this.head_lower = head_main.getChild("head_lower");
		this.head_upper = head_main.getChild("head_upper");
		this.crest_mid = head_main.getChild("crest_mid");
		this.crest_right = head_main.getChild("crest_right");
		this.crest_left = head_main.getChild("crest_left");
		this.snout = head_main.getChild("snout");
		this.ear_right = head_main.getChild("ear_right");
		this.ear_left = head_main.getChild("ear_left");
		this.body_front = body_main.getChild("body_front");
		this.plate_front_right = body_main.getChild("plate_front_right");
		this.plate_rear_right = body_main.getChild("plate_rear_right");
		this.body_rear = body_main.getChild("body_rear");
		this.plate_rear_left = body_main.getChild("plate_rear_left");
		this.plate_front_left = body_main.getChild("plate_front_left");
		this.leg_front_right = body_main.getChild("leg_front_right");
		this.leg_front_right_lower = leg_front_right.getChild("leg_front_right_lower");
		this.leg_front_left = body_main.getChild("leg_front_left");
		this.leg_front_left_lower = leg_front_left.getChild("leg_front_left_lower");
		this.leg_rear_right = body_main.getChild("leg_rear_right");
		this.leg_rear_right_lower = leg_rear_right.getChild("leg_rear_right_lower");
		this.leg_rear_left = body_main.getChild("leg_rear_left");
		this.leg_rear_left_lower = leg_rear_left.getChild("leg_rear_left_lower");
		this.tail = body_main.getChild("tail");
		this.spines = body_main.getChild("spines");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(19, 67).mirror().addBox(-4.5F, 5.0F, -12.0F, 9.0F, 12.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition head_main = body_main.addOrReplaceChild("head_main", CubeListBuilder.create().texOffs(33, 11).mirror().addBox(-5.0F, -3.1128F, -9.9981F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.5F, -8.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head_lower = head_main.addOrReplaceChild("head_lower", CubeListBuilder.create().texOffs(27, 38).mirror().addBox(-2.0F, 3.4F, -10.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0872F, -0.9981F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head_upper = head_main.addOrReplaceChild("head_upper", CubeListBuilder.create().texOffs(39, 26).mirror().addBox(-2.0F, -6.9F, -12.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0872F, -1.9981F, 0.7854F, 0.0F, 0.0F));

		PartDefinition crest_mid = head_main.addOrReplaceChild("crest_mid", CubeListBuilder.create().texOffs(41, 0).mirror().addBox(-1.5F, -9.0F, -8.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0872F, -1.9981F, 0.5236F, 0.0F, 0.0F));

		PartDefinition crest_right = head_main.addOrReplaceChild("crest_right", CubeListBuilder.create().texOffs(61, 4).mirror().addBox(-4.0F, -9.1F, -6.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0872F, -1.9981F, 0.5236F, 0.0F, -0.1745F));

		PartDefinition crest_left = head_main.addOrReplaceChild("crest_left", CubeListBuilder.create().texOffs(29, 4).mirror().addBox(2.0F, -9.1F, -6.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0872F, -1.9981F, 0.5236F, 0.0F, 0.1745F));

		PartDefinition snout = head_main.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(41, 56).mirror().addBox(-2.5F, 2.4F, -16.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 56).addBox(-4.5F, 3.4F, -15.0F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0872F, -1.9981F, 0.0873F, 0.0F, 0.0F));

		PartDefinition ear_right = head_main.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(61, 11).addBox(0.0F, -1.2F, -0.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0872F, -7.9981F, 0.1745F, -0.7854F, 0.0F));

		PartDefinition ear_left = head_main.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(29, 11).addBox(-1.0F, -1.1F, -0.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0872F, -7.9981F, 0.1745F, 0.7854F, 0.0F));

		PartDefinition body_front = body_main.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-6.0F, -5.0F, -13.0F, 12.0F, 16.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition plate_front_right = body_main.addOrReplaceChild("plate_front_right", CubeListBuilder.create().texOffs(76, 0).mirror().addBox(-9.0F, -1.0F, -8.5F, 4.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.5F, 0.0F, 0.0F, -0.1745F, 0.3491F));

		PartDefinition plate_rear_right = body_main.addOrReplaceChild("plate_rear_right", CubeListBuilder.create().texOffs(76, 49).mirror().addBox(-9.5F, 2.5F, 8.5F, 4.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, -4.5F, 0.0873F, 0.1745F, 0.3491F));

		PartDefinition body_rear = body_main.addOrReplaceChild("body_rear", CubeListBuilder.create().texOffs(56, 102).mirror().addBox(-5.0F, -3.0F, -2.5F, 10.0F, 13.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition plate_rear_left = body_main.addOrReplaceChild("plate_rear_left", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(5.5F, 2.5F, 8.5F, 4.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, -4.5F, 0.0873F, -0.1745F, -0.3491F));

		PartDefinition plate_front_left = body_main.addOrReplaceChild("plate_front_left", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(5.0F, -1.0F, -8.5F, 4.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.5F, 0.0F, 0.0F, 0.1745F, -0.3491F));

		PartDefinition leg_front_right = body_main.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offset(-5.0F, 13.0F, -4.5F));

		PartDefinition leg_front_right_r1 = leg_front_right.addOrReplaceChild("leg_front_right_r1", CubeListBuilder.create().texOffs(78, 22).mirror().addBox(-7.5F, -14.0F, -9.25F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 11.0F, 7.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition leg_front_right_lower = leg_front_right.addOrReplaceChild("leg_front_right_lower", CubeListBuilder.create().texOffs(83, 35).mirror().addBox(-1.0F, 0.0F, -0.75F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg_front_left = body_main.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offset(5.0F, 13.0F, -4.5F));

		PartDefinition leg_front_left_r1 = leg_front_left.addOrReplaceChild("leg_front_left_r1", CubeListBuilder.create().texOffs(2, 22).mirror().addBox(2.5F, -14.0F, -9.25F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 11.0F, 7.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition leg_front_left_lower = leg_front_left.addOrReplaceChild("leg_front_left_lower", CubeListBuilder.create().texOffs(7, 35).mirror().addBox(-2.0F, 0.0F, -0.75F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg_rear_right = body_main.addOrReplaceChild("leg_rear_right", CubeListBuilder.create(), PartPose.offset(-5.0F, 13.0F, 9.5F));

		PartDefinition leg_rear_right_r1 = leg_rear_right.addOrReplaceChild("leg_rear_right_r1", CubeListBuilder.create().texOffs(78, 69).mirror().addBox(-6.5F, -11.0F, 4.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 11.0F, -6.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition leg_rear_right_lower = leg_rear_right.addOrReplaceChild("leg_rear_right_lower", CubeListBuilder.create().texOffs(83, 81).mirror().addBox(-0.5F, 1.0F, -0.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition leg_rear_left = body_main.addOrReplaceChild("leg_rear_left", CubeListBuilder.create(), PartPose.offset(5.0F, 13.0F, 9.5F));

		PartDefinition leg_rear_left_r1 = leg_rear_left.addOrReplaceChild("leg_rear_left_r1", CubeListBuilder.create().texOffs(2, 69).mirror().addBox(1.5F, -11.0F, 4.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 11.0F, -6.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition leg_rear_left_lower = leg_rear_left.addOrReplaceChild("leg_rear_left_lower", CubeListBuilder.create().texOffs(7, 81).mirror().addBox(-2.5F, 1.0F, -0.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 102).mirror().addBox(-6.0F, 0.4F, -1.5F, 12.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.7F, 15.8F, 0.2618F, 0.0F, 0.0F));

		PartDefinition spines = body_main.addOrReplaceChild("spines", CubeListBuilder.create().texOffs(36, 117).mirror().addBox(0.0F, -6.7F, -2.5F, 1.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 114, 139);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Taegore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head_main.xRot = headPitch * Mth.DEG_TO_RAD;
		this.head_main.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.leg_rear_right.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_rear_left.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_front_right.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_front_left.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}