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
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.LidBase1 = new ModelRenderer(this, 0, 0);
		this.LidBase1.addBox(-2F, -1F, -10F, 4, 1, 10);
		this.LidBase1.setRotationPoint(0F, 10F, 5F);
		this.LidBase1.setTextureSize(64, 32);
		this.LidBase1.mirror = true;
		this.setRotation(this.LidBase1, 0F, 0F, 0F);
		this.LidBase2 = new ModelRenderer(this, 0, 0);
		this.LidBase2.addBox(3F, -1F, -5F, 4, 1, 10);
		this.LidBase2.setRotationPoint(0F, 10F, 5F);
		this.LidBase2.setTextureSize(64, 32);
		this.LidBase2.mirror = true;
		this.setRotation(this.LidBase2, 0F, 1.570796F, 0F);
		this.LidBase3 = new ModelRenderer(this, 0, 0);
		this.LidBase3.addBox(1.5F, -0.9F, -8.5F, 4, 1, 10);
		this.LidBase3.setRotationPoint(0F, 10F, 5F);
		this.LidBase3.setTextureSize(64, 32);
		this.LidBase3.mirror = true;
		this.setRotation(this.LidBase3, 0F, 0.7853982F, 0F);
		this.LidBase4 = new ModelRenderer(this, 0, 0);
		this.LidBase4.addBox(-5.5F, -0.9F, -8.5F, 4, 1, 10);
		this.LidBase4.setRotationPoint(0F, 10F, 5F);
		this.LidBase4.setTextureSize(64, 32);
		this.LidBase4.mirror = true;
		this.setRotation(this.LidBase4, 0F, -0.7853982F, 0F);
		this.LidHingeRight = new ModelRenderer(this, 58, 0);
		this.LidHingeRight.addBox(-2F, -1F, -1F, 1, 1, 2);
		this.LidHingeRight.setRotationPoint(0F, 10F, 5F);
		this.LidHingeRight.setTextureSize(64, 32);
		this.LidHingeRight.mirror = true;
		this.setRotation(this.LidHingeRight, -0.6981317F, 0F, 0F);
		this.LidHingeLeft = new ModelRenderer(this, 58, 0);
		this.LidHingeLeft.addBox(1F, -1F, -1F, 1, 1, 2);
		this.LidHingeLeft.setRotationPoint(0F, 10F, 5F);
		this.LidHingeLeft.setTextureSize(64, 32);
		this.LidHingeLeft.mirror = true;
		this.setRotation(this.LidHingeLeft, -0.6981317F, 0F, 0F);
		this.LidHandleBarRight = new ModelRenderer(this, 50, 3);
		this.LidHandleBarRight.addBox(-3F, -1.5F, -12F, 1, 1, 6);
		this.LidHandleBarRight.setRotationPoint(0F, 10F, 5F);
		this.LidHandleBarRight.setTextureSize(64, 32);
		this.LidHandleBarRight.mirror = true;
		this.setRotation(this.LidHandleBarRight, 0F, 0F, 0F);
		this.LidHandleBarLeft = new ModelRenderer(this, 50, 3);
		this.LidHandleBarLeft.addBox(2F, -1.5F, -12F, 1, 1, 6);
		this.LidHandleBarLeft.setRotationPoint(0F, 10F, 5F);
		this.LidHandleBarLeft.setTextureSize(64, 32);
		this.LidHandleBarLeft.mirror = true;
		this.setRotation(this.LidHandleBarLeft, 0F, 0F, 0F);
		this.LidHandle = new ModelRenderer(this, 54, 10);
		this.LidHandle.addBox(-2F, -9.3F, -8F, 4, 1, 1);
		this.LidHandle.setRotationPoint(0F, 10F, 5F);
		this.LidHandle.setTextureSize(64, 32);
		this.LidHandle.mirror = true;
		this.setRotation(this.LidHandle, 0.7853982F, 0F, 0F);
		this.LidTopCenter = new ModelRenderer(this, 26, 0);
		this.LidTopCenter.addBox(0.5F, -2F, -6.5F, 6, 1, 6);
		this.LidTopCenter.setRotationPoint(0F, 10F, 5F);
		this.LidTopCenter.setTextureSize(64, 32);
		this.LidTopCenter.mirror = true;
		this.setRotation(this.LidTopCenter, 0F, 0.7853982F, 0F);
		this.LidTop1 = new ModelRenderer(this, 31, 7);
		this.LidTop1.addBox(1F, -5.6F, -5.7F, 5, 1, 2);
		this.LidTop1.setRotationPoint(0F, 10F, 5F);
		this.LidTop1.setTextureSize(64, 32);
		this.LidTop1.mirror = true;
		this.setRotation(this.LidTop1, 0.6981317F, 0.7853982F, 0F);
		this.LidTop2 = new ModelRenderer(this, 31, 7);
		this.LidTop2.addBox(-6F, -5.6F, -5.7F, 5, 1, 2);
		this.LidTop2.setRotationPoint(0F, 10F, 5F);
		this.LidTop2.setTextureSize(64, 32);
		this.LidTop2.mirror = true;
		this.setRotation(this.LidTop2, 0.6981317F, -0.7853982F, 0F);
		this.LidTop3 = new ModelRenderer(this, 31, 7);
		this.LidTop3.addBox(1F, -1.1F, -0.4F, 5, 1, 2);
		this.LidTop3.setRotationPoint(0F, 10F, 5F);
		this.LidTop3.setTextureSize(64, 32);
		this.LidTop3.mirror = true;
		this.setRotation(this.LidTop3, 0.6981317F, 2.356194F, 0F);
		this.LidTop4 = new ModelRenderer(this, 31, 7);
		this.LidTop4.addBox(-6F, -1.1F, -0.4F, 5, 1, 2);
		this.LidTop4.setRotationPoint(0F, 10F, 5F);
		this.LidTop4.setTextureSize(64, 32);
		this.LidTop4.mirror = true;
		this.setRotation(this.LidTop4, 0.6981317F, -2.356194F, 0F);
		this.ChamberFront = new ModelRenderer(this, 0, 15);
		this.ChamberFront.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		this.ChamberFront.setRotationPoint(0F, 0F, 0F);
		this.ChamberFront.setTextureSize(64, 32);
		this.ChamberFront.mirror = true;
		this.setRotation(this.ChamberFront, 0F, 0F, 0F);
		this.ChamberFrontLeft = new ModelRenderer(this, 0, 15);
		this.ChamberFrontLeft.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		this.ChamberFrontLeft.setRotationPoint(0F, 0F, 0F);
		this.ChamberFrontLeft.setTextureSize(64, 32);
		this.ChamberFrontLeft.mirror = true;
		this.setRotation(this.ChamberFrontLeft, 0F, -0.7853982F, 0F);
		this.ChamberFrontRight = new ModelRenderer(this, 0, 15);
		this.ChamberFrontRight.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		this.ChamberFrontRight.setRotationPoint(0F, 0F, 0F);
		this.ChamberFrontRight.setTextureSize(64, 32);
		this.ChamberFrontRight.mirror = true;
		this.setRotation(this.ChamberFrontRight, 0F, 0.7853982F, 0F);
		this.ChamberBack = new ModelRenderer(this, 0, 15);
		this.ChamberBack.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		this.ChamberBack.setRotationPoint(0F, 0F, 0F);
		this.ChamberBack.setTextureSize(64, 32);
		this.ChamberBack.mirror = true;
		this.setRotation(this.ChamberBack, 0F, 3.141593F, 0F);
		this.ChamberBackLeft = new ModelRenderer(this, 0, 15);
		this.ChamberBackLeft.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		this.ChamberBackLeft.setRotationPoint(0F, 0F, 0F);
		this.ChamberBackLeft.setTextureSize(64, 32);
		this.ChamberBackLeft.mirror = true;
		this.setRotation(this.ChamberBackLeft, 0F, -2.356194F, 0F);
		this.ChamberBackRight = new ModelRenderer(this, 0, 15);
		this.ChamberBackRight.addBox(-3F, 10.1F, -7F, 6, 14, 3);
		this.ChamberBackRight.setRotationPoint(0F, 0F, 0F);
		this.ChamberBackRight.setTextureSize(64, 32);
		this.ChamberBackRight.mirror = true;
		this.setRotation(this.ChamberBackRight, 0F, 2.356194F, 0F);
		this.ChamberLeft = new ModelRenderer(this, 0, 15);
		this.ChamberLeft.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		this.ChamberLeft.setRotationPoint(0F, 0F, 0F);
		this.ChamberLeft.setTextureSize(64, 32);
		this.ChamberLeft.mirror = true;
		this.setRotation(this.ChamberLeft, 0F, -1.570796F, 0F);
		this.ChamberRight = new ModelRenderer(this, 0, 15);
		this.ChamberRight.addBox(-3F, 10F, -7.1F, 6, 14, 3);
		this.ChamberRight.setRotationPoint(0F, 0F, 0F);
		this.ChamberRight.setTextureSize(64, 32);
		this.ChamberRight.mirror = true;
		this.setRotation(this.ChamberRight, 0F, 1.570796F, 0F);
		this.ChamberBase = new ModelRenderer(this, 18, 16);
		this.ChamberBase.addBox(-4F, 15.9F, -4F, 8, 8, 8);
		this.ChamberBase.setRotationPoint(0F, 0F, 0F);
		this.ChamberBase.setTextureSize(64, 32);
		this.ChamberBase.mirror = true;
		this.setRotation(this.ChamberBase, 0F, 0F, 0F);
		this.ChamberAccentFront = new ModelRenderer(this, 50, 25);
		this.ChamberAccentFront.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		this.ChamberAccentFront.setRotationPoint(0F, 0F, 0F);
		this.ChamberAccentFront.setTextureSize(64, 32);
		this.ChamberAccentFront.mirror = true;
		this.setRotation(this.ChamberAccentFront, -0.122173F, 0F, 0F);
		this.ChamberAccentBack = new ModelRenderer(this, 50, 25);
		this.ChamberAccentBack.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		this.ChamberAccentBack.setRotationPoint(0F, 0F, 0F);
		this.ChamberAccentBack.setTextureSize(64, 32);
		this.ChamberAccentBack.mirror = true;
		this.setRotation(this.ChamberAccentBack, -0.122173F, 3.141593F, 0F);
		this.ChamberAccentLeft = new ModelRenderer(this, 50, 25);
		this.ChamberAccentLeft.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		this.ChamberAccentLeft.setRotationPoint(0F, 0F, 0F);
		this.ChamberAccentLeft.setTextureSize(64, 32);
		this.ChamberAccentLeft.mirror = true;
		this.setRotation(this.ChamberAccentLeft, -0.122173F, -1.570796F, 0F);
		this.ChamberAccentRight = new ModelRenderer(this, 50, 25);
		this.ChamberAccentRight.addBox(-2F, 18.7F, -5F, 4, 6, 1);
		this.ChamberAccentRight.setRotationPoint(0F, 0F, 0F);
		this.ChamberAccentRight.setTextureSize(64, 32);
		this.ChamberAccentRight.mirror = true;
		this.setRotation(this.ChamberAccentRight, -0.122173F, 1.570796F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.render(f5);
	}

	public void render(float f5)
	{
		this.LidBase1.render(f5);
		this.LidBase2.render(f5);
		this.LidBase3.render(f5);
		this.LidBase4.render(f5);
		this.LidHingeRight.render(f5);
		this.LidHingeLeft.render(f5);
		this.LidHandleBarRight.render(f5);
		this.LidHandleBarLeft.render(f5);
		this.LidHandle.render(f5);
		this.LidTopCenter.render(f5);
		this.LidTop1.render(f5);
		this.LidTop2.render(f5);
		this.LidTop3.render(f5);
		this.LidTop4.render(f5);
		this.ChamberFront.render(f5);
		this.ChamberFrontLeft.render(f5);
		this.ChamberFrontRight.render(f5);
		this.ChamberBack.render(f5);
		this.ChamberBackLeft.render(f5);
		this.ChamberBackRight.render(f5);
		this.ChamberLeft.render(f5);
		this.ChamberRight.render(f5);
		this.ChamberBase.render(f5);
		this.ChamberAccentFront.render(f5);
		this.ChamberAccentBack.render(f5);
		this.ChamberAccentLeft.render(f5);
		this.ChamberAccentRight.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
