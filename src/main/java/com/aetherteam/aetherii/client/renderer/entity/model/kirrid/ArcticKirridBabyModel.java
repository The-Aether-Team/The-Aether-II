package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ArcticKirridBabyModel extends AbstractKirridBabyModel {
	public ArcticKirridBabyModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(17, 10).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -0.5F));

		PartDefinition wool = body_main.addOrReplaceChild("wool", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.5F, -1.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body_main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -5.1F, -4.7F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, -2.0F, 0.6374F, 0.0F, 0.0F));

		PartDefinition plate = head.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(1, 0).addBox(-2.5F, -4.0F, -5.3F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-4.0F, -3.5F, -4.5F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -1.25F, -1.5385F, 0.0F, 0.0F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(0, 8).addBox(-3.5F, -2.8F, 1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg_b_l = body_main.addOrReplaceChild("leg_b_l", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.7F, 1.0F, 5.5F));

		PartDefinition leg_b_r = body_main.addOrReplaceChild("leg_b_r", CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.7F, 1.0F, 5.5F));

		PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(29, 25).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg_f_l = body_main.addOrReplaceChild("leg_f_l", CubeListBuilder.create().texOffs(54, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.7F, 1.0F, -0.9F));

		PartDefinition leg_f_r = body_main.addOrReplaceChild("leg_f_r", CubeListBuilder.create().texOffs(46, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.7F, 1.0F, -0.9F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}