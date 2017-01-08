package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelIcestoneCooler extends ModelBase
{
	ModelRenderer LidBase1;

	ModelRenderer LidBase2;

	ModelRenderer LidBase3;

	ModelRenderer LidBase4;

	ModelRenderer LidHingeRight;

	ModelRenderer LidHingeLeft;

	ModelRenderer LidHandleBarRight;

	ModelRenderer LidHandleBarLeft;

	ModelRenderer LidHandle;

	ModelRenderer LidTopCenter;

	ModelRenderer LidTop1;

	ModelRenderer LidTop2;

	ModelRenderer LidTop3;

	ModelRenderer LidTop4;

	ModelRenderer ChamberFront;

	ModelRenderer ChamberFrontLeft;

	ModelRenderer ChamberFrontRight;

	ModelRenderer ChamberBack;

	ModelRenderer ChamberBackLeft;

	ModelRenderer ChamberBackRight;

	ModelRenderer ChamberLeft;

	ModelRenderer ChamberRight;

	ModelRenderer ChamberBase;

	ModelRenderer ChamberAccentFront;

	ModelRenderer ChamberAccentBack;

	ModelRenderer ChamberAccentLeft;

	ModelRenderer ChamberAccentRight;

	public ModelIcestoneCooler()
	{
		textureWidth = 64;
		textureHeight = 32;

		LidBase1 = new ModelRenderer(this, 0, 0);
		LidBase1.addBox(-2F, -1F, -10F, 4, 1, 10);
		LidBase1.setRotationPoint(0F, 10F, 5F);
		LidBase1.setTextureSize(64, 32);
		LidBase1.mirror = true;
		setRotation(LidBase1, 0F, 0F, 0F);
		LidBase2 = new ModelRenderer(this, 0, 0);
		LidBase2.addBox(3F, -1F, -5F, 4, 1, 10);
		LidBase2.setRotationPoint(0F, 10F, 5F);
		LidBase2.setTextureSize(64, 32);
		LidBase2.mirror = true;
		setRotation(LidBase2, 0F, 1.570796F, 0F);
		LidBase3 = new ModelRenderer(this, 0, 0);
		LidBase3.addBox(1.5F, -0.9F, -8.5F, 4, 1, 10);
		LidBase3.setRotationPoint(0F, 10F, 5F);
		LidBase3.setTextureSize(64, 32);
		LidBase3.mirror = true;
		setRotation(LidBase3, 0F, 0.7853982F, 0F);
		LidBase4 = new ModelRenderer(this, 0, 0);
		LidBase4.addBox(-5.5F, -0.9F, -8.5F, 4, 1, 10);
		LidBase4.setRotationPoint(0F, 10F, 5F);
		LidBase4.setTextureSize(64, 32);
		LidBase4.mirror = true;
		setRotation(LidBase4, 0F, -0.7853982F, 0F);
		LidHingeRight = new ModelRenderer(this, 58, 0);
		LidHingeRight.addBox(-2F, -1F, -1F, 1, 1, 2);
		LidHingeRight.setRotationPoint(0F, 10F, 5F);
		LidHingeRight.setTextureSize(64, 32);
		LidHingeRight.mirror = true;
		setRotation(LidHingeRight, -0.6981317F, 0F, 0F);
		LidHingeLeft = new ModelRenderer(this, 58, 0);
		LidHingeLeft.addBox(1F, -1F, -1F, 1, 1, 2);
		LidHingeLeft.setRotationPoint(0F, 10F, 5F);
		LidHingeLeft.setTextureSize(64, 32);
		LidHingeLeft.mirror = true;
		setRotation(LidHingeLeft, -0.6981317F, 0F, 0F);
		LidHandleBarRight = new ModelRenderer(this, 50, 3);
		LidHandleBarRight.addBox(-3F, -1.5F, -12F, 1, 1, 6);
		LidHandleBarRight.setRotationPoint(0F, 10F, 5F);
		LidHandleBarRight.setTextureSize(64, 32);
		LidHandleBarRight.mirror = true;
		setRotation(LidHandleBarRight, 0F, 0F, 0F);
		LidHandleBarLeft = new ModelRenderer(this, 50, 3);
		LidHandleBarLeft.addBox(2F, -1.5F, -12F, 1, 1, 6);
		LidHandleBarLeft.setRotationPoint(0F, 10F, 5F);
		LidHandleBarLeft.setTextureSize(64, 32);
		LidHandleBarLeft.mirror = true;
		setRotation(LidHandleBarLeft, 0F, 0F, 0F);
		LidHandle = new ModelRenderer(this, 54, 10);
		LidHandle.addBox(-2F, -9.3F, -8F, 4, 1, 1);
		LidHandle.setRotationPoint(0F, 10F, 5F);
		LidHandle.setTextureSize(64, 32);
		LidHandle.mirror = true;
		setRotation(LidHandle, 0.7853982F, 0F, 0F);
		LidTopCenter = new ModelRenderer(this, 26, 0);
		LidTopCenter.addBox(0.5F, -2F, -6.5F, 6, 1, 6);
		LidTopCenter.setRotationPoint(0F, 10F, 5F);
		LidTopCenter.setTextureSize(64, 32);
		LidTopCenter.mirror = true;
		setRotation(LidTopCenter, 0F, 0.7853982F, 0F);
		LidTop1 = new ModelRenderer(this, 31, 7);
		LidTop1.addBox(1F, -5.6F, -5.7F, 5, 1, 2);
		LidTop1.setRotationPoint(0F, 10F, 5F);
		LidTop1.setTextureSize(64, 32);
		LidTop1.mirror = true;
		setRotation(LidTop1, 0.6981317F, 0.7853982F, 0F);
		LidTop2 = new ModelRenderer(this, 31, 7);
		LidTop2.addBox(-6F, -5.6F, -5.7F, 5, 1, 2);
		LidTop2.setRotationPoint(0F, 10F, 5F);
		LidTop2.setTextureSize(64, 32);
		LidTop2.mirror = true;
		setRotation(LidTop2, 0.6981317F, -0.7853982F, 0F);
		LidTop3 = new ModelRenderer(this, 31, 7);
		LidTop3.addBox(1F, -1.1F, -0.4F, 5, 1, 2);
		LidTop3.setRotationPoint(0F, 10F, 5F);
		LidTop3.setTextureSize(64, 32);
		LidTop3.mirror = true;
		setRotation(LidTop3, 0.6981317F, 2.356194F, 0F);
		LidTop4 = new ModelRenderer(this, 31, 7);
		LidTop4.addBox(-6F, -1.1F, -0.4F, 5, 1, 2);
		LidTop4.setRotationPoint(0F, 10F, 5F);
		LidTop4.setTextureSize(64, 32);
		LidTop4.mirror = true;
		setRotation(LidTop4, 0.6981317F, -2.356194F, 0F);
		ChamberFront = new ModelRenderer(this, 0, 15);
		ChamberFront.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		ChamberFront.setRotationPoint(0F, 0F, 0F);
		ChamberFront.setTextureSize(64, 32);
		ChamberFront.mirror = true;
		setRotation(ChamberFront, 0F, 0F, 0F);
		ChamberFrontLeft = new ModelRenderer(this, 0, 15);
		ChamberFrontLeft.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		ChamberFrontLeft.setRotationPoint(0F, 0F, 0F);
		ChamberFrontLeft.setTextureSize(64, 32);
		ChamberFrontLeft.mirror = true;
		setRotation(ChamberFrontLeft, 0F, -0.7853982F, 0F);
		ChamberFrontRight = new ModelRenderer(this, 0, 15);
		ChamberFrontRight.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		ChamberFrontRight.setRotationPoint(0F, 0F, 0F);
		ChamberFrontRight.setTextureSize(64, 32);
		ChamberFrontRight.mirror = true;
		setRotation(ChamberFrontRight, 0F, 0.7853982F, 0F);
		ChamberBack = new ModelRenderer(this, 0, 15);
		ChamberBack.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		ChamberBack.setRotationPoint(0F, 0F, 0F);
		ChamberBack.setTextureSize(64, 32);
		ChamberBack.mirror = true;
		setRotation(ChamberBack, 0F, 3.141593F, 0F);
		ChamberBackLeft = new ModelRenderer(this, 0, 15);
		ChamberBackLeft.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		ChamberBackLeft.setRotationPoint(0F, 0F, 0F);
		ChamberBackLeft.setTextureSize(64, 32);
		ChamberBackLeft.mirror = true;
		setRotation(ChamberBackLeft, 0F, -2.356194F, 0F);
		ChamberBackRight = new ModelRenderer(this, 0, 15);
		ChamberBackRight.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		ChamberBackRight.setRotationPoint(0F, 0F, 0F);
		ChamberBackRight.setTextureSize(64, 32);
		ChamberBackRight.mirror = true;
		setRotation(ChamberBackRight, 0F, 2.356194F, 0F);
		ChamberLeft = new ModelRenderer(this, 0, 15);
		ChamberLeft.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		ChamberLeft.setRotationPoint(0F, 0F, 0F);
		ChamberLeft.setTextureSize(64, 32);
		ChamberLeft.mirror = true;
		setRotation(ChamberLeft, 0F, -1.570796F, 0F);
		ChamberRight = new ModelRenderer(this, 0, 15);
		ChamberRight.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		ChamberRight.setRotationPoint(0F, 0F, 0F);
		ChamberRight.setTextureSize(64, 32);
		ChamberRight.mirror = true;
		setRotation(ChamberRight, 0F, 1.570796F, 0F);
		ChamberBase = new ModelRenderer(this, 18, 16);
		ChamberBase.addBox(-4F, 15.9F, -4F, 8, 8, 8);
		ChamberBase.setRotationPoint(0F, 0F, 0F);
		ChamberBase.setTextureSize(64, 32);
		ChamberBase.mirror = true;
		setRotation(ChamberBase, 0F, 0F, 0F);
		ChamberAccentFront = new ModelRenderer(this, 50, 25);
		ChamberAccentFront.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		ChamberAccentFront.setRotationPoint(0F, 0F, 0F);
		ChamberAccentFront.setTextureSize(64, 32);
		ChamberAccentFront.mirror = true;
		setRotation(ChamberAccentFront, -0.122173F, 0F, 0F);
		ChamberAccentBack = new ModelRenderer(this, 50, 25);
		ChamberAccentBack.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		ChamberAccentBack.setRotationPoint(0F, 0F, 0F);
		ChamberAccentBack.setTextureSize(64, 32);
		ChamberAccentBack.mirror = true;
		setRotation(ChamberAccentBack, -0.122173F, 3.141593F, 0F);
		ChamberAccentLeft = new ModelRenderer(this, 50, 25);
		ChamberAccentLeft.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		ChamberAccentLeft.setRotationPoint(0F, 0F, 0F);
		ChamberAccentLeft.setTextureSize(64, 32);
		ChamberAccentLeft.mirror = true;
		setRotation(ChamberAccentLeft, -0.122173F, -1.570796F, 0F);
		ChamberAccentRight = new ModelRenderer(this, 50, 25);
		ChamberAccentRight.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		ChamberAccentRight.setRotationPoint(0F, 0F, 0F);
		ChamberAccentRight.setTextureSize(64, 32);
		ChamberAccentRight.mirror = true;
		setRotation(ChamberAccentRight, -0.122173F, 1.570796F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.render(f5);
	}

	public void render(float f5)
	{
		LidBase1.render(f5);
		LidBase2.render(f5);
		LidBase3.render(f5);
		LidBase4.render(f5);
		LidHingeRight.render(f5);
		LidHingeLeft.render(f5);
		LidHandleBarRight.render(f5);
		LidHandleBarLeft.render(f5);
		LidHandle.render(f5);
		LidTopCenter.render(f5);
		LidTop1.render(f5);
		LidTop2.render(f5);
		LidTop3.render(f5);
		LidTop4.render(f5);
		ChamberFront.render(f5);
		ChamberFrontLeft.render(f5);
		ChamberFrontRight.render(f5);
		ChamberBack.render(f5);
		ChamberBackLeft.render(f5);
		ChamberBackRight.render(f5);
		ChamberLeft.render(f5);
		ChamberRight.render(f5);
		ChamberBase.render(f5);
		ChamberAccentFront.render(f5);
		ChamberAccentBack.render(f5);
		ChamberAccentLeft.render(f5);
		ChamberAccentRight.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
