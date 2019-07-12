package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.characters.EntityNecromancer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelNecromancer extends EntityModel<EntityNecromancer>
{
	public final RendererModel Torso1;

	public final RendererModel Torso2;

	public final RendererModel RightShoulder;

	public final RendererModel LeftShoulder;

	public final RendererModel Shoulders;

	public final RendererModel Robes7;

	public final RendererModel RightLeg1;

	public final RendererModel LeftLeg1;

	public final RendererModel Robes1;

	public final RendererModel RightKnee;

	public final RendererModel RightLeg2;

	public final RendererModel RightAnkle;

	public final RendererModel RightLeg3;

	public final RendererModel RightFoot1;

	public final RendererModel RightFoot2;

	public final RendererModel LeftKnee;

	public final RendererModel LeftLeg2;

	public final RendererModel LeftAnkle;

	public final RendererModel LeftLeg3;

	public final RendererModel LeftFoot1;

	public final RendererModel LeftFoot2;

	public final RendererModel Robes2;

	public final RendererModel Robes3;

	public final RendererModel Robes4;

	public final RendererModel RightArm1;

	public final RendererModel RightElbow;

	public final RendererModel Robes5;

	public final RendererModel RightArm2;

	public final RendererModel RightHand1;

	public final RendererModel RightHand2;

	public final RendererModel RightHand3;

	public final RendererModel LeftArm1;

	public final RendererModel LeftShoulder_1;

	public final RendererModel Robes6;

	public final RendererModel LeftArm2;

	public final RendererModel LeftHand1;

	public final RendererModel LeftHand2;

	public final RendererModel Hood1;

	public final RendererModel Hood2;

	public final RendererModel Neck;

	public final RendererModel Head;

	public final RendererModel Hood3;

	public final RendererModel Mask1;

	public final RendererModel Mask2;

	public ModelNecromancer()
	{
		this.textureWidth = 128;
		this.textureHeight = 192;
		this.Hood3 = new RendererModel(this, 0, 12);
		this.Hood3.setRotationPoint(0.0F, -0.5F, 0.0F);
		this.Hood3.addBox(-3.5F, -7.5F, -3.5F, 7, 10, 7, 0.0F);
		this.Mask1 = new RendererModel(this, 0, 0);
		this.Mask1.setRotationPoint(0.0F, -7.0F, -3.0F);
		this.Mask1.addBox(-3.0F, 0.0F, -1.0F, 6, 4, 1, 0.0F);
		this.Shoulders = new RendererModel(this, 0, 60);
		this.Shoulders.setRotationPoint(0.0F, 0.7F, 0.0F);
		this.Shoulders.addBox(-6.0F, -2.9F, -3.5F, 12, 3, 7, 0.0F);
		this.Hood1 = new RendererModel(this, 0, 50);
		this.Hood1.setRotationPoint(-3.0F, -4.0F, 0.0F);
		this.Hood1.addBox(-5.0F, -2.0F, -2.3F, 6, 4, 6, 0.0F);
		this.setRotateAngle(this.Hood1, 0.0F, 0.0F, -0.6108652381980153F);
		this.Robes5 = new RendererModel(this, 0, 70);
		this.Robes5.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Robes5.addBox(-4.0F, -0.5F, -2.0F, 5, 5, 5, 0.0F);
		this.LeftLeg1 = new RendererModel(this, 0, 150);
		this.LeftLeg1.mirror = true;
		this.LeftLeg1.setRotationPoint(2.5F, 7.0F, 0.0F);
		this.LeftLeg1.addBox(-2.0F, -1.0F, -3.0F, 4, 9, 6, 0.0F);
		this.setRotateAngle(this.LeftLeg1, 0.08726646259971647F, 0.0F, -0.08726646259971647F);
		this.LeftAnkle = new RendererModel(this, 0, 182);
		this.LeftAnkle.setRotationPoint(0.0F, 8.0F, 1.1F);
		this.LeftAnkle.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
		this.setRotateAngle(this.LeftAnkle, 0.0F, 0.0F, 0.08726646259971647F);
		this.RightHand3 = new RendererModel(this, 0, 118);
		this.RightHand3.setRotationPoint(-1.0F, 3.0F, 0.0F);
		this.RightHand3.addBox(0.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
		this.setRotateAngle(this.RightHand3, 0.0F, 0.0F, -0.2617993877991494F);
		this.RightLeg2 = new RendererModel(this, 0, 172);
		this.RightLeg2.mirror = true;
		this.RightLeg2.setRotationPoint(0.0F, 2.0F, -1.6F);
		this.RightLeg2.addBox(-1.5F, 0.0F, -1.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(this.RightLeg2, 0.3490658503988659F, 0.0F, 0.0F);
		this.LeftShoulder = new RendererModel(this, 20, 80);
		this.LeftShoulder.mirror = true;
		this.LeftShoulder.setRotationPoint(4.5F, 0.0F, 0.0F);
		this.LeftShoulder.addBox(-0.5F, 0.0F, -1.5F, 4, 4, 4, 0.0F);
		this.setRotateAngle(this.LeftShoulder, 0.0F, 0.0F, -0.17453292519943295F);
		this.Mask2 = new RendererModel(this, 0, 5);
		this.Mask2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Mask2.addBox(-2.0F, 4.0F, -1.0F, 4, 2, 3, 0.0F);
		this.Torso2 = new RendererModel(this, 0, 136);
		this.Torso2.setRotationPoint(0.0F, 5.4F, 0.0F);
		this.Torso2.addBox(-4.5F, -0.7F, -2.5F, 9, 9, 5, 0.0F);
		this.setRotateAngle(this.Torso2, -0.17453292519943295F, 0.0F, 0.0F);
		this.RightArm1 = new RendererModel(this, 0, 88);
		this.RightArm1.setRotationPoint(-3.0F, 4.0F, -1.0F);
		this.RightArm1.addBox(0.0F, 0.0F, 0.0F, 3, 5, 3, 0.0F);
		this.LeftArm1 = new RendererModel(this, 20, 88);
		this.LeftArm1.mirror = true;
		this.LeftArm1.setRotationPoint(3.0F, 4.0F, -1.0F);
		this.LeftArm1.addBox(-3.0F, 0.0F, 0.0F, 3, 5, 3, 0.0F);
		this.LeftShoulder_1 = new RendererModel(this, 20, 97);
		this.LeftShoulder_1.setRotationPoint(1.5F, 8.0F, 1.1F);
		this.LeftShoulder_1.addBox(-1.3F, -0.5F, -2.0F, 3, 3, 4, 0.0F);
		this.setRotateAngle(this.LeftShoulder_1, -0.3490658503988659F, 0.0F, 0.0F);
		this.RightAnkle = new RendererModel(this, 0, 182);
		this.RightAnkle.mirror = true;
		this.RightAnkle.setRotationPoint(0.0F, 8.0F, 1.1F);
		this.RightAnkle.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
		this.setRotateAngle(this.RightAnkle, 0.0F, 0.0F, -0.08726646259971647F);
		this.RightFoot1 = new RendererModel(this, 0, 186);
		this.RightFoot1.mirror = true;
		this.RightFoot1.setRotationPoint(0.0F, 1.6F, 0.3F);
		this.RightFoot1.addBox(-2.0F, 0.0F, -3.5F, 4, 2, 4, 0.0F);
		this.RightLeg3 = new RendererModel(this, 12, 165);
		this.RightLeg3.mirror = true;
		this.RightLeg3.setRotationPoint(0.0F, -0.8F, 3.0F);
		this.RightLeg3.addBox(-1.3F, -0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(this.RightLeg3, -0.08726646259971647F, 0.0F, 0.0F);
		this.RightShoulder = new RendererModel(this, 0, 80);
		this.RightShoulder.setRotationPoint(-4.5F, 0.0F, 0.0F);
		this.RightShoulder.addBox(-3.5F, 0.0F, -1.5F, 4, 4, 4, 0.0F);
		this.setRotateAngle(this.RightShoulder, 0.0F, 0.0F, 0.17453292519943295F);
		this.LeftHand2 = new RendererModel(this, 20, 113);
		this.LeftHand2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LeftHand2.addBox(-1.0F, 0.7F, -1.5F, 2, 4, 3, 0.0F);
		this.setRotateAngle(this.LeftHand2, 0.0F, 0.0F, 0.18203784098300857F);
		this.LeftLeg2 = new RendererModel(this, 0, 172);
		this.LeftLeg2.setRotationPoint(0.0F, 2.0F, -1.6F);
		this.LeftLeg2.addBox(-1.5F, 0.0F, -1.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(this.LeftLeg2, 0.3490658503988659F, 0.0F, 0.0F);
		this.LeftArm2 = new RendererModel(this, 20, 104);
		this.LeftArm2.setRotationPoint(0.0F, 2.5F, 0.0F);
		this.LeftArm2.addBox(-1.5F, 0.0F, -1.4F, 3, 6, 3, 0.0F);
		this.LeftFoot1 = new RendererModel(this, 0, 186);
		this.LeftFoot1.setRotationPoint(0.0F, 1.6F, 0.3F);
		this.LeftFoot1.addBox(-2.0F, 0.0F, -3.5F, 4, 2, 4, 0.0F);
		this.Robes4 = new RendererModel(this, 44, 177);
		this.Robes4.setRotationPoint(0.0F, 3.0F, 3.5F);
		this.Robes4.addBox(1.0F, 0.0F, 0.0F, 4, 15, 0, 0.0F);
		this.setRotateAngle(this.Robes4, 0.08726646259971647F, 0.0F, 0.0F);
		this.Robes1 = new RendererModel(this, 26, 166);
		this.Robes1.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.Robes1.addBox(-5.0F, 0.0F, -3.5F, 10, 3, 7, 0.0F);
		this.Head = new RendererModel(this, 0, 29);
		this.Head.setRotationPoint(0.0F, -1.2F, 0.0F);
		this.Head.addBox(-3.0F, -6.1F, -3.0F, 6, 6, 6, 0.0F);
		this.Robes7 = new RendererModel(this, 21, 0);
		this.Robes7.setRotationPoint(0.0F, -2.4F, 0.0F);
		this.Robes7.addBox(-8.5F, 0.0F, -4.0F, 17, 8, 8, 0.0F);
		this.RightElbow = new RendererModel(this, 0, 97);
		this.RightElbow.setRotationPoint(-1.5F, 8.0F, 1.1F);
		this.RightElbow.addBox(-1.7F, -0.5F, -2.0F, 3, 3, 4, 0.0F);
		this.setRotateAngle(this.RightElbow, -0.3490658503988659F, 0.0F, 0.0F);
		this.Hood2 = new RendererModel(this, 24, 50);
		this.Hood2.setRotationPoint(3.0F, -4.0F, 0.0F);
		this.Hood2.addBox(-1.0F, -2.0F, -2.3F, 6, 4, 6, 0.0F);
		this.setRotateAngle(this.Hood2, 0.0F, 0.0F, 0.6108652381980153F);
		this.Robes6 = new RendererModel(this, 20, 70);
		this.Robes6.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Robes6.addBox(-1.0F, -0.5F, -2.0F, 5, 5, 5, 0.0F);
		this.LeftLeg3 = new RendererModel(this, 12, 165);
		this.LeftLeg3.setRotationPoint(0.0F, -0.8F, 3.0F);
		this.LeftLeg3.addBox(-1.7F, -0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(this.LeftLeg3, -0.08726646259971647F, 0.0F, 0.0F);
		this.LeftHand1 = new RendererModel(this, 30, 113);
		this.LeftHand1.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.LeftHand1.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 4, 0.0F);
		this.Torso1 = new RendererModel(this, 0, 124);
		this.Torso1.setRotationPoint(0.0F, -7.4F, 0.0F);
		this.Torso1.addBox(-5.5F, 0.0F, -3.0F, 11, 6, 6, 0.0F);
		this.setRotateAngle(this.Torso1, 0.08726646259971647F, 0.0F, 0.0F);
		this.RightKnee = new RendererModel(this, 0, 165);
		this.RightKnee.mirror = true;
		this.RightKnee.setRotationPoint(0.0F, 7.6F, -1.3F);
		this.RightKnee.addBox(-1.5F, -2.0F, -2.0F, 3, 4, 3, 0.0F);
		this.RightFoot2 = new RendererModel(this, 16, 188);
		this.RightFoot2.mirror = true;
		this.RightFoot2.setRotationPoint(0.0F, 1.0F, 1.0F);
		this.RightFoot2.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 3, 0.0F);
		this.RightArm2 = new RendererModel(this, 0, 104);
		this.RightArm2.setRotationPoint(0.0F, 2.5F, 0.0F);
		this.RightArm2.addBox(-1.0F, 0.0F, -1.5F, 2, 4, 3, 0.0F);
		this.RightLeg1 = new RendererModel(this, 0, 150);
		this.RightLeg1.setRotationPoint(-2.5F, 7.0F, 0.0F);
		this.RightLeg1.addBox(-2.0F, -1.0F, -3.0F, 4, 9, 6, 0.0F);
		this.setRotateAngle(this.RightLeg1, 0.08726646259971647F, 0.0F, 0.08726646259971647F);
		this.Robes3 = new RendererModel(this, 36, 177);
		this.Robes3.setRotationPoint(0.0F, 3.0F, 3.5F);
		this.Robes3.addBox(-5.0F, 0.0F, 0.0F, 4, 15, 0, 0.0F);
		this.setRotateAngle(this.Robes3, 0.08726646259971647F, 0.0F, 0.0F);
		this.LeftFoot2 = new RendererModel(this, 16, 188);
		this.LeftFoot2.setRotationPoint(0.0F, 1.0F, 1.0F);
		this.LeftFoot2.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 3, 0.0F);
		this.LeftKnee = new RendererModel(this, 0, 165);
		this.LeftKnee.setRotationPoint(0.0F, 7.6F, -1.3F);
		this.LeftKnee.addBox(-1.5F, -2.0F, -2.0F, 3, 4, 3, 0.0F);
		this.RightHand1 = new RendererModel(this, 0, 112);
		this.RightHand1.setRotationPoint(0.0F, 5.5F, 0.0F);
		this.RightHand1.addBox(-1.1F, 0.4F, -1.5F, 2, 3, 3, 0.0F);
		this.setRotateAngle(this.RightHand1, 0.17453292519943295F, 0.08726646259971647F, -0.08726646259971647F);
		this.RightHand2 = new RendererModel(this, 8, 120);
		this.RightHand2.setRotationPoint(0.0F, 1.0F, -1.5F);
		this.RightHand2.addBox(0.0F, -0.1F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.RightHand2, -0.17453292519943295F, 0.0F, 0.0F);
		this.Neck = new RendererModel(this, 0, 41);
		this.Neck.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.Neck.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.Robes2 = new RendererModel(this, 26, 177);
		this.Robes2.setRotationPoint(0.0F, 3.0F, -3.5F);
		this.Robes2.addBox(-2.5F, 0.0F, 0.0F, 5, 15, 0, 0.0F);
		this.setRotateAngle(this.Robes2, 0.08726646259971647F, 0.0F, 0.0F);
		this.Neck.addChild(this.Hood3);
		this.Neck.addChild(this.Mask1);
		this.Torso1.addChild(this.Shoulders);
		this.Shoulders.addChild(this.Hood1);
		this.RightShoulder.addChild(this.Robes5);
		this.Torso2.addChild(this.LeftLeg1);
		this.LeftKnee.addChild(this.LeftAnkle);
		this.RightHand1.addChild(this.RightHand3);
		this.RightKnee.addChild(this.RightLeg2);
		this.Torso1.addChild(this.LeftShoulder);
		this.Mask1.addChild(this.Mask2);
		this.Torso1.addChild(this.Torso2);
		this.RightShoulder.addChild(this.RightArm1);
		this.LeftShoulder.addChild(this.LeftArm1);
		this.LeftShoulder.addChild(this.LeftShoulder_1);
		this.RightKnee.addChild(this.RightAnkle);
		this.RightAnkle.addChild(this.RightFoot1);
		this.RightKnee.addChild(this.RightLeg3);
		this.Torso1.addChild(this.RightShoulder);
		this.LeftHand1.addChild(this.LeftHand2);
		this.LeftKnee.addChild(this.LeftLeg2);
		this.LeftShoulder_1.addChild(this.LeftArm2);
		this.LeftAnkle.addChild(this.LeftFoot1);
		this.Robes1.addChild(this.Robes4);
		this.Torso2.addChild(this.Robes1);
		this.Neck.addChild(this.Head);
		this.Torso1.addChild(this.Robes7);
		this.RightShoulder.addChild(this.RightElbow);
		this.Shoulders.addChild(this.Hood2);
		this.LeftShoulder.addChild(this.Robes6);
		this.LeftKnee.addChild(this.LeftLeg3);
		this.LeftShoulder_1.addChild(this.LeftHand1);
		this.RightLeg1.addChild(this.RightKnee);
		this.RightFoot1.addChild(this.RightFoot2);
		this.RightElbow.addChild(this.RightArm2);
		this.Torso2.addChild(this.RightLeg1);
		this.Robes1.addChild(this.Robes3);
		this.LeftFoot1.addChild(this.LeftFoot2);
		this.LeftLeg1.addChild(this.LeftKnee);
		this.RightElbow.addChild(this.RightHand1);
		this.RightHand1.addChild(this.RightHand2);
		this.Shoulders.addChild(this.Neck);
		this.Robes1.addChild(this.Robes2);
	}

	@Override
	public void render(final EntityNecromancer entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.Torso1.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(final RendererModel modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(final EntityNecromancer entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw,
			final float headPitch, final float scaleFactor)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		final float pitch = headPitch * 0.017453292F;
		final float yaw = netHeadYaw * 0.017453292F;

		this.Neck.rotateAngleX = pitch;
		this.Neck.rotateAngleY = yaw;

		final float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.75F * limbSwingAmount);
		final float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.75F * limbSwingAmount);

		this.LeftLeg1.rotateAngleX = 0.08726646259971647F + leftSwingX;

		this.RightLeg1.rotateAngleX = 0.08726646259971647F + rightSwingX;

		this.LeftShoulder.rotateAngleX = leftSwingX;

		this.RightShoulder.rotateAngleX = rightSwingX;
	}
}
