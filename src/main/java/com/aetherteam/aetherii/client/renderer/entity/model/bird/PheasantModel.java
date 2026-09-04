package com.aetherteam.aetherii.client.renderer.entity.model.bird;

import com.aetherteam.aetherii.client.renderer.entity.animation.PheasantAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PheasantModel extends EntityModel<BirdRenderState> {
	private final KeyframeAnimation flyingAnimation;
	private final ModelPart pheasant;
	private final ModelPart legs;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tails;
	private final ModelPart wings;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public PheasantModel(ModelPart root) {
		super(root);
		this.flyingAnimation = PheasantAnimations.FLYING.bake(root);
		this.pheasant = root.getChild("pheasant");
		this.legs = this.pheasant.getChild("legs");
		this.leftLeg = this.legs.getChild("left_leg");
		this.rightLeg = this.legs.getChild("right_leg");
		this.head = this.pheasant.getChild("head");
		this.body = this.pheasant.getChild("body");
		this.tails = this.pheasant.getChild("tails");
		this.wings = this.pheasant.getChild("wings");
		this.leftWing = this.wings.getChild("left_wing");
		this.rightWing = this.wings.getChild("right_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition pheasant = partDefinition.addOrReplaceChild("pheasant", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.5F));
		pheasant.addOrReplaceChild("head", CubeListBuilder.create().texOffs(5, 11).addBox(-1.5F, -6.25F, -3.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(38, 3).addBox(-1.5F, -10.25F, -3.0F, 3.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(40, 14).addBox(0.0F, -10.25F, -3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(26, 29).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -1.5F));

		PartDefinition legs = pheasant.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(15, 35).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.0F, 0.0F));
		legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(9, 35).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, 0.0F, 0.0F));

		PartDefinition body = pheasant.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.25F, -0.5F));
		body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(19, 9).addBox(-2.0F, -2.0F, -2.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.5F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tails = pheasant.addOrReplaceChild("tails", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.5F));
		tails.addOrReplaceChild("tail_horizontal_r1", CubeListBuilder.create().texOffs(30, 33).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 1.5F, 0.4363F, 0.0F, 0.0F));

		PartDefinition wings = pheasant.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.125F, -1.5F, -4.0F));

		PartDefinition leftWing = wings.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.375F, 0.0F, 0.0F));
		leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(5, 24).addBox(0.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.25F, 5.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition rightWing = wings.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.375F, 0.0F, 0.0F));
		rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(15, 24).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.25F, 5.0F, 1.3948F, -0.1289F, 0.0229F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		this.flyingAnimation.applyWalk(state.ageInTicks, state.rest ? 0.0F : 1.0F, 1.0F, 1.0F);
	}
}