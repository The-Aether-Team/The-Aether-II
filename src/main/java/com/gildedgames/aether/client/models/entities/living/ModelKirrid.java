package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.math.MathHelper;

public class ModelKirrid extends ModelBase
{
	public ModelRenderer BodyMain;
	public ModelRenderer BodyBack;
	public ModelRenderer Tail;
	public ModelRenderer HeadNeck;
	public ModelRenderer LegBackRight1;
	public ModelRenderer LegBackLeft1;
	public ModelRenderer LegFrontLeft1;
	public ModelRenderer LegFrontRight1;
	public ModelRenderer HeadMain;
	public ModelRenderer HeadSnout;
	public ModelRenderer HeadCheekLeft;
	public ModelRenderer HeadJaw;
	public ModelRenderer HeadEyeRight;
	public ModelRenderer HeadCheekRight;
	public ModelRenderer HeadEyeLeft;
	public ModelRenderer HeadPlate;
	public ModelRenderer HeadEarLeft;
	public ModelRenderer HeadEarRight;
	public ModelRenderer LegBackRight2;
	public ModelRenderer LegBacRight3;
	public ModelRenderer LegBackLeft2;
	public ModelRenderer LegBackLeft3;
	public ModelRenderer LegFrontLeft2;
	public ModelRenderer LegFrontLeft3;
	public ModelRenderer LegFrontRight2;
	public ModelRenderer LegFrontRight3;

	public final float childZOffset = 4.0F;

	public ModelKirrid()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.LegFrontRight3 = new ModelRenderer(this, 23, 71);
		this.LegFrontRight3.setRotationPoint(0.0F, 5.0F, 0.5F);
		this.LegFrontRight3.addBox(-0.5F, 0.0F, -0.9F, 2, 5, 2, 0.0F);
		this.setRotateAngle(LegFrontRight3, -0.08726646259971647F, -0.0F, 0.0F);
		this.LegBackLeft2 = new ModelRenderer(this, 92, 96);
		this.LegBackLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegBackLeft2.addBox(-1.5F, 6.6F, -6.5F, 3, 4, 4, 0.0F);
		this.setRotateAngle(LegBackLeft2, 0.5235987755982988F, -0.0F, 0.0F);
		this.HeadMain = new ModelRenderer(this, 49, 24);
		this.HeadMain.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadMain.addBox(-4.0F, -5.13F, -6.0F, 8, 10, 5, 0.0F);
		this.LegFrontLeft3 = new ModelRenderer(this, 93, 71);
		this.LegFrontLeft3.setRotationPoint(0.0F, 5.0F, 0.5F);
		this.LegFrontLeft3.addBox(-1.5F, 0.0F, -0.9F, 2, 5, 2, 0.0F);
		this.setRotateAngle(LegFrontLeft3, -0.08726646259971647F, -0.0F, 0.0F);
		this.LegBackLeft1 = new ModelRenderer(this, 88, 79);
		this.LegBackLeft1.setRotationPoint(3.5F, 7.0F, 9.0F);
		this.LegBackLeft1.addBox(-2.0F, -1.0F, -3.0F, 4, 10, 7, 0.0F);
		this.setRotateAngle(LegBackLeft1, 0.22689280275926282F, -0.0F, 0.0F);
		this.BodyMain = new ModelRenderer(this, 36, 66);
		this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BodyMain.addBox(-5.0F, 0.0F, -8.0F, 10, 14, 16, 0.0F);
		this.Tail = new ModelRenderer(this, 57, 117);
		this.Tail.setRotationPoint(0.0F, 3.5F, 16.8F);
		this.Tail.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 2, 0.0F);
		this.setRotateAngle(Tail, 0.4363323129985824F, -0.0F, 0.0F);
		this.HeadEarLeft = new ModelRenderer(this, 93, 39);
		this.HeadEarLeft.setRotationPoint(3.5F, -2.0F, -4.0F);
		this.HeadEarLeft.addBox(0.0F, -1.0F, -1.0F, 1, 4, 3, 0.0F);
		this.setRotateAngle(HeadEarLeft, 0.0F, -0.0F, -0.4363323129985824F);
		this.HeadEarRight = new ModelRenderer(this, 23, 39);
		this.HeadEarRight.setRotationPoint(-3.5F, -2.0F, -4.0F);
		this.HeadEarRight.addBox(-1.0F, -1.0F, -1.0F, 1, 4, 3, 0.0F);
		this.setRotateAngle(HeadEarRight, 0.0F, 0.0F, 0.4363323129985824F);
		this.HeadNeck = new ModelRenderer(this, 52, 54);
		this.HeadNeck.setRotationPoint(0.0F, 6.0F, -9.0F);
		this.HeadNeck.addBox(-2.0F, -3.0F, -2.5F, 4, 6, 6, 0.0F);
		this.LegBackRight2 = new ModelRenderer(this, 18, 96);
		this.LegBackRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegBackRight2.addBox(-1.5F, 6.6F, -6.5F, 3, 4, 4, 0.0F);
		this.setRotateAngle(LegBackRight2, 0.5235987755982988F, -0.0F, 0.0F);
		this.HeadSnout = new ModelRenderer(this, 48, 10);
		this.HeadSnout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadSnout.addBox(-2.5F, -7.5F, -11.0F, 5, 5, 9, 0.0F);
		this.setRotateAngle(HeadSnout, 0.6108652381980153F, -0.0F, 0.0F);
		this.LegFrontRight2 = new ModelRenderer(this, 21, 61);
		this.LegFrontRight2.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.LegFrontRight2.addBox(-1.0F, -1.0F, -1.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(LegFrontRight2, 0.08726646259971647F, -0.0F, 0.0F);
		this.LegBackRight1 = new ModelRenderer(this, 14, 79);
		this.LegBackRight1.setRotationPoint(-3.5F, 7.0F, 9.0F);
		this.LegBackRight1.addBox(-2.0F, -1.0F, -3.0F, 4, 10, 7, 0.0F);
		this.setRotateAngle(LegBackRight1, 0.22689280275926282F, -0.0F, 0.0F);
		this.LegFrontLeft1 = new ModelRenderer(this, 88, 50);
		this.LegFrontLeft1.setRotationPoint(4.0F, 13.0F, -5.0F);
		this.LegFrontLeft1.addBox(-2.5F, -4.0F, -2.0F, 4, 6, 5, 0.0F);
		this.HeadCheekRight = new ModelRenderer(this, 31, 36);
		this.HeadCheekRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekRight.addBox(-5.3F, 0.8F, -10.5F, 3, 4, 6, 0.0F);
		this.setRotateAngle(HeadCheekRight, 0.0F, -0.2617993877991494F, 0.0F);
		this.LegFrontRight1 = new ModelRenderer(this, 18, 50);
		this.LegFrontRight1.setRotationPoint(-4.0F, 13.0F, -5.0F);
		this.LegFrontRight1.addBox(-1.5F, -4.0F, -2.0F, 4, 6, 5, 0.0F);
		this.HeadCheekLeft = new ModelRenderer(this, 75, 36);
		this.HeadCheekLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekLeft.addBox(2.3F, 0.8F, -10.5F, 3, 4, 6, 0.0F);
		this.setRotateAngle(HeadCheekLeft, 0.0F, 0.2617993877991494F, 0.0F);
		this.LegBackLeft3 = new ModelRenderer(this, 95, 104);
		this.LegBackLeft3.setRotationPoint(0.0F, 7.5F, 1.0F);
		this.LegBackLeft3.addBox(-1.0F, 4.5F, -2.1F, 2, 8, 2, 0.0F);
		this.setRotateAngle(LegBackLeft3, -0.8726646259971648F, -0.0F, 0.0F);
		this.LegFrontLeft2 = new ModelRenderer(this, 91, 61);
		this.LegFrontLeft2.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.LegFrontLeft2.addBox(-2.0F, -1.0F, -1.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(LegFrontLeft2, 0.08726646259971647F, -0.0F, 0.0F);
		this.LegBacRight3 = new ModelRenderer(this, 21, 104);
		this.LegBacRight3.setRotationPoint(0.0F, 7.5F, 1.0F);
		this.LegBacRight3.addBox(-1.0F, 4.5F, -2.1F, 2, 8, 2, 0.0F);
		this.setRotateAngle(LegBacRight3, -0.8726646259971648F, -0.0F, 0.0F);
		this.HeadPlate = new ModelRenderer(this, 45, 0);
		this.HeadPlate.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadPlate.addBox(-4.5F, -8.5F, -4.0F, 9, 2, 8, 0.0F);
		this.setRotateAngle(HeadPlate, 0.6108652381980153F, -0.0F, 0.0F);
		this.HeadJaw = new ModelRenderer(this, 47, 39);
		this.HeadJaw.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadJaw.addBox(-2.0F, 3.0F, -11.0F, 4, 4, 11, 0.0F);
		this.setRotateAngle(HeadJaw, -0.12217304763960307F, -0.0F, 0.0F);
		this.HeadEyeRight = new ModelRenderer(this, 36, 29);
		this.HeadEyeRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeRight.addBox(-4.6F, -3.0F, -7.5F, 1, 4, 3, 0.0F);
		this.setRotateAngle(HeadEyeRight, 0.0F, -0.2617993877991494F, 0.0F);
		this.BodyBack = new ModelRenderer(this, 44, 96);
		this.BodyBack.setRotationPoint(0.0F, 1.5F, 0.0F);
		this.BodyBack.addBox(-4.0F, -2.0F, 7.0F, 8, 11, 10, 0.0F);
		this.setRotateAngle(BodyBack, -0.17453292519943295F, 0.0F, 0.0F);
		this.HeadEyeLeft = new ModelRenderer(this, 80, 29);
		this.HeadEyeLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeLeft.addBox(3.6F, -3.0F, -7.5F, 1, 4, 3, 0.0F);
		this.setRotateAngle(HeadEyeLeft, 0.0F, 0.2617993877991494F, 0.0F);
		this.LegFrontRight2.addChild(this.LegFrontRight3);
		this.LegBackLeft1.addChild(this.LegBackLeft2);
		this.HeadNeck.addChild(this.HeadMain);
		this.LegFrontLeft2.addChild(this.LegFrontLeft3);
		this.BodyMain.addChild(this.LegBackLeft1);
		this.BodyMain.addChild(this.Tail);
		this.HeadNeck.addChild(this.HeadEarLeft);
		this.HeadNeck.addChild(this.HeadEarRight);
		this.BodyMain.addChild(this.HeadNeck);
		this.LegBackRight1.addChild(this.LegBackRight2);
		this.HeadNeck.addChild(this.HeadSnout);
		this.LegFrontRight1.addChild(this.LegFrontRight2);
		this.BodyMain.addChild(this.LegBackRight1);
		this.BodyMain.addChild(this.LegFrontLeft1);
		this.HeadNeck.addChild(this.HeadCheekRight);
		this.BodyMain.addChild(this.LegFrontRight1);
		this.HeadNeck.addChild(this.HeadCheekLeft);
		this.LegBackLeft2.addChild(this.LegBackLeft3);
		this.LegFrontLeft1.addChild(this.LegFrontLeft2);
		this.LegBackRight2.addChild(this.LegBacRight3);
		this.HeadNeck.addChild(this.HeadPlate);
		this.HeadNeck.addChild(this.HeadJaw);
		this.HeadNeck.addChild(this.HeadEyeRight);
		this.BodyMain.addChild(this.BodyBack);
		this.HeadNeck.addChild(this.HeadEyeLeft);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
			this.BodyMain.render(f5);
			GlStateManager.popMatrix();
		}
		else
		{
			this.BodyMain.render(f5);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
								  float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.Tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

		this.HeadNeck.rotateAngleX = headPitch * 0.017453292F;
		this.HeadNeck.rotateAngleY = netHeadYaw * 0.017453292F;

		this.LegFrontLeft1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
		this.LegFrontRight1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);

		this.LegBackLeft1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
		this.LegBackRight1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

		this.HeadNeck.rotationPointY = 6.0F + ((EntityKirrid) entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
		this.HeadNeck.rotateAngleX = ((EntityKirrid) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
	}
}