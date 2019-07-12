package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.monsters.EntityTempest;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelTempest extends EntityModel<EntityTempest>
{
	private final RendererModel Body1;

	private final RendererModel Body2;

	private final RendererModel Body3;

	private final RendererModel Body4;

	private final RendererModel Body5;

	private final RendererModel Body6;

	private final RendererModel Body7;

	private final RendererModel Body8;

	private final RendererModel Body9;

	private final RendererModel Body10;

	private final RendererModel Body11;

	private final RendererModel Body12;

	private final RendererModel Body13;

	private final RendererModel Body14;

	private final RendererModel Body15;

	private final RendererModel Body16;

	private final RendererModel Body17;

	private final RendererModel LeftArm1;

	private final RendererModel LeftArm2;

	private final RendererModel LeftArm3;

	private final RendererModel RightArm1;

	private final RendererModel RightArm2;

	private final RendererModel RightArm3;

	private final RendererModel LeftFrontLeg1;

	private final RendererModel LeftFrontLeg2;

	private final RendererModel LeftBackLeg1;

	private final RendererModel LeftBackLeg2;

	private final RendererModel RightFrontLeg1;

	private final RendererModel RightFrontLeg2;

	private final RendererModel RightBackLeg1;

	private final RendererModel RightBackLeg2;

	private final RendererModel Tail1;

	private final RendererModel Tail2;

	private final RendererModel Tail3;

	private final RendererModel Tail4;

	private final RendererModel HeadTop1;

	private final RendererModel HeadTop2;

	private final RendererModel HeadTop3;

	private final RendererModel HeadTop4;

	private final RendererModel HeadTop5;

	private final RendererModel HeadTop6;

	private final RendererModel HeadTop7;

	private final RendererModel HeadTop8;

	private final RendererModel HeadTop9;

	private final RendererModel HeadTop10;

	private final RendererModel HeadTop11;

	private final RendererModel HeadTop12;

	private final RendererModel Jaw1;

	private final RendererModel Jaw2;

	private final RendererModel Jaw3;

	private final RendererModel Jaw4;

	private final RendererModel Jaw5;

	private final RendererModel Jaw6;

	private final RendererModel Jaw7;

	private final RendererModel Jaw8;

	private final RendererModel Jaw9;

	private final RendererModel Jaw10;

	private final RendererModel Jaw11;

	private final RendererModel Jaw12;

	public ModelTempest()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.Body1 = new RendererModel(this, 0, 0);
		this.Body1.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body1.addBox(-4.0F, -2.0F, 0.0F, 8, 5, 12, 0.0F);
		this.LeftFrontLeg2 = new RendererModel(this, 18, 24);
		this.LeftFrontLeg2.mirror = true;
		this.LeftFrontLeg2.setRotationPoint(4.0F, 16.0F, 4.0F);
		this.LeftFrontLeg2.addBox(1.5F, -0.5F, -1.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.LeftFrontLeg2, -0.1719337068869827F, -0.030158267073592475F, 1.398862573507409F);
		this.RightBackLeg1 = new RendererModel(this, 18, 20);
		this.RightBackLeg1.mirror = true;
		this.RightBackLeg1.setRotationPoint(-4.0F, 16.0F, 5.5F);
		this.RightBackLeg1.addBox(-0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.RightBackLeg1, 0.17453292012214658F, -0.0F, 0.34906584024429316F);
		this.HeadTop6 = new RendererModel(this, 50, 17);
		this.HeadTop6.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop6.addBox(-4.5F, -2.5F, -4.0F, 9, 4, 5, 0.0F);
		this.setRotateAngle(this.HeadTop6, 0.2617993950843811F, -0.0F, 0.0F);
		this.HeadTop4 = new RendererModel(this, 22, 30);
		this.HeadTop4.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop4.addBox(3.0F, 1.5F, -5.0F, 2, 1, 6, 0.0F);
		this.setRotateAngle(this.HeadTop4, 0.2617993950843811F, 0.3839724361896515F, 0.0F);
		this.HeadTop10 = new RendererModel(this, 50, 26);
		this.HeadTop10.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.HeadTop10.addBox(-2.0F, -4.0F, 1.0F, 4, 2, 9, 0.0F);
		this.setRotateAngle(this.HeadTop10, 0.34906584024429316F, -0.0F, 0.0F);
		this.Body11 = new RendererModel(this, 70, 5);
		this.Body11.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body11.addBox(-2.0F, -5.0F, 12.0F, 4, 2, 3, 0.0F);
		this.setRotateAngle(this.Body11, -0.34906584024429316F, -0.0F, 0.0F);
		this.HeadTop1 = new RendererModel(this, 0, 30);
		this.HeadTop1.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop1.addBox(-3.5F, -1.0F, -4.0F, 7, 3, 4, 0.0F);
		this.setRotateAngle(this.HeadTop1, 0.2617993950843811F, -0.0F, 0.0F);
		this.Body16 = new RendererModel(this, 38, 43);
		this.Body16.setRotationPoint(0.0F, 15.0F, 1.0F);
		this.Body16.addBox(4.0F, 0.0F, 1.0F, 0, 3, 12, 0.0F);
		this.setRotateAngle(this.Body16, -0.17453292012214658F, 0.43633231520652765F, 0.0F);
		this.Jaw10 = new RendererModel(this, 78, 36);
		this.Jaw10.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw10.addBox(1.0F, 0.0F, -5.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.Jaw10, -0.17453292012214658F, -0.34906584024429316F, 0.0F);
		this.Body6 = new RendererModel(this, 24, 17);
		this.Body6.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body6.addBox(-4.0F, 1.0F, 11.0F, 4, 2, 9, 0.0F);
		this.setRotateAngle(this.Body6, 0.4144236271299977F, 0.31516727383825993F, 0.46063047019741254F);
		this.RightFrontLeg2 = new RendererModel(this, 18, 24);
		this.RightFrontLeg2.mirror = true;
		this.RightFrontLeg2.setRotationPoint(-4.0F, 16.0F, 4.0F);
		this.RightFrontLeg2.addBox(-2.5F, -0.5F, -1.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.RightFrontLeg2, -0.1719337068869827F, 0.030158267073592475F, -1.398862573507409F);
		this.LeftFrontLeg1 = new RendererModel(this, 18, 20);
		this.LeftFrontLeg1.setRotationPoint(4.0F, 16.0F, 4.0F);
		this.LeftFrontLeg1.addBox(-0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.LeftFrontLeg1, -0.17453292012214658F, -0.0F, -0.34906584024429316F);
		this.Tail2 = new RendererModel(this, 70, 4);
		this.Tail2.setRotationPoint(0.0F, 15.0F, 11.0F);
		this.Tail2.addBox(0.0F, 1.2999999523162842F, 7.400000095367432F, 0, 2, 8, 0.0F);
		this.setRotateAngle(this.Tail2, 0.08726646006107329F, -0.0F, 0.0F);
		this.LeftBackLeg1 = new RendererModel(this, 18, 20);
		this.LeftBackLeg1.setRotationPoint(4.0F, 16.0F, 5.5F);
		this.LeftBackLeg1.addBox(-0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.LeftBackLeg1, 0.17453292012214658F, -0.0F, -0.34906584024429316F);
		this.LeftBackLeg2 = new RendererModel(this, 18, 24);
		this.LeftBackLeg2.setRotationPoint(4.0F, 16.0F, 5.5F);
		this.LeftBackLeg2.addBox(1.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.LeftBackLeg2, 0.1719337068869827F, 0.030158267073592475F, 1.398862573507409F);
		this.Jaw3 = new RendererModel(this, 0, 44);
		this.Jaw3.setRotationPoint(0.0F, 14.5F, -5.0F);
		this.Jaw3.addBox(-2.0F, 2.0F, -5.0F, 4, 1, 6, 0.0F);
		this.Body14 = new RendererModel(this, 32, 33);
		this.Body14.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body14.addBox(0.5F, 1.0F, 11.0F, 0, 4, 18, 0.0F);
		this.setRotateAngle(this.Body14, 0.2617993950843811F, 0.2094395160675049F, 0.0F);
		this.Jaw9 = new RendererModel(this, 78, 36);
		this.Jaw9.mirror = true;
		this.Jaw9.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw9.addBox(-2.0F, 0.0F, -5.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.Jaw9, -0.17453292012214658F, 0.34906584024429316F, 0.0F);
		this.RightArm1 = new RendererModel(this, 0, 24);
		this.RightArm1.mirror = true;
		this.RightArm1.setRotationPoint(-5.0F, 16.0F, 0.0F);
		this.RightArm1.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.RightArm1, -0.08726646006107329F, -0.17453292012214658F, 0.0F);
		this.Body17 = new RendererModel(this, 38, 43);
		this.Body17.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body17.addBox(-2.0F, -1.0F, 5.5F, 0, 3, 12, 0.0F);
		this.setRotateAngle(this.Body17, -0.17453292012214658F, -0.43633231520652765F, 0.0F);
		this.Tail3 = new RendererModel(this, 78, 43);
		this.Tail3.setRotationPoint(0.0F, 15.0F, 11.0F);
		this.Tail3.addBox(-1.5F, -0.5F, 0.0F, 3, 3, 8, 0.0F);
		this.setRotateAngle(this.Tail3, -0.08726646006107329F, -0.0F, 0.0F);
		this.HeadTop12 = new RendererModel(this, 50, 37);
		this.HeadTop12.mirror = true;
		this.HeadTop12.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.HeadTop12.addBox(-3.5F, -5.0F, 2.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(this.HeadTop12, 0.40359461763480853F, -0.37470514616201034F, -0.5672073239515355F);
		this.Jaw6 = new RendererModel(this, 0, 46);
		this.Jaw6.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw6.addBox(3.0F, 1.0F, -4.5F, 0, 1, 5, 0.0F);
		this.setRotateAngle(this.Jaw6, 0.0F, 0.34906584024429316F, 0.0F);
		this.Jaw2 = new RendererModel(this, 20, 44);
		this.Jaw2.setRotationPoint(0.0F, 14.5F, -5.0F);
		this.Jaw2.addBox(1.0F, 2.0F, -4.0F, 3, 1, 6, 0.0F);
		this.setRotateAngle(this.Jaw2, 0.0F, 0.34906584024429316F, 0.0F);
		this.Body5 = new RendererModel(this, 16, 17);
		this.Body5.mirror = true;
		this.Body5.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body5.addBox(-7.0F, 1.0F, 0.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(this.Body5, 0.31845654632260784F, 0.17185474647631838F, -0.17716979319067175F);
		this.HeadTop2 = new RendererModel(this, 0, 37);
		this.HeadTop2.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop2.addBox(-3.0F, -1.5F, -7.0F, 6, 3, 4, 0.0F);
		this.setRotateAngle(this.HeadTop2, 0.34906584024429316F, -0.0F, 0.0F);
		this.RightBackLeg2 = new RendererModel(this, 18, 24);
		this.RightBackLeg2.mirror = true;
		this.RightBackLeg2.setRotationPoint(-4.0F, 16.0F, 5.5F);
		this.RightBackLeg2.addBox(-2.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.RightBackLeg2, 0.1719337068869827F, -0.030158267073592475F, -1.398862573507409F);
		this.Jaw12 = new RendererModel(this, 82, 36);
		this.Jaw12.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw12.addBox(3.0F, 1.5F, -3.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Jaw12, 0.17453292012214658F, 0.2094395160675049F, 0.0F);
		this.Jaw1 = new RendererModel(this, 20, 44);
		this.Jaw1.mirror = true;
		this.Jaw1.setRotationPoint(0.0F, 14.5F, -5.0F);
		this.Jaw1.addBox(-4.0F, 2.0F, -4.0F, 3, 1, 6, 0.0F);
		this.setRotateAngle(this.Jaw1, 0.0F, -0.34906584024429316F, 0.0F);
		this.Jaw7 = new RendererModel(this, 0, 46);
		this.Jaw7.mirror = true;
		this.Jaw7.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw7.addBox(-3.0F, 1.0F, -4.5F, 0, 1, 5, 0.0F);
		this.setRotateAngle(this.Jaw7, 0.0F, -0.34906584024429316F, 0.0F);
		this.HeadTop3 = new RendererModel(this, 20, 37);
		this.HeadTop3.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop3.addBox(-3.0F, 1.5F, -6.0F, 6, 1, 6, 0.0F);
		this.setRotateAngle(this.HeadTop3, 0.19198621809482574F, -0.0F, 0.0F);
		this.Body12 = new RendererModel(this, 108, 26);
		this.Body12.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body12.addBox(0.0F, -10.0F, -6.0F, 3, 7, 2, 0.0F);
		this.setRotateAngle(this.Body12, -1.1402987923351886F, 0.07345294096835633F, 1.4123296882068237F);
		this.HeadTop9 = new RendererModel(this, 96, 17);
		this.HeadTop9.mirror = true;
		this.HeadTop9.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop9.addBox(-6.0F, -2.0F, -7.0F, 3, 4, 5, 0.0F);
		this.setRotateAngle(this.HeadTop9, 0.34906584024429316F, -0.43633231520652765F, 0.0F);
		this.Body4 = new RendererModel(this, 0, 17);
		this.Body4.mirror = true;
		this.Body4.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body4.addBox(-7.0F, -1.0F, 0.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(this.Body4, 0.31845654632260784F, 0.17185474647631838F, -0.17716979319067175F);
		this.Body3 = new RendererModel(this, 16, 17);
		this.Body3.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body3.addBox(4.0F, 1.0F, 0.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(this.Body3, 0.31845654632260784F, -0.17185474647631838F, 0.17716979319067175F);
		this.RightArm3 = new RendererModel(this, 12, 24);
		this.RightArm3.setRotationPoint(-5.0F, 16.0F, 0.0F);
		this.RightArm3.addBox(-1.0F, 1.0F, -6.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.RightArm3, 1.745329260826111F, -0.17453292012214658F, 0.0F);
		this.Body8 = new RendererModel(this, 40, 8);
		this.Body8.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body8.addBox(-4.5F, -4.5F, 4.0F, 9, 3, 6, 0.0F);
		this.setRotateAngle(this.Body8, -0.17453292012214658F, -0.0F, 0.0F);
		this.LeftArm3 = new RendererModel(this, 12, 24);
		this.LeftArm3.setRotationPoint(5.0F, 16.0F, 0.0F);
		this.LeftArm3.addBox(-1.0F, 1.0F, -6.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.LeftArm3, 1.745329260826111F, 0.17453292012214658F, 0.0F);
		this.LeftArm2 = new RendererModel(this, 4, 24);
		this.LeftArm2.setRotationPoint(5.0F, 16.0F, 0.0F);
		this.LeftArm2.addBox(-1.0F, 1.0F, -4.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(this.LeftArm2, 0.9599310755729675F, 0.17453292012214658F, 0.0F);
		this.HeadTop7 = new RendererModel(this, 78, 17);
		this.HeadTop7.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop7.addBox(-2.0F, -3.0F, -8.5F, 4, 4, 5, 0.0F);
		this.setRotateAngle(this.HeadTop7, 0.43633231520652765F, -0.0F, 0.0F);
		this.Tail1 = new RendererModel(this, 70, 2);
		this.Tail1.setRotationPoint(0.0F, 15.0F, 11.0F);
		this.Tail1.addBox(0.0F, 0.0F, 0.0F, 0, 2, 8, 0.0F);
		this.setRotateAngle(this.Tail1, -0.08726646006107329F, -0.0F, 0.0F);
		this.Jaw11 = new RendererModel(this, 82, 36);
		this.Jaw11.mirror = true;
		this.Jaw11.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw11.addBox(-5.0F, 1.5F, -3.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Jaw11, 0.17453292012214658F, -0.2094395160675049F, 0.0F);
		this.RightArm2 = new RendererModel(this, 4, 24);
		this.RightArm2.mirror = true;
		this.RightArm2.setRotationPoint(-5.0F, 16.0F, 0.0F);
		this.RightArm2.addBox(-1.0F, 1.0F, -4.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(this.RightArm2, 0.9599310755729675F, -0.17453292012214658F, 0.0F);
		this.Body10 = new RendererModel(this, 70, 0);
		this.Body10.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body10.addBox(-2.5F, -4.0F, 11.0F, 5, 3, 2, 0.0F);
		this.setRotateAngle(this.Body10, -0.2617993950843811F, -0.0F, 0.0F);
		this.HeadTop8 = new RendererModel(this, 96, 17);
		this.HeadTop8.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop8.addBox(3.0F, -2.0F, -7.0F, 3, 4, 5, 0.0F);
		this.setRotateAngle(this.HeadTop8, 0.34906584024429316F, 0.43633231520652765F, 0.0F);
		this.Body2 = new RendererModel(this, 0, 17);
		this.Body2.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body2.addBox(4.0F, -1.0F, 0.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(this.Body2, 0.31845654632260784F, -0.17185474647631838F, 0.17716979319067175F);
		this.Body7 = new RendererModel(this, 24, 17);
		this.Body7.mirror = true;
		this.Body7.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body7.addBox(0.0F, 1.0F, 11.0F, 4, 2, 9, 0.0F);
		this.setRotateAngle(this.Body7, 0.4144236271299977F, -0.31516727383825993F, -0.46063047019741254F);
		this.Body15 = new RendererModel(this, 32, 33);
		this.Body15.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body15.addBox(-0.5F, 1.0F, 11.0F, 0, 4, 18, 0.0F);
		this.setRotateAngle(this.Body15, 0.2617993950843811F, -0.2094395160675049F, 0.0F);
		this.Body13 = new RendererModel(this, 108, 26);
		this.Body13.mirror = true;
		this.Body13.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body13.addBox(-3.0F, -10.0F, -6.0F, 3, 7, 2, 0.0F);
		this.setRotateAngle(this.Body13, -1.1402987923351886F, -0.07345294096835633F, -1.4123296882068237F);
		this.HeadTop5 = new RendererModel(this, 22, 30);
		this.HeadTop5.mirror = true;
		this.HeadTop5.setRotationPoint(0.0F, 13.0F, -4.0F);
		this.HeadTop5.addBox(-5.0F, 1.5F, -5.0F, 2, 1, 6, 0.0F);
		this.setRotateAngle(this.HeadTop5, 0.2617993950843811F, -0.3839724361896515F, 0.0F);
		this.Jaw4 = new RendererModel(this, 0, 44);
		this.Jaw4.mirror = true;
		this.Jaw4.setRotationPoint(0.0F, 14.5F, -5.0F);
		this.Jaw4.addBox(-3.5F, 1.0F, 0.0F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.Jaw4, 0.23008793503710206F, -0.3476811722201176F, 0.09283586346797108F);
		this.Jaw5 = new RendererModel(this, 0, 44);
		this.Jaw5.setRotationPoint(0.0F, 14.5F, -5.0F);
		this.Jaw5.addBox(2.5F, 1.0F, 0.0F, 1, 2, 1, 0.0F);
		this.setRotateAngle(this.Jaw5, 0.23008793503710206F, 0.3476811722201176F, -0.09283586346797108F);
		this.HeadTop11 = new RendererModel(this, 50, 37);
		this.HeadTop11.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.HeadTop11.addBox(-0.5F, -5.0F, 2.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(this.HeadTop11, 0.40359461763480853F, 0.37470514616201034F, 0.5672073239515355F);
		this.Jaw8 = new RendererModel(this, 76, 26);
		this.Jaw8.setRotationPoint(0.0F, 14.5F, -4.0F);
		this.Jaw8.addBox(-4.0F, 1.5F, -8.0F, 8, 2, 8, 0.0F);
		this.setRotateAngle(this.Jaw8, 0.17453292012214658F, -0.0F, 0.0F);
		this.RightFrontLeg1 = new RendererModel(this, 18, 20);
		this.RightFrontLeg1.mirror = true;
		this.RightFrontLeg1.setRotationPoint(-4.0F, 16.0F, 4.0F);
		this.RightFrontLeg1.addBox(-0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.RightFrontLeg1, -0.17453292012214658F, -0.0F, 0.34906584024429316F);
		this.Body9 = new RendererModel(this, 40, 0);
		this.Body9.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.Body9.addBox(-4.5F, -2.5F, 0.0F, 9, 2, 6, 0.0F);
		this.setRotateAngle(this.Body9, 0.2617993950843811F, -0.0F, 0.0F);
		this.LeftArm1 = new RendererModel(this, 0, 24);
		this.LeftArm1.setRotationPoint(5.0F, 16.0F, 0.0F);
		this.LeftArm1.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.LeftArm1, -0.08726646006107329F, 0.17453292012214658F, 0.0F);
		this.Tail4 = new RendererModel(this, 92, 36);
		this.Tail4.setRotationPoint(0.0F, 15.0F, 11.0F);
		this.Tail4.addBox(-1.5F, 0.800000011920929F, 7.400000095367432F, 3, 3, 12, 0.0F);
		this.setRotateAngle(this.Tail4, 0.08726646006107329F, -0.0F, 0.0F);
	}

	@Override
	public void render(EntityTempest entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.Body1.render(f5);
		this.LeftFrontLeg2.render(f5);
		this.RightBackLeg1.render(f5);
		this.HeadTop6.render(f5);
		this.HeadTop4.render(f5);
		this.HeadTop10.render(f5);
		this.Body11.render(f5);
		this.HeadTop1.render(f5);
		this.Body16.render(f5);
		this.Jaw10.render(f5);
		this.Body6.render(f5);
		this.RightFrontLeg2.render(f5);
		this.LeftFrontLeg1.render(f5);
		this.Tail2.render(f5);
		this.LeftBackLeg1.render(f5);
		this.LeftBackLeg2.render(f5);
		this.Jaw3.render(f5);
		this.Body14.render(f5);
		this.Jaw9.render(f5);
		this.RightArm1.render(f5);
		this.Body17.render(f5);
		this.Tail3.render(f5);
		this.HeadTop12.render(f5);
		this.Jaw6.render(f5);
		this.Jaw2.render(f5);
		this.Body5.render(f5);
		this.HeadTop2.render(f5);
		this.RightBackLeg2.render(f5);
		this.Jaw12.render(f5);
		this.Jaw1.render(f5);
		this.Jaw7.render(f5);
		this.HeadTop3.render(f5);
		this.Body12.render(f5);
		this.HeadTop9.render(f5);
		this.Body4.render(f5);
		this.Body3.render(f5);
		this.RightArm3.render(f5);
		this.Body8.render(f5);
		this.LeftArm3.render(f5);
		this.LeftArm2.render(f5);
		this.HeadTop7.render(f5);
		this.Tail1.render(f5);
		this.Jaw11.render(f5);
		this.RightArm2.render(f5);
		this.Body10.render(f5);
		this.HeadTop8.render(f5);
		this.Body2.render(f5);
		this.Body7.render(f5);
		this.Body15.render(f5);
		this.Body13.render(f5);
		this.HeadTop5.render(f5);
		this.Jaw4.render(f5);
		this.Jaw5.render(f5);
		this.HeadTop11.render(f5);
		this.Jaw8.render(f5);
		this.RightFrontLeg1.render(f5);
		this.Body9.render(f5);
		this.LeftArm1.render(f5);
		this.Tail4.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	private void setRotateAngle(RendererModel modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
