package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.characters.EntityTivalier;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTivalier extends EntityModel<EntityTivalier>
{
	public final RendererModel BodyMain;

	public final RendererModel BodyFront;

	public final RendererModel BodyBack;

	public final RendererModel TailBase;

	public final RendererModel LegL1;

	public final RendererModel LegR1;

	public final RendererModel ShoulderL;

	public final RendererModel ShoulderR;

	public final RendererModel Neck;

	public final RendererModel WingL1;

	public final RendererModel WingL2;

	public final RendererModel WingLFeatherInt2;

	public final RendererModel WingL3;

	public final RendererModel WingLFeatherInt1;

	public final RendererModel WingLFeatherExt1;

	public final RendererModel WingLFeatherExt2;

	public final RendererModel WingLFeatherExt3;

	public final RendererModel WingR1;

	public final RendererModel WingR2;

	public final RendererModel WingRFeatherInt2;

	public final RendererModel WingR3;

	public final RendererModel WingRFeatherInt1;

	public final RendererModel WingRFeatherExt1;

	public final RendererModel WingRFeatherExt2;

	public final RendererModel WingRFeatherExt3;

	public final RendererModel HeadMain;

	public final RendererModel HeadBack;

	public final RendererModel HeadBeakMain;

	public final RendererModel JawMain;

	public final RendererModel HeadFront;

	public final RendererModel HeadBrow;

	public final RendererModel HeadFeatherL1;

	public final RendererModel HeadFeatherR1;

	public final RendererModel HeadFeatherL2;

	public final RendererModel HeadFeatherR2;

	public final RendererModel HeadBeakIntL;

	public final RendererModel HeadBeakIntR;

	public final RendererModel HeadBeakFrontL;

	public final RendererModel HeadBeakFrontR;

	public final RendererModel JawBack;

	public final RendererModel JawFrontL;

	public final RendererModel JawToothL2;

	public final RendererModel JawToothR2;

	public final RendererModel JawFrontR;

	public final RendererModel JawToothL3;

	public final RendererModel JawToothR3;

	public final RendererModel JawToothL1;

	public final RendererModel JawToothL1_1;

	public final RendererModel TailFeatherR;

	public final RendererModel TailFeatherM;

	public final RendererModel TailFeatherL;

	public final RendererModel LegL2;

	public final RendererModel LegL3;

	public final RendererModel LegLAnkle;

	public final RendererModel LegLFoot;

	public final RendererModel LegLToeM;

	public final RendererModel LegLToeR;

	public final RendererModel LegLToeL;

	public final RendererModel LegLTalonM;

	public final RendererModel LegLTalonR;

	public final RendererModel LegLTalonL;

	public final RendererModel LegR2;

	public final RendererModel LegR3;

	public final RendererModel LegRAnkle;

	public final RendererModel LegRFoot;

	public final RendererModel LegRToeM;

	public final RendererModel LegRToeR;

	public final RendererModel LegRToeL;

	public final RendererModel LegRTalonM;

	public final RendererModel LegRTalonR;

	public final RendererModel LegRTalonL;

	public ModelTivalier()
	{
		this.textureWidth = 128;
		this.textureHeight = 256;
		this.HeadBeakIntR = new RendererModel(this, 86, 11);
		this.HeadBeakIntR.setRotationPoint(-3.5F, 0.0F, -4.5F);
		this.HeadBeakIntR.addBox(0.0F, 0.0F, -3.0F, 0, 4, 3, 0.0F);
		this.setRotateAngle(this.HeadBeakIntR, 0.0F, -0.5235987755982988F, 0.0F);
		this.HeadBeakFrontR = new RendererModel(this, 31, 69);
		this.HeadBeakFrontR.setRotationPoint(-1.2F, 2.0F, -10.7F);
		this.HeadBeakFrontR.addBox(-2.0F, -5.8F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(this.HeadBeakFrontR, 0.05235987755982988F, 0.7853981633974483F, 0.0F);
		this.HeadBrow = new RendererModel(this, 25, 32);
		this.HeadBrow.setRotationPoint(0.0F, -5.9F, -5.0F);
		this.HeadBrow.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
		this.setRotateAngle(this.HeadBrow, 0.0F, 0.7853981633974483F, 0.0F);
		this.HeadBeakFrontL = new RendererModel(this, 39, 69);
		this.HeadBeakFrontL.setRotationPoint(1.2F, 2.0F, -10.7F);
		this.HeadBeakFrontL.addBox(0.0F, -5.8F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(this.HeadBeakFrontL, 0.05235987755982988F, -0.7853981633974483F, 0.0F);
		this.LegLTalonR = new RendererModel(this, 53, 210);
		this.LegLTalonR.setRotationPoint(0.0F, 0.5F, -4.5F);
		this.LegLTalonR.addBox(-0.5F, -0.6F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.LegLTalonR, 0.17453292519943295F, 0.0F, 0.0F);
		this.HeadBeakMain = new RendererModel(this, 25, 54);
		this.HeadBeakMain.setRotationPoint(0.0F, -2.0F, -4.7F);
		this.HeadBeakMain.addBox(-2.5F, -3.8F, -9.4F, 5, 6, 9, 0.0F);
		this.setRotateAngle(this.HeadBeakMain, 0.13962634015954636F, 0.0F, 0.0F);
		this.JawToothL1_1 = new RendererModel(this, 85, 0);
		this.JawToothL1_1.setRotationPoint(1.5F, -1.6F, -2.3F);
		this.JawToothL1_1.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothL1_1, 0.0F, 0.0F, 0.7853981633974483F);
		this.LegRToeL = new RendererModel(this, 34, 203);
		this.LegRToeL.setRotationPoint(1.0F, 1.0F, -2.5F);
		this.LegRToeL.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegRToeL, -0.17453292519943295F, -0.5235987755982988F, 0.0F);
		this.WingRFeatherExt2 = new RendererModel(this, 2, 131);
		this.WingRFeatherExt2.setRotationPoint(0.0F, 0.5F, 2.0F);
		this.WingRFeatherExt2.addBox(-14.0F, -1.0F, -2.5F, 16, 1, 5, 0.0F);
		this.setRotateAngle(this.WingRFeatherExt2, 0.0F, 0.17453292519943295F, 0.0F);
		this.LegR2 = new RendererModel(this, 16, 165);
		this.LegR2.setRotationPoint(-1.5F, 7.0F, 1.5F);
		this.LegR2.addBox(-2.0F, -1.0F, -2.0F, 4, 9, 4, 0.0F);
		this.setRotateAngle(this.LegR2, 2.0943951023931953F, 0.0F, -0.08726646259971647F);
		this.LegR1 = new RendererModel(this, 12, 146);
		this.LegR1.setRotationPoint(-2.5F, 6.0F, 3.0F);
		this.LegR1.addBox(-4.0F, -2.5F, -3.5F, 5, 12, 7, 0.0F);
		this.setRotateAngle(this.LegR1, -2.007128639793479F, 0.2617993877991494F, 0.0F);
		this.JawToothL2 = new RendererModel(this, 120, 4);
		this.JawToothL2.setRotationPoint(2.3F, -1.5F, -9.6F);
		this.JawToothL2.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothL2, 0.7853981633974483F, 0.0F, -0.17453292519943295F);
		this.LegLAnkle = new RendererModel(this, 65, 189);
		this.LegLAnkle.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.LegLAnkle.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegLAnkle, 2.1816615649929116F, 0.0F, 0.24434609527920614F);
		this.WingL3 = new RendererModel(this, 0, 0);
		this.WingL3.setRotationPoint(9.0F, 0.0F, 0.0F);
		this.WingL3.addBox(-3.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
		this.setRotateAngle(this.WingL3, 0.0F, -0.2617993877991494F, 0.17453292519943295F);
		this.JawFrontR = new RendererModel(this, 94, 25);
		this.JawFrontR.setRotationPoint(-1.3F, 0.0F, -9.8F);
		this.JawFrontR.addBox(0.0F, -1.6F, -3.0F, 3, 4, 3, 0.0F);
		this.setRotateAngle(this.JawFrontR, -0.05235987755982988F, 0.7853981633974483F, 0.0F);
		this.JawToothL3 = new RendererModel(this, 120, 8);
		this.JawToothL3.setRotationPoint(2.3F, -1.5F, -6.5F);
		this.JawToothL3.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothL3, 0.7853981633974483F, 0.0F, -0.17453292519943295F);
		this.WingR1 = new RendererModel(this, 8, 94);
		this.WingR1.setRotationPoint(-2.5F, 0.0F, 0.0F);
		this.WingR1.addBox(-7.5F, -2.0F, -4.0F, 10, 2, 6, 0.0F);
		this.setRotateAngle(this.WingR1, 0.0F, -0.17453292519943295F, 0.0F);
		this.JawToothL1 = new RendererModel(this, 119, 0);
		this.JawToothL1.setRotationPoint(-1.5F, -1.6F, -2.3F);
		this.JawToothL1.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothL1, 0.0F, 0.0F, 0.7853981633974483F);
		this.HeadFront = new RendererModel(this, 27, 42);
		this.HeadFront.setRotationPoint(0.0F, 0.5F, -4.5F);
		this.HeadFront.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(this.HeadFront, 0.0F, 0.7853981633974483F, 0.0F);
		this.BodyBack = new RendererModel(this, 97, 114);
		this.BodyBack.setRotationPoint(0.0F, 10.0F, 7.0F);
		this.BodyBack.addBox(-4.0F, -6.0F, -2.0F, 8, 6, 2, 0.0F);
		this.setRotateAngle(this.BodyBack, -0.41887902047863906F, 0.0F, 0.0F);
		this.JawToothR2 = new RendererModel(this, 86, 4);
		this.JawToothR2.setRotationPoint(-2.3F, -1.5F, -9.6F);
		this.JawToothR2.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothR2, 0.7853981633974483F, 0.0F, 0.17453292519943295F);
		this.HeadFeatherR2 = new RendererModel(this, 0, 18);
		this.HeadFeatherR2.setRotationPoint(-3.5F, 1.0F, 1.9F);
		this.HeadFeatherR2.addBox(-0.5F, -2.0F, -1.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(this.HeadFeatherR2, -0.17453292519943295F, -0.3490658503988659F, 0.0F);
		this.Neck = new RendererModel(this, 96, 47);
		this.Neck.setRotationPoint(0.0F, 4.0F, -7.0F);
		this.Neck.addBox(-2.5F, -13.0F, -3.0F, 5, 17, 6, 0.0F);
		this.setRotateAngle(this.Neck, 0.17453292519943295F, 0.0F, 0.0F);
		this.LegL3 = new RendererModel(this, 66, 178);
		this.LegL3.setRotationPoint(0.0F, 6.5F, 0.0F);
		this.LegL3.addBox(-1.5F, -1.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(this.LegL3, -1.7453292519943295F, 0.0F, 0.12217304763960307F);
		this.LegLTalonM = new RendererModel(this, 69, 212);
		this.LegLTalonM.setRotationPoint(0.5F, 0.5F, -6.5F);
		this.LegLTalonM.addBox(-1.0F, -0.7F, -0.7F, 1, 5, 2, 0.0F);
		this.setRotateAngle(this.LegLTalonM, 0.17453292519943295F, 0.0F, 0.0F);
		this.LegRFoot = new RendererModel(this, 15, 196);
		this.LegRFoot.setRotationPoint(0.0F, 0.0F, -1.5F);
		this.LegRFoot.addBox(-2.0F, 0.0F, -3.5F, 4, 2, 5, 0.0F);
		this.setRotateAngle(this.LegRFoot, -0.593411945678072F, 0.0F, 0.0F);
		this.WingLFeatherExt1 = new RendererModel(this, 44, 137);
		this.WingLFeatherExt1.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.WingLFeatherExt1.addBox(0.0F, -1.0F, -2.5F, 17, 1, 5, 0.0F);
		this.setRotateAngle(this.WingLFeatherExt1, 0.0F, 0.17453292519943295F, 0.0F);
		this.WingR2 = new RendererModel(this, 0, 102);
		this.WingR2.setRotationPoint(-8.0F, -1.3F, -1.0F);
		this.WingR2.addBox(-10.0F, -1.5F, -3.5F, 13, 3, 7, 0.0F);
		this.setRotateAngle(this.WingR2, 0.0F, 1.8325957145940461F, -0.5235987755982988F);
		this.WingL1 = new RendererModel(this, 40, 94);
		this.WingL1.setRotationPoint(2.5F, 0.0F, 0.0F);
		this.WingL1.addBox(-2.5F, -2.0F, -4.0F, 10, 2, 6, 0.0F);
		this.setRotateAngle(this.WingL1, 0.0F, 0.17453292519943295F, 0.0F);
		this.WingLFeatherExt3 = new RendererModel(this, 44, 125);
		this.WingLFeatherExt3.setRotationPoint(-2.0F, 1.0F, 3.0F);
		this.WingLFeatherExt3.addBox(-0.5F, -1.0F, -2.5F, 14, 1, 5, 0.0F);
		this.setRotateAngle(this.WingLFeatherExt3, 0.0F, -0.6981317007977318F, 0.0F);
		this.LegL1 = new RendererModel(this, 60, 146);
		this.LegL1.setRotationPoint(2.5F, 6.0F, 3.0F);
		this.LegL1.addBox(-1.0F, -2.5F, -3.5F, 5, 12, 7, 0.0F);
		this.setRotateAngle(this.LegL1, -2.007128639793479F, -0.2617993877991494F, 0.0F);
		this.LegLTalonL = new RendererModel(this, 87, 210);
		this.LegLTalonL.setRotationPoint(0.0F, 0.5F, -4.5F);
		this.LegLTalonL.addBox(-0.5F, -0.6F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.LegLTalonL, 0.17453292519943295F, 0.0F, 0.0F);
		this.LegLToeM = new RendererModel(this, 62, 203);
		this.LegLToeM.setRotationPoint(0.0F, 1.0F, -2.5F);
		this.LegLToeM.addBox(-1.5F, -1.0F, -7.0F, 3, 2, 7, 0.0F);
		this.setRotateAngle(this.LegLToeM, -0.5235987755982988F, 0.0F, 0.0F);
		this.WingRFeatherInt1 = new RendererModel(this, 10, 119);
		this.WingRFeatherInt1.setRotationPoint(-2.0F, 0.0F, 3.0F);
		this.WingRFeatherInt1.addBox(-11.5F, 0.0F, -2.5F, 12, 1, 5, 0.0F);
		this.setRotateAngle(this.WingRFeatherInt1, 0.0F, 1.3962634015954636F, 0.0F);
		this.ShoulderL = new RendererModel(this, 40, 81);
		this.ShoulderL.setRotationPoint(3.0F, 1.5F, -4.0F);
		this.ShoulderL.addBox(-1.5F, -2.5F, -4.5F, 6, 4, 9, 0.0F);
		this.setRotateAngle(this.ShoulderL, 0.0F, 0.0F, 0.9599310885968813F);
		this.HeadBack = new RendererModel(this, 25, 0);
		this.HeadBack.setRotationPoint(0.0F, -6.0F, 3.5F);
		this.HeadBack.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 6, 0.0F);
		this.setRotateAngle(this.HeadBack, -0.6283185307179586F, 0.0F, 0.0F);
		this.WingL2 = new RendererModel(this, 40, 102);
		this.WingL2.setRotationPoint(8.0F, -1.3F, -1.0F);
		this.WingL2.addBox(-3.0F, -1.5F, -3.5F, 13, 3, 7, 0.0F);
		this.setRotateAngle(this.WingL2, 0.0F, -1.8325957145940461F, 0.5235987755982988F);
		this.LegRToeR = new RendererModel(this, 0, 203);
		this.LegRToeR.setRotationPoint(-1.0F, 1.0F, -2.5F);
		this.LegRToeR.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegRToeR, -0.17453292519943295F, 0.5235987755982988F, 0.0F);
		this.HeadBeakIntL = new RendererModel(this, 120, 11);
		this.HeadBeakIntL.setRotationPoint(3.5F, 0.0F, -4.5F);
		this.HeadBeakIntL.addBox(0.0F, 0.0F, -3.0F, 0, 4, 3, 0.0F);
		this.setRotateAngle(this.HeadBeakIntL, 0.0F, 0.5235987755982988F, 0.0F);
		this.LegLToeL = new RendererModel(this, 82, 203);
		this.LegLToeL.setRotationPoint(1.0F, 1.0F, -2.5F);
		this.LegLToeL.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegLToeL, -0.17453292519943295F, -0.5235987755982988F, 0.0F);
		this.LegRTalonM = new RendererModel(this, 21, 212);
		this.LegRTalonM.setRotationPoint(0.5F, 0.5F, -6.5F);
		this.LegRTalonM.addBox(-1.0F, -0.7F, -0.7F, 1, 5, 2, 0.0F);
		this.setRotateAngle(this.LegRTalonM, 0.17453292519943295F, 0.0F, 0.0F);
		this.TailFeatherR = new RendererModel(this, 10, 230);
		this.TailFeatherR.setRotationPoint(-1.0F, 1.0F, 3.0F);
		this.TailFeatherR.addBox(-2.5F, -1.0F, 0.0F, 5, 2, 20, 0.0F);
		this.setRotateAngle(this.TailFeatherR, -0.5235987755982988F, 0.0F, 0.0F);
		this.HeadFeatherR1 = new RendererModel(this, 0, 4);
		this.HeadFeatherR1.setRotationPoint(-3.5F, -4.0F, 2.0F);
		this.HeadFeatherR1.addBox(-0.5F, -2.0F, -1.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(this.HeadFeatherR1, 0.17453292519943295F, -0.3490658503988659F, 0.0F);
		this.WingR3 = new RendererModel(this, 0, 0);
		this.WingR3.setRotationPoint(-9.0F, 0.0F, 0.0F);
		this.WingR3.addBox(1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
		this.setRotateAngle(this.WingR3, 0.0F, 0.2617993877991494F, -0.17453292519943295F);
		this.JawBack = new RendererModel(this, 92, 0);
		this.JawBack.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.JawBack.addBox(-3.5F, -1.6F, -3.5F, 7, 4, 7, 0.0F);
		this.setRotateAngle(this.JawBack, 0.0F, -0.7853981633974483F, 0.0F);
		this.BodyFront = new RendererModel(this, 87, 70);
		this.BodyFront.setRotationPoint(0.0F, -0.5F, -4.0F);
		this.BodyFront.addBox(-4.0F, 0.0F, -9.0F, 8, 10, 12, 0.0F);
		this.setRotateAngle(this.BodyFront, -0.17453292519943295F, 0.0F, 0.0F);
		this.LegRTalonR = new RendererModel(this, 5, 210);
		this.LegRTalonR.setRotationPoint(0.0F, 0.5F, -4.5F);
		this.LegRTalonR.addBox(-0.5F, -0.6F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.LegRTalonR, 0.17453292519943295F, 0.0F, 0.0F);
		this.HeadFeatherL2 = new RendererModel(this, 56, 18);
		this.HeadFeatherL2.setRotationPoint(3.5F, 1.0F, 2.0F);
		this.HeadFeatherL2.addBox(-0.5F, -2.0F, -1.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(this.HeadFeatherL2, -0.17453292519943295F, 0.3490658503988659F, 0.0F);
		this.LegLToeR = new RendererModel(this, 48, 203);
		this.LegLToeR.setRotationPoint(-1.0F, 1.0F, -2.5F);
		this.LegLToeR.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegLToeR, -0.17453292519943295F, 0.5235987755982988F, 0.0F);
		this.HeadMain = new RendererModel(this, 22, 14);
		this.HeadMain.setRotationPoint(0.0F, -12.0F, 0.0F);
		this.HeadMain.addBox(-4.5F, -6.0F, -4.5F, 9, 10, 8, 0.0F);
		this.setRotateAngle(this.HeadMain, 0.6981317007977318F, 0.0F, 0.0F);
		this.WingLFeatherExt2 = new RendererModel(this, 44, 131);
		this.WingLFeatherExt2.setRotationPoint(0.0F, 0.5F, 2.0F);
		this.WingLFeatherExt2.addBox(-2.0F, -1.0F, -2.5F, 16, 1, 5, 0.0F);
		this.setRotateAngle(this.WingLFeatherExt2, 0.0F, -0.17453292519943295F, 0.0F);
		this.WingRFeatherExt1 = new RendererModel(this, 0, 137);
		this.WingRFeatherExt1.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.WingRFeatherExt1.addBox(-17.0F, -1.0F, -2.5F, 17, 1, 5, 0.0F);
		this.setRotateAngle(this.WingRFeatherExt1, 0.0F, -0.17453292519943295F, 0.0F);
		this.HeadFeatherL1 = new RendererModel(this, 56, 4);
		this.HeadFeatherL1.setRotationPoint(3.5F, -4.0F, 2.0F);
		this.HeadFeatherL1.addBox(-0.5F, -2.0F, -1.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(this.HeadFeatherL1, 0.17453292519943295F, 0.3490658503988659F, 0.0F);
		this.JawToothR3 = new RendererModel(this, 86, 8);
		this.JawToothR3.setRotationPoint(-2.3F, -1.5F, -6.5F);
		this.JawToothR3.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.JawToothR3, 0.7853981633974483F, 0.0F, 0.17453292519943295F);
		this.WingRFeatherExt3 = new RendererModel(this, 6, 125);
		this.WingRFeatherExt3.setRotationPoint(2.0F, 1.0F, 3.0F);
		this.WingRFeatherExt3.addBox(-12.5F, -1.0F, -2.5F, 14, 1, 5, 0.0F);
		this.setRotateAngle(this.WingRFeatherExt3, 0.0F, 0.6981317007977318F, 0.0F);
		this.LegLFoot = new RendererModel(this, 63, 196);
		this.LegLFoot.setRotationPoint(0.0F, 0.0F, -1.5F);
		this.LegLFoot.addBox(-2.0F, 0.0F, -3.5F, 4, 2, 5, 0.0F);
		this.setRotateAngle(this.LegLFoot, -0.593411945678072F, 0.0F, 0.0F);
		this.LegRTalonL = new RendererModel(this, 39, 210);
		this.LegRTalonL.setRotationPoint(0.0F, 0.5F, -4.5F);
		this.LegRTalonL.addBox(-0.5F, -0.6F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.LegRTalonL, 0.17453292519943295F, 0.0F, 0.0F);
		this.BodyMain = new RendererModel(this, 86, 92);
		this.BodyMain.setRotationPoint(0.0F, 11.5F, 0.0F);
		this.BodyMain.addBox(-4.5F, 0.0F, -5.0F, 9, 10, 12, 0.0F);
		this.WingRFeatherInt2 = new RendererModel(this, 12, 113);
		this.WingRFeatherInt2.setRotationPoint(-6.0F, -1.5F, 0.5F);
		this.WingRFeatherInt2.addBox(-11.5F, 0.0F, -2.5F, 11, 1, 5, 0.0F);
		this.setRotateAngle(this.WingRFeatherInt2, 0.0F, 1.48352986419518F, 0.0F);
		this.TailBase = new RendererModel(this, 91, 122);
		this.TailBase.setRotationPoint(0.0F, 1.7F, 8.5F);
		this.TailBase.addBox(-4.5F, -1.6F, -4.5F, 8, 5, 8, 0.0F);
		this.setRotateAngle(this.TailBase, 0.0F, -0.7853981633974483F, 0.0F);
		this.WingLFeatherInt1 = new RendererModel(this, 44, 119);
		this.WingLFeatherInt1.setRotationPoint(2.0F, 0.0F, 3.0F);
		this.WingLFeatherInt1.addBox(-0.5F, 0.0F, -2.5F, 12, 1, 5, 0.0F);
		this.setRotateAngle(this.WingLFeatherInt1, 0.0F, -1.3962634015954636F, 0.0F);
		this.ShoulderR = new RendererModel(this, 10, 81);
		this.ShoulderR.setRotationPoint(-3.0F, 1.5F, -4.0F);
		this.ShoulderR.addBox(-4.5F, -2.5F, -4.5F, 6, 4, 9, 0.0F);
		this.setRotateAngle(this.ShoulderR, 0.0F, 0.0F, -0.9599310885968813F);
		this.JawFrontL = new RendererModel(this, 106, 25);
		this.JawFrontL.setRotationPoint(1.3F, 0.0F, -9.8F);
		this.JawFrontL.addBox(-3.0F, -1.6F, -3.0F, 3, 4, 3, 0.0F);
		this.setRotateAngle(this.JawFrontL, -0.05235987755982988F, -0.7853981633974483F, 0.0F);
		this.LegL2 = new RendererModel(this, 64, 165);
		this.LegL2.setRotationPoint(1.5F, 7.0F, 1.5F);
		this.LegL2.addBox(-2.0F, -1.0F, -2.0F, 4, 9, 4, 0.0F);
		this.setRotateAngle(this.LegL2, 2.0943951023931953F, 0.0F, 0.08726646259971647F);
		this.TailFeatherL = new RendererModel(this, 70, 230);
		this.TailFeatherL.setRotationPoint(3.0F, 1.0F, -1.0F);
		this.TailFeatherL.addBox(-2.5F, -1.0F, 0.0F, 5, 2, 20, 0.0F);
		this.setRotateAngle(this.TailFeatherL, -0.5235987755982988F, 1.5707963267948966F, 0.0F);
		this.LegRAnkle = new RendererModel(this, 17, 189);
		this.LegRAnkle.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.LegRAnkle.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.LegRAnkle, 2.1816615649929116F, 0.0F, -0.24434609527920614F);
		this.LegRToeM = new RendererModel(this, 14, 203);
		this.LegRToeM.setRotationPoint(0.0F, 1.0F, -2.5F);
		this.LegRToeM.addBox(-1.5F, -1.0F, -7.0F, 3, 2, 7, 0.0F);
		this.setRotateAngle(this.LegRToeM, -0.5235987755982988F, 0.0F, 0.0F);
		this.LegR3 = new RendererModel(this, 18, 178);
		this.LegR3.setRotationPoint(0.0F, 6.5F, 0.0F);
		this.LegR3.addBox(-1.5F, -1.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(this.LegR3, -1.7453292519943295F, 0.0F, -0.12217304763960307F);
		this.TailFeatherM = new RendererModel(this, 36, 230);
		this.TailFeatherM.setRotationPoint(1.0F, 0.0F, 1.0F);
		this.TailFeatherM.addBox(-2.5F, -1.0F, 0.0F, 5, 2, 24, 0.0F);
		this.setRotateAngle(this.TailFeatherM, -0.5235987755982988F, 0.7853981633974483F, 0.0F);
		this.JawMain = new RendererModel(this, 90, 11);
		this.JawMain.setRotationPoint(0.0F, 2.0F, -2.0F);
		this.JawMain.addBox(-3.0F, -1.7F, -11.5F, 6, 4, 10, 0.0F);
		this.WingLFeatherInt2 = new RendererModel(this, 44, 113);
		this.WingLFeatherInt2.setRotationPoint(6.0F, -1.5F, 0.5F);
		this.WingLFeatherInt2.addBox(-0.5F, 0.0F, -2.5F, 11, 1, 5, 0.0F);
		this.setRotateAngle(this.WingLFeatherInt2, 0.0F, -1.48352986419518F, 0.0F);
		this.HeadMain.addChild(this.HeadBeakIntR);
		this.HeadBeakMain.addChild(this.HeadBeakFrontR);
		this.HeadMain.addChild(this.HeadBrow);
		this.HeadBeakMain.addChild(this.HeadBeakFrontL);
		this.LegLToeR.addChild(this.LegLTalonR);
		this.HeadMain.addChild(this.HeadBeakMain);
		this.JawFrontR.addChild(this.JawToothL1_1);
		this.LegRFoot.addChild(this.LegRToeL);
		this.WingR3.addChild(this.WingRFeatherExt2);
		this.LegR1.addChild(this.LegR2);
		this.BodyMain.addChild(this.LegR1);
		this.JawMain.addChild(this.JawToothL2);
		this.LegL3.addChild(this.LegLAnkle);
		this.WingL2.addChild(this.WingL3);
		this.JawMain.addChild(this.JawFrontR);
		this.JawMain.addChild(this.JawToothL3);
		this.ShoulderR.addChild(this.WingR1);
		this.JawFrontL.addChild(this.JawToothL1);
		this.HeadMain.addChild(this.HeadFront);
		this.BodyMain.addChild(this.BodyBack);
		this.JawMain.addChild(this.JawToothR2);
		this.HeadMain.addChild(this.HeadFeatherR2);
		this.BodyFront.addChild(this.Neck);
		this.LegL2.addChild(this.LegL3);
		this.LegLToeM.addChild(this.LegLTalonM);
		this.LegRAnkle.addChild(this.LegRFoot);
		this.WingL3.addChild(this.WingLFeatherExt1);
		this.WingR1.addChild(this.WingR2);
		this.ShoulderL.addChild(this.WingL1);
		this.WingL3.addChild(this.WingLFeatherExt3);
		this.BodyMain.addChild(this.LegL1);
		this.LegLToeL.addChild(this.LegLTalonL);
		this.LegLFoot.addChild(this.LegLToeM);
		this.WingR2.addChild(this.WingRFeatherInt1);
		this.BodyFront.addChild(this.ShoulderL);
		this.HeadMain.addChild(this.HeadBack);
		this.WingL1.addChild(this.WingL2);
		this.LegRFoot.addChild(this.LegRToeR);
		this.HeadMain.addChild(this.HeadBeakIntL);
		this.LegLFoot.addChild(this.LegLToeL);
		this.LegRToeM.addChild(this.LegRTalonM);
		this.TailBase.addChild(this.TailFeatherR);
		this.HeadMain.addChild(this.HeadFeatherR1);
		this.WingR2.addChild(this.WingR3);
		this.JawMain.addChild(this.JawBack);
		this.BodyMain.addChild(this.BodyFront);
		this.LegRToeR.addChild(this.LegRTalonR);
		this.HeadMain.addChild(this.HeadFeatherL2);
		this.LegLFoot.addChild(this.LegLToeR);
		this.Neck.addChild(this.HeadMain);
		this.WingL3.addChild(this.WingLFeatherExt2);
		this.WingR3.addChild(this.WingRFeatherExt1);
		this.HeadMain.addChild(this.HeadFeatherL1);
		this.JawMain.addChild(this.JawToothR3);
		this.WingR3.addChild(this.WingRFeatherExt3);
		this.LegLAnkle.addChild(this.LegLFoot);
		this.LegRToeL.addChild(this.LegRTalonL);
		this.WingR1.addChild(this.WingRFeatherInt2);
		this.BodyMain.addChild(this.TailBase);
		this.WingL2.addChild(this.WingLFeatherInt1);
		this.BodyFront.addChild(this.ShoulderR);
		this.JawMain.addChild(this.JawFrontL);
		this.LegL1.addChild(this.LegL2);
		this.TailBase.addChild(this.TailFeatherL);
		this.LegR3.addChild(this.LegRAnkle);
		this.LegRFoot.addChild(this.LegRToeM);
		this.LegR2.addChild(this.LegR3);
		this.TailBase.addChild(this.TailFeatherM);
		this.HeadMain.addChild(this.JawMain);
		this.WingL1.addChild(this.WingLFeatherInt2);
	}

	@Override
	public void render(EntityTivalier entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.BodyMain.render(f5);
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
	public void setRotationAngles(EntityTivalier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor);

		float tailSwayRange = 0.05F;

		this.HeadMain.rotateAngleX = 0.6981317007977318F + (MathHelper.cos((ageInTicks) * 0.05662F) * 0.025F);

		this.TailFeatherL.rotateAngleZ = (MathHelper.cos((24F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherM.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherR.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * tailSwayRange);

		this.TailFeatherL.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherM.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherR.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0F + ageInTicks) * 0.15662F) * tailSwayRange);

		this.HeadFeatherL1.rotateAngleZ = (MathHelper.cos((24F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherL2.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherR1.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherR2.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * 0.1F);

		this.HeadFeatherL1.rotateAngleX = 0.17453292519943295F + (MathHelper.cos((0.0F + ageInTicks) * 0.075662F) * 0.05F);
		this.HeadFeatherL2.rotateAngleX = -0.17453292519943295F + (MathHelper.cos((10.0F + ageInTicks) * 0.0755662F) * 0.05F);
		this.HeadFeatherR1.rotateAngleX = 0.17453292519943295F + (MathHelper.cos((20.0F + ageInTicks) * 0.0755662F) * 0.05F);
		this.HeadFeatherR2.rotateAngleX = -0.17453292519943295F + (MathHelper.cos((30.0F + ageInTicks) * 0.0755662F) * 0.05F);

		float wingSwayRange = 0.05F;

		this.WingLFeatherExt1.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherExt2.rotateAngleZ = (MathHelper.cos((10.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherExt3.rotateAngleZ = (MathHelper.cos((20.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherInt1.rotateAngleZ = (MathHelper.cos((30.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherInt2.rotateAngleZ = (MathHelper.cos((40.0F + ageInTicks) * 0.15662F) * wingSwayRange);

		this.WingRFeatherExt1.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherExt2.rotateAngleZ = (MathHelper.cos((10.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherExt3.rotateAngleZ = (MathHelper.cos((20.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherInt1.rotateAngleZ = (MathHelper.cos((30.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherInt2.rotateAngleZ = (MathHelper.cos((40.0F + ageInTicks) * 0.15662F) * wingSwayRange);
	}
}
