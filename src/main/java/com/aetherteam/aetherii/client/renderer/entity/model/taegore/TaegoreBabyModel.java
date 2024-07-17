package com.aetherteam.aetherii.client.renderer.entity.model.taegore;

import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TaegoreBabyModel extends HierarchicalModel<Taegore> {
	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart tail;
	private final ModelPart plate_f_l;
	private final ModelPart head;
	private final ModelPart ears;
	private final ModelPart snout;
	private final ModelPart leg_f_l;
	private final ModelPart leg_f_r;
	private final ModelPart leg_b_l;
	private final ModelPart leg_b_r;
	private final ModelPart plate_f_r;
	private final ModelPart plate_b_l;
	private final ModelPart plate_b_r;

	public TaegoreBabyModel(ModelPart root) {
		this.root = root;
		this.body_main = root.getChild("body_main");
		this.tail = body_main.getChild("tail");
		this.plate_f_l = body_main.getChild("plate_f_l");
		this.head = body_main.getChild("head");
		this.ears = head.getChild("ears");
		this.snout = head.getChild("snout");
		this.leg_f_l = body_main.getChild("leg_f_l");
		this.leg_f_r = body_main.getChild("leg_f_r");
		this.leg_b_l = body_main.getChild("leg_b_l");
		this.leg_b_r = body_main.getChild("leg_b_r");
		this.plate_f_r = body_main.getChild("plate_f_r");
		this.plate_b_l = body_main.getChild("plate_b_l");
		this.plate_b_r = body_main.getChild("plate_b_r");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(14, 14).mirror().addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.0F, -0.5F));

		PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 7.5F, -1.0297F, 0.0F, 0.0F));

		PartDefinition plate_f_l = body_main.addOrReplaceChild("plate_f_l", CubeListBuilder.create().texOffs(42, 0).mirror().addBox(-4.0F, -3.0F, -3.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.0873F, 0.0F, -0.0873F));

		PartDefinition head = body_main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-3.0F, -3.0F, -3.4F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.5F, -2.5F, -3.2F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(1, 14).mirror().addBox(-1.5F, -0.1F, -4.4F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.1F, 0.0F));

		PartDefinition leg_f_l = body_main.addOrReplaceChild("leg_f_l", CubeListBuilder.create().texOffs(56, 17).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.2F, 1.0F, -0.9F));

		PartDefinition leg_f_r = body_main.addOrReplaceChild("leg_f_r", CubeListBuilder.create().texOffs(48, 17).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.2F, 1.0F, -0.9F));

		PartDefinition leg_b_l = body_main.addOrReplaceChild("leg_b_l", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.2F, 1.0F, 5.5F));

		PartDefinition leg_b_r = body_main.addOrReplaceChild("leg_b_r", CubeListBuilder.create().texOffs(48, 9).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.2F, 1.0F, 5.5F));

		PartDefinition plate_f_r = body_main.addOrReplaceChild("plate_f_r", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(3.0F, -3.0F, -3.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.0873F, 0.0F, 0.0873F));

		PartDefinition plate_b_l = body_main.addOrReplaceChild("plate_b_l", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-4.0F, -3.0F, -3.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 6.0F, 0.0873F, 0.0F, -0.0873F));

		PartDefinition plate_b_r = body_main.addOrReplaceChild("plate_b_r", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(3.0F, -3.0F, -3.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 6.0F, 0.0873F, 0.0F, 0.0873F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Taegore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = headPitch * Mth.DEG_TO_RAD;
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.leg_b_r.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_b_l.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_f_r.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg_f_l.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}