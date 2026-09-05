package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import com.aetherteam.aetherii.client.renderer.entity.animation.FinchAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class FinchModel extends EntityModel<BirdRenderState> {
	private final KeyframeAnimation flyingAnimation;
	private final ModelPart finch;
	private final ModelPart legs;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public FinchModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucent);
		this.flyingAnimation = FinchAnimations.FLYING.bake(root);
		this.finch = root.getChild("finch");
		this.legs = this.finch.getChild("legs");
		this.head = this.finch.getChild("head");
		this.body = this.finch.getChild("body");
		this.tail = this.body.getChild("tail");
		this.leftWing = this.finch.getChild("left_wing");
		this.rightWing = this.finch.getChild("right_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition finch = partDefinition.addOrReplaceChild("finch", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, -0.5F));
		finch.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(12, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.5F));

		PartDefinition head = finch.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 7).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));
		head.addOrReplaceChild("head_crest_front_r1", CubeListBuilder.create().texOffs(13, 17).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.0F, -1.0F, 1.5708F, 0.0F, -1.5708F));
		head.addOrReplaceChild("head_crest_r1", CubeListBuilder.create().texOffs(-3, 1).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 3.1416F, 0.0F, -1.5708F));
		head.addOrReplaceChild("beak_r1", CubeListBuilder.create().texOffs(3, 12).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.75F, -2.25F, -0.5672F, 0.0F, 0.0F));

		PartDefinition body = finch.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 6).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.75F, 1.25F));
		tail.addOrReplaceChild("tail_horizontal_r1", CubeListBuilder.create().texOffs(8, 1).addBox(-3.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.75F, 0.75F, 0.7854F, 0.0F, 0.0F));
		tail.addOrReplaceChild("tail_vertical_r1", CubeListBuilder.create().texOffs(-1, 15).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 2.25F, 0.0F, -1.5708F, 1.5708F));

		PartDefinition leftWing = finch.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, -0.5F));
		leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(22, 4).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0896F, 0.2613F, 0.0118F));

		PartDefinition rightWing = finch.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -0.5F));
		rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(22, 7).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0896F, -0.2613F, -0.0118F));

		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		this.flyingAnimation.applyWalk(state.ageInTicks, state.flying ? 1.0F : 0.0F, 1.0F, 1.0F);
	}
}