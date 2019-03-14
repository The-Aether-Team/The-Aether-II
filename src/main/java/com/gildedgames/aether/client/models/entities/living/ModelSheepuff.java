package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.passive.EntityKirrid;
import com.gildedgames.aether.common.entities.living.passive.EntitySheepuff;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelSheepuff extends ModelBase
{
	public ModelRenderer mainBody;
	public ModelRenderer legFrontLeftThigh;
	public ModelRenderer legFrontRightThigh;
	public ModelRenderer legBackLeftThigh;
	public ModelRenderer legBackRightThigh;
	public ModelRenderer bodyBack;
	public ModelRenderer neckFluff;
	public ModelRenderer legFrontLeftFoot;
	public ModelRenderer legFrontRightFoot;
	public ModelRenderer legBackLeftFoot;
	public ModelRenderer legBackRightFoot;
	public ModelRenderer tail;
	public ModelRenderer headMain;
	public ModelRenderer earLeftTop;
	public ModelRenderer earRightTop;
	public ModelRenderer headJaw;
	public ModelRenderer headSnout;
	public ModelRenderer hornLeft;
	public ModelRenderer hornRight;
	public ModelRenderer headPlatingBack;
	public ModelRenderer headPlatingMid;
	public ModelRenderer headPlatingTop;
	public ModelRenderer eyeRight;
	public ModelRenderer eyeLeft;
	public ModelRenderer headCheekRight;
	public ModelRenderer headCheekLeft;
	public ModelRenderer clothBaseRight;
	public ModelRenderer clothBaseLeft;
	public ModelRenderer earLeftBottom;
	public ModelRenderer earRightBottom;
	public ModelRenderer hornLeftCurl;
	public ModelRenderer hornLeftCurlBase;
	public ModelRenderer hornLeftPointTip;
	public ModelRenderer hornRightCurl;
	public ModelRenderer hornRightCurlBase;
	public ModelRenderer hornRightPoint;
	public ModelRenderer clothRightConnect1;
	public ModelRenderer clothRightConnect2;
	public ModelRenderer clothRightConnect3;
	public ModelRenderer clothLeftConnect1;
	public ModelRenderer clothLeftConnect2;
	public ModelRenderer clothLeftConnect3;

	public ModelSheepuff()
	{
		this.textureWidth = 150;
		this.textureHeight = 230;
		this.clothBaseRight = new ModelRenderer(this, 59, 28);
		this.clothBaseRight.setRotationPoint(-3.5F, -6.7F, -2.3F);
		this.clothBaseRight.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
		this.clothBaseLeft = new ModelRenderer(this, 85, 28);
		this.clothBaseLeft.setRotationPoint(2.5F, -6.7F, -2.3F);
		this.clothBaseLeft.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
		this.legFrontRightFoot = new ModelRenderer(this, 106, 66);
		this.legFrontRightFoot.setRotationPoint(0.0F, 2.9F, 0.0F);
		this.legFrontRightFoot.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		this.hornRight = new ModelRenderer(this, 47, 38);
		this.hornRight.setRotationPoint(-5.8F, -6.8F, -2.2F);
		this.hornRight.addBox(-2.0F, 0.6F, -2.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(hornRight, 0.0F, 0.0F, -0.27314402793711257F);
		this.headCheekRight = new ModelRenderer(this, 59, 34);
		this.headCheekRight.setRotationPoint(-0.3F, -0.8F, -5.0F);
		this.headCheekRight.addBox(-2.0F, 0.0F, -2.0F, 1, 3, 4, 0.0F);
		this.setRotateAngle(headCheekRight, 0.0F, -0.36425021489121656F, 0.0F);
		this.legBackLeftFoot = new ModelRenderer(this, 47, 99);
		this.legBackLeftFoot.setRotationPoint(0.0F, 2.9F, 0.0F);
		this.legBackLeftFoot.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		this.legFrontLeftThigh = new ModelRenderer(this, 34, 74);
		this.legFrontLeftThigh.setRotationPoint(4.0F, 9.0F, -4.0F);
		this.legFrontLeftThigh.addBox(-1.5F, -3.0F, -2.5F, 3, 6, 5, 0.0F);
		this.headJaw = new ModelRenderer(this, 54, 21);
		this.headJaw.setRotationPoint(0.0F, 1.8F, -3.0F);
		this.headJaw.addBox(-1.5F, -1.0F, -5.0F, 3, 2, 5, 0.0F);
		this.headPlatingTop = new ModelRenderer(this, 70, 21);
		this.headPlatingTop.setRotationPoint(0.0F, -5.0F, -3.1F);
		this.headPlatingTop.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 3, 0.0F);
		this.setRotateAngle(headPlatingTop, -0.8196066167365371F, 0.0F, 0.0F);
		this.neckFluff = new ModelRenderer(this, 64, 48);
		this.neckFluff.setRotationPoint(0.0F, 1.9F, -3.6F);
		this.neckFluff.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 6, 0.0F);
		this.headPlatingMid = new ModelRenderer(this, 67, 27);
		this.headPlatingMid.setRotationPoint(0.0F, -3.5F, -4.7F);
		this.headPlatingMid.addBox(-2.5F, 0.0F, 0.0F, 5, 1, 4, 0.0F);
		this.setRotateAngle(headPlatingMid, 0.8651597102135892F, 0.0F, 0.0F);
		this.headCheekLeft = new ModelRenderer(this, 83, 34);
		this.headCheekLeft.setRotationPoint(3.2F, -0.7F, -6.1F);
		this.headCheekLeft.addBox(-2.0F, 0.0F, -2.0F, 1, 3, 4, 0.0F);
		this.setRotateAngle(headCheekLeft, 0.0F, 0.37472219040318255F, 0.0F);
		this.eyeRight = new ModelRenderer(this, 56, 34);
		this.eyeRight.setRotationPoint(-1.6F, -1.1F, -4.4F);
		this.eyeRight.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(eyeRight, 0.0F, -0.5918411493512771F, 0.0F);
		this.earLeftBottom = new ModelRenderer(this, 90, 53);
		this.earLeftBottom.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.earLeftBottom.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.earRightBottom = new ModelRenderer(this, 56, 53);
		this.earRightBottom.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.earRightBottom.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.clothRightConnect1 = new ModelRenderer(this, 30, 123);
		this.clothRightConnect1.setRotationPoint(-0.2F, 3.1F, 2.9F);
		this.clothRightConnect1.addBox(0.0F, -3.0F, 0.0F, 0, 11, 9, 0.0F);
		this.setRotateAngle(clothRightConnect1, 0.0F, -0.9105382707654417F, 0.0F);
		this.bodyBack = new ModelRenderer(this, 59, 85);
		this.bodyBack.setRotationPoint(0.0F, 0.1F, 4.4F);
		this.bodyBack.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 8, 0.0F);
		this.setRotateAngle(bodyBack, -0.18203784098300857F, 0.0F, 0.0F);
		this.mainBody = new ModelRenderer(this, 50, 60);
		this.mainBody.setRotationPoint(0.0F, 6.1F, 0.0F);
		this.mainBody.addBox(-5.0F, 0.0F, -7.5F, 10, 9, 16, 0.0F);
		this.earLeftTop = new ModelRenderer(this, 90, 46);
		this.earLeftTop.setRotationPoint(3.8F, -3.4F, -1.5F);
		this.earLeftTop.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.eyeLeft = new ModelRenderer(this, 90, 34);
		this.eyeLeft.setRotationPoint(1.6F, -1.1F, -4.5F);
		this.eyeLeft.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(eyeLeft, 0.0F, 0.5918411493512771F, 0.0F);
		this.hornLeftPointTip = new ModelRenderer(this, 102, 38);
		this.hornLeftPointTip.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.hornLeftPointTip.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.legFrontRightThigh = new ModelRenderer(this, 102, 74);
		this.legFrontRightThigh.setRotationPoint(-4.0F, 9.0F, -4.0F);
		this.legFrontRightThigh.addBox(-1.5F, -3.0F, -2.5F, 3, 6, 5, 0.0F);
		this.hornLeft = new ModelRenderer(this, 89, 38);
		this.hornLeft.setRotationPoint(5.8F, -6.8F, -2.2F);
		this.hornLeft.addBox(-2.0F, 0.6F, -2.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(hornLeft, 0.0F, 0.0F, 0.27314402793711257F);
		this.clothRightConnect2 = new ModelRenderer(this, 48, 124);
		this.clothRightConnect2.setRotationPoint(0.0F, 3.0F, 9.0F);
		this.clothRightConnect2.addBox(0.0F, -3.0F, 0.0F, 0, 8, 11, 0.0F);
		this.setRotateAngle(clothRightConnect2, 0.0F, 0.8651597102135892F, 0.0F);
		this.hornRightCurl = new ModelRenderer(this, 35, 40);
		this.hornRightCurl.setRotationPoint(-2.5F, 3.0F, -3.0F);
		this.hornRightCurl.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.headSnout = new ModelRenderer(this, 82, 20);
		this.headSnout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.headSnout.addBox(-1.5F, -4.9F, -7.5F, 3, 2, 6, 0.0F);
		this.setRotateAngle(headSnout, 0.5918411493512771F, 0.0F, 0.0F);
		this.hornLeftCurl = new ModelRenderer(this, 105, 40);
		this.hornLeftCurl.setRotationPoint(-0.4F, 3.0F, -3.0F);
		this.hornLeftCurl.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.hornRightCurlBase = new ModelRenderer(this, 37, 44);
		this.hornRightCurlBase.setRotationPoint(-2.51F, 2.51F, 0.0F);
		this.hornRightCurlBase.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
		this.headPlatingBack = new ModelRenderer(this, 69, 32);
		this.headPlatingBack.setRotationPoint(0.0F, -5.7F, 0.5F);
		this.headPlatingBack.addBox(-2.5F, 0.0F, -2.5F, 5, 3, 2, 0.0F);
		this.hornRightPoint = new ModelRenderer(this, 46, 38);
		this.hornRightPoint.setRotationPoint(2.0F, -1.0F, 0.0F);
		this.hornRightPoint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.clothLeftConnect3 = new ModelRenderer(this, 70, 118);
		this.clothLeftConnect3.setRotationPoint(0.0F, 0.0F, 11.0F);
		this.clothLeftConnect3.addBox(0.0F, -3.0F, 0.0F, 0, 8, 4, 0.0F);
		this.legBackRightFoot = new ModelRenderer(this, 97, 99);
		this.legBackRightFoot.setRotationPoint(0.0F, 2.9F, 0.0F);
		this.legBackRightFoot.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		this.headMain = new ModelRenderer(this, 62, 40);
		this.headMain.setRotationPoint(0.0F, -1.7F, -6.1F);
		this.headMain.addBox(-3.5F, -3.5F, -4.0F, 7, 7, 7, 0.0F);
		this.legFrontLeftFoot = new ModelRenderer(this, 38, 66);
		this.legFrontLeftFoot.setRotationPoint(0.0F, 2.9F, 0.0F);
		this.legFrontLeftFoot.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		this.legBackRightThigh = new ModelRenderer(this, 93, 88);
		this.legBackRightThigh.setRotationPoint(-3.7F, 9.0F, 7.0F);
		this.legBackRightThigh.addBox(-1.5F, -3.0F, -2.5F, 3, 6, 5, 0.0F);
		this.tail = new ModelRenderer(this, 73, 99);
		this.tail.setRotationPoint(0.0F, 0.7F, 7.2F);
		this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(tail, 0.5918411493512771F, 0.0F, 0.0F);
		this.clothRightConnect3 = new ModelRenderer(this, 70, 131);
		this.clothRightConnect3.setRotationPoint(0.0F, 0.0F, 11.0F);
		this.clothRightConnect3.addBox(0.0F, -3.0F, 0.0F, 0, 8, 4, 0.0F);
		this.legBackLeftThigh = new ModelRenderer(this, 43, 88);
		this.legBackLeftThigh.setRotationPoint(3.7F, 9.0F, 7.0F);
		this.legBackLeftThigh.addBox(-1.5F, -3.0F, -2.5F, 3, 6, 5, 0.0F);
		this.earRightTop = new ModelRenderer(this, 56, 46);
		this.earRightTop.setRotationPoint(-3.8F, -3.4F, -1.5F);
		this.earRightTop.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.hornLeftCurlBase = new ModelRenderer(this, 107, 44);
		this.hornLeftCurlBase.setRotationPoint(1.61F, 2.51F, 0.0F);
		this.hornLeftCurlBase.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
		this.clothLeftConnect2 = new ModelRenderer(this, 48, 111);
		this.clothLeftConnect2.setRotationPoint(0.0F, 3.0F, 9.0F);
		this.clothLeftConnect2.addBox(0.0F, -3.0F, 0.0F, 0, 8, 11, 0.0F);
		this.setRotateAngle(clothLeftConnect2, 0.0F, -0.8651597102135892F, 0.0F);
		this.clothLeftConnect1 = new ModelRenderer(this, 30, 110);
		this.clothLeftConnect1.setRotationPoint(1.1F, 3.1F, 2.9F);
		this.clothLeftConnect1.addBox(0.0F, -3.0F, 0.0F, 0, 11, 9, 0.0F);
		this.setRotateAngle(clothLeftConnect1, 0.0F, 0.9105382707654417F, 0.0F);
		this.headMain.addChild(this.clothBaseRight);
		this.headMain.addChild(this.clothBaseLeft);
		this.legFrontRightThigh.addChild(this.legFrontRightFoot);
		this.headMain.addChild(this.hornRight);
		this.headMain.addChild(this.headCheekRight);
		this.legBackLeftThigh.addChild(this.legBackLeftFoot);
		this.mainBody.addChild(this.legFrontLeftThigh);
		this.headMain.addChild(this.headJaw);
		this.headMain.addChild(this.headPlatingTop);
		this.mainBody.addChild(this.neckFluff);
		this.headMain.addChild(this.headPlatingMid);
		this.headMain.addChild(this.headCheekLeft);
		this.headMain.addChild(this.eyeRight);
		this.earLeftTop.addChild(this.earLeftBottom);
		this.earRightTop.addChild(this.earRightBottom);
		this.clothBaseRight.addChild(this.clothRightConnect1);
		this.mainBody.addChild(this.bodyBack);
		this.headMain.addChild(this.earLeftTop);
		this.headMain.addChild(this.eyeLeft);
		this.hornLeftCurl.addChild(this.hornLeftPointTip);
		this.mainBody.addChild(this.legFrontRightThigh);
		this.headMain.addChild(this.hornLeft);
		this.clothRightConnect1.addChild(this.clothRightConnect2);
		this.hornRight.addChild(this.hornRightCurl);
		this.headMain.addChild(this.headSnout);
		this.hornLeft.addChild(this.hornLeftCurl);
		this.hornRight.addChild(this.hornRightCurlBase);
		this.headMain.addChild(this.headPlatingBack);
		this.hornRightCurl.addChild(this.hornRightPoint);
		this.clothLeftConnect2.addChild(this.clothLeftConnect3);
		this.legBackRightThigh.addChild(this.legBackRightFoot);
		this.neckFluff.addChild(this.headMain);
		this.legFrontLeftThigh.addChild(this.legFrontLeftFoot);
		this.mainBody.addChild(this.legBackRightThigh);
		this.bodyBack.addChild(this.tail);
		this.clothRightConnect2.addChild(this.clothRightConnect3);
		this.mainBody.addChild(this.legBackLeftThigh);
		this.headMain.addChild(this.earRightTop);
		this.hornLeft.addChild(this.hornLeftCurlBase);
		this.clothLeftConnect1.addChild(this.clothLeftConnect2);
		this.clothBaseLeft.addChild(this.clothLeftConnect1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.mainBody.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
			Entity entityIn)
	{
		EntitySheepuff sheepuff = (EntitySheepuff) entityIn;

		float headRotY = netHeadYaw * 0.017453292F;
		float headRotX = headPitch * 0.017453292F;

		this.headMain.rotateAngleY = headRotY;
		this.clothLeftConnect1.rotateAngleX = -headRotX;
		this.clothRightConnect1.rotateAngleX = -headRotX;
		this.clothLeftConnect1.rotateAngleY = 0.91f -headRotY;
		this.clothRightConnect1.rotateAngleY = -0.91f -headRotY;

		float puffiness = sheepuff.getPuffiness();

		float swing =  MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		float swing180 =  MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;

		this.earLeftTop.rotateAngleX = swing180 / 3f;
		this.earLeftBottom.rotateAngleX = swing / 3f;
		this.earRightTop.rotateAngleX = swing / 3f;
		this.earRightBottom.rotateAngleX = swing180 / 3f;

		if (!entityIn.onGround && puffiness > .5f)
		{
			float swayLeft = MathHelper.cos(ageInTicks) / 5f;
			float swayRight = MathHelper.sin(ageInTicks) / 5f;

			float p2 = puffiness * 2f - 1f;

			this.legBackLeftThigh.rotateAngleX = (0.54F + swayLeft) * p2;
			this.legFrontLeftThigh.rotateAngleX = (-0.50F  + swayLeft) * p2;
			this.legBackRightThigh.rotateAngleX = (0.54F + swayRight) * p2;
			this.legFrontRightThigh.rotateAngleX =(-0.5F + swayRight) * p2;
			this.legBackLeftThigh.rotateAngleZ = (-1F + swayLeft) * p2;
			this.legFrontLeftThigh.rotateAngleZ = (-1F + swayRight) * p2;
			this.legBackRightThigh.rotateAngleZ = (1.13F + swayRight) * p2;
			this.legFrontRightThigh.rotateAngleZ = (1.13F + swayLeft) * p2;
		}
		else
		{
			this.legBackLeftThigh.rotateAngleX = swing * 1.4F;
			this.legFrontLeftThigh.rotateAngleX = swing180 * 1.4F;
			this.legBackRightThigh.rotateAngleX = swing180 * 1.4F;
			this.legFrontRightThigh.rotateAngleX = swing * 1.4F;
			this.legBackLeftThigh.rotateAngleZ = 0;
			this.legFrontLeftThigh.rotateAngleZ = 0;
			this.legBackRightThigh.rotateAngleZ = 0;
			this.legFrontRightThigh.rotateAngleZ = 0;
		}

		if (!sheepuff.getSheared())
		{
			this.tail.isHidden = false;
			this.tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);
		}
		else
		{
			this.tail.isHidden = true;
		}

	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime)
	{
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);

		float perc = ((EntitySheepuff) entitylivingbaseIn).getHeadRotationPointY(partialTickTime);
		float rotationPointY = 1.9F + perc * 4.1F;
		float headAng = ((EntitySheepuff) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);

		this.headMain.rotateAngleX = headAng / 2f;
		this.neckFluff.rotateAngleX = headAng / 2f;
		this.neckFluff.rotationPointY = rotationPointY;

		float legRotPoint = 2.9F + perc * -1.9F;
		float bod = 6.1F + perc * 1.9F;

		this.legBackLeftFoot.rotationPointY = legRotPoint;
		this.legBackRightFoot.rotationPointY = legRotPoint;
		this.legFrontRightFoot.rotationPointY = legRotPoint;
		this.legFrontLeftFoot.rotationPointY = legRotPoint;
		this.mainBody.rotationPointY = bod;
	}
}
