package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRam extends ModelBase
{
	//fields
	ModelRenderer BodyMain;
	ModelRenderer BodyBack;
	ModelRenderer BodyBottom;
	ModelRenderer LegFrontLeft1;
	ModelRenderer LegFrontLeft2;
	ModelRenderer LegFrontLeft3;
	ModelRenderer LegFrontRight1;
	ModelRenderer LegFrontRight2;
	ModelRenderer LegFrontRight3;
	ModelRenderer LegBackLeft1;
	ModelRenderer LegBackLeft2;
	ModelRenderer LegBackLeft3;
	ModelRenderer LegBackRight1;
	ModelRenderer LegBackRight2;
	ModelRenderer LegBackRight3;
	ModelRenderer Tail;
	ModelRenderer HeadNeck;
	ModelRenderer HeadMain;
	ModelRenderer HeadSnout;
	ModelRenderer HeadCheekLeft;
	ModelRenderer HeadCheekRight;
	ModelRenderer HeadJaw;
	ModelRenderer HeadEyeRight;
	ModelRenderer HeadBrowRight;
	ModelRenderer HeadEyeLeft;
	ModelRenderer HeadBrowLeft;
	ModelRenderer HeadPlate;
	ModelRenderer HeadTop;
	ModelRenderer HeadEarLeft;
	ModelRenderer HeadEarRight;

	protected float childYOffset = 8.0F;
	protected float childZOffset = 4.0F;

	public ModelRam()
	{
		this.textureWidth = 128;
		this.textureHeight = 256;

		this.BodyMain = new ModelRenderer(this, 36, 74);
		this.BodyMain.addBox(-5F, 0F, -8F, 10, 13, 16);
		this.BodyMain.setRotationPoint(0F, 0F, 0F);
		this.BodyMain.setTextureSize(128, 256);
		this.BodyMain.mirror = true;
		this.setRotation(this.BodyMain, 0F, 0F, 0F);
		this.BodyBack = new ModelRenderer(this, 41, 103);
		this.BodyBack.addBox(-4F, -2F, 7F, 8, 9, 13);
		this.BodyBack.setRotationPoint(0F, 0F, 0F);
		this.BodyBack.setTextureSize(128, 256);
		this.BodyBack.mirror = true;
		this.setRotation(this.BodyBack, -0.3490659F, 0F, 0F);
		this.BodyBottom = new ModelRenderer(this, 36, 125);
		this.BodyBottom.addBox(-3F, 10F, -4F, 6, 5, 20);
		this.BodyBottom.setRotationPoint(0F, 0F, 0F);
		this.BodyBottom.setTextureSize(128, 256);
		this.BodyBottom.mirror = true;
		this.setRotation(this.BodyBottom, 0F, 0F, 0F);
		this.LegFrontLeft1 = new ModelRenderer(this, 88, 63);
		this.LegFrontLeft1.addBox(-3F, -4F, -2F, 5, 6, 5);
		this.LegFrontLeft1.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft1.setTextureSize(128, 256);
		this.LegFrontLeft1.mirror = true;
		this.setRotation(this.LegFrontLeft1, 0F, 0F, 0F);
		this.LegFrontLeft2 = new ModelRenderer(this, 91, 74);
		this.LegFrontLeft2.addBox(-2.5F, 0F, -1F, 4, 7, 3);
		this.LegFrontLeft2.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft2.setTextureSize(128, 256);
		this.LegFrontLeft2.mirror = true;
		this.setRotation(this.LegFrontLeft2, 0.0872665F, 0F, 0F);
		this.LegFrontLeft3 = new ModelRenderer(this, 93, 84);
		this.LegFrontLeft3.addBox(-2F, 6F, 0F, 3, 5, 2);
		this.LegFrontLeft3.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft3.setTextureSize(128, 256);
		this.LegFrontLeft3.mirror = true;
		this.setRotation(this.LegFrontLeft3, 0F, 0F, 0F);
		this.LegFrontRight1 = new ModelRenderer(this, 16, 63);
		this.LegFrontRight1.addBox(-2F, -4F, -2F, 5, 6, 5);
		this.LegFrontRight1.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight1.setTextureSize(128, 256);
		this.LegFrontRight1.mirror = true;
		this.setRotation(this.LegFrontRight1, 0F, 0F, 0F);
		this.LegFrontRight2 = new ModelRenderer(this, 19, 74);
		this.LegFrontRight2.addBox(-1.5F, 0F, -1F, 4, 7, 3);
		this.LegFrontRight2.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight2.setTextureSize(128, 256);
		this.LegFrontRight2.mirror = true;
		this.setRotation(this.LegFrontRight2, 0.0872665F, 0F, 0F);
		this.LegFrontRight3 = new ModelRenderer(this, 21, 84);
		this.LegFrontRight3.addBox(-1F, 6F, 0F, 3, 5, 2);
		this.LegFrontRight3.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight3.setTextureSize(128, 256);
		this.LegFrontRight3.mirror = true;
		this.setRotation(this.LegFrontRight3, 0F, 0F, 0F);
		this.LegBackLeft1 = new ModelRenderer(this, 88, 112);
		this.LegBackLeft1.addBox(-2F, -1F, -1F, 4, 7, 6);
		this.LegBackLeft1.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft1.setTextureSize(128, 256);
		this.LegBackLeft1.mirror = true;
		this.setRotation(this.LegBackLeft1, 0.6108652F, 0F, 0F);
		this.LegBackLeft2 = new ModelRenderer(this, 91, 125);
		this.LegBackLeft2.addBox(-2F, 6F, -1F, 4, 2, 3);
		this.LegBackLeft2.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft2.setTextureSize(128, 256);
		this.LegBackLeft2.mirror = true;
		this.setRotation(this.LegBackLeft2, 0.6108652F, 0F, 0F);
		this.LegBackLeft3 = new ModelRenderer(this, 93, 130);
		this.LegBackLeft3.addBox(-1.5F, 5F, 3.5F, 3, 7, 2);
		this.LegBackLeft3.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft3.setTextureSize(128, 256);
		this.LegBackLeft3.mirror = true;
		this.setRotation(this.LegBackLeft3, 0F, 0F, 0F);
		this.LegBackRight1 = new ModelRenderer(this, 16, 112);
		this.LegBackRight1.addBox(-2F, -1F, -1F, 4, 7, 6);
		this.LegBackRight1.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight1.setTextureSize(128, 256);
		this.LegBackRight1.mirror = true;
		this.setRotation(this.LegBackRight1, 0.6108652F, 0F, 0F);
		this.LegBackRight2 = new ModelRenderer(this, 19, 125);
		this.LegBackRight2.addBox(-2F, 6F, -1F, 4, 2, 3);
		this.LegBackRight2.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight2.setTextureSize(128, 256);
		this.LegBackRight2.mirror = true;
		this.setRotation(this.LegBackRight2, 0.6108652F, 0F, 0F);
		this.LegBackRight3 = new ModelRenderer(this, 21, 130);
		this.LegBackRight3.addBox(-1.5F, 5F, 3.5F, 3, 7, 2);
		this.LegBackRight3.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight3.setTextureSize(128, 256);
		this.LegBackRight3.mirror = true;
		this.setRotation(this.LegBackRight3, 0F, 0F, 0F);
		this.Tail = new ModelRenderer(this, 58, 150);
		this.Tail.addBox(-1.5F, 0F, 0F, 3, 8, 1);
		this.Tail.setRotationPoint(0F, 5F, 18F);
		this.Tail.setTextureSize(128, 256);
		this.Tail.mirror = true;
		this.setRotation(this.Tail, 0.1745329F, 0F, 0F);
		this.HeadNeck = new ModelRenderer(this, 49, 61);
		this.HeadNeck.addBox(-3F, -3F, -2.5F, 6, 6, 7);
		this.HeadNeck.setRotationPoint(0F, 6F, -9F);
		this.HeadNeck.setTextureSize(128, 256);
		this.HeadNeck.mirror = true;
		this.setRotation(this.HeadNeck, 0F, 0F, 0F);
		this.HeadMain = new ModelRenderer(this, 49, 31);
		this.HeadMain.addBox(-4F, -5.133333F, -6F, 8, 10, 5);
		this.HeadMain.setRotationPoint(0F, 6F, -9F);
		this.HeadMain.setTextureSize(128, 256);
		this.HeadMain.mirror = true;
		this.setRotation(this.HeadMain, 0F, 0F, 0F);
		this.HeadSnout = new ModelRenderer(this, 48, 17);
		this.HeadSnout.addBox(-2.5F, -7.5F, -11F, 5, 5, 9);
		this.HeadSnout.setRotationPoint(0F, 6F, -9F);
		this.HeadSnout.setTextureSize(128, 256);
		this.HeadSnout.mirror = true;
		this.setRotation(this.HeadSnout, 0.6108652F, 0F, 0F);
		this.HeadCheekLeft = new ModelRenderer(this, 75, 36);
		this.HeadCheekLeft.addBox(2.3F, 0.5F, -10.5F, 3, 4, 6);
		this.HeadCheekLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadCheekLeft.setTextureSize(128, 256);
		this.HeadCheekLeft.mirror = true;
		this.setRotation(this.HeadCheekLeft, 0F, 0.2617994F, 0F);
		this.HeadCheekRight = new ModelRenderer(this, 31, 36);
		this.HeadCheekRight.addBox(-5.3F, 0.5F, -10.5F, 3, 4, 6);
		this.HeadCheekRight.setRotationPoint(0F, 6F, -9F);
		this.HeadCheekRight.setTextureSize(128, 256);
		this.HeadCheekRight.mirror = true;
		this.setRotation(this.HeadCheekRight, 0F, -0.2617994F, 0F);
		this.HeadJaw = new ModelRenderer(this, 47, 46);
		this.HeadJaw.addBox(-2F, 3F, -11F, 4, 4, 11);
		this.HeadJaw.setRotationPoint(0F, 6F, -9F);
		this.HeadJaw.setTextureSize(128, 256);
		this.HeadJaw.mirror = true;
		this.setRotation(this.HeadJaw, -0.1745329F, 0F, 0F);
		this.HeadEyeRight = new ModelRenderer(this, 36, 30);
		this.HeadEyeRight.addBox(-4.6F, -2F, -7.5F, 1, 3, 3);
		this.HeadEyeRight.setRotationPoint(0F, 6F, -9F);
		this.HeadEyeRight.setTextureSize(128, 256);
		this.HeadEyeRight.mirror = true;
		this.setRotation(this.HeadEyeRight, 0F, -0.2617994F, 0F);
		this.HeadBrowRight = new ModelRenderer(this, 35, 26);
		this.HeadBrowRight.addBox(-5F, -3F, -7.7F, 2, 1, 3);
		this.HeadBrowRight.setRotationPoint(0F, 6F, -9F);
		this.HeadBrowRight.setTextureSize(128, 256);
		this.HeadBrowRight.mirror = true;
		this.setRotation(this.HeadBrowRight, 0F, -0.2617994F, 0F);
		this.HeadEyeLeft = new ModelRenderer(this, 80, 30);
		this.HeadEyeLeft.addBox(3.6F, -2F, -7.5F, 1, 3, 3);
		this.HeadEyeLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadEyeLeft.setTextureSize(128, 256);
		this.HeadEyeLeft.mirror = true;
		this.setRotation(this.HeadEyeLeft, 0F, 0.2617994F, 0F);
		this.HeadBrowLeft = new ModelRenderer(this, 79, 26);
		this.HeadBrowLeft.addBox(3F, -3F, -7.7F, 2, 1, 3);
		this.HeadBrowLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadBrowLeft.setTextureSize(128, 256);
		this.HeadBrowLeft.mirror = true;
		this.setRotation(this.HeadBrowLeft, 0F, 0.2617994F, 0F);
		this.HeadPlate = new ModelRenderer(this, 45, 2);
		this.HeadPlate.addBox(-4.5F, -8.5F, -4F, 9, 2, 8);
		this.HeadPlate.setRotationPoint(0F, 6F, -9F);
		this.HeadPlate.setTextureSize(128, 256);
		this.HeadPlate.mirror = true;
		this.setRotation(this.HeadPlate, 0.6108652F, 0F, 0F);
		this.HeadTop = new ModelRenderer(this, 52, 12);
		this.HeadTop.addBox(-3F, -6F, -4.533333F, 6, 1, 4);
		this.HeadTop.setRotationPoint(0F, 6F, -9F);
		this.HeadTop.setTextureSize(128, 256);
		this.HeadTop.mirror = true;
		this.setRotation(this.HeadTop, 0.1745329F, 0F, 0F);
		this.HeadEarLeft = new ModelRenderer(this, 93, 39);
		this.HeadEarLeft.addBox(4F, -1F, -5F, 1, 4, 3);
		this.HeadEarLeft.setRotationPoint(0F, 6F, -9F);
		this.HeadEarLeft.setTextureSize(128, 256);
		this.HeadEarLeft.mirror = true;
		this.setRotation(this.HeadEarLeft, 0F, 0F, -0.4363323F);
		this.HeadEarRight = new ModelRenderer(this, 23, 39);
		this.HeadEarRight.addBox(-5F, -1F, -5F, 1, 4, 3);
		this.HeadEarRight.setRotationPoint(0F, 6F, -9F);
		this.HeadEarRight.setTextureSize(128, 256);
		this.HeadEarRight.mirror = true;
		this.setRotation(this.HeadEarRight, 0F, 0F, 0.4363323F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		super.render(entity, f, f1, f2, f3, f4, scale);
		this.setRotationAngles(f, f1, f2, f3, f4, scale, entity);

		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 8.75F * scale, this.childZOffset * scale);
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
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
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

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.Tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

		this.HeadNeck.rotateAngleX = headPitch * 0.017453292F;
		this.HeadNeck.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadMain.rotateAngleX = headPitch * 0.017453292F;
		this.HeadMain.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadSnout.rotateAngleX = 0.6108652F + (headPitch * 0.017453292F);
		this.HeadSnout.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadCheekLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadCheekLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadCheekRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadCheekRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadJaw.rotateAngleX = -0.1745329F + (headPitch * 0.017453292F);
		this.HeadJaw.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEyeLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEyeLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadEyeRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEyeRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadBrowLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadBrowLeft.rotateAngleY = 0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadBrowRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadBrowRight.rotateAngleY = -0.2617994F + (netHeadYaw * 0.017453292F);
		this.HeadPlate.rotateAngleX = 0.6108652F + (headPitch * 0.017453292F);
		this.HeadPlate.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadTop.rotateAngleX = 0.1745329F + (headPitch * 0.017453292F);
		this.HeadTop.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEarLeft.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEarLeft.rotateAngleY = netHeadYaw * 0.017453292F;
		this.HeadEarRight.rotateAngleX = headPitch * 0.017453292F;
		this.HeadEarRight.rotateAngleY = netHeadYaw * 0.017453292F;

		this.LegFrontLeft1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegFrontLeft2.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegFrontLeft3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		this.LegFrontRight1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
		this.LegFrontRight2.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
		this.LegFrontRight3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

		this.LegBackLeft1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
		this.LegBackLeft2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
		this.LegBackLeft3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

		this.LegBackRight1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegBackRight2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegBackRight3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

}
