package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTaegore extends ModelBaseAether
{
	public ModelRenderer BodyMain;
	public ModelRenderer HeadMain;
	public ModelRenderer TailRight;
	public ModelRenderer BodyFront;
	public ModelRenderer PlateFrontLeft;
	public ModelRenderer PlateBackLeft;
	public ModelRenderer BodyBack;
	public ModelRenderer PlateBackRight;
	public ModelRenderer PlateFrontRight;
	public ModelRenderer LegFrontLeft1;
	public ModelRenderer LegFrontRight1;
	public ModelRenderer LegBackLeft1;
	public ModelRenderer LegBackRight1;
	public ModelRenderer TailLeft;
	public ModelRenderer TorsoRidge;
	public ModelRenderer SnoutRight;
	public ModelRenderer SnoutLeft;
	public ModelRenderer SnoutRidge;
	public ModelRenderer EyeLeft;
	public ModelRenderer EyeRight;
	public ModelRenderer CrestMiddle;
	public ModelRenderer CrestLeft;
	public ModelRenderer CrestRight;
	public ModelRenderer Jaw;
	public ModelRenderer Snout;
	public ModelRenderer HeadTop;
	public ModelRenderer EarLeft;
	public ModelRenderer EarRight;
	public ModelRenderer TuskLeft;
	public ModelRenderer TuskRight;
	public ModelRenderer LegFrontLeft2;
	public ModelRenderer LegFrontRight2;
	public ModelRenderer LegBackLeft2;
	public ModelRenderer LegBackRight2;

	public final float childZOffset = 4.0F;

	public ModelTaegore() {
		this.textureWidth = 128;
		this.textureHeight = 256;
		this.BodyFront = new ModelRenderer(this, 39, 67);
		this.BodyFront.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.BodyFront.addBox(-6.0F, -5.0F, -13.0F, 12, 16, 15, 0.0F);
		this.setRotateAngle(BodyFront, 0.4363323129985824F, 0.0F, 0.0F);
		this.PlateFrontRight = new ModelRenderer(this, 13, 76);
		this.PlateFrontRight.setRotationPoint(0.0F, 7.5F, 0.0F);
		this.PlateFrontRight.addBox(-9.0F, -1.0F, -8.5F, 4, 13, 9, 0.0F);
		this.setRotateAngle(PlateFrontRight, 0.0F, -0.17453292519943295F, 0.3490658503988659F);
		this.TailLeft = new ModelRenderer(this, 56, 165);
		this.TailLeft.setRotationPoint(0.0F, 5.7F, 15.8F);
		this.TailLeft.addBox(0.0F, 0.4F, -1.5F, 4, 16, 1, 0.0F);
		this.setRotateAngle(TailLeft, 0.2617993877991494F, 0.0F, -0.17453292519943295F);
		this.PlateBackLeft = new ModelRenderer(this, 95, 110);
		this.PlateBackLeft.setRotationPoint(0.0F, 6.0F, -4.5F);
		this.PlateBackLeft.addBox(5.5F, 2.5F, 9.0F, 4, 11, 9, 0.0F);
		this.setRotateAngle(PlateBackLeft, 0.08726646259971647F, -0.17453292519943295F, -0.3490658503988659F);
		this.SnoutLeft = new ModelRenderer(this, 75, 42);
		this.SnoutLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.SnoutLeft.addBox(2.8F, 3.4F, -10.2F, 5, 6, 14, 0.0F);
		this.setRotateAngle(SnoutLeft, -0.08726646259971647F, 0.4363323129985824F, 0.0F);
		this.HeadMain = new ModelRenderer(this, 48, 23);
		this.HeadMain.setRotationPoint(0.0F, 8.5F, -10.0F);
		this.HeadMain.addBox(-5.0F, -2.2F, -7.0F, 10, 11, 8, 0.0F);
		this.CrestMiddle = new ModelRenderer(this, 56, 0);
		this.CrestMiddle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.CrestMiddle.addBox(-1.5F, -9.0F, -8.0F, 3, 4, 7, 0.0F);
		this.setRotateAngle(CrestMiddle, 0.5235987755982988F, 0.0F, 0.0F);
		this.CrestLeft = new ModelRenderer(this, 76, 4);
		this.CrestLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.CrestLeft.addBox(2.5F, -9.1F, -6.0F, 2, 3, 4, 0.0F);
		this.setRotateAngle(CrestLeft, 0.5235987755982988F, 0.0F, 0.17453292519943295F);
		this.CrestRight = new ModelRenderer(this, 44, 4);
		this.CrestRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.CrestRight.addBox(-4.0F, -9.1F, -6.0F, 2, 3, 4, 0.0F);
		this.setRotateAngle(CrestRight, 0.5235987755982988F, 0.0F, -0.17453292519943295F);
		this.LegFrontRight1 = new ModelRenderer(this, 14, 162);
		this.LegFrontRight1.setRotationPoint(-5.0F, 13.0F, -4.5F);
		this.LegFrontRight1.addBox(-2.5F, -5.0F, -5.0F, 5, 10, 10, 0.0F);
		this.setRotateAngle(LegFrontRight1, -0.7853981633974483F, 0.0F, 0.0F);
		this.LegFrontLeft2 = new ModelRenderer(this, 97, 182);
		this.LegFrontLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegFrontLeft2.addBox(-2.0F, 0.0F, -0.5F, 3, 11, 3, 0.0F);
		this.setRotateAngle(LegFrontLeft2, 0.7853981633974483F, 0.0F, 0.0F);
		this.BodyMain = new ModelRenderer(this, 34, 130);
		this.BodyMain.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.BodyMain.addBox(-4.5F, 5.0F, -12.0F, 9, 12, 23, 0.0F);
		this.EarLeft = new ModelRenderer(this, 96, 34);
		this.EarLeft.setRotationPoint(5.0F, 0.0F, -6.0F);
		this.EarLeft.addBox(-1.0F, -1.2F, -1.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(EarLeft, 0.17453292519943295F, 0.7853981633974483F, 0.0F);
		this.LegFrontRight2 = new ModelRenderer(this, 23, 182);
		this.LegFrontRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegFrontRight2.addBox(-1.0F, 0.0F, -0.5F, 3, 11, 3, 0.0F);
		this.setRotateAngle(LegFrontRight2, 0.7853981633974483F, 0.0F, 0.0F);
		this.EyeLeft = new ModelRenderer(this, 84, 35);
		this.EyeLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.EyeLeft.addBox(-5.5F, -1.7F, -8.4F, 4, 5, 2, 0.0F);
		this.setRotateAngle(EyeLeft, 0.0F, -0.7853981633974483F, 0.0F);
		this.HeadTop = new ModelRenderer(this, 51, 11);
		this.HeadTop.setRotationPoint(0.0F, -1.8F, -7.5F);
		this.HeadTop.addBox(-4.0F, 0.0F, -0.5F, 8, 5, 7, 0.0F);
		this.setRotateAngle(HeadTop, 0.6829473363053812F, 0.0F, 0.0F);
		this.EyeRight = new ModelRenderer(this, 36, 35);
		this.EyeRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.EyeRight.addBox(1.5F, -1.7F, -8.4F, 4, 5, 2, 0.0F);
		this.setRotateAngle(EyeRight, 0.0F, 0.7853981633974483F, 0.0F);
		this.PlateFrontLeft = new ModelRenderer(this, 93, 76);
		this.PlateFrontLeft.setRotationPoint(0.0F, 7.5F, 0.0F);
		this.PlateFrontLeft.addBox(5.0F, -1.0F, -8.5F, 4, 13, 9, 0.0F);
		this.setRotateAngle(PlateFrontLeft, 0.0F, 0.17453292519943295F, -0.3490658503988659F);
		this.LegBackRight2 = new ModelRenderer(this, 46, 198);
		this.LegBackRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegBackRight2.addBox(-0.5F, 1.0F, -0.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(LegBackRight2, 0.3490658503988659F, 0.0F, 0.0F);
		this.TorsoRidge = new ModelRenderer(this, 81, 121);
		this.TorsoRidge.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.TorsoRidge.addBox(0.0F, -6.7F, -2.5F, 0, 4, 18, 0.0F);
		this.setRotateAngle(TorsoRidge, -0.6108652381980153F, 0.0F, 0.0F);
		this.SnoutRidge = new ModelRenderer(this, 56, 42);
		this.SnoutRidge.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.SnoutRidge.addBox(-2.0F, -8.5F, -11.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(SnoutRidge, 0.8726646259971648F, 0.0F, 0.0F);
		this.LegBackRight1 = new ModelRenderer(this, 38, 182);
		this.LegBackRight1.setRotationPoint(-5.0F, 15.0F, 9.5F);
		this.LegBackRight1.addBox(-2.5F, -5.0F, -5.0F, 6, 8, 8, 0.0F);
		this.setRotateAngle(LegBackRight1, -0.3490658503988659F, 0.0F, 0.0F);
		this.LegBackLeft2 = new ModelRenderer(this, 74, 198);
		this.LegBackLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegBackLeft2.addBox(-2.5F, 1.0F, -0.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(LegBackLeft2, 0.3490658503988659F, 0.0F, 0.0F);
		this.SnoutRight = new ModelRenderer(this, 19, 42);
		this.SnoutRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.SnoutRight.addBox(-7.8F, 3.4F, -10.2F, 5, 6, 14, 0.0F);
		this.setRotateAngle(SnoutRight, -0.08726646259971647F, -0.4363323129985824F, 0.0F);
		this.Snout = new ModelRenderer(this, 57, 52);
		this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Snout.addBox(-2.5F, 1.5F, -16.5F, 5, 4, 4, 0.0F);
		this.setRotateAngle(Snout, 0.08726646259971647F, 0.0F, 0.0F);
		this.TuskLeft = new ModelRenderer(this, 75, 62);
		this.TuskLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.TuskLeft.addBox(2.0F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(TuskLeft, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
		this.TuskRight = new ModelRenderer(this, 53, 62);
		this.TuskRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.TuskRight.addBox(-3.0F, -5.0F, -3.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(TuskRight, 0.2617993877991494F, 0.0F, -0.17453292519943295F);
		this.LegBackLeft1 = new ModelRenderer(this, 66, 182);
		this.LegBackLeft1.setRotationPoint(5.0F, 15.0F, 9.5F);
		this.LegBackLeft1.addBox(-3.5F, -5.0F, -5.0F, 6, 8, 8, 0.0F);
		this.setRotateAngle(LegBackLeft1, -0.3490658503988659F, 0.0F, 0.0F);
		this.BodyBack = new ModelRenderer(this, 37, 98);
		this.BodyBack.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BodyBack.addBox(-5.0F, -3.0F, -2.5F, 10, 13, 19, 0.0F);
		this.setRotateAngle(BodyBack, -0.5235987755982988F, 0.0F, 0.0F);
		this.Jaw = new ModelRenderer(this, 57, 60);
		this.Jaw.setRotationPoint(0.0F, 8.0F, -11.0F);
		this.Jaw.addBox(-2.0F, -1.3F, -4.9F, 4, 2, 5, 0.0F);
		this.setRotateAngle(Jaw, -0.08726646259971647F, 0.0F, 0.0F);
		this.TailRight = new ModelRenderer(this, 66, 165);
		this.TailRight.setRotationPoint(0.0F, 5.7F, 15.8F);
		this.TailRight.addBox(-4.0F, 0.4F, -1.5F, 4, 16, 1, 0.0F);
		this.setRotateAngle(TailRight, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
		this.PlateBackRight = new ModelRenderer(this, 11, 110);
		this.PlateBackRight.setRotationPoint(0.0F, 6.0F, -4.5F);
		this.PlateBackRight.addBox(-9.5F, 2.5F, 9.0F, 4, 11, 9, 0.0F);
		this.setRotateAngle(PlateBackRight, 0.08726646259971647F, 0.17453292519943295F, 0.3490658503988659F);
		this.EarRight = new ModelRenderer(this, 24, 34);
		this.EarRight.setRotationPoint(-5.0F, 0.0F, -6.0F);
		this.EarRight.addBox(0.0F, -1.1F, -1.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(EarRight, 0.17453292519943295F, -0.7853981633974483F, 0.0F);
		this.LegFrontLeft1 = new ModelRenderer(this, 88, 162);
		this.LegFrontLeft1.setRotationPoint(5.0F, 13.0F, -4.5F);
		this.LegFrontLeft1.addBox(-2.5F, -5.0F, -5.0F, 5, 10, 10, 0.0F);
		this.setRotateAngle(LegFrontLeft1, -0.7853981633974483F, 0.0F, 0.0F);
		this.BodyMain.addChild(this.BodyFront);
		this.HeadMain.addChild(this.EyeRight);
		this.Jaw.addChild(this.TuskRight);
		this.HeadMain.addChild(this.SnoutRight);
		this.BodyMain.addChild(this.TailRight);
		this.LegFrontLeft1.addChild(this.LegFrontLeft2);
		this.BodyMain.addChild(this.PlateBackRight);
		this.HeadMain.addChild(this.EyeLeft);
		this.HeadMain.addChild(this.CrestLeft);
		this.HeadMain.addChild(this.CrestMiddle);
		this.HeadMain.addChild(this.EarRight);
		this.BodyMain.addChild(this.PlateFrontRight);
		this.HeadMain.addChild(this.Jaw);
		this.LegFrontRight1.addChild(this.LegFrontRight2);
		this.BodyMain.addChild(this.PlateFrontLeft);
		this.LegBackRight1.addChild(this.LegBackRight2);
		this.HeadMain.addChild(this.HeadTop);
		this.HeadMain.addChild(this.SnoutRidge);
		this.BodyMain.addChild(this.TorsoRidge);
		this.BodyMain.addChild(this.PlateBackLeft);
		this.BodyMain.addChild(this.LegFrontLeft1);
		this.HeadMain.addChild(this.Snout);
		this.HeadMain.addChild(this.EarLeft);
		this.BodyMain.addChild(this.TailLeft);
		this.HeadMain.addChild(this.CrestRight);
		this.LegBackLeft1.addChild(this.LegBackLeft2);
		this.HeadMain.addChild(this.SnoutLeft);
		this.BodyMain.addChild(this.HeadMain);
		this.BodyMain.addChild(this.BodyBack);
		this.BodyMain.addChild(this.LegFrontRight1);
		this.Jaw.addChild(this.TuskLeft);
		this.BodyMain.addChild(this.LegBackRight1);
		this.BodyMain.addChild(this.LegBackLeft1);
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

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);


		this.HeadMain.rotateAngleX = headPitch * 0.017453292F;
		this.HeadMain.rotateAngleY = netHeadYaw * 0.017453292F;

		this.LegFrontLeft1.rotateAngleX = -0.7853981633974483F + (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
		this.LegFrontRight1.rotateAngleX = -0.7853981633974483F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);

		this.LegBackLeft1.rotateAngleX = -0.31869712141416456F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
		this.LegBackRight1.rotateAngleX = -0.3490658503988659F + (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
	}
}
