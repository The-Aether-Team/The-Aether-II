package com.aetherteam.aetherii.client.renderer.entity.model.bird;// Made with Blockbench 5.1.4

import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MacawModel extends EntityModel<BirdRenderState> {
	private final ModelPart bone;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart Wing_Right;
	private final ModelPart Wing_Left;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart Tail;

	public MacawModel(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
		this.Head = this.bone.getChild("Head");
		this.Body = this.bone.getChild("Body");
		this.Wing_Right = this.bone.getChild("Wing_Right");
		this.Wing_Left = this.bone.getChild("Wing_Left");
		this.LeftLeg = this.bone.getChild("LeftLeg");
		this.RightLeg = this.bone.getChild("RightLeg");
		this.Tail = this.bone.getChild("Tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, -2.5F));

		PartDefinition Head = bone.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 16).addBox(-0.5F, -3.0F, -2.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 10).addBox(-0.5F, -3.75F, -1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 9).addBox(-1.0F, -4.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition HeadHorn_r1 = Head.addOrReplaceChild("HeadHorn_r1", CubeListBuilder.create().texOffs(36, 19).addBox(0.01F, -2.0F, -2.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.25F, -0.25F, 0.5672F, 0.0F, 0.0F));

		PartDefinition HeadCrest_r1 = Head.addOrReplaceChild("HeadCrest_r1", CubeListBuilder.create().texOffs(1, -9).addBox(0.0F, -4.0F, -6.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition Body = bone.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 0.5F));

		PartDefinition Body_r1 = Body.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, 0.75F, 0.3927F, 0.0F, 0.0F));

		PartDefinition BodyCrest_r1 = Body.addOrReplaceChild("BodyCrest_r1", CubeListBuilder.create().texOffs(22, -4).addBox(0.0F, -2.0F, -2.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, 3.5F, -1.1781F, 0.0F, 0.0F));

		PartDefinition Wing_Right = bone.addOrReplaceChild("Wing_Right", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, 0.0F));

		PartDefinition Wing_Right_r1 = Wing_Right.addOrReplaceChild("Wing_Right_r1", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, -5.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.75F, 3.75F, 0.6545F, 0.0F, 0.0F));

		PartDefinition Wing_Left = bone.addOrReplaceChild("Wing_Left", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, 0.0F));

		PartDefinition Wing_Left_r1 = Wing_Left.addOrReplaceChild("Wing_Left_r1", CubeListBuilder.create().texOffs(16, 18).addBox(0.0F, -5.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.75F, 3.75F, 0.6545F, 0.0F, 0.0F));

		PartDefinition LeftLeg = bone.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(37, 14).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 6.5F, 2.0F));

		PartDefinition RightLeg = bone.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(43, 14).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, 6.5F, 2.0F));

		PartDefinition Tail = bone.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 2.5F));

		PartDefinition Tail_r1 = Tail.addOrReplaceChild("Tail_r1", CubeListBuilder.create().texOffs(3, 22).addBox(-0.99F, -2.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.75F, -0.75F, -0.7418F, 0.0F, 0.0F));

		PartDefinition Tail_r2 = Tail.addOrReplaceChild("Tail_r2", CubeListBuilder.create().texOffs(3, 29).addBox(-1.01F, -2.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.0F, 1.25F, -0.3491F, 0.0F, 0.0F));

		PartDefinition Tail_r3 = Tail.addOrReplaceChild("Tail_r3", CubeListBuilder.create().texOffs(2, 36).addBox(-0.48F, -0.5F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1188F, 2.5796F, 0.2182F, 0.0F, 0.0F));

		PartDefinition Tail_r4 = Tail.addOrReplaceChild("Tail_r4", CubeListBuilder.create().texOffs(36, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition TailCrest_r1 = Tail.addOrReplaceChild("TailCrest_r1", CubeListBuilder.create().texOffs(35, -4).addBox(-0.01F, 0.0F, -2.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, 3.5F, -0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BirdRenderState state) {
		super.setupAnim(state);
		float bobbingBody = state.flapAngle * 0.3F;
		this.Head.y += bobbingBody;
		this.Body.y += bobbingBody;
		this.Wing_Left.zRot = -0.0873F - state.flapAngle;
		this.Wing_Left.y += bobbingBody;
		this.Wing_Right.zRot = 0.0873F + state.flapAngle;
		this.Wing_Right.y += bobbingBody;
		this.LeftLeg.y += bobbingBody;
		this.RightLeg.y += bobbingBody;
	}
}