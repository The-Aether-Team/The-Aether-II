package com.aetherteam.aetherii.client.renderer.entity.model.bird;

import com.aetherteam.aetherii.client.renderer.entity.animation.MacawAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MacawModel extends EntityModel<BirdRenderState> {
	private final KeyframeAnimation flyingAnimation;
	private final ModelPart macaw;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart legs;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart tail;

	public MacawModel(ModelPart root) {
		super(root);
		this.flyingAnimation = MacawAnimations.FLYING.bake(root);
		this.macaw = root.getChild("macaw");
		this.head = this.macaw.getChild("head");
		this.body = this.macaw.getChild("body");
		this.rightWing = this.macaw.getChild("right_wing");
		this.leftWing = this.macaw.getChild("left_wing");
		this.legs = this.macaw.getChild("legs");
		this.rightLeg = this.legs.getChild("right_leg");
		this.leftLeg = this.legs.getChild("left_leg");
		this.tail = this.macaw.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition macaw = partDefinition.addOrReplaceChild("macaw", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, -2.5F));

		PartDefinition head = macaw.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(11, 16).addBox(-0.5F, -3.0F, -2.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(11, 10).addBox(-0.5F, -3.75F, -1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(1, 9).addBox(-1.0F, -4.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		head.addOrReplaceChild("head_horn_r1", CubeListBuilder.create().texOffs(36, 19).addBox(0.01F, -2.0F, -2.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.25F, -0.25F, 0.5672F, 0.0F, 0.0F));
		head.addOrReplaceChild("head_crest_r1", CubeListBuilder.create().texOffs(1, -9).addBox(0.0F, -4.0F, -6.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body = macaw.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 0.5F));
		body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, 0.75F, 0.3927F, 0.0F, 0.0F));
		body.addOrReplaceChild("body_crest_r1", CubeListBuilder.create().texOffs(22, -4).addBox(0.0F, -2.0F, -2.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, 3.5F, -1.1781F, 0.0F, 0.0F));

		PartDefinition rightWing = macaw.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, 0.0F));
		rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, -5.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.75F, 3.75F, 0.6545F, 0.0F, 0.0F));

		PartDefinition leftWing = macaw.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, 0.0F));
		leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(16, 18).addBox(0.0F, -5.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.75F, 3.75F, 0.6545F, 0.0F, 0.0F));

		PartDefinition legs = macaw.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-0.75F, 6.5F, 2.0F));
		legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(43, 14).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(37, 14).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 0.0F));

		PartDefinition tail = macaw.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 2.5F));
		tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(3, 22).addBox(-0.99F, -2.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.75F, -0.75F, -0.7418F, 0.0F, 0.0F));
		tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(3, 29).addBox(-1.01F, -2.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.0F, 1.25F, -0.3491F, 0.0F, 0.0F));
		tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(2, 36).addBox(-0.48F, -0.5F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1188F, 2.5796F, 0.2182F, 0.0F, 0.0F));
		tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(36, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.5F, -0.3927F, 0.0F, 0.0F));
		tail.addOrReplaceChild("tail_crest_r1", CubeListBuilder.create().texOffs(35, -4).addBox(-0.01F, 0.0F, -2.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, 3.5F, -0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		this.flyingAnimation.applyWalk(state.ageInTicks, state.rest ? 0.0F : 1.0F, 1.0F, 1.0F);
	}
}