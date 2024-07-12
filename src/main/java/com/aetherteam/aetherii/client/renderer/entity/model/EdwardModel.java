package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.npc.outpost.Edward;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class EdwardModel extends HierarchicalModel<Edward> implements ArmedModel, HeadedModel {
	private final ModelPart root;
	private final ModelPart waist;
	private final ModelPart abdomen;
	private final ModelPart chest;
	private final ModelPart head;
	private final ModelPart earleft;
	private final ModelPart earright;
	private final ModelPart hatlayer;
	private final ModelPart armleft1;
	private final ModelPart armleft2;
	private final ModelPart elbowleft;
	private final ModelPart armright1;
	private final ModelPart armright2;
	private final ModelPart elbowright;
	private final ModelPart backpack;
	private final ModelPart bedroll;
	private final ModelPart legleft1;
	private final ModelPart legleft2;
	private final ModelPart legright1;
	private final ModelPart legright2;
	private final ModelPart book;

	public EdwardModel(ModelPart root) {
		this.root = root.getChild("root");
		this.waist = this.root.getChild("waist");
		this.abdomen = this.waist.getChild("abdomen");
		this.chest = this.abdomen.getChild("chest");
		this.head = this.root.getChild("head");
		this.earleft = this.head.getChild("earleft");
		this.earright = this.head.getChild("earright");
		this.hatlayer = this.head.getChild("hatlayer");
		this.armleft1 = this.chest.getChild("armleft1");
		this.armleft2 = this.armleft1.getChild("armleft2");
		this.elbowleft = this.armleft1.getChild("elbowleft");
		this.armright1 = this.chest.getChild("armright1");
		this.armright2 = this.armright1.getChild("armright2");
		this.elbowright = this.armright1.getChild("elbowright");
		this.backpack = this.chest.getChild("backpack");
		this.bedroll = this.backpack.getChild("bedroll");
		this.legleft1 = this.waist.getChild("legleft1");
		this.legleft2 = this.legleft1.getChild("legleft2");
		this.legright1 = this.waist.getChild("legright1");
		this.legright2 = this.legright1.getChild("legright2");
		this.book = this.waist.getChild("book");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition waist = root.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-4.5F, 10.0F, -2.5F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition abdomen = waist.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(2, 28).mirror().addBox(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition chest = abdomen.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-4.5F, -7.0F, -2.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition earleft = head.addOrReplaceChild("earleft", CubeListBuilder.create().texOffs(24, 2).mirror().addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -3.0F, -2.0F, 0.0F, -0.2618F, -0.0873F));

		PartDefinition earright = head.addOrReplaceChild("earright", CubeListBuilder.create().texOffs(24, 2).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, -2.0F, 0.0F, 0.2618F, 0.0873F));

		PartDefinition hatlayer = head.addOrReplaceChild("hatlayer", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armleft1 = chest.addOrReplaceChild("armleft1", CubeListBuilder.create().texOffs(28, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition armleft2 = armleft1.addOrReplaceChild("armleft2", CubeListBuilder.create().texOffs(28, 26).mirror().addBox(-2.01F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 4.0F, -1.0F));

		PartDefinition elbowleft = armleft1.addOrReplaceChild("elbowleft", CubeListBuilder.create().texOffs(44, 24).mirror().addBox(-2.5F, 3.8F, -1.2F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armright1 = chest.addOrReplaceChild("armright1", CubeListBuilder.create().texOffs(28, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition armright2 = armright1.addOrReplaceChild("armright2", CubeListBuilder.create().texOffs(28, 26).mirror().addBox(0.01F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 4.0F, -1.0F));

		PartDefinition elbowright = armright1.addOrReplaceChild("elbowright", CubeListBuilder.create().texOffs(44, 24).mirror().addBox(-0.5F, 3.8F, -1.2F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition backpack = chest.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(33, 46).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 2.0F));

		PartDefinition bedroll = backpack.addOrReplaceChild("bedroll", CubeListBuilder.create().texOffs(32, 36).mirror().addBox(-5.5F, -4.5F, 1.6F, 11.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.3F, 0.2F, -0.1745F, 0.0F, 0.0F));

		PartDefinition legleft1 = waist.addOrReplaceChild("legleft1", CubeListBuilder.create().texOffs(0, 46).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4F, 12.0F, 0.0F));

		PartDefinition legleft2 = legleft1.addOrReplaceChild("legleft2", CubeListBuilder.create().texOffs(16, 46).addBox(-2.01F, 0.0F, 0.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -2.0F));

		PartDefinition legright1 = waist.addOrReplaceChild("legright1", CubeListBuilder.create().texOffs(0, 46).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 12.0F, 0.0F));

		PartDefinition legright2 = legright1.addOrReplaceChild("legright2", CubeListBuilder.create().texOffs(16, 46).mirror().addBox(-2.01F, 0.0F, 0.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, -2.0F));

		PartDefinition book = waist.addOrReplaceChild("book", CubeListBuilder.create().texOffs(52, 26).mirror().addBox(0.0F, -0.5F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 10.0F, -1.5F, 0.3491F, -0.2618F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public void setupAnim(Edward entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.xRot = headPitch * (float) (Math.PI / 180.0);
		this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);

		if (entity.isSitting()) {
			this.head.y = 5.25F;
			this.head.z = -1.0F;

			this.waist.y = 5.0F;
			this.waist.z = 2.0F;

			this.abdomen.xRot = 0.0873F;

			this.chest.xRot = 0.2618F;

			this.armleft1.yRot = -0.5009F;
			this.armleft1.zRot = 0.2731F;

			this.armleft2.xRot = -1.309F;

			this.armright1.xRot = 0.3643F;
			this.armright1.yRot = -0.3187F;
			this.armright1.zRot = -0.0456F;

			this.armright2.xRot = -0.8196F;

			this.legleft1.xRot = -1.2217F;
			this.legleft1.yRot = 0.3142F;

			this.legleft2.xRot = 0.3491F;

			this.legright1.xRot = -1.1839F;
			this.legright2.yRot = -0.2731F;

			this.legright2.xRot = 0.1745F;

			this.backpack.x = -4.0F;
			this.backpack.y = 12.0F;
			this.backpack.z = -2.0F;
			this.backpack.xRot = 1.5708F;
			this.backpack.yRot = 1.5708F;
			this.backpack.zRot = 0.0F;
		} else {
			this.head.y = 0.0F;

			this.waist.yRot = 0.0F;

			float f = 1.0F;

			this.armright1.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
			this.armleft1.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
			this.armright1.zRot = 0.0F;
			this.armleft1.zRot = 0.0F;
			this.legright1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
			this.legleft1.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
			this.legright1.yRot = 0.005F;
			this.legleft1.yRot = -0.005F;
			this.legright1.zRot = 0.005F;
			this.legleft1.zRot = -0.005F;
			if (this.riding) {
				this.armright1.xRot += (float) (-Math.PI / 5);
				this.armleft1.xRot += (float) (-Math.PI / 5);
				this.legright1.xRot = -1.4137167F;
				this.legright1.yRot = (float) (Math.PI / 10);
				this.legright1.zRot = 0.07853982F;
				this.legleft1.xRot = -1.4137167F;
				this.legleft1.yRot = (float) (-Math.PI / 10);
				this.legleft1.zRot = -0.07853982F;
			}

			this.armright1.yRot = 0.0F;
			this.armleft1.yRot = 0.0F;

			this.waist.xRot = 0.0F;

			AnimationUtils.bobModelPart(this.armright1, ageInTicks, -1.0F);
			AnimationUtils.bobModelPart(this.armleft1, ageInTicks, 1.0F);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
	}

	@Override
	public void translateToHand(HumanoidArm side, PoseStack poseStack) {
		
	}
}