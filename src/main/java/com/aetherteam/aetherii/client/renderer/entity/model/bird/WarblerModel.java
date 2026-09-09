package com.aetherteam.aetherii.client.renderer.entity.model.bird;

import com.aetherteam.aetherii.client.renderer.entity.animation.WarblerAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class WarblerModel extends EntityModel<BirdRenderState> {
	private final KeyframeAnimation flyingAnimation;
	private final ModelPart warbler;
	private final ModelPart head;
	private final ModelPart skull;
	private final ModelPart neck;
	private final ModelPart body;
	private final ModelPart tails;
	private final ModelPart wings;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart legs;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public WarblerModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucent);
		this.flyingAnimation = WarblerAnimations.FLYING.bake(root);
		this.warbler = root.getChild("warbler");
		this.head = this.warbler.getChild("head");
		this.skull = this.head.getChild("skull");
		this.neck = this.head.getChild("neck");
		this.body = this.head.getChild("body");
		this.tails = this.warbler.getChild("tails");
		this.wings = this.warbler.getChild("wings");
		this.leftWing = this.wings.getChild("left_wing");
		this.rightWing = this.wings.getChild("right_wing");
		this.legs = this.warbler.getChild("legs");
		this.leftLeg = this.legs.getChild("left_leg");
		this.rightLeg = this.legs.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition warbler = partDefinition.addOrReplaceChild("warbler", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, -1.0F));

		PartDefinition head = warbler.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(39, 27).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(3, 17).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(22, 4).addBox(0.0F, 0.0F, -8.25F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 2.0F));
		head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(34, 13).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, 0.5F));

		PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 4.25F, 1.25F));
		body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(3, 4).addBox(-2.0F, -2.0F, -1.0F, 5.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5F, -1.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tails = warbler.addOrReplaceChild("tails", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 3.75F));
		tails.addOrReplaceChild("tail_horizontal_r1", CubeListBuilder.create().texOffs(43, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 0.5F, 1.2602F, 0.4503F, 0.982F));
		tails.addOrReplaceChild("tail_horizontal_r2", CubeListBuilder.create().texOffs(37, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 0.5F, 1.1834F, 0.344F, 0.6711F));
		tails.addOrReplaceChild("tail_horizontal_r3", CubeListBuilder.create().texOffs(31, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 0.5F, 1.0887F, 0.2129F, 0.3838F));
		tails.addOrReplaceChild("tail_horizontal_r4", CubeListBuilder.create().texOffs(25, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.25F, 0.5F, 1.0821F, 0.1084F, 0.1897F));
		tails.addOrReplaceChild("tail_horizontal_r5", CubeListBuilder.create().texOffs(-5, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, 0.5F, 1.2602F, -0.4503F, -0.982F));
		tails.addOrReplaceChild("tail_horizontal_r6", CubeListBuilder.create().texOffs(1, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, 0.5F, 1.1834F, -0.344F, -0.6711F));
		tails.addOrReplaceChild("tail_horizontal_r7", CubeListBuilder.create().texOffs(7, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, 0.5F, 1.0887F, -0.2129F, -0.3838F));
		tails.addOrReplaceChild("tail_horizontal_r8", CubeListBuilder.create().texOffs(13, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.25F, 0.5F, 1.0821F, -0.1084F, -0.1897F));
		tails.addOrReplaceChild("tail_horizontal_r9", CubeListBuilder.create().texOffs(19, 34).addBox(-1.0F, 0.0F, 0.25F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.5F, 1.0996F, 0.0F, 0.0F));

		PartDefinition wings = warbler.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(2.0F, 5.25F, 4.0F));

		PartDefinition leftWing = wings.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.75F, -1.5F, -5.0F));
		leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(21, 25).addBox(-0.0192F, 0.0937F, -0.8989F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3959F, 0.2222F, 0.0066F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition rightWing = wings.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-4.5F, -1.25F, -5.0F));
		rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(31, 25).addBox(-0.9808F, 0.0937F, -0.8989F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1459F, -0.0278F, 0.0066F, 1.3948F, -0.1289F, 0.0229F));

		PartDefinition legs = warbler.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 5.5F, 2.0F));
		legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(3, 27).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.0F, 0.0F));
		legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(11, 27).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, 0.0F, 0.0F));

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