package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelKirrid extends EntityModel<EntityKirrid>
{
	public final RendererModel BodyMain;

	public final RendererModel BodyBack;

	public final RendererModel BodyBottom;

	public final RendererModel LegFrontLeft1;

	public final RendererModel LegFrontLeft2;

	public final RendererModel LegFrontLeft3;

	public final RendererModel LegFrontRight1;

	public final RendererModel LegFrontRight2;

	public final RendererModel LegFrontRight3;

	public final RendererModel LegBackLeft1;

	public final RendererModel LegBackLeft2;

	public final RendererModel LegBackLeft3;

	public final RendererModel LegBackRight1;

	public final RendererModel LegBackRight2;

	public final RendererModel LegBackRight3;

	public final RendererModel Tail;

	public final RendererModel HeadNeck;

	public final RendererModel HeadMain;

	public final RendererModel HeadSnout;

	public final RendererModel HeadCheekLeft;

	public final RendererModel HeadCheekRight;

	public final RendererModel HeadJaw;

	public final RendererModel HeadEyeRight;

	public final RendererModel HeadBrowRight;

	public final RendererModel HeadEyeLeft;

	public final RendererModel HeadBrowLeft;

	public final RendererModel HeadPlate;

	public final RendererModel HeadTop;

	public final RendererModel HeadEarLeft;

	public final RendererModel HeadEarRight;

	public final float childZOffset = 4.0F;

	public ModelKirrid()
	{
		this.textureWidth = 128;
		this.textureHeight = 256;

		this.BodyMain = new RendererModel(this, 36, 74);
		this.BodyMain.addBox(-5F, 0F, -8F, 10, 13, 16);
		this.BodyMain.setRotationPoint(0F, 0F, 0F);
		this.BodyMain.setTextureSize(128, 256);
		this.BodyMain.mirror = true;
		this.setRotation(this.BodyMain, 0F, 0F, 0F);
		this.BodyBack = new RendererModel(this, 41, 103);
		this.BodyBack.addBox(-4F, -2F, 7F, 8, 9, 13);
		this.BodyBack.setRotationPoint(0F, 0F, 0F);
		this.BodyBack.setTextureSize(128, 256);
		this.BodyBack.mirror = true;
		this.setRotation(this.BodyBack, -0.3490659F, 0F, 0F);
		this.BodyBottom = new RendererModel(this, 36, 125);
		this.BodyBottom.addBox(-3F, 10F, -4F, 6, 5, 20);
		this.BodyBottom.setRotationPoint(0F, 0F, 0F);
		this.BodyBottom.setTextureSize(128, 256);
		this.BodyBottom.mirror = true;
		this.setRotation(this.BodyBottom, 0F, 0F, 0F);
		this.LegFrontLeft1 = new RendererModel(this, 88, 63);
		this.LegFrontLeft1.addBox(-3F, -4F, -2F, 5, 6, 5);
		this.LegFrontLeft1.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft1.setTextureSize(128, 256);
		this.LegFrontLeft1.mirror = true;
		this.setRotation(this.LegFrontLeft1, 0F, 0F, 0F);
		this.LegFrontLeft2 = new RendererModel(this, 91, 74);
		this.LegFrontLeft2.addBox(-2.5F, 0F, -1F, 4, 7, 3);
		this.LegFrontLeft2.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft2.setTextureSize(128, 256);
		this.LegFrontLeft2.mirror = true;
		this.setRotation(this.LegFrontLeft2, 0.0872665F, 0F, 0F);
		this.LegFrontLeft3 = new RendererModel(this, 93, 84);
		this.LegFrontLeft3.addBox(-2F, 6F, 0F, 3, 5, 2);
		this.LegFrontLeft3.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft3.setTextureSize(128, 256);
		this.LegFrontLeft3.mirror = true;
		this.setRotation(this.LegFrontLeft3, 0F, 0F, 0F);
		this.LegFrontRight1 = new RendererModel(this, 16, 63);
		this.LegFrontRight1.addBox(-2F, -4F, -2F, 5, 6, 5);
		this.LegFrontRight1.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight1.setTextureSize(128, 256);
		this.LegFrontRight1.mirror = true;
		this.setRotation(this.LegFrontRight1, 0F, 0F, 0F);
		this.LegFrontRight2 = new RendererModel(this, 19, 74);
		this.LegFrontRight2.addBox(-1.5F, 0F, -1F, 4, 7, 3);
		this.LegFrontRight2.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight2.setTextureSize(128, 256);
		this.LegFrontRight2.mirror = true;
		this.setRotation(this.LegFrontRight2, 0.0872665F, 0F, 0F);
		this.LegFrontRight3 = new RendererModel(this, 21, 84);
		this.LegFrontRight3.addBox(-1F, 6F, 0F, 3, 5, 2);
		this.LegFrontRight3.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight3.setTextureSize(128, 256);
		this.LegFrontRight3.mirror = true;
		this.setRotation(this.LegFrontRight3, 0F, 0F, 0F);
		this.LegBackLeft1 = new RendererModel(this, 88, 112);
		this.LegBackLeft1.addBox(-2F, -1F, -1F, 4, 7, 6);
		this.LegBackLeft1.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft1.setTextureSize(128, 256);
		this.LegBackLeft1.mirror = true;
		this.setRotation(this.LegBackLeft1, 0.6108652F, 0F, 0F);
		this.LegBackLeft2 = new RendererModel(this, 91, 125);
		this.LegBackLeft2.addBox(-2F, 6F, -1F, 4, 2, 3);
		this.LegBackLeft2.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft2.setTextureSize(128, 256);
		this.LegBackLeft2.mirror = true;
		this.setRotation(this.LegBackLeft2, 0.6108652F, 0F, 0F);
		this.LegBackLeft3 = new RendererModel(this, 93, 130);
		this.LegBackLeft3.addBox(-1.5F, 5F, 3.5F, 3, 7, 2);
		this.LegBackLeft3.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft3.setTextureSize(128, 256);
		this.LegBackLeft3.mirror = true;
		this.setRotation(this.LegBackLeft3, 0F, 0F, 0F);
		this.LegBackRight1 = new RendererModel(this, 16, 112);
		this.LegBackRight1.addBox(-2F, -1F, -1F, 4, 7, 6);
		this.LegBackRight1.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight1.setTextureSize(128, 256);
		this.LegBackRight1.mirror = true;
		this.setRotation(this.LegBackRight1, 0.6108652F, 0F, 0F);
		this.LegBackRight2 = new RendererModel(this, 19, 125);
		this.LegBackRight2.addBox(-2F, 6F, -1F, 4, 2, 3);
		this.LegBackRight2.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight2.setTextureSize(128, 256);
		this.LegBackRight2.mirror = true;
		this.setRotation(this.LegBackRight2, 0.6108652F, 0F, 0F);
		this.LegBackRight3 = new RendererModel(this, 21, 130);
		this.LegBackRight3.addBox(-1.5F, 5F, 3.5F, 3, 7, 2);
		this.LegBackRight3.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight3.setTextureSize(128, 256);
		this.LegBackRight3.mirror = true;
		this.setRotation(this.LegBackRight3, 0F, 0F, 0F);
		this.Tail = new RendererModel(this, 58, 150);
		this.Tail.addBox(-1.5F, 0F, 0F, 3, 8, 1);
		this.Tail.setRotationPoint(0F, 5F, 18F);
		this.Tail.setTextureSize(128, 256);
		this.Tail.mirror = true;
		this.setRotation(this.Tail, 0.1745329F, 0F, 0F);
		this.HeadNeck = new RendererModel(this, 49, 61);
		this.HeadNeck.addBox(-3F, -3F, -2.5F, 6, 6, 7);
		this.HeadNeck.setRotationPoint(0F, 6F, -9F);
		this.HeadNeck.setTextureSize(128, 256);
		this.HeadNeck.mirror = true;
		this.setRotation(this.HeadNeck, 0F, 0F, 0F);
		this.HeadMain = new RendererModel(this, 49, 31);
		this.HeadMain.addBox(-4F, -5.133333F, -6F, 8, 10, 5);
		this.HeadMain.setRotationPoint(0F, 6F, -9F);
		this.HeadMain.setTextureSize(128, 256);
		this.HeadMain.mirror = true;
		this.setRotation(this.HeadMain, 0F, 0F, 0F);
		this.HeadSnout = new RendererModel(this, 48, 17);
		this.HeadSnout.addBox(-2.5F, -7.5F, -11F, 5, 5, 9);
		this.HeadSnout.setRotationPoint(0F, 6F, -9F);
		this.HeadSnout.setTextureSize(128, 256);
		this.HeadSnout.mirror = true;
		this.setRotation(this.HeadSnout, 0.6108652F, 0F, 0F);
		this.HeadCheekLeft = new RendererModel(this, 75, 36);
		this.HeadCheekLeft.addBox(2.3F, 0.5F, -10.5F, 3, 4, 6);
		this.HeadCheekLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadCheekLeft.setTextureSize(128, 256);
		this.HeadCheekLeft.mirror = true;
		this.setRotation(this.HeadCheekLeft, 0F, 0.2617994F, 0F);
		this.HeadCheekRight = new RendererModel(this, 31, 36);
		this.HeadCheekRight.addBox(-5.3F, 0.5F, -10.5F, 3, 4, 6);
		this.HeadCheekRight.setRotationPoint(0F, 6F, -9F);
		this.HeadCheekRight.setTextureSize(128, 256);
		this.HeadCheekRight.mirror = true;
		this.setRotation(this.HeadCheekRight, 0F, -0.2617994F, 0F);
		this.HeadJaw = new RendererModel(this, 47, 46);
		this.HeadJaw.addBox(-2F, 3F, -11F, 4, 4, 11);
		this.HeadJaw.setRotationPoint(0F, 6F, -9F);
		this.HeadJaw.setTextureSize(128, 256);
		this.HeadJaw.mirror = true;
		this.setRotation(this.HeadJaw, -0.1745329F, 0F, 0F);
		this.HeadEyeRight = new RendererModel(this, 36, 30);
		this.HeadEyeRight.addBox(-4.6F, -2F, -7.5F, 1, 3, 3);
		this.HeadEyeRight.setRotationPoint(0F, 6F, -9F);
		this.HeadEyeRight.setTextureSize(128, 256);
		this.HeadEyeRight.mirror = true;
		this.setRotation(this.HeadEyeRight, 0F, -0.2617994F, 0F);
		this.HeadBrowRight = new RendererModel(this, 35, 26);
		this.HeadBrowRight.addBox(-5F, -3F, -7.7F, 2, 1, 3);
		this.HeadBrowRight.setRotationPoint(0F, 6F, -9F);
		this.HeadBrowRight.setTextureSize(128, 256);
		this.HeadBrowRight.mirror = true;
		this.setRotation(this.HeadBrowRight, 0F, -0.2617994F, 0F);
		this.HeadEyeLeft = new RendererModel(this, 80, 30);
		this.HeadEyeLeft.addBox(3.6F, -2F, -7.5F, 1, 3, 3);
		this.HeadEyeLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadEyeLeft.setTextureSize(128, 256);
		this.HeadEyeLeft.mirror = true;
		this.setRotation(this.HeadEyeLeft, 0F, 0.2617994F, 0F);
		this.HeadBrowLeft = new RendererModel(this, 79, 26);
		this.HeadBrowLeft.addBox(3F, -3F, -7.7F, 2, 1, 3);
		this.HeadBrowLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadBrowLeft.setTextureSize(128, 256);
		this.HeadBrowLeft.mirror = true;
		this.setRotation(this.HeadBrowLeft, 0F, 0.2617994F, 0F);
		this.HeadPlate = new RendererModel(this, 45, 2);
		this.HeadPlate.addBox(-4.5F, -8.5F, -4F, 9, 2, 8);
		this.HeadPlate.setRotationPoint(0F, 6F, -9F);
		this.HeadPlate.setTextureSize(128, 256);
		this.HeadPlate.mirror = true;
		this.setRotation(this.HeadPlate, 0.6108652F, 0F, 0F);
		this.HeadTop = new RendererModel(this, 52, 12);
		this.HeadTop.addBox(-3F, -6F, -4.533333F, 6, 1, 4);
		this.HeadTop.setRotationPoint(0F, 6F, -9F);
		this.HeadTop.setTextureSize(128, 256);
		this.HeadTop.mirror = true;
		this.setRotation(this.HeadTop, 0.1745329F, 0F, 0F);
		this.HeadEarLeft = new RendererModel(this, 93, 39);
		this.HeadEarLeft.addBox(4F, -1F, -5F, 1, 4, 3);
		this.HeadEarLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadEarLeft.setTextureSize(128, 256);
		this.HeadEarLeft.mirror = true;
		this.setRotation(this.HeadEarLeft, 0F, 0F, -0.4363323F);
		this.HeadEarRight = new RendererModel(this, 23, 39);
		this.HeadEarRight.addBox(-5F, -1F, -5F, 1, 4, 3);
		this.HeadEarRight.setRotationPoint(0F, 6F, -9F);
		this.HeadEarRight.setTextureSize(128, 256);
		this.HeadEarRight.mirror = true;
		this.setRotation(this.HeadEarRight, 0F, 0F, 0.4363323F);
	}

	@Override
	public void render(EntityKirrid entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		super.render(entity, f, f1, f2, f3, f4, scale);
		this.setRotationAngles(entity, f, f1, f2, f3, f4, scale);

		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translatef(0.0F, 8.75F * scale, this.childZOffset * scale);
			this.HeadNeck.render(scale);
			this.HeadMain.render(scale);
			this.HeadSnout.render(scale);
			this.HeadCheekLeft.render(scale);
			this.HeadCheekRight.render(scale);
			this.HeadJaw.render(scale);
			this.HeadEyeRight.render(scale);
			this.HeadBrowRight.render(scale);
			this.HeadEyeLeft.render(scale);
			this.HeadBrowLeft.render(scale);
			this.HeadPlate.render(scale);
			this.HeadTop.render(scale);
			this.HeadEarLeft.render(scale);
			this.HeadEarRight.render(scale);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scalef(0.5F, 0.5F, 0.5F);
			GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
			this.BodyMain.render(scale);
			this.BodyBack.render(scale);
			this.BodyBottom.render(scale);
			this.LegFrontLeft1.render(scale);
			this.LegFrontLeft2.render(scale);
			this.LegFrontLeft3.render(scale);
			this.LegFrontRight1.render(scale);
			this.LegFrontRight2.render(scale);
			this.LegFrontRight3.render(scale);
			this.LegBackLeft1.render(scale);
			this.LegBackLeft2.render(scale);
			this.LegBackLeft3.render(scale);
			this.LegBackRight1.render(scale);
			this.LegBackRight2.render(scale);
			this.LegBackRight3.render(scale);
			this.Tail.render(scale);
			GlStateManager.popMatrix();
		}
		else
		{
			this.BodyMain.render(scale);
			this.BodyBack.render(scale);
			this.BodyBottom.render(scale);
			this.LegFrontLeft1.render(scale);
			this.LegFrontLeft2.render(scale);
			this.LegFrontLeft3.render(scale);
			this.LegFrontRight1.render(scale);
			this.LegFrontRight2.render(scale);
			this.LegFrontRight3.render(scale);
			this.LegBackLeft1.render(scale);
			this.LegBackLeft2.render(scale);
			this.LegBackLeft3.render(scale);
			this.LegBackRight1.render(scale);
			this.LegBackRight2.render(scale);
			this.LegBackRight3.render(scale);
			this.Tail.render(scale);
			this.HeadNeck.render(scale);
			this.HeadMain.render(scale);
			this.HeadSnout.render(scale);
			this.HeadCheekLeft.render(scale);
			this.HeadCheekRight.render(scale);
			this.HeadJaw.render(scale);
			this.HeadEyeRight.render(scale);
			this.HeadBrowRight.render(scale);
			this.HeadEyeLeft.render(scale);
			this.HeadBrowLeft.render(scale);
			this.HeadPlate.render(scale);
			this.HeadTop.render(scale);
			this.HeadEarLeft.render(scale);
			this.HeadEarRight.render(scale);
		}
	}

	public void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(EntityKirrid entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		this.Tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

		this.setHeadRotations(headPitch);

		this.HeadNeck.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadMain.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadSnout.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadCheekLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadCheekRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadJaw.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEyeLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadEyeRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadBrowLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadBrowRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadPlate.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadTop.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEarLeft.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEarRight.rotateAngleY = netHeadYaw * 0.017453292F;

		this.LegFrontLeft1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegFrontLeft2.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegFrontLeft3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		this.LegFrontRight1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
		this.LegFrontRight2.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
		this.LegFrontRight3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

		this.LegBackLeft1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
		this.LegBackLeft2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
		this.LegBackLeft3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

		this.LegBackRight1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegBackRight2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegBackRight3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

	}

	@Override
	public void setLivingAnimations(EntityKirrid entity, float p_78086_2_, float p_78086_3_, float partialTickTime)
	{
		super.setLivingAnimations(entity, p_78086_2_, p_78086_3_, partialTickTime);

		float rotationPointY = 6.0F + entity.getHeadRotationPointY(partialTickTime) * 9.0F;
		float headAng = entity.getHeadRotationAngleX(partialTickTime);

		this.HeadNeck.rotationPointY = rotationPointY;
		this.HeadMain.rotationPointY = rotationPointY;
		this.HeadSnout.rotationPointY = rotationPointY;
		this.HeadCheekLeft.rotationPointY = rotationPointY;
		this.HeadCheekRight.rotationPointY = rotationPointY;
		this.HeadJaw.rotationPointY = rotationPointY;
		this.HeadEyeLeft.rotationPointY = rotationPointY;
		this.HeadEyeRight.rotationPointY = rotationPointY;
		this.HeadBrowLeft.rotationPointY = rotationPointY;
		this.HeadBrowRight.rotationPointY = rotationPointY;
		this.HeadPlate.rotationPointY = rotationPointY;
		this.HeadTop.rotationPointY = rotationPointY;
		this.HeadEarLeft.rotationPointY = rotationPointY;
		this.HeadEarRight.rotationPointY = rotationPointY;

		this.HeadCheekLeft.offsetX =
				MathHelper.sin(headAng * -0.3662F + (float) Math.PI) * 0.1F * ((EntityKirrid) entity).getHeadRotationAngleX(partialTickTime)
						+ .005f;
		this.HeadCheekRight.offsetX =
				MathHelper.sin(headAng * 0.3662F + (float) Math.PI) * 0.1F * ((EntityKirrid) entity).getHeadRotationAngleX(partialTickTime) - .001f;
		this.HeadJaw.offsetY =
				MathHelper.sin(headAng * 0.6662F + (float) Math.PI) * 0.2F * ((EntityKirrid) entity).getHeadRotationAngleX(partialTickTime);

		this.setHeadRotations(headAng * 9.0F);
	}

	public void setHeadRotations(float headPitch)
	{
		this.HeadNeck.rotateAngleX = headPitch * 0.017453292F;
		this.HeadMain.rotateAngleX = headPitch * 0.017453292F;
		this.HeadSnout.rotateAngleX = 0.6108652F + (headPitch * 0.017453292F);
		this.HeadCheekLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadCheekRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadJaw.rotateAngleX = -0.1745329F + (headPitch * 0.017453292F);
		this.HeadEyeLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEyeRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadBrowLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadBrowRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadPlate.rotateAngleX = 0.6108652F + (headPitch * 0.017453292F);
		this.HeadTop.rotateAngleX = 0.1745329F + (headPitch * 0.017453292F);
		this.HeadEarLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEarRight.rotateAngleX = headPitch * 0.017453292F;
	}
}
