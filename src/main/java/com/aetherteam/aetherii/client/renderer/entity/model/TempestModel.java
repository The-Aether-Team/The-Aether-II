package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.monster.Tempest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TempestModel extends EntityModel<Tempest> {
	private final ModelPart body;

	public TempestModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(47, 69).addBox(-3.5F, -12.0F, -3.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));

		body.addOrReplaceChild("legs_right_r1", CubeListBuilder.create().texOffs(18, 96).addBox(0.0F, 0.0F, -3.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -6.0F, 5.5F, 0.0F, 0.0F, 0.2618F));

		body.addOrReplaceChild("legs_left_r1", CubeListBuilder.create().texOffs(94, 96).addBox(-1.0F, 0.0F, -3.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -6.0F, 5.5F, 0.0F, 0.0F, -0.2618F));

		body.addOrReplaceChild("wingcase_right_r1", CubeListBuilder.create().texOffs(8, 76).addBox(-3.0F, -2.0F, -1.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -11.0F, 5.0F, 0.2182F, -0.3491F, -0.1309F));

		body.addOrReplaceChild("wingcase_left_r1", CubeListBuilder.create().texOffs(80, 76).addBox(-2.0F, -2.0F, -1.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -11.0F, 5.0F, 0.2182F, 0.3491F, 0.1309F));

		PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create(), PartPose.offset(3.0F, -5.5F, 0.0F));

		arm_left.addOrReplaceChild("claw_left_r1", CubeListBuilder.create().texOffs(84, 111).addBox(0.75F, 8.5F, -6.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.6981F, 0.0F, 0.0436F));

		arm_left.addOrReplaceChild("arm_left_2_r1", CubeListBuilder.create().texOffs(80, 100).addBox(-0.75F, 2.5F, -3.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.2618F, 0.0F, 0.0436F));

		arm_left.addOrReplaceChild("arm_left_1_r1", CubeListBuilder.create().texOffs(83, 94).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, -0.2618F));

		PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create(), PartPose.offset(-3.0F, -5.5F, 0.0F));

		arm_right.addOrReplaceChild("claw_right_r1", CubeListBuilder.create().texOffs(38, 111).addBox(-1.75F, 8.5F, -6.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.6981F, 0.0F, -0.0436F));

		arm_right.addOrReplaceChild("arm_right_2_r1", CubeListBuilder.create().texOffs(34, 100).addBox(-3.25F, 2.5F, -3.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.2618F, 0.0F, -0.0436F));

		arm_right.addOrReplaceChild("arm_right_1_r1", CubeListBuilder.create().texOffs(37, 94).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.2618F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("head_inner_r1", CubeListBuilder.create().texOffs(55, 98).addBox(-4.0F, 0.0F, -1.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 103).addBox(-4.0F, 0.0F, -7.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(48, 86).addBox(-4.0F, -4.0F, -7.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5F, -3.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(56, 115).addBox(-1.0F, -10.5F, 6.0F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(78, 112).addBox(-1.0F, -1.5F, 8.0F, 1.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 7.0F));

		tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(104, 114).addBox(-1.0F, -1.5F, 12.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition cloud_shell = body.addOrReplaceChild("cloud_shell", CubeListBuilder.create().texOffs(30, 0).addBox(-7.5F, -6.0F, -11.0F, 15.0F, 10.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(30, 29).addBox(-7.5F, -6.0F, -11.0F, 15.0F, 10.0F, 19.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		cloud_shell.addOrReplaceChild("wisp_right_r1", CubeListBuilder.create().texOffs(7, 21).addBox(-1.5F, 0.0F, 0.0F, 1.0F, 7.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -5.0F, 8.0F, 0.0F, -0.2618F, -0.1309F));

		cloud_shell.addOrReplaceChild("wisp_left_r1", CubeListBuilder.create().texOffs(79, 21).addBox(0.5F, 0.0F, 0.0F, 1.0F, 7.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -5.0F, 8.0F, 0.0F, 0.2618F, 0.1309F));

		PartDefinition fin_right = cloud_shell.addOrReplaceChild("fin_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.5F, -1.0F, -8.0F, 0.0F, -0.0873F, 0.0F));

		fin_right.addOrReplaceChild("fin_wisp_right_r1", CubeListBuilder.create().texOffs(1, 4).addBox(1.15F, -9.0F, 6.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, 6.5F, 0.0F, -0.3054F, 0.0F));

		fin_right.addOrReplaceChild("fin_right_r1", CubeListBuilder.create().texOffs(19, 0).addBox(1.0F, -4.0F, 1.0F, 3.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition fin_left = cloud_shell.addOrReplaceChild("fin_left", CubeListBuilder.create(), PartPose.offsetAndRotation(8.5F, -1.0F, -8.0F, 0.0F, 0.0873F, 0.0F));

		fin_left.addOrReplaceChild("fin_wisp_left_r1", CubeListBuilder.create().texOffs(109, 4).addBox(-2.15F, -9.0F, 6.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, 6.5F, 0.0F, 0.3054F, 0.0F));

		fin_left.addOrReplaceChild("fin_left_r1", CubeListBuilder.create().texOffs(79, 0).addBox(-4.0F, -4.0F, 1.0F, 3.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition jaw = cloud_shell.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(47, 58).addBox(-6.0F, -3.1F, -3.75F, 12.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -9.5F, 0.0873F, 0.0F, 0.0F));

		jaw.addOrReplaceChild("jaw_left_2_r1", CubeListBuilder.create().texOffs(104, 55).addBox(0.25F, -4.6F, 10.2F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.1F))
		.texOffs(80, 58).addBox(-0.75F, -2.6F, 1.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.0F, -0.5F, -5.0F, 0.0F, 0.5236F, 0.0F));

		jaw.addOrReplaceChild("jaw_right_2_r1", CubeListBuilder.create().texOffs(0, 55).addBox(-2.25F, -4.6F, 10.2F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.1F))
		.texOffs(24, 58).addBox(-2.25F, -2.6F, 1.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.0F, -0.5F, -5.0F, 0.0F, -0.5236F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Tempest entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}