package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityBurrukai;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Burrukai.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelBurrukai extends EntityModel<EntityBurrukai>
{
	public final RendererModel Tail;

	public final RendererModel HeadMain;

	public final RendererModel HeadAntlerTopLeft1;

	public final RendererModel HeadAntlerTopLeft2;

	public final RendererModel HeadAntlerBottomLeft1;

	public final RendererModel HeadAntlerBottomRight1;

	public final RendererModel HeadAntlerTopRight2;

	public final RendererModel HeadAntlerBottomLeft2;

	public final RendererModel HeadAntlerBottomRight2;

	public final RendererModel HeadPlateTop;

	public final RendererModel HeadPlateLow;

	public final RendererModel HeadAntlersMiddle;

	public final RendererModel HeadBrowLeft;

	public final RendererModel HeadBrowRight;

	public final RendererModel HeadEyeLeft;

	public final RendererModel HeadEyeRight;

	public final RendererModel HeadEarLeft;

	public final RendererModel HeadEarRight;

	public final RendererModel HeadRidge;

	public final RendererModel HeadBase;

	public final RendererModel HeadCheekRight;

	public final RendererModel HeadCheekLeft;

	public final RendererModel HeadChin;

	public final RendererModel HeadAntlerTopRight1;

	public final RendererModel HeadSnout;

	public final RendererModel TorsoNeckJoint;

	public final RendererModel TorsoFrontFurLeft;

	public final RendererModel TorsoFrontFurRight;

	public final RendererModel TorsoShoulderLeft;

	public final RendererModel TorsoShoulderRight;

	public final RendererModel TorsoChest;

	public final RendererModel TorsoPlate1;

	public final RendererModel TorsoPlate2;

	public final RendererModel TorsoPlate3;

	public final RendererModel TorsoShoulderPlateLeftMid;

	public final RendererModel TorsoShoulderPlateLeftFront;

	public final RendererModel TorsoShoulderPlateLeftBack;

	public final RendererModel TorsoShoulderPlateRightMid;

	public final RendererModel TorsoShoulderPlateRightFront;

	public final RendererModel TorsoShoulderPlateRightBack;

	public final RendererModel TorsoBackFur;

	public final RendererModel TorsoRear;

	public final RendererModel TorsoCrotch;

	public final RendererModel FrontLegLeftTop;

	public final RendererModel FrontLegLeftBottom;

	public final RendererModel FrontLegLeftHoofIn;

	public final RendererModel FrontLegLeftHoofOut;

	public final RendererModel HindLegRightCalfMiddle;

	public final RendererModel HindLegRightKnee;

	public final RendererModel HindLegRightShin;

	public final RendererModel HindLegRightHoofIn;

	public final RendererModel HindLegRightHoofOut;

	public final RendererModel HindLegRightHoofMiddle;

	public final RendererModel HindLegLeftCalfMiddle;

	public final RendererModel HindLegLeftKnee;

	public final RendererModel HindLegLeftShin;

	public final RendererModel HindLegLeftHoofIn;

	public final RendererModel HindLegLeftHoofOut;

	public final RendererModel HindLegLeftHoofMiddle;

	public final RendererModel FrontLegRightTop;

	public final RendererModel FrontLegRightBottom;

	public final RendererModel FrontLegRightHoofIn;

	public final RendererModel FrontLegRightHoofOut;

	public ModelBurrukai()
	{
		this.textureWidth = 128;
		this.textureHeight = 512;
		this.HeadEyeLeft = new RendererModel(this, 80, 42);
		this.HeadEyeLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeLeft.addBox(4.5F, -3.0F, -5.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(this.HeadEyeLeft, 0.0F, 0.6108652381980153F, 0.0F);
		this.TorsoPlate2 = new RendererModel(this, 40, 151);
		this.TorsoPlate2.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoPlate2.addBox(-5.0F, -2.0F, 1.0F, 10, 4, 12, 0.0F);
		this.setRotateAngle(this.TorsoPlate2, 0.5235987901687622F, -0.0F, 0.0F);
		this.HeadMain = new RendererModel(this, 48, 27);
		this.HeadMain.setRotationPoint(0.0F, 3.0F, -5.0F);
		this.HeadMain.addBox(-4.0F, -2.0F, -4.0F, 8, 3, 6, 0.0F);
		this.FrontLegRightBottom = new RendererModel(this, 96, 286);
		this.FrontLegRightBottom.setRotationPoint(2.5F, 9.8F, -0.0F);
		this.FrontLegRightBottom.addBox(-2.0F, 0.0F, -1.2F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.FrontLegRightBottom, -0.5009094953223726F, 0.0F, 0.0F);
		this.FrontLegLeftTop = new RendererModel(this, 92, 265);
		this.FrontLegLeftTop.setRotationPoint(4.0F, 1.0F, 4.5F);
		this.FrontLegLeftTop.addBox(0.0F, -2.0F, -3.0F, 5, 14, 7, 0.0F);
		this.setRotateAngle(this.FrontLegLeftTop, 0.0F, -0.03490658476948738F, 0.0F);
		this.HeadAntlerTopLeft1 = new RendererModel(this, 101, 16);
		this.HeadAntlerTopLeft1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopLeft1.addBox(5.0F, 12.0F, -1.0F, 9, 2, 2, 0.0F);
		this.setRotateAngle(this.HeadAntlerTopLeft1, 0.0F, -0.0F, -1.7453292519943295F);
		this.HeadEarRight = new RendererModel(this, 26, 43);
		this.HeadEarRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEarRight.addBox(-5.0F, 0.0F, -2.0F, 1, 5, 3, 0.0F);
		this.setRotateAngle(this.HeadEarRight, 0.0F, -0.0F, 0.5235987755982988F);
		this.HindLegLeftHoofOut = new RendererModel(this, 107, 368);
		this.HindLegLeftHoofOut.setRotationPoint(-0.3F, 9.7F, -1.7F);
		this.HindLegLeftHoofOut.addBox(1.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
		this.setRotateAngle(this.HindLegLeftHoofOut, 0.17453292519943295F, -0.17453292519943295F, 0.0F);
		this.HindLegRightHoofMiddle = new RendererModel(this, 101, 371);
		this.HindLegRightHoofMiddle.setRotationPoint(0.0F, 9.5F, 0.0F);
		this.HindLegRightHoofMiddle.addBox(-1.1F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.HindLegRightHoofMiddle, 0.2617993877991494F, -0.0F, 0.0F);
		this.HindLegLeftKnee = new RendererModel(this, 91, 340);
		this.HindLegLeftKnee.setRotationPoint(-0.4F, 11.0F, -0.9F);
		this.HindLegLeftKnee.addBox(-2.0F, -5.0F, 0.0F, 4, 5, 9, 0.0F);
		this.setRotateAngle(this.HindLegLeftKnee, -0.17453292519943295F, -0.0F, 0.0F);
		this.HeadPlateLow = new RendererModel(this, 52, 10);
		this.HeadPlateLow.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadPlateLow.addBox(-0.5F, -8.5F, -4.3F, 5, 2, 5, 0.0F);
		this.setRotateAngle(this.HeadPlateLow, 0.9431759277777356F, 0.5654866776461628F, 0.6227334771115768F);
		this.HindLegRightHoofIn = new RendererModel(this, 11, 369);
		this.HindLegRightHoofIn.setRotationPoint(-1.7F, 9.7F, -1.3F);
		this.HindLegRightHoofIn.addBox(0.0F, 0.0F, -0.0F, 1, 2, 4, 0.0F);
		this.setRotateAngle(this.HindLegRightHoofIn, 0.17453292519943295F, 0.17453292519943295F, 0.0F);
		this.HeadEyeRight = new RendererModel(this, 34, 42);
		this.HeadEyeRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEyeRight.addBox(-5.5F, -3.0F, -5.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(this.HeadEyeRight, 0.0F, -0.6108652381980153F, 0.0F);
		this.HeadSnout = new RendererModel(this, 46, 73);
		this.HeadSnout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadSnout.addBox(-2.0F, -2.5F, -11.5F, 4, 3, 2, 0.0F);
		this.setRotateAngle(this.HeadSnout, 0.3490658503988659F, -0.0F, 0.0F);
		this.TorsoFrontFurRight = new RendererModel(this, 0, 91);
		this.TorsoFrontFurRight.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoFrontFurRight.addBox(-9.0F, -3.0F, -0.5F, 9, 9, 13, 0.0F);
		this.setRotateAngle(this.TorsoFrontFurRight, 0.0F, -0.0F, -0.2617993877991494F);
		this.HeadBase = new RendererModel(this, 43, 53);
		this.HeadBase.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBase.addBox(-2.5F, 0.7F, -10.6F, 5, 7, 13, 0.0F);
		this.TorsoShoulderPlateRightMid = new RendererModel(this, 15, 150);
		this.TorsoShoulderPlateRightMid.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateRightMid.addBox(-13.0F, -8.0F, 3.0F, 5, 18, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateRightMid, 0.0F, -0.0F, -0.17453292012214658F);
		this.HindLegLeftHoofIn = new RendererModel(this, 91, 368);
		this.HindLegLeftHoofIn.setRotationPoint(-1.7F, 9.7F, -1.3F);
		this.HindLegLeftHoofIn.addBox(0.0F, 0.0F, -0.0F, 1, 2, 4, 0.0F);
		this.setRotateAngle(this.HindLegLeftHoofIn, 0.17453292519943295F, 0.17453292519943295F, 0.0F);
		this.HeadEarLeft = new RendererModel(this, 90, 43);
		this.HeadEarLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadEarLeft.addBox(4.0F, 0.0F, -2.0F, 1, 5, 3, 0.0F);
		this.setRotateAngle(this.HeadEarLeft, 0.0F, -0.0F, -0.5235987755982988F);
		this.TorsoShoulderPlateRightBack = new RendererModel(this, 17, 134);
		this.TorsoShoulderPlateRightBack.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateRightBack.addBox(-13.5F, -9.0F, 3.5F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateRightBack, -0.30740453246205135F, 0.16852786693084162F, -0.2656520475633568F);
		this.FrontLegLeftBottom = new RendererModel(this, 96, 286);
		this.FrontLegLeftBottom.setRotationPoint(2.5F, 9.8F, -0.0F);
		this.FrontLegLeftBottom.addBox(-2.0F, 0.0F, -1.2F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.FrontLegLeftBottom, -0.5009094953223726F, 0.0F, 0.0F);
		this.HeadAntlerTopRight2 = new RendererModel(this, 23, 14);
		this.HeadAntlerTopRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopRight2.addBox(-14.0F, -3.0F, -1.5F, 9, 3, 3, 0.0F);
		this.setRotateAngle(this.HeadAntlerTopRight2, 0.0F, -0.0F, 0.5235987755982988F);
		this.HeadChin = new RendererModel(this, 50, 73);
		this.HeadChin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadChin.addBox(-2.0F, -2.0F, -13.0F, 4, 7, 8, 0.0F);
		this.setRotateAngle(this.HeadChin, 0.7853981633974483F, -0.0F, 0.0F);
		this.TorsoShoulderPlateLeftBack = new RendererModel(this, 91, 134);
		this.TorsoShoulderPlateLeftBack.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateLeftBack.addBox(9.5F, -9.0F, 3.5F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateLeftBack, -0.30740453246205135F, -0.16852786693084162F, 0.2656520475633568F);
		this.HeadAntlerBottomLeft2 = new RendererModel(this, 77, 20);
		this.HeadAntlerBottomLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomLeft2.addBox(5.0F, -1.5F, -2.5F, 7, 3, 3, 0.0F);
		this.setRotateAngle(this.HeadAntlerBottomLeft2, 0.0F, -0.0F, -0.7853981633974483F);
		this.TorsoPlate3 = new RendererModel(this, 44, 167);
		this.TorsoPlate3.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoPlate3.addBox(-4.0F, -1.5F, 9.0F, 8, 3, 10, 0.0F);
		this.setRotateAngle(this.TorsoPlate3, 0.17453292519943295F, -0.0F, 0.0F);
		this.TorsoShoulderLeft = new RendererModel(this, 91, 113);
		this.TorsoShoulderLeft.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderLeft.addBox(8.0F, -4.0F, 0.5F, 3, 12, 9, 0.0F);
		this.setRotateAngle(this.TorsoShoulderLeft, 0.0F, -0.0F, 0.5235987901687622F);
		this.TorsoShoulderPlateLeftFront = new RendererModel(this, 91, 172);
		this.TorsoShoulderPlateLeftFront.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateLeftFront.addBox(7.5F, -6.0F, 2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateLeftFront, 0.3073524812762014F, 0.16859880574265224F, 0.26563911215353697F);
		this.HindLegRightShin = new RendererModel(this, 18, 355);
		this.HindLegRightShin.setRotationPoint(0.0F, -2.5F, 6.1F);
		this.HindLegRightShin.addBox(-1.5F, 0.0F, -1.3F, 3, 10, 4, 0.0F);
		this.setRotateAngle(this.HindLegRightShin, 0.2617993877991494F, -0.0F, 0.0F);
		this.HindLegRightHoofOut = new RendererModel(this, 29, 369);
		this.HindLegRightHoofOut.setRotationPoint(-0.3F, 9.7F, -1.7F);
		this.HindLegRightHoofOut.addBox(1.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
		this.setRotateAngle(this.HindLegRightHoofOut, 0.17453292519943295F, -0.17453292519943295F, 0.0F);
		this.FrontLegRightTop = new RendererModel(this, 92, 265);
		this.FrontLegRightTop.setRotationPoint(-9.2F, 1.0F, 4.5F);
		this.FrontLegRightTop.addBox(0.0F, -2.0F, -3.0F, 5, 14, 7, 0.0F);
		this.setRotateAngle(this.FrontLegRightTop, 0.0F, -0.03490658503988659F, 0.0F);
		this.FrontLegRightHoofOut = new RendererModel(this, 104, 302);
		this.FrontLegRightHoofOut.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegRightHoofOut.addBox(-1.1F, 10.1F, -7.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(this.FrontLegRightHoofOut, 0.5235987755982988F, -0.17453292519943295F, -0.0916297857297023F);
		this.TorsoChest = new RendererModel(this, 38, 115);
		this.TorsoChest.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoChest.addBox(-6.0F, 7.0F, -1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(this.TorsoChest, 0.11153583228588103F, -0.0F, 0.0F);
		this.HindLegRightKnee = new RendererModel(this, 12, 341);
		this.HindLegRightKnee.setRotationPoint(-0.4F, 11.0F, -0.9F);
		this.HindLegRightKnee.addBox(-2.0F, -5.0F, 0.0F, 4, 5, 9, 0.0F);
		this.setRotateAngle(this.HindLegRightKnee, -0.17453292519943295F, -0.0F, 0.0F);
		this.HindLegRightCalfMiddle = new RendererModel(this, 13, 321);
		this.HindLegRightCalfMiddle.setRotationPoint(-4.1F, 2.0F, 23.0F);
		this.HindLegRightCalfMiddle.addBox(-3.0F, -2.0F, -1.5F, 5, 13, 7, 0.0F);
		this.setRotateAngle(this.HindLegRightCalfMiddle, -0.2617993877991494F, 0.0F, 0.0F);
		this.HeadAntlerBottomLeft1 = new RendererModel(this, 97, 22);
		this.HeadAntlerBottomLeft1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomLeft1.addBox(2.5F, 10.0F, -2.0F, 6, 2, 2, 0.0F);
		this.setRotateAngle(this.HeadAntlerBottomLeft1, 0.0F, -0.0F, -2.0943951023931953F);
		this.FrontLegLeftHoofOut = new RendererModel(this, 104, 302);
		this.FrontLegLeftHoofOut.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegLeftHoofOut.addBox(-1.1F, 10.1F, -7.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(this.FrontLegLeftHoofOut, 0.5235987755982988F, -0.17453292519943295F, -0.0916297857297023F);
		this.HindLegLeftCalfMiddle = new RendererModel(this, 92, 320);
		this.HindLegLeftCalfMiddle.setRotationPoint(5.0F, 2.0F, 23.0F);
		this.HindLegLeftCalfMiddle.addBox(-3.0F, -2.0F, -1.5F, 5, 13, 7, 0.0F);
		this.setRotateAngle(this.HindLegLeftCalfMiddle, -0.2617993877991494F, 0.0F, 0.0F);
		this.TorsoCrotch = new RendererModel(this, 54, 232);
		this.TorsoCrotch.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoCrotch.addBox(-2.0F, 1.0F, 25.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(this.TorsoCrotch, -0.17453292012214658F, -0.0F, 0.0F);
		this.HeadAntlerBottomRight2 = new RendererModel(this, 27, 20);
		this.HeadAntlerBottomRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomRight2.addBox(-12.0F, -1.5F, -2.5F, 7, 3, 3, 0.0F);
		this.setRotateAngle(this.HeadAntlerBottomRight2, 0.0F, -0.0F, 0.7853981633974483F);
		this.HeadBrowLeft = new RendererModel(this, 77, 33);
		this.HeadBrowLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBrowLeft.addBox(2.0F, -5.5F, -3.6F, 4, 3, 6, 0.0F);
		this.setRotateAngle(this.HeadBrowLeft, 0.3490658503988659F, 0.6981317007977318F, 0.17453292519943295F);
		this.HeadAntlerBottomRight1 = new RendererModel(this, 11, 22);
		this.HeadAntlerBottomRight1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerBottomRight1.addBox(-8.5F, 10.0F, -2.0F, 6, 2, 2, 0.0F);
		this.setRotateAngle(this.HeadAntlerBottomRight1, 0.0F, -0.0F, 2.0943951023931953F);
		this.HeadPlateTop = new RendererModel(this, 46, 0);
		this.HeadPlateTop.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadPlateTop.addBox(-4.0F, -8.8F, -1.5F, 8, 2, 8, 0.0F);
		this.setRotateAngle(this.HeadPlateTop, 0.8726646259971648F, -0.0F, 0.0F);
		this.HeadCheekRight = new RendererModel(this, 11, 51);
		this.HeadCheekRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekRight.addBox(-5.0F, 0.8F, -9.0F, 4, 7, 12, 0.0F);
		this.setRotateAngle(this.HeadCheekRight, 0.0F, -0.2617993877991494F, 0.0F);
		this.HeadBrowRight = new RendererModel(this, 27, 33);
		this.HeadBrowRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadBrowRight.addBox(-6.0F, -5.5F, -3.6F, 4, 3, 6, 0.0F);
		this.setRotateAngle(this.HeadBrowRight, 0.3490658503988659F, -0.6981317007977318F, -0.17453292519943295F);
		this.TorsoFrontFurLeft = new RendererModel(this, 78, 91);
		this.TorsoFrontFurLeft.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoFrontFurLeft.addBox(0.0F, -3.0F, -0.5F, 9, 9, 13, 0.0F);
		this.setRotateAngle(this.TorsoFrontFurLeft, 0.0F, -0.0F, 0.2617993877991494F);
		this.HeadRidge = new RendererModel(this, 47, 36);
		this.HeadRidge.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadRidge.addBox(-2.0F, -7.0F, -8.0F, 4, 6, 11, 0.0F);
		this.setRotateAngle(this.HeadRidge, 0.7853981633974483F, -0.0F, 0.0F);
		this.HeadAntlersMiddle = new RendererModel(this, 47, 17);
		this.HeadAntlersMiddle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlersMiddle.addBox(-5.0F, -7.0F, -3.0F, 10, 5, 5, 0.0F);
		this.HeadAntlerTopLeft2 = new RendererModel(this, 77, 14);
		this.HeadAntlerTopLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopLeft2.addBox(5.0F, -3.0F, -1.5F, 9, 3, 3, 0.0F);
		this.setRotateAngle(this.HeadAntlerTopLeft2, 0.0F, -0.0F, -0.5235987755982988F);
		this.HindLegLeftShin = new RendererModel(this, 97, 354);
		this.HindLegLeftShin.setRotationPoint(0.0F, -2.5F, 6.1F);
		this.HindLegLeftShin.addBox(-1.5F, 0.0F, -1.3F, 3, 10, 4, 0.0F);
		this.setRotateAngle(this.HindLegLeftShin, 0.2617993877991494F, -0.0F, 0.0F);
		this.FrontLegRightHoofIn = new RendererModel(this, 92, 302);
		this.FrontLegRightHoofIn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegRightHoofIn.addBox(-0.9F, 10.1F, -7.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(this.FrontLegRightHoofIn, 0.5235987755982988F, 0.17453292519943295F, 0.0916297857297023F);
		this.HindLegLeftHoofMiddle = new RendererModel(this, 101, 371);
		this.HindLegLeftHoofMiddle.setRotationPoint(0.0F, 9.5F, 0.0F);
		this.HindLegLeftHoofMiddle.addBox(-1.1F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.HindLegLeftHoofMiddle, 0.2617993877991494F, -0.0F, 0.0F);
		this.FrontLegLeftHoofIn = new RendererModel(this, 92, 302);
		this.FrontLegLeftHoofIn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontLegLeftHoofIn.addBox(-0.9F, 10.1F, -7.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(this.FrontLegLeftHoofIn, 0.5235987755982988F, 0.17453292519943295F, 0.0916297857297023F);
		this.TorsoShoulderRight = new RendererModel(this, 9, 113);
		this.TorsoShoulderRight.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderRight.addBox(-11.0F, -4.0F, 0.5F, 3, 12, 9, 0.0F);
		this.setRotateAngle(this.TorsoShoulderRight, 0.0F, -0.0F, -0.5235987901687622F);
		this.TorsoBackFur = new RendererModel(this, 33, 180);
		this.TorsoBackFur.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoBackFur.addBox(-5.0F, -3.0F, 11.0F, 10, 4, 19, 0.0F);
		this.setRotateAngle(this.TorsoBackFur, -0.17453292012214658F, -0.0F, 0.0F);
		this.TorsoRear = new RendererModel(this, 40, 203);
		this.TorsoRear.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoRear.addBox(-3.5F, 3.0F, 11.0F, 7, 14, 15, 0.0F);
		this.HeadCheekLeft = new RendererModel(this, 79, 51);
		this.HeadCheekLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadCheekLeft.addBox(0.9F, 0.8F, -9.0F, 4, 7, 12, 0.0F);
		this.setRotateAngle(this.HeadCheekLeft, 0.0F, 0.2617993877991494F, 0.0F);
		this.Tail = new RendererModel(this, 56, 246);
		this.Tail.setRotationPoint(0.0F, -3.0F, 29.0F);
		this.Tail.addBox(-2.0F, 0.0F, -2.0F, 3, 10, 3, 0.0F);
		this.setRotateAngle(this.Tail, 0.515060975741379F, -0.7518794329071241F, -0.3622181621097308F);
		this.TorsoPlate1 = new RendererModel(this, 44, 139);
		this.TorsoPlate1.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoPlate1.addBox(-4.5F, -3.0F, -3.0F, 9, 3, 9, 0.0F);
		this.setRotateAngle(this.TorsoPlate1, 1.2217304706573486F, -0.0F, 0.0F);
		this.HeadAntlerTopRight1 = new RendererModel(this, 1, 16);
		this.HeadAntlerTopRight1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.HeadAntlerTopRight1.addBox(-14.0F, 12.0F, -1.0F, 9, 2, 2, 0.0F);
		this.setRotateAngle(this.HeadAntlerTopRight1, 0.0F, -0.0F, 1.7453292519943295F);
		this.TorsoNeckJoint = new RendererModel(this, 44, 95);
		this.TorsoNeckJoint.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoNeckJoint.addBox(-3.5F, 2.0F, -2.0F, 7, 10, 10, 0.0F);
		this.setRotateAngle(this.TorsoNeckJoint, -0.5235987901687622F, -0.0F, 0.0F);
		this.TorsoShoulderPlateRightFront = new RendererModel(this, 17, 172);
		this.TorsoShoulderPlateRightFront.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateRightFront.addBox(-11.5F, -6.0F, 2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateRightFront, 0.30740453246205135F, -0.16852786693084162F, -0.2656520475633568F);
		this.TorsoShoulderPlateLeftMid = new RendererModel(this, 91, 150);
		this.TorsoShoulderPlateLeftMid.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.TorsoShoulderPlateLeftMid.addBox(8.0F, -8.0F, 3.0F, 5, 18, 4, 0.0F);
		this.setRotateAngle(this.TorsoShoulderPlateLeftMid, 0.0F, -0.0F, 0.17453292012214658F);
		this.HeadMain.addChild(this.HeadEyeLeft);
		this.FrontLegRightTop.addChild(this.FrontLegRightBottom);
		this.HeadMain.addChild(this.HeadAntlerTopLeft1);
		this.HeadMain.addChild(this.HeadEarRight);
		this.HindLegLeftShin.addChild(this.HindLegLeftHoofOut);
		this.HindLegRightShin.addChild(this.HindLegRightHoofMiddle);
		this.HindLegLeftCalfMiddle.addChild(this.HindLegLeftKnee);
		this.HeadMain.addChild(this.HeadPlateLow);
		this.HindLegRightShin.addChild(this.HindLegRightHoofIn);
		this.HeadMain.addChild(this.HeadEyeRight);
		this.HeadMain.addChild(this.HeadSnout);
		this.HeadMain.addChild(this.HeadBase);
		this.HindLegLeftShin.addChild(this.HindLegLeftHoofIn);
		this.HeadMain.addChild(this.HeadEarLeft);
		this.FrontLegLeftTop.addChild(this.FrontLegLeftBottom);
		this.HeadMain.addChild(this.HeadAntlerTopRight2);
		this.HeadMain.addChild(this.HeadChin);
		this.HeadMain.addChild(this.HeadAntlerBottomLeft2);
		this.HindLegRightKnee.addChild(this.HindLegRightShin);
		this.HindLegRightShin.addChild(this.HindLegRightHoofOut);
		this.FrontLegRightBottom.addChild(this.FrontLegRightHoofOut);
		this.HindLegRightCalfMiddle.addChild(this.HindLegRightKnee);
		this.HeadMain.addChild(this.HeadAntlerBottomLeft1);
		this.FrontLegLeftBottom.addChild(this.FrontLegLeftHoofOut);
		this.HeadMain.addChild(this.HeadAntlerBottomRight2);
		this.HeadMain.addChild(this.HeadBrowLeft);
		this.HeadMain.addChild(this.HeadAntlerBottomRight1);
		this.HeadMain.addChild(this.HeadPlateTop);
		this.HeadMain.addChild(this.HeadCheekRight);
		this.HeadMain.addChild(this.HeadBrowRight);
		this.HeadMain.addChild(this.HeadRidge);
		this.HeadMain.addChild(this.HeadAntlersMiddle);
		this.HeadMain.addChild(this.HeadAntlerTopLeft2);
		this.HindLegLeftKnee.addChild(this.HindLegLeftShin);
		this.FrontLegRightBottom.addChild(this.FrontLegRightHoofIn);
		this.HindLegLeftShin.addChild(this.HindLegLeftHoofMiddle);
		this.FrontLegLeftBottom.addChild(this.FrontLegLeftHoofIn);
		this.HeadMain.addChild(this.HeadCheekLeft);
		this.HeadMain.addChild(this.HeadAntlerTopRight1);
	}

	@Override
	public void render(EntityBurrukai entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.TorsoPlate2.render(f5);
		this.HeadMain.render(f5);
		this.FrontLegLeftTop.render(f5);
		this.TorsoFrontFurRight.render(f5);
		this.TorsoShoulderPlateRightMid.render(f5);
		this.TorsoShoulderPlateRightBack.render(f5);
		this.TorsoShoulderPlateLeftBack.render(f5);
		this.TorsoPlate3.render(f5);
		this.TorsoShoulderLeft.render(f5);
		this.TorsoShoulderPlateLeftFront.render(f5);
		this.FrontLegRightTop.render(f5);
		this.TorsoChest.render(f5);
		this.HindLegRightCalfMiddle.render(f5);
		this.HindLegLeftCalfMiddle.render(f5);
		this.TorsoCrotch.render(f5);
		this.TorsoFrontFurLeft.render(f5);
		this.TorsoShoulderRight.render(f5);
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
	public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(EntityBurrukai entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

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

		float leftSwingXLower = (Math.max(-0.5009094953223726F, -rightSwingX * 2f)) - 0.1009094953223726F;
		float rightSwingXLower = (Math.max(-0.5009094953223726F, -leftSwingX * 2f)) - 0.1009094953223726F;

		this.FrontLegLeftBottom.rotateAngleX = rightSwingXLower;
		this.FrontLegRightBottom.rotateAngleX = leftSwingXLower;

		float shinMod = 3f;

		this.HindLegRightKnee.rotateAngleX = -0.17453292519943295F * 2 + leftSwingX * .75f;
		this.HindLegRightShin.rotateAngleX = 0.5617993877991494F + rightSwingX * shinMod;

		this.HindLegLeftKnee.rotateAngleX = -0.17453292519943295F * 2 + rightSwingX * .75f;
		this.HindLegLeftShin.rotateAngleX = 0.5617993877991494F + leftSwingX * shinMod;

		this.HindLegLeftCalfMiddle.rotateAngleX = -0.2617993877991494F + leftSwingX * .55f;
		this.HindLegRightCalfMiddle.rotateAngleX = -0.2617993877991494F + rightSwingX * .55f;

		this.HindLegLeftCalfMiddle.offsetY = rightSwingX / 7f - .1f;
		this.HindLegRightCalfMiddle.offsetY = leftSwingX / 7f - .1f;

		this.TorsoShoulderPlateRightBack.offsetY = rightSwingX * .12f + .01f;
		this.TorsoShoulderPlateRightMid.offsetY = rightSwingX * .12f + .01f;
		this.TorsoShoulderPlateRightFront.offsetY = rightSwingX * .12f + .01f;

		this.TorsoShoulderPlateLeftBack.offsetY = leftSwingX * .12f + .01f;
		this.TorsoShoulderPlateLeftMid.offsetY = leftSwingX * .12f + .01f;
		this.TorsoShoulderPlateLeftFront.offsetY = leftSwingX * .12f + .01f;

		this.Tail.rotateAngleX = 0.515060975741379F + rightSwingX;
	}
}
