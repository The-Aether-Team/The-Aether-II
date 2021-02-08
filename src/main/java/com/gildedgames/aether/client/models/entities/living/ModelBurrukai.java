package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Burrukai.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelBurrukai extends ModelBaseAether
{
	public ModelRenderer Tail;
	public ModelRenderer TorsoNeckJoint;
	public ModelRenderer TorsoFrontFurRight;
	public ModelRenderer TorsoChest;
	public ModelRenderer TorsoPlate1;
	public ModelRenderer TorsoPlate2;
	public ModelRenderer TorsoPlate3;
	public ModelRenderer TorsoShoulderPlateLeftMid;
	public ModelRenderer TorsoShoulderPlateLeftFront;
	public ModelRenderer TorsoShoulderPlateRightMid;
	public ModelRenderer TorsoShoulderPlateRightFront;
	public ModelRenderer TorsoBackFur;
	public ModelRenderer TorsoRear;
	public ModelRenderer FrontLegRightTop;
	public ModelRenderer FrontLegRightBottom;
	public ModelRenderer FrontLegRightHoofOut;
	public ModelRenderer FrontLegLeftTop;
	public ModelRenderer FrontLegLeftBottom;
	public ModelRenderer FrontLegLeftHoofIn;
	public ModelRenderer HindLegRightCalf;
	public ModelRenderer HindLegRightShin;
	public ModelRenderer HindLegRightKnee;
	public ModelRenderer HindLegRightHoofIn;
	public ModelRenderer HindLegLeftCalfMiddle;
	public ModelRenderer HindLegLeftKnee;
	public ModelRenderer HindLegLeftShin;
	public ModelRenderer HindLegLeftHoofOut;
	public ModelRenderer HeadMain;
	public ModelRenderer HeadAntlerTopLeft1;
	public ModelRenderer HeadAntlerTopLeft2;
	public ModelRenderer HeadAntlerBottomLeft1;
	public ModelRenderer HeadAntlerBottomRight1;
	public ModelRenderer HeadAntlerTopRight2;
	public ModelRenderer HeadPlateTop;
	public ModelRenderer HeadAntlersMiddle;
	public ModelRenderer HeadBrowLeft;
	public ModelRenderer HeadBrowRight;
	public ModelRenderer HeadEyeLeft;
	public ModelRenderer HeadEyeRight;
	public ModelRenderer HeadEarLeft;
	public ModelRenderer HeadEarRight;
	public ModelRenderer HeadRidge;
	public ModelRenderer HeadBase;
	public ModelRenderer HeadCheekRight;
	public ModelRenderer HeadCheekLeft;
	public ModelRenderer HeadChin;
	public ModelRenderer HeadAntlerTopRight1;
	public ModelRenderer HeadSnout;

	public final float childZOffset = 4.0F;

	public ModelBurrukai()
	{
		this.textureWidth = 128;
		this.textureHeight = 512;
		this.TorsoShoulderPlateLeftMid = new ModelRenderer(this, 91, 150);
		this.TorsoShoulderPlateLeftMid.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoShoulderPlateLeftMid.addBox(8.0F, -5.0F, 3.5F, 5, 15, 4, 0.0F);
		this.setRotateAngle(TorsoShoulderPlateLeftMid, 0.0F, 0.0F, 0.17453292519943295F);
		this.HeadEarLeft = new ModelRenderer(this, 90, 43);
		this.HeadEarLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEarLeft.addBox(4.0F, 0.0F, -2.0F, 1, 5, 3, 0.0F);
		this.setRotateAngle(HeadEarLeft, 0.0F, -0.0F, -0.5235987755982988F);
		this.FrontLegRightTop = new ModelRenderer(this, 12, 265);
		this.FrontLegRightTop.setRotationPoint(-4.0F, 3.0F, 6.0F);
		this.FrontLegRightTop.addBox(-5.0F, -2.0F, -3.0F, 5, 14, 7, 0.0F);
		this.HeadCheekLeft = new ModelRenderer(this, 80, 51);
		this.HeadCheekLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekLeft.addBox(0.9F, 0.8F, -9.0F, 4, 6, 12, 0.0F);
		this.setRotateAngle(HeadCheekLeft, 0.0F, 0.2617993877991494F, 0.0F);
		this.HeadBase = new ModelRenderer(this, 44, 48);
		this.HeadBase.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBase.addBox(-2.5F, 0.7F, -10.6F, 5, 6, 13, 0.0F);
		this.TorsoShoulderPlateLeftFront = new ModelRenderer(this, 85, 172);
		this.TorsoShoulderPlateLeftFront.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoShoulderPlateLeftFront.addBox(7.5F, -4.0F, 0.0F, 4, 9, 11, 0.0F);
		this.setRotateAngle(TorsoShoulderPlateLeftFront, 0.0F, 0.0F, 0.26563911215353697F);
		this.TorsoPlate2 = new ModelRenderer(this, 40, 169);
		this.TorsoPlate2.setRotationPoint(0.0F, -2.0F, 1.0F);
		this.TorsoPlate2.addBox(-5.0F, -2.0F, 1.0F, 10, 4, 12, 0.0F);
		this.setRotateAngle(TorsoPlate2, 0.5235987755982988F, 0.0F, 0.0F);
		this.FrontLegLeftTop = new ModelRenderer(this, 92, 265);
		this.FrontLegLeftTop.setRotationPoint(4.0F, 3.0F, 6.0F);
		this.FrontLegLeftTop.addBox(0.0F, -2.0F, -3.0F, 5, 14, 7, 0.0F);
		this.setRotateAngle(FrontLegLeftTop, 0.0F, -0.03490658503988659F, 0.0F);
		this.FrontLegLeftBottom = new ModelRenderer(this, 96, 286);
		this.FrontLegLeftBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegLeftBottom.addBox(0.5F, 8.5F, 4.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(FrontLegLeftBottom, -0.5235987755982988F, -0.0F, 0.0F);
		this.HeadAntlerBottomLeft1 = new ModelRenderer(this, 97, 22);
		this.HeadAntlerBottomLeft1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomLeft1.addBox(2.5F, 9.0F, -1.5F, 5, 2, 2, 0.0F);
		this.setRotateAngle(HeadAntlerBottomLeft1, 0.0F, 0.0F, -2.0943951023931953F);
		this.HeadCheekRight = new ModelRenderer(this, 12, 51);
		this.HeadCheekRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekRight.addBox(-5.0F, 0.8F, -9.0F, 4, 6, 12, 0.0F);
		this.setRotateAngle(HeadCheekRight, 0.0F, -0.2617993877991494F, 0.0F);
		this.HeadAntlerTopLeft2 = new ModelRenderer(this, 77, 14);
		this.HeadAntlerTopLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopLeft2.addBox(5.0F, -3.0F, -2.0F, 8, 3, 3, 0.0F);
		this.setRotateAngle(HeadAntlerTopLeft2, 0.0F, -0.0F, -0.5235987755982988F);
		this.HeadEyeLeft = new ModelRenderer(this, 80, 42);
		this.HeadEyeLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeLeft.addBox(4.5F, -3.0F, -5.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(HeadEyeLeft, 0.0F, 0.6108652381980153F, 0.0F);
		this.FrontLegRightBottom = new ModelRenderer(this, 16, 286);
		this.FrontLegRightBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegRightBottom.addBox(-4.5F, 8.5F, 4.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(FrontLegRightBottom, -0.5235987755982988F, -0.0F, 0.0F);
		this.TorsoChest = new ModelRenderer(this, 38, 133);
		this.TorsoChest.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoChest.addBox(-6.0F, 7.0F, -1.0F, 12, 11, 12, 0.0F);
		this.setRotateAngle(TorsoChest, 0.11152653920243764F, 0.0F, 0.0F);
		this.FrontLegRightHoofOut = new ModelRenderer(this, 16, 302);
		this.FrontLegRightHoofOut.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegRightHoofOut.addBox(-4.4F, 18.0F, -5.5F, 4, 3, 4, 0.0F);
		this.HeadAntlerBottomRight1 = new ModelRenderer(this, 11, 22);
		this.HeadAntlerBottomRight1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomRight1.addBox(-7.5F, 9.0F, -1.5F, 5, 2, 2, 0.0F);
		this.setRotateAngle(HeadAntlerBottomRight1, 0.0F, -0.0F, 2.0943951023931953F);
		this.HeadAntlerTopRight1 = new ModelRenderer(this, 1, 16);
		this.HeadAntlerTopRight1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopRight1.addBox(-12.5F, 10.5F, -1.5F, 8, 2, 2, 0.0F);
		this.setRotateAngle(HeadAntlerTopRight1, 0.0F, -0.0F, 1.7453292519943295F);
		this.HeadEyeRight = new ModelRenderer(this, 34, 42);
		this.HeadEyeRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeRight.addBox(-5.5F, -3.0F, -5.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(HeadEyeRight, 0.0F, -0.6108652381980153F, 0.0F);
		this.HindLegLeftCalfMiddle = new ModelRenderer(this, 92, 320);
		this.HindLegLeftCalfMiddle.setRotationPoint(5.0F, 4.0F, 19.0F);
		this.HindLegLeftCalfMiddle.addBox(-3.0F, -2.0F, -1.5F, 5, 13, 7, 0.0F);
		this.setRotateAngle(HindLegLeftCalfMiddle, -0.2617993877991494F, 0.0F, 0.0F);
		this.HeadAntlerTopRight2 = new ModelRenderer(this, 23, 14);
		this.HeadAntlerTopRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopRight2.addBox(-13.0F, -3.0F, -2.0F, 8, 3, 3, 0.0F);
		this.setRotateAngle(HeadAntlerTopRight2, 0.0F, -0.0F, 0.5235987755982988F);
		this.HeadPlateTop = new ModelRenderer(this, 44, 0);
		this.HeadPlateTop.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadPlateTop.addBox(-4.0F, -8.8F, -3.5F, 8, 2, 10, 0.0F);
		this.setRotateAngle(HeadPlateTop, 0.8726646259971648F, 0.0F, 0.0F);
		this.HindLegRightHoofIn = new ModelRenderer(this, 19, 369);
		this.HindLegRightHoofIn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegRightHoofIn.addBox(-0.9F, 18.0F, 0.5F, 3, 2, 3, 0.0F);
		this.setRotateAngle(HindLegRightHoofIn, 0.2617993877991494F, 0.0F, 0.0F);
		this.HeadAntlerTopLeft1 = new ModelRenderer(this, 101, 16);
		this.HeadAntlerTopLeft1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopLeft1.addBox(4.5F, 10.5F, -1.5F, 8, 2, 2, 0.0F);
		this.setRotateAngle(HeadAntlerTopLeft1, 0.0F, -0.0F, -1.7453292519943295F);
		this.HindLegRightShin = new ModelRenderer(this, 18, 355);
		this.HindLegRightShin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegRightShin.addBox(-1.0F, 10.0F, 3.5F, 3, 8, 4, 0.0F);
		this.setRotateAngle(HindLegRightShin, 0.08726646259971647F, -0.0F, 0.0F);
		this.FrontLegLeftHoofIn = new ModelRenderer(this, 96, 302);
		this.FrontLegLeftHoofIn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegLeftHoofIn.addBox(0.4F, 18.0F, -5.5F, 4, 3, 4, 0.0F);
		this.TorsoNeckJoint = new ModelRenderer(this, 47, 93);
		this.TorsoNeckJoint.setRotationPoint(0.0F, -2.0F, 1.0F);
		this.TorsoNeckJoint.addBox(-3.5F, 0.5F, -2.0F, 7, 9, 8, 0.0F);
		this.setRotateAngle(TorsoNeckJoint, -0.5235987755982988F, 0.0F, 0.0F);
		this.HindLegRightKnee = new ModelRenderer(this, 12, 341);
		this.HindLegRightKnee.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegRightKnee.addBox(-1.5F, 6.0F, 1.0F, 4, 5, 9, 0.0F);
		this.setRotateAngle(HindLegRightKnee, -0.17453292519943295F, -0.0F, 0.0F);
		this.HindLegLeftKnee = new ModelRenderer(this, 91, 340);
		this.HindLegLeftKnee.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegLeftKnee.addBox(-2.5F, 6.0F, 1.0F, 4, 5, 9, 0.0F);
		this.setRotateAngle(HindLegLeftKnee, -0.17453292519943295F, -0.0F, 0.0F);
		this.Tail = new ModelRenderer(this, 56, 251);
		this.Tail.setRotationPoint(0.0F, -1.0F, 23.7F);
		this.Tail.addBox(-2.0F, 0.0F, -2.0F, 3, 10, 3, 0.0F);
		this.setRotateAngle(Tail, 0.5150466622635267F, -0.7518878417591571F, -0.3621558197888234F);
		this.TorsoShoulderPlateRightMid = new ModelRenderer(this, 15, 150);
		this.TorsoShoulderPlateRightMid.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoShoulderPlateRightMid.addBox(-13.0F, -5.0F, 3.5F, 5, 15, 4, 0.0F);
		this.setRotateAngle(TorsoShoulderPlateRightMid, 0.0F, 0.0F, -0.17453292519943295F);
		this.TorsoPlate1 = new ModelRenderer(this, 44, 157);
		this.TorsoPlate1.setRotationPoint(0.0F, -3.0F, 2.0F);
		this.TorsoPlate1.addBox(-4.5F, -3.0F, -3.0F, 9, 3, 9, 0.0F);
		this.setRotateAngle(TorsoPlate1, 1.2217304763960306F, 0.0F, 0.0F);
		this.TorsoFrontFurRight = new ModelRenderer(this, 31, 110);
		this.TorsoFrontFurRight.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoFrontFurRight.addBox(-9.0F, -0.8F, -1.0F, 18, 8, 13, 0.0F);
		this.TorsoBackFur = new ModelRenderer(this, 33, 198);
		this.TorsoBackFur.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoBackFur.addBox(-5.0F, -2.0F, 11.0F, 10, 3, 13, 0.0F);
		this.setRotateAngle(TorsoBackFur, -0.17453292519943295F, 0.0F, 0.0F);
		this.HindLegLeftHoofOut = new ModelRenderer(this, 98, 368);
		this.HindLegLeftHoofOut.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegLeftHoofOut.addBox(-2.1F, 18.0F, 0.5F, 3, 2, 3, 0.0F);
		this.setRotateAngle(HindLegLeftHoofOut, 0.2617993877991494F, 0.0F, 0.0F);
		this.HindLegLeftShin = new ModelRenderer(this, 97, 354);
		this.HindLegLeftShin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HindLegLeftShin.addBox(-2.0F, 10.0F, 3.5F, 3, 8, 4, 0.0F);
		this.setRotateAngle(HindLegLeftShin, 0.08726646259971647F, -0.0F, 0.0F);
		this.HeadMain = new ModelRenderer(this, 48, 22);
		this.HeadMain.setRotationPoint(0.0F, 5.0F, -2.5F);
		this.HeadMain.addBox(-4.0F, -2.0F, -4.0F, 8, 3, 6, 0.0F);
		this.HeadAntlersMiddle = new ModelRenderer(this, 47, 12);
		this.HeadAntlersMiddle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlersMiddle.addBox(-5.0F, -7.0F, -3.0F, 10, 5, 5, 0.0F);
		this.HeadBrowLeft = new ModelRenderer(this, 77, 33);
		this.HeadBrowLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBrowLeft.addBox(2.0F, -5.5F, -3.6F, 4, 3, 6, 0.0F);
		this.setRotateAngle(HeadBrowLeft, 0.3490658503988659F, 0.6981317007977318F, 0.17453292519943295F);
		this.TorsoPlate3 = new ModelRenderer(this, 44, 185);
		this.TorsoPlate3.setRotationPoint(0.0F, -2.0F, 1.0F);
		this.TorsoPlate3.addBox(-4.0F, -1.5F, 9.0F, 8, 3, 10, 0.0F);
		this.setRotateAngle(TorsoPlate3, 0.17453292519943295F, 0.0F, 0.0F);
		this.HeadBrowRight = new ModelRenderer(this, 27, 33);
		this.HeadBrowRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBrowRight.addBox(-6.0F, -5.5F, -3.6F, 4, 3, 6, 0.0F);
		this.setRotateAngle(HeadBrowRight, 0.3490658503988659F, -0.6981317007977318F, -0.17453292519943295F);
		this.HeadChin = new ModelRenderer(this, 51, 73);
		this.HeadChin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadChin.addBox(-2.0F, -2.9F, -12.0F, 4, 6, 7, 0.0F);
		this.setRotateAngle(HeadChin, 0.7853981633974483F, -0.0F, 0.0F);
		this.HeadEarRight = new ModelRenderer(this, 26, 43);
		this.HeadEarRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEarRight.addBox(-5.0F, 0.0F, -2.0F, 1, 5, 3, 0.0F);
		this.setRotateAngle(HeadEarRight, 0.0F, -0.0F, 0.5235987755982988F);
		this.HeadSnout = new ModelRenderer(this, 56, 68);
		this.HeadSnout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadSnout.addBox(-2.0F, -2.5F, -11.5F, 4, 3, 2, 0.0F);
		this.setRotateAngle(HeadSnout, 0.3490658503988659F, -0.0F, 0.0F);
		this.HeadRidge = new ModelRenderer(this, 47, 31);
		this.HeadRidge.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadRidge.addBox(-2.0F, -7.0F, -8.0F, 4, 6, 11, 0.0F);
		this.setRotateAngle(HeadRidge, 0.7853981633974483F, 0.0F, 0.0F);
		this.TorsoShoulderPlateRightFront = new ModelRenderer(this, 9, 172);
		this.TorsoShoulderPlateRightFront.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoShoulderPlateRightFront.addBox(-11.5F, -4.0F, 0.0F, 4, 9, 11, 0.0F);
		this.setRotateAngle(TorsoShoulderPlateRightFront, 0.0F, 0.0F, -0.26563911215353697F);
		this.HindLegRightCalf = new ModelRenderer(this, 13, 321);
		this.HindLegRightCalf.setRotationPoint(-5.0F, 4.0F, 19.0F);
		this.HindLegRightCalf.addBox(-2.0F, -2.0F, -1.5F, 5, 13, 7, 0.0F);
		this.setRotateAngle(HindLegRightCalf, -0.2617993877991494F, -0.0F, 0.0F);
		this.TorsoRear = new ModelRenderer(this, 39, 221);
		this.TorsoRear.setRotationPoint(0.0F, -4.0F, 1.0F);
		this.TorsoRear.addBox(-3.5F, 3.0F, 11.0F, 7, 14, 11, 0.0F);
		this.HeadMain.addChild(this.HeadEarLeft);
		this.HindLegRightCalf.addChild(this.HindLegRightKnee);
		this.HindLegRightCalf.addChild(this.HindLegRightHoofIn);
		this.HindLegLeftCalfMiddle.addChild(this.HindLegLeftKnee);
		this.HeadMain.addChild(this.HeadEyeLeft);
		this.HeadMain.addChild(this.HeadBrowLeft);
		this.HeadMain.addChild(this.HeadChin);
		this.HindLegRightCalf.addChild(this.HindLegRightShin);
		this.FrontLegRightTop.addChild(this.FrontLegRightBottom);
		this.HindLegLeftCalfMiddle.addChild(this.HindLegLeftHoofOut);
		this.HeadMain.addChild(this.HeadCheekRight);
		this.HeadMain.addChild(this.HeadPlateTop);
		this.HeadMain.addChild(this.HeadAntlersMiddle);
		this.HeadMain.addChild(this.HeadCheekLeft);
		this.FrontLegRightTop.addChild(this.FrontLegRightHoofOut);
		this.FrontLegLeftTop.addChild(this.FrontLegLeftHoofIn);
		this.HeadMain.addChild(this.HeadAntlerTopRight2);
		this.HeadMain.addChild(this.HeadAntlerBottomLeft1);
		this.HeadMain.addChild(this.HeadBase);
		this.HeadMain.addChild(this.HeadBrowRight);
		this.HeadMain.addChild(this.HeadAntlerBottomRight1);
		this.HeadMain.addChild(this.HeadAntlerTopLeft2);
		this.HeadMain.addChild(this.HeadAntlerTopRight1);
		this.HindLegLeftCalfMiddle.addChild(this.HindLegLeftShin);
		this.HeadMain.addChild(this.HeadEyeRight);
		this.HeadMain.addChild(this.HeadSnout);
		this.HeadMain.addChild(this.HeadEarRight);
		this.HeadMain.addChild(this.HeadAntlerTopLeft1);
		this.HeadMain.addChild(this.HeadRidge);
		this.FrontLegLeftTop.addChild(this.FrontLegLeftBottom);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.TorsoPlate2.render(f5);
		this.HeadMain.render(f5);
		this.FrontLegLeftTop.render(f5);
		this.TorsoFrontFurRight.render(f5);
		this.TorsoShoulderPlateRightMid.render(f5);
		this.TorsoPlate3.render(f5);
		this.TorsoShoulderPlateLeftFront.render(f5);
		this.FrontLegRightTop.render(f5);
		this.TorsoChest.render(f5);
		this.HindLegLeftCalfMiddle.render(f5);
		this.HindLegRightCalf.render(f5);
		this.TorsoBackFur.render(f5);
		this.TorsoRear.render(f5);
		this.Tail.render(f5);
		this.TorsoPlate1.render(f5);
		this.TorsoNeckJoint.render(f5);
		this.TorsoShoulderPlateRightFront.render(f5);
		this.TorsoShoulderPlateLeftMid.render(f5);
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
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.Tail.rotateAngleZ = -0.5096392150483358F + (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

		float pitch = headPitch * 0.017453292F;
		float yaw = netHeadYaw * 0.017453292F;

		this.HeadMain.rotateAngleX = pitch;
		this.HeadMain.rotateAngleY = yaw;

		float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.75F * limbSwingAmount);
		float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.75F * limbSwingAmount);

		this.HeadMain.rotateAngleZ = leftSwingX * .1f;
		this.HeadMain.offsetY = leftSwingX * .015f;

		this.FrontLegLeftTop.offsetY = leftSwingX / 4f + .01f;
		this.FrontLegRightTop.offsetY = rightSwingX / 4f + .01f;

		this.FrontLegLeftTop.rotateAngleX = rightSwingX * .8f;
		this.FrontLegRightTop.rotateAngleX = leftSwingX * .8f;

		//hind leg animation code is lifting them up off the ground somehow

		this.HindLegLeftCalfMiddle.rotateAngleX = -0.2617993877991494F + leftSwingX * .55f;
		this.HindLegRightCalf.rotateAngleX = -0.2617993877991494F + rightSwingX * .55f;

		this.HindLegLeftCalfMiddle.offsetY = rightSwingX / 7f;
		this.HindLegRightCalf.offsetY = leftSwingX / 7f;

		this.TorsoShoulderPlateRightMid.offsetY = rightSwingX * .12f + .01f;
		this.TorsoShoulderPlateRightFront.offsetY = rightSwingX * .12f + .01f;

		this.TorsoShoulderPlateLeftMid.offsetY = leftSwingX * .12f + .01f;
		this.TorsoShoulderPlateLeftFront.offsetY = leftSwingX * .12f + .01f;

		this.Tail.rotateAngleX = 0.515060975741379F + rightSwingX;
	}
}
