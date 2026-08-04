package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ChonkModel extends EntityModel<BirdRenderState> {
	private final ModelPart bone;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart Tails;
	private final ModelPart Wing_Left;
	private final ModelPart Wing_Right;

	public ChonkModel(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
		this.Head = this.bone.getChild("Head");
		this.Body = this.bone.getChild("Body");
		this.LeftLeg = this.bone.getChild("LeftLeg");
		this.RightLeg = this.bone.getChild("RightLeg");
		this.Tails = this.bone.getChild("Tails");
		this.Wing_Left = this.bone.getChild("Wing_Left");
		this.Wing_Right = this.bone.getChild("Wing_Right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.5F, 19.1724F, 0.0714F));

		PartDefinition Head = bone.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(25, 40).addBox(-1.5F, -4.25F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(41, 6).addBox(-2.0F, -4.5F, -2.25F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(42, 15).addBox(-1.5F, -4.25F, -5.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.6724F, -2.5714F));

		PartDefinition Whiskers_Right_r1 = Head.addOrReplaceChild("Whiskers_Right_r1", CubeListBuilder.create().texOffs(21, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.25F, -2.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition Whiskers_Left_r1 = Head.addOrReplaceChild("Whiskers_Left_r1", CubeListBuilder.create().texOffs(2, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.25F, -2.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition Head_Crest_r1 = Head.addOrReplaceChild("Head_Crest_r1", CubeListBuilder.create().texOffs(-4, 26).addBox(-4.0F, 0.0F, -3.0F, 6.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, 2.5F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition Beak_Crest_r1 = Head.addOrReplaceChild("Beak_Crest_r1", CubeListBuilder.create().texOffs(37, 26).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -2.5F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition Body = bone.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.5776F, -1.0714F));

		PartDefinition Body_r1 = Body.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(22, 4).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition LeftLeg = bone.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(41, 43).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 1.8276F, -1.0714F));

		PartDefinition RightLeg = bone.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(48, 43).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, 1.8276F, -1.0714F));

		PartDefinition Tails = bone.addOrReplaceChild("Tails", CubeListBuilder.create(), PartPose.offset(-0.5F, -3.1724F, 6.9286F));

		PartDefinition Tail_Vertical_r1 = Tails.addOrReplaceChild("Tail_Vertical_r1", CubeListBuilder.create().texOffs(-8, 3).addBox(-4.5F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -1.0F, 3.1416F, -1.5708F, -1.5708F));

		PartDefinition Tail_Horizontal_Crest_r1 = Tails.addOrReplaceChild("Tail_Horizontal_Crest_r1", CubeListBuilder.create().texOffs(48, 35).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -4.0F, 0.0F, 0.4363F, -1.5708F));

		PartDefinition Tail_Horizontal_r1 = Tails.addOrReplaceChild("Tail_Horizontal_r1", CubeListBuilder.create().texOffs(37, 35).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -4.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition Wing_Left = bone.addOrReplaceChild("Wing_Left", CubeListBuilder.create(), PartPose.offset(1.25F, 0.3276F, -3.5714F));

		PartDefinition Wing_Left_Crest_r1 = Wing_Left.addOrReplaceChild("Wing_Left_Crest_r1", CubeListBuilder.create().texOffs(20, 22).addBox(1.0F, -2.0F, -3.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.25F, 5.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition Wing_Left_r1 = Wing_Left.addOrReplaceChild("Wing_Left_r1", CubeListBuilder.create().texOffs(4, 38).addBox(0.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.25F, 5.0F, 1.3948F, 0.1289F, -0.0229F));

		PartDefinition Wing_Right = bone.addOrReplaceChild("Wing_Right", CubeListBuilder.create(), PartPose.offset(-2.25F, 0.3276F, -3.8214F));

		PartDefinition Wing_Right_Crest_r1 = Wing_Right.addOrReplaceChild("Wing_Right_Crest_r1", CubeListBuilder.create().texOffs(30, 22).addBox(0.0F, -2.0F, -3.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.25F, 5.25F, 1.3948F, -0.1289F, 0.0229F));

		PartDefinition Wing_Right_r1 = Wing_Right.addOrReplaceChild("Wing_Right_r1", CubeListBuilder.create().texOffs(14, 38).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.25F, 5.25F, 1.3948F, -0.1289F, 0.0229F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		float bobbingBody = state.flapAngle * 0.3F;
		this.Head.y += bobbingBody;
		this.Tails.xRot = this.Tails.xRot + Mth.cos(state.walkAnimationPos * 0.6662F) * 0.3F * state.walkAnimationSpeed;
		this.Tails.y += bobbingBody;
		this.Body.y += bobbingBody;
		this.Wing_Left.zRot = -0.0873F - state.flapAngle;
		this.Wing_Left.y += bobbingBody;
		this.Wing_Right.zRot = 0.0873F + state.flapAngle;
		this.Wing_Right.y += bobbingBody;
		this.LeftLeg.y += bobbingBody;
		this.RightLeg.y += bobbingBody;
	}
}