package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelZephyr extends ModelBase
{
	private final ModelRenderer Head1;

	private final ModelRenderer Head2;

	private final ModelRenderer Head3;

	private final ModelRenderer Head4;

	private final ModelRenderer Head5;

	private final ModelRenderer Head6;

	private final ModelRenderer Head7;

	private final ModelRenderer Head8;

	private final ModelRenderer Head9;

	private final ModelRenderer Head10;

	private final ModelRenderer Head11;

	private final ModelRenderer Head12;

	private final ModelRenderer Body1;

	private final ModelRenderer Body2;

	private final ModelRenderer Body3;

	private final ModelRenderer Body4;

	private final ModelRenderer Body5;

	private final ModelRenderer Body6;

	private final ModelRenderer Body7;

	private final ModelRenderer Body8;

	private final ModelRenderer FLLeg1;

	private final ModelRenderer FLLeg2;

	private final ModelRenderer FLLeg3;

	private final ModelRenderer MLLeg1;

	private final ModelRenderer MLLeg2;

	private final ModelRenderer MLLeg3;

	private final ModelRenderer BLLeg1;

	private final ModelRenderer BLLeg2;

	private final ModelRenderer BLLeg3;

	private final ModelRenderer FRLeg1;

	private final ModelRenderer FRLeg2;

	private final ModelRenderer FRLeg3;

	private final ModelRenderer MRLeg1;

	private final ModelRenderer MRLeg2;

	private final ModelRenderer MRLeg3;

	private final ModelRenderer BRLeg1;

	private final ModelRenderer BRLeg2;

	private final ModelRenderer BRLeg3;

	private final ModelRenderer Tail1;

	private final ModelRenderer Tail2;

	private final ModelRenderer Shell1;

	private final ModelRenderer Shell2;

	private final ModelRenderer Shell3;

	private final ModelRenderer Shell4;

	private final ModelRenderer Shell5;

	private final ModelRenderer Shell6;

	private final ModelRenderer Shell7;

	private final ModelRenderer Shell8;

	private final ModelRenderer Shell9;

	private final ModelRenderer Shell10;

	private final ModelRenderer Shell11;

	private final ModelRenderer Shell12;

	private final ModelRenderer Shell13;

	private final ModelRenderer Shell14;

	private final ModelRenderer Shell15;

	private final ModelRenderer Shell16;

	private final ModelRenderer Shell17;

	private final ModelRenderer Shell18;

	private final ModelRenderer Shell19;

	private final ModelRenderer Shell20;

	private final ModelRenderer Shape59;

	private final ModelRenderer Shape60;

	public ModelZephyr()
	{
		this.textureWidth = 124;
		this.textureHeight = 64;
		this.Shell1 = new ModelRenderer(this, 32, 16);
		this.Shell1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell1.addBox(-5.5F, -3.0F, -6.0F, 11, 8, 14, 0.0F);
		this.FRLeg3 = new ModelRenderer(this, 6, 10);
		this.FRLeg3.mirror = true;
		this.FRLeg3.setRotationPoint(-1.0F, 21.0F, -1.0F);
		this.FRLeg3.addBox(-3.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.FRLeg3, -0.1331815482280095F, -0.22606446757040743F, 0.5387391349116264F);
		this.Head4 = new ModelRenderer(this, 0, 12);
		this.Head4.mirror = true;
		this.Head4.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head4.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(this.Head4, 0.7853981852531433F, 0.7853981852531433F, 0.0F);
		this.Body7 = new ModelRenderer(this, 118, 33);
		this.Body7.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body7.addBox(1.0F, 0.0F, 4.0F, 0, 3, 6, 0.0F);
		this.setRotateAngle(this.Body7, 0.08060767156031835F, 0.33669140556689653F, -0.27777379737156505F);
		this.MLLeg3 = new ModelRenderer(this, 6, 10);
		this.MLLeg3.setRotationPoint(1.0F, 21.0F, 1.0F);
		this.MLLeg3.addBox(2.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.MLLeg3, 0.0F, -0.0F, -0.5235987901687622F);
		this.Shell2 = new ModelRenderer(this, 68, 16);
		this.Shell2.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell2.addBox(-2.5F, 2.0F, -11.0F, 5, 3, 5, 0.0F);
		this.Shell11 = new ModelRenderer(this, 32, 49);
		this.Shell11.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell11.addBox(2.0F, 0.0F, -3.0F, 4, 6, 8, 0.0F);
		this.setRotateAngle(this.Shell11, -0.19495728290313993F, 0.4891166817886874F, -0.39786310576687983F);
		this.BLLeg3 = new ModelRenderer(this, 6, 10);
		this.BLLeg3.setRotationPoint(1.0F, 21.0F, 3.0F);
		this.BLLeg3.addBox(2.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.BLLeg3, 0.1331815482280095F, -0.22606446757040743F, -0.5387391349116264F);
		this.FLLeg3 = new ModelRenderer(this, 6, 10);
		this.FLLeg3.setRotationPoint(1.0F, 21.0F, -1.0F);
		this.FLLeg3.addBox(2.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.FLLeg3, -0.1331815482280095F, 0.22606446757040743F, -0.5387391349116264F);
		this.FLLeg1 = new ModelRenderer(this, 0, 8);
		this.FLLeg1.setRotationPoint(1.0F, 21.0F, -1.0F);
		this.FLLeg1.addBox(0.20000000298023224F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.FLLeg1, -0.066920127335292F, 0.056051975927624384F, -0.8745413241609143F);
		this.BRLeg2 = new ModelRenderer(this, 6, 8);
		this.BRLeg2.mirror = true;
		this.BRLeg2.setRotationPoint(-1.0F, 21.0F, 3.0F);
		this.BRLeg2.addBox(-3.200000047683716F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.BRLeg2, 0.02334906682266612F, 0.26077990661147094F, 0.09032845229959166F);
		this.Head3 = new ModelRenderer(this, 0, 12);
		this.Head3.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head3.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(this.Head3, 0.7853981852531433F, -0.7853981852531433F, 0.0F);
		this.Shell17 = new ModelRenderer(this, 80, 33);
		this.Shell17.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell17.addBox(-1.0F, 2.0F, 15.0F, 2, 3, 5, 0.0F);
		this.setRotateAngle(this.Shell17, 0.08726646006107329F, -0.0F, 0.0F);
		this.Shell16 = new ModelRenderer(this, 66, 49);
		this.Shell16.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell16.addBox(-1.5F, -2.0F, 11.0F, 3, 4, 5, 0.0F);
		this.setRotateAngle(this.Shell16, -0.17453292012214658F, -0.0F, 0.0F);
		this.Shape60 = new ModelRenderer(this, 116, 34);
		this.Shape60.mirror = true;
		this.Shape60.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shape60.addBox(-5.0F, 0.0F, 6.400000095367432F, 0, 6, 8, 0.0F);
		this.setRotateAngle(this.Shape60, 0.11275987657103122F, 0.23677591696847156F, 0.449758473028646F);
		this.Body3 = new ModelRenderer(this, 18, 0);
		this.Body3.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body3.addBox(0.5F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Body3, -0.06722259481424472F, -0.3930658138789867F, -0.4751909408299354F);
		this.Shell18 = new ModelRenderer(this, 105, 53);
		this.Shell18.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell18.addBox(-4.0F, -3.200000047683716F, -3.0F, 8, 0, 11, 0.0F);
		this.setRotateAngle(this.Shell18, 0.08726646006107329F, -0.0F, 0.0F);
		this.Body4 = new ModelRenderer(this, 18, 7);
		this.Body4.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body4.addBox(-1.5F, -1.0F, 6.0F, 3, 2, 2, 0.0F);
		this.setRotateAngle(this.Body4, -0.2617993950843811F, -0.0F, 0.0F);
		this.MRLeg2 = new ModelRenderer(this, 6, 8);
		this.MRLeg2.mirror = true;
		this.MRLeg2.setRotationPoint(-1.0F, 21.0F, 1.0F);
		this.MRLeg2.addBox(-3.200000047683716F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.MRLeg2, 0.0F, -0.0F, 0.08726646006107329F);
		this.BRLeg1 = new ModelRenderer(this, 0, 8);
		this.BRLeg1.mirror = true;
		this.BRLeg1.setRotationPoint(-1.0F, 21.0F, 3.0F);
		this.BRLeg1.addBox(-1.2000000476837158F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.BRLeg1, 0.066920127335292F, 0.056051975927624384F, 0.8745413241609143F);
		this.Shell5 = new ModelRenderer(this, 32, 16);
		this.Shell5.mirror = true;
		this.Shell5.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell5.addBox(5.5F, -0.8999999761581421F, -5.0F, 2, 3, 3, 0.0F);
		this.setRotateAngle(this.Shell5, 0.0F, 0.5235987901687622F, 0.0F);
		this.Shell20 = new ModelRenderer(this, 118, 42);
		this.Shell20.mirror = true;
		this.Shell20.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell20.addBox(-5.0F, 0.0F, 4.0F, 0, 5, 6, 0.0F);
		this.setRotateAngle(this.Shell20, -0.0631188088532655F, -0.34354184013562056F, 0.18548632869502668F);
		this.Head9 = new ModelRenderer(this, -3, 18);
		this.Head9.mirror = true;
		this.Head9.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head9.addBox(-0.800000011920929F, 0.5F, -1.5F, 0, 3, 3, 0.0F);
		this.setRotateAngle(this.Head9, 0.07207538260523014F, -0.08593738767230333F, 0.17518607543307713F);
		this.Shape59 = new ModelRenderer(this, 116, 34);
		this.Shape59.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shape59.addBox(5.0F, 0.0F, 6.400000095367432F, 0, 6, 8, 0.0F);
		this.setRotateAngle(this.Shape59, 0.11275987657103122F, -0.23677591696847156F, -0.449758473028646F);
		this.Body2 = new ModelRenderer(this, 18, 0);
		this.Body2.mirror = true;
		this.Body2.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body2.addBox(-2.5F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Body2, -0.06722259481424472F, 0.3930658138789867F, 0.4751909408299354F);
		this.Body8 = new ModelRenderer(this, 118, 33);
		this.Body8.mirror = true;
		this.Body8.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body8.addBox(-1.0F, 0.0F, 4.0F, 0, 3, 6, 0.0F);
		this.setRotateAngle(this.Body8, 0.08060767156031835F, -0.33669140556689653F, 0.27777379737156505F);
		this.Body1 = new ModelRenderer(this, 0, 0);
		this.Body1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body1.addBox(-2.0F, 0.5F, 1.0F, 4, 3, 5, 0.0F);
		this.FRLeg1 = new ModelRenderer(this, 0, 8);
		this.FRLeg1.mirror = true;
		this.FRLeg1.setRotationPoint(-1.0F, 21.0F, -1.0F);
		this.FRLeg1.addBox(-1.2000000476837158F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.FRLeg1, -0.066920127335292F, -0.056051975927624384F, 0.8745413241609143F);
		this.MRLeg1 = new ModelRenderer(this, 0, 8);
		this.MRLeg1.mirror = true;
		this.MRLeg1.setRotationPoint(-1.0F, 21.0F, 1.0F);
		this.MRLeg1.addBox(-1.2000000476837158F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.MRLeg1, 0.0F, -0.0F, 0.8726646304130553F);
		this.Shell6 = new ModelRenderer(this, 32, 16);
		this.Shell6.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell6.addBox(-7.5F, -0.8999999761581421F, -5.0F, 2, 3, 3, 0.0F);
		this.setRotateAngle(this.Shell6, 0.0F, -0.5235987901687622F, 0.0F);
		this.Head7 = new ModelRenderer(this, 3, 19);
		this.Head7.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head7.addBox(-1.5F, 3.0F, -3.0F, 3, 1, 4, 0.0F);
		this.setRotateAngle(this.Head7, 0.08726646006107329F, -0.0F, 0.0F);
		this.Shell7 = new ModelRenderer(this, 82, 25);
		this.Shell7.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell7.addBox(-3.5F, -3.0F, -11.0F, 7, 3, 5, 0.0F);
		this.Head11 = new ModelRenderer(this, 0, 14);
		this.Head11.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head11.addBox(-1.5F, -0.5F, 0.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(this.Head11, 0.2974288761615753F, -0.0F, 0.0F);
		this.Tail1 = new ModelRenderer(this, 18, 11);
		this.Tail1.setRotationPoint(0.0F, 22.0F, 4.5F);
		this.Tail1.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
		this.setRotateAngle(this.Tail1, -0.08726646006107329F, -0.0F, 0.0F);
		this.Shell10 = new ModelRenderer(this, 32, 38);
		this.Shell10.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell10.addBox(-2.5F, -5.5F, -9.5F, 5, 3, 8, 0.0F);
		this.setRotateAngle(this.Shell10, 0.2617993950843811F, -0.0F, 0.0F);
		this.MLLeg1 = new ModelRenderer(this, 0, 8);
		this.MLLeg1.setRotationPoint(1.0F, 21.0F, 1.0F);
		this.MLLeg1.addBox(0.20000000298023224F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.MLLeg1, 0.0F, -0.0F, -0.8726646304130553F);
		this.Shell8 = new ModelRenderer(this, 103, 20);
		this.Shell8.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell8.addBox(5.0F, -2.0F, -9.0F, 3, 3, 5, 0.0F);
		this.setRotateAngle(this.Shell8, -0.006658788500754937F, 0.33669140556689653F, -0.27777379737156505F);
		this.FLLeg2 = new ModelRenderer(this, 6, 8);
		this.FLLeg2.setRotationPoint(1.0F, 21.0F, -1.0F);
		this.FLLeg2.addBox(0.20000000298023224F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.FLLeg2, -0.02334906682266612F, 0.26077990661147094F, -0.09032845229959166F);
		this.MLLeg2 = new ModelRenderer(this, 6, 8);
		this.MLLeg2.setRotationPoint(1.0F, 21.0F, 1.0F);
		this.MLLeg2.addBox(0.20000000298023224F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.MLLeg2, 0.0F, -0.0F, -0.08726646006107329F);
		this.Head8 = new ModelRenderer(this, -3, 24);
		this.Head8.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head8.addBox(-1.5F, 2.5F, -2.5F, 3, 1, 0, 0.0F);
		this.setRotateAngle(this.Head8, 0.08726646006107329F, -0.0F, 0.0F);
		this.Head10 = new ModelRenderer(this, -3, 18);
		this.Head10.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head10.addBox(0.800000011920929F, 0.5F, -1.5F, 0, 3, 3, 0.0F);
		this.setRotateAngle(this.Head10, 0.07207538260523014F, 0.08593738767230333F, -0.17518607543307713F);
		this.Shell9 = new ModelRenderer(this, 103, 20);
		this.Shell9.mirror = true;
		this.Shell9.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell9.addBox(-8.0F, -2.0F, -9.0F, 3, 3, 5, 0.0F);
		this.setRotateAngle(this.Shell9, -0.006658788500754937F, -0.33669140556689653F, 0.27777379737156505F);
		this.Body6 = new ModelRenderer(this, 32, 0);
		this.Body6.mirror = true;
		this.Body6.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body6.addBox(-3.5F, -0.5F, 3.0F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Body6, 0.38034173732879695F, -0.2839675431162167F, -0.6403868480256188F);
		this.Shell14 = new ModelRenderer(this, 58, 38);
		this.Shell14.mirror = true;
		this.Shell14.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell14.addBox(-7.099999904632568F, -5.0F, 3.5F, 5, 5, 6, 0.0F);
		this.setRotateAngle(this.Shell14, -0.49086109259704297F, 0.37470514616201034F, -0.5672073239515355F);
		this.Shell12 = new ModelRenderer(this, 32, 49);
		this.Shell12.mirror = true;
		this.Shell12.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell12.addBox(-6.0F, 0.0F, -3.0F, 4, 6, 8, 0.0F);
		this.setRotateAngle(this.Shell12, -0.19495728290313993F, -0.4891166817886874F, 0.39786310576687983F);
		this.Body5 = new ModelRenderer(this, 32, 0);
		this.Body5.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body5.addBox(0.5F, -0.5F, 3.0F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Body5, 0.38034173732879695F, 0.2839675431162167F, 0.6403868480256188F);
		this.BLLeg1 = new ModelRenderer(this, 0, 8);
		this.BLLeg1.setRotationPoint(1.0F, 21.0F, 3.0F);
		this.BLLeg1.addBox(0.20000000298023224F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.BLLeg1, 0.066920127335292F, -0.056051975927624384F, -0.8745413241609143F);
		this.MRLeg3 = new ModelRenderer(this, 6, 10);
		this.MRLeg3.mirror = true;
		this.MRLeg3.setRotationPoint(-1.0F, 21.0F, 1.0F);
		this.MRLeg3.addBox(-3.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.MRLeg3, 0.0F, -0.0F, 0.5235987901687622F);
		this.Shell15 = new ModelRenderer(this, 48, 49);
		this.Shell15.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell15.addBox(-2.0F, -5.0F, 6.0F, 4, 2, 5, 0.0F);
		this.setRotateAngle(this.Shell15, -0.34906584024429316F, -0.0F, 0.0F);
		this.Shell3 = new ModelRenderer(this, 88, 16);
		this.Shell3.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell3.addBox(4.0F, 2.0999999046325684F, -8.0F, 4, 3, 6, 0.0F);
		this.setRotateAngle(this.Shell3, 0.0F, 0.5235987901687622F, 0.0F);
		this.FRLeg2 = new ModelRenderer(this, 6, 8);
		this.FRLeg2.mirror = true;
		this.FRLeg2.setRotationPoint(-1.0F, 21.0F, -1.0F);
		this.FRLeg2.addBox(-3.200000047683716F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.FRLeg2, -0.02334906682266612F, -0.26077990661147094F, 0.09032845229959166F);
		this.BLLeg2 = new ModelRenderer(this, 6, 8);
		this.BLLeg2.setRotationPoint(1.0F, 21.0F, 3.0F);
		this.BLLeg2.addBox(0.20000000298023224F, 1.0F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.BLLeg2, 0.02334906682266612F, -0.26077990661147094F, -0.09032845229959166F);
		this.BRLeg3 = new ModelRenderer(this, 6, 10);
		this.BRLeg3.mirror = true;
		this.BRLeg3.setRotationPoint(-1.0F, 21.0F, 3.0F);
		this.BRLeg3.addBox(-3.0F, 2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.BRLeg3, 0.1331815482280095F, 0.22606446757040743F, 0.5387391349116264F);
		this.Head5 = new ModelRenderer(this, 10, 10);
		this.Head5.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head5.addBox(0.0F, -2.0F, -1.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(this.Head5, 0.2617993950843811F, -0.0F, 0.6981316804885863F);
		this.Shell19 = new ModelRenderer(this, 118, 42);
		this.Shell19.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell19.addBox(5.0F, 0.0F, 4.0F, 0, 5, 6, 0.0F);
		this.setRotateAngle(this.Shell19, -0.0631188088532655F, 0.34354184013562056F, -0.18548632869502668F);
		this.Head1 = new ModelRenderer(this, 8, 14);
		this.Head1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head1.addBox(-1.0F, -1.5F, -3.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(this.Head1, 0.43633231520652765F, -0.0F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 18, 19);
		this.Tail2.setRotationPoint(0.0F, 22.0F, 4.5F);
		this.Tail2.addBox(-0.5F, 0.0F, 5.5F, 1, 2, 6, 0.0F);
		this.setRotateAngle(this.Tail2, 0.08726646006107329F, -0.0F, 0.0F);
		this.Head12 = new ModelRenderer(this, 32, 8);
		this.Head12.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head12.addBox(-1.5F, -1.0F, -0.5F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Head12, 0.2094395160675048F, -0.0F, 0.0F);
		this.Head2 = new ModelRenderer(this, 0, 19);
		this.Head2.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head2.addBox(-0.5F, 0.4000000059604645F, -2.799999952316284F, 1, 1, 1, 0.0F);
		this.setRotateAngle(this.Head2, 0.2617993950843811F, -0.0F, 0.0F);
		this.Head6 = new ModelRenderer(this, 10, 10);
		this.Head6.mirror = true;
		this.Head6.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head6.addBox(-2.0F, -2.0F, -1.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(this.Head6, 0.2617993950843811F, -0.0F, -0.6981316804885863F);
		this.Shell4 = new ModelRenderer(this, 88, 16);
		this.Shell4.mirror = true;
		this.Shell4.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell4.addBox(-8.0F, 2.0999999046325684F, -8.0F, 4, 3, 6, 0.0F);
		this.setRotateAngle(this.Shell4, 0.0F, -0.5235987901687622F, 0.0F);
		this.Shell13 = new ModelRenderer(this, 58, 38);
		this.Shell13.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Shell13.addBox(2.0999999046325684F, -5.0F, 3.5F, 5, 5, 6, 0.0F);
		this.setRotateAngle(this.Shell13, -0.49086109259704297F, -0.37470514616201034F, 0.5672073239515355F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.Shell1.render(f5);
		this.FRLeg3.render(f5);
		this.Head4.render(f5);
		this.Body7.render(f5);
		this.MLLeg3.render(f5);
		this.Shell2.render(f5);
		this.Shell11.render(f5);
		this.BLLeg3.render(f5);
		this.FLLeg3.render(f5);
		this.FLLeg1.render(f5);
		this.BRLeg2.render(f5);
		this.Head3.render(f5);
		this.Shell17.render(f5);
		this.Shell16.render(f5);
		this.Shape60.render(f5);
		this.Body3.render(f5);
		this.Shell18.render(f5);
		this.Body4.render(f5);
		this.MRLeg2.render(f5);
		this.BRLeg1.render(f5);
		this.Shell5.render(f5);
		this.Shell20.render(f5);
		this.Head9.render(f5);
		this.Shape59.render(f5);
		this.Body2.render(f5);
		this.Body8.render(f5);
		this.Body1.render(f5);
		this.FRLeg1.render(f5);
		this.MRLeg1.render(f5);
		this.Shell6.render(f5);
		this.Head7.render(f5);
		this.Shell7.render(f5);
		this.Head11.render(f5);
		this.Tail1.render(f5);
		this.Shell10.render(f5);
		this.MLLeg1.render(f5);
		this.Shell8.render(f5);
		this.FLLeg2.render(f5);
		this.MLLeg2.render(f5);
		this.Head8.render(f5);
		this.Head10.render(f5);
		this.Shell9.render(f5);
		this.Body6.render(f5);
		this.Shell14.render(f5);
		this.Shell12.render(f5);
		this.Body5.render(f5);
		this.BLLeg1.render(f5);
		this.MRLeg3.render(f5);
		this.Shell15.render(f5);
		this.Shell3.render(f5);
		this.FRLeg2.render(f5);
		this.BLLeg2.render(f5);
		this.BRLeg3.render(f5);
		this.Head5.render(f5);
		this.Shell19.render(f5);
		this.Head1.render(f5);
		this.Tail2.render(f5);
		this.Head12.render(f5);
		this.Head2.render(f5);
		this.Head6.render(f5);
		this.Shell4.render(f5);
		this.Shell13.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
