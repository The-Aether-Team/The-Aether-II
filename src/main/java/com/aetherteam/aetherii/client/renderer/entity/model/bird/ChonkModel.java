package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import com.aetherteam.aetherii.client.renderer.entity.animation.ChonkAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class ChonkModel extends EntityModel<BirdRenderState> {
	private final KeyframeAnimation flyingAnimation;
	private final ModelPart chonk;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tails;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public ChonkModel(ModelPart root) {
		super(root, RenderTypes::entityTranslucent);
		this.flyingAnimation = ChonkAnimations.FLYING.bake(root);
		this.chonk = root.getChild("chonk");
		this.leftLeg = this.chonk.getChild("left_leg");
		this.rightLeg = this.chonk.getChild("right_leg");
		this.head = this.chonk.getChild("head");
		this.body = this.chonk.getChild("body");
		this.tails = this.chonk.getChild("tails");
		this.leftWing = this.chonk.getChild("left_wing");
		this.rightWing = this.chonk.getChild("right_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition chonk = partDefinition.addOrReplaceChild("chonk", CubeListBuilder.create(), PartPose.offset(0.5F, 19.1724F, 1.0714F));
		chonk.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(41, 43).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 1.8276F, -1.0714F));
		chonk.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 43).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, 1.8276F, -1.0714F));

		PartDefinition head = chonk.addOrReplaceChild("head", CubeListBuilder.create().texOffs(25, 40).addBox(-1.5F, -4.25F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(41, 6).addBox(-2.0F, -4.5F, -2.25F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(42, 15).addBox(-1.5F, -4.25F, -5.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.6724F, -2.5714F));
		head.addOrReplaceChild("whiskers_right_r1", CubeListBuilder.create().texOffs(21, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.25F, -2.5F, 0.0F, -0.5672F, 0.0F));
		head.addOrReplaceChild("whiskers_left_r1", CubeListBuilder.create().texOffs(2, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.25F, -2.5F, 0.0F, 0.5672F, 0.0F));
		head.addOrReplaceChild("head_crest_r1", CubeListBuilder.create().texOffs(-4, 26).addBox(-4.0F, 0.0F, -3.0F, 6.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, 2.5F, 1.5708F, -1.5708F, 0.0F));
		head.addOrReplaceChild("beak_crest_r1", CubeListBuilder.create().texOffs(37, 26).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -2.5F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition body = chonk.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.5776F, -1.0714F));
		body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(22, 4).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tails = chonk.addOrReplaceChild("tails", CubeListBuilder.create(), PartPose.offset(-0.5F, -0.6724F, 1.4286F));
		tails.addOrReplaceChild("tail_vertical_r1", CubeListBuilder.create().texOffs(-8, 3).addBox(-4.5F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 4.5F, 3.1416F, -1.5708F, -1.5708F));
		tails.addOrReplaceChild("tail_horizontal_crest_r1", CubeListBuilder.create().texOffs(48, 35).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.5F, 1.5F, 0.0F, 0.4363F, -1.5708F));
		tails.addOrReplaceChild("tail_horizontal_r1", CubeListBuilder.create().texOffs(37, 35).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 1.5F, 0.4363F, 0.0F, 0.0F));

		PartDefinition leftWing = chonk.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.25F, 0.3276F, -3.5714F));
		leftWing.addOrReplaceChild("left_wing_crest_r1", CubeListBuilder.create().texOffs(20, 22).addBox(1.0F, -2.0F, -3.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.25F, 5.0F, 1.3948F, 0.1289F, -0.0229F));
		leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(4, 38).addBox(0.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.25F, 5.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition rightWing = chonk.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.25F, 0.3276F, -3.8214F));
		rightWing.addOrReplaceChild("right_wing_crest_r1", CubeListBuilder.create().texOffs(30, 22).addBox(0.0F, -2.0F, -3.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.25F, 5.25F, 1.3948F, -0.1289F, 0.0229F));
		rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(14, 38).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.25F, 5.25F, 1.3948F, -0.1289F, 0.0229F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		this.flyingAnimation.applyWalk(state.ageInTicks, state.flying ? 1.0F : 0.0F, 1.0F, 1.0F);
	}
}