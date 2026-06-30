package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.CloudSkiffAnimations;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CloudSkiffModel extends HierarchicalModel<CloudSkiff> {
	private final ModelPart root;
	private final ModelPart cloudSkiff;
	private final ModelPart wingLeft;
	private final ModelPart wingRight;
	private final ModelPart sailRudder;
	private final ModelPart handle;

	public CloudSkiffModel(ModelPart root) {
		this.root = root;
		this.cloudSkiff = root.getChild("cloud_skiff");
		this.wingLeft = this.cloudSkiff.getChild("wing_left");
		this.wingRight = this.cloudSkiff.getChild("wing_right");
		this.sailRudder = this.cloudSkiff.getChild("sail_rudder");
		this.handle = this.sailRudder.getChild("handle");
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition skiff = partdefinition.addOrReplaceChild("cloud_skiff", CubeListBuilder.create().texOffs(28, 55).addBox(-9.0F, -3.0F, -16.0F, 18.0F, 2.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(62, 28).addBox(-4.0F, -1.0F, -12.0F, 8.0F, 1.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(0.0F, 0.0F, -13.0F, 0.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(65, 42).addBox(-2.0F, -4.5F, 13.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 2.0F));

		skiff.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(73, 115).addBox(-1.0F, 0.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(73, 99).addBox(-1.0F, 0.0F, 7.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(60, 95).addBox(4.0F, -1.0F, -14.0F, 6.0F, 2.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, -2.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		skiff.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(41, 118).addBox(-4.0F, 0.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(41, 102).addBox(-4.0F, 0.0F, 7.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 98).addBox(-10.0F, -1.0F, -14.0F, 6.0F, 2.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -2.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition rudder = skiff.addOrReplaceChild("sail_rudder", CubeListBuilder.create().texOffs(69, 8).addBox(-1.0F, -30.5F, -1.0F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(65, 1).addBox(-2.0F, -32.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(81, 12).addBox(-2.0F, -12.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, -32).addBox(0.0F, -30.5F, -1.0F, 0.0F, 48.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 15.0F));

		rudder.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(CloudSkiff cloudSkiff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(cloudSkiff.unfoldAnimationState, CloudSkiffAnimations.UNFOLD, ageInTicks, 1.0F);
		this.animate(cloudSkiff.foldAnimationState, CloudSkiffAnimations.FOLD, ageInTicks, 1.0F);
		float partialTick = Mth.clamp(ageInTicks - cloudSkiff.tickCount, 0.0F, 1.0F);
		float steering = Mth.lerp(partialTick, cloudSkiff.steeringO, cloudSkiff.steering) * Mth.DEG_TO_RAD;
		float wingLift = Mth.lerp(partialTick, cloudSkiff.wingLiftO, cloudSkiff.wingLift) * Mth.DEG_TO_RAD;
		this.sailRudder.yRot += steering + Mth.sin(3.0F * ageInTicks * Mth.DEG_TO_RAD) / 15.0F;
		this.wingLeft.zRot += wingLift;
		this.wingRight.zRot -= wingLift;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
