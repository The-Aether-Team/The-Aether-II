package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * Zephyr.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelZephyr extends ModelBase
{
	public final ModelRenderer Head1;

	public final ModelRenderer Head2;

	public final ModelRenderer Head3;

	public final ModelRenderer Head4;

	public final ModelRenderer Head5;

	public final ModelRenderer Head6;

	public final ModelRenderer Jaw1;

	public final ModelRenderer Head11;

	public final ModelRenderer Head12;

	public final ModelRenderer Body1;

	public final ModelRenderer Body2;

	public final ModelRenderer Body3;

	public final ModelRenderer Body4;

	public final ModelRenderer Body5;

	public final ModelRenderer Body6;

	public final ModelRenderer Body7;

	public final ModelRenderer Body8;

	public final ModelRenderer FLLeg1;

	public final ModelRenderer MLLeg1;

	public final ModelRenderer BLLeg1;

	public final ModelRenderer FRLeg1;

	public final ModelRenderer MRLeg1;

	public final ModelRenderer BRLeg1;

	public final ModelRenderer Tail1;

	public final ModelRenderer HeadShell1;

	public final ModelRenderer HeadShell4;

	public final ModelRenderer HeadShell2;

	public final ModelRenderer HeadShell3;

	public final ModelRenderer HeadShell5;

	public final ModelRenderer HeadShell6;

	public final ModelRenderer Jaw2;

	public final ModelRenderer Jaw3;

	public final ModelRenderer Jaw4;

	public final ModelRenderer JawShell1;

	public final ModelRenderer JawShell2;

	public final ModelRenderer JawShell3;

	public final ModelRenderer JawShell4;

	public final ModelRenderer JawShell5;

	public final ModelRenderer BodyShell1;

	public final ModelRenderer Shell1;

	public final ModelRenderer Shell3;

	public final ModelRenderer Shell5;

	public final ModelRenderer Shell6;

	public final ModelRenderer BodyShell2;

	public final ModelRenderer BodyShell6;

	public final ModelRenderer BodyShell3;

	public final ModelRenderer BodyShell4;

	public final ModelRenderer BodyShell5;

	public final ModelRenderer BodyShell7;

	public final ModelRenderer BodyShell8;

	public final ModelRenderer BodyShell9;

	public final ModelRenderer Shell2;

	public final ModelRenderer Shell4;

	public final ModelRenderer BodyShell10;

	public final ModelRenderer BodyShell11;

	public final ModelRenderer BodyShell12;

	public final ModelRenderer BodyShell13;

	public final ModelRenderer FLLeg2;

	public final ModelRenderer FLLeg3;

	public final ModelRenderer MLLeg2;

	public final ModelRenderer MLLeg3;

	public final ModelRenderer BLLeg2;

	public final ModelRenderer BLLeg3;

	public final ModelRenderer FRLeg2;

	public final ModelRenderer FRLeg3;

	public final ModelRenderer MRLeg2;

	public final ModelRenderer MRLeg3;

	public final ModelRenderer BRLeg2;

	public final ModelRenderer BRLeg3;

	public final ModelRenderer Tail2;

	public final ModelRenderer TailShell1;

	public final ModelRenderer TailShell2;

	public ModelZephyr()
	{
		this.textureWidth = 124;
		this.textureHeight = 64;
		this.Head2 = new ModelRenderer(this, 0, 19);
//		this.Head2.setRotationPoint(0.0F, 20.0F, -3.0F);
//		this.Head2.addBox(-0.5F, 0.4000000059604645F, -2.799999952316284F, 1, 1, 1, 0.0F);
		this.Head2.addBox(0, 0, 0, 1, 1, 1, 0.0F);
//		this.setRotateAngle(this.Head2, 0.2617993950843811F, -0.0F, 0.0F);
		this.Head11 = new ModelRenderer(this, 0, 14);
		this.Head11.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head11.addBox(-1.5F, -0.5F, 0.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(this.Head11, 0.2974288761615753F, -0.0F, 0.0F);
		this.BodyShell13 = new ModelRenderer(this, 101, 4);
		this.BodyShell13.mirror = true;
		this.BodyShell13.setRotationPoint(0.5F, 0.5F, 6.0F);
		this.BodyShell13.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 15, 0.0F);
		this.setRotateAngle(this.BodyShell13, -0.17453292519943295F, 0.08726646259971647F, 0.0F);
		this.Head3 = new ModelRenderer(this, 0, 12);
		this.Head3.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head3.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(this.Head3, 0.7853981852531433F, -0.7853981852531433F, 0.0F);
		this.Head4 = new ModelRenderer(this, 0, 12);
		this.Head4.mirror = true;
		this.Head4.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head4.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(this.Head4, 0.7853981852531433F, 0.7853981852531433F, 0.0F);
		this.Body1 = new ModelRenderer(this, 0, 0);
		this.Body1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body1.addBox(-2.0F, 0.5F, 1.0F, 4, 3, 5, 0.0F);
		this.Tail1 = new ModelRenderer(this, 18, 11);
		this.Tail1.setRotationPoint(0.0F, 22.0F, 4.5F);
		this.Tail1.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
		this.setRotateAngle(this.Tail1, -0.08726646006107329F, -0.0F, 0.0F);
		this.Body7 = new ModelRenderer(this, 118, 48);
		this.Body7.mirror = true;
		this.Body7.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body7.addBox(1.0F, 0.0F, 4.0F, 0, 3, 6, 0.0F);
		this.setRotateAngle(this.Body7, 0.08063421144213803F, 0.33667401270970615F, -0.2778564169174973F);
		this.Head6 = new ModelRenderer(this, 10, 10);
		this.Head6.mirror = true;
		this.Head6.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head6.addBox(-2.0F, -2.0F, -1.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(this.Head6, 0.2617993950843811F, -0.0F, -0.6981316804885863F);
		this.MRLeg3 = new ModelRenderer(this, 6, 10);
		this.MRLeg3.mirror = true;
		this.MRLeg3.setRotationPoint(-1.1F, 2.2F, 0.0F);
		this.MRLeg3.addBox(-1.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.MRLeg3, 0.0F, -0.0F, -0.2617993877991494F);
		this.TailShell2 = new ModelRenderer(this, 32, 44);
		this.TailShell2.setRotationPoint(0.0F, 1.5F, 6.0F);
		this.TailShell2.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 12, 0.0F);
		this.setRotateAngle(this.TailShell2, 0.17453292519943295F, 0.0F, 0.0F);
		this.Jaw2 = new ModelRenderer(this, 0, 22);
		this.Jaw2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Jaw2.addBox(-1.5F, 0.6F, -2.6F, 3, 1, 0, 0.0F);
		this.setRotateAngle(this.Jaw2, 0.08726646259971647F, -0.0F, 0.0F);
		this.BodyShell10 = new ModelRenderer(this, 52, 0);
		this.BodyShell10.setRotationPoint(1.5F, -1.3F, 7.0F);
		this.BodyShell10.addBox(-2.9F, 0.0F, 0.0F, 5, 2, 7, 0.0F);
		this.setRotateAngle(this.BodyShell10, -0.17453292519943295F, 0.0F, 0.0F);
		this.Head5 = new ModelRenderer(this, 10, 10);
		this.Head5.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head5.addBox(0.0F, -2.0F, -1.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(this.Head5, 0.2617993950843811F, -0.0F, 0.6981316804885863F);
		this.Jaw3 = new ModelRenderer(this, 0, 21);
		this.Jaw3.setRotationPoint(0.0F, -1.4F, -0.1F);
		this.Jaw3.addBox(0.8F, 0.5F, -1.5F, 0, 3, 3, 0.0F);
		this.setRotateAngle(this.Jaw3, -0.0F, 0.08726646259971647F, -0.17453292519943295F);
		this.Jaw4 = new ModelRenderer(this, 0, 21);
		this.Jaw4.mirror = true;
		this.Jaw4.setRotationPoint(0.0F, -1.4F, -0.1F);
		this.Jaw4.addBox(-0.8F, 0.5F, -1.5F, 0, 3, 3, 0.0F);
		this.setRotateAngle(this.Jaw4, 0.0F, -0.08726646259971647F, 0.17453292519943295F);
		this.BLLeg2 = new ModelRenderer(this, 6, 8);
		this.BLLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BLLeg2.addBox(0.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.BLLeg2, 0.017453292519943295F, -0.2617993877991494F, 0.7853981633974483F);
		this.JawShell4 = new ModelRenderer(this, 19, 31);
		this.JawShell4.setRotationPoint(2.5F, 0.0F, 0.0F);
		this.JawShell4.addBox(0.0F, -3.0F, -4.0F, 0, 3, 4, 0.0F);
		this.setRotateAngle(this.JawShell4, 0.0F, 0.17453292519943295F, -0.2617993877991494F);
		this.Shell5 = new ModelRenderer(this, 0, 52);
		this.Shell5.mirror = true;
		this.Shell5.setRotationPoint(-2.4F, 0.2F, 3.0F);
		this.Shell5.addBox(-3.0F, -2.0F, -1.0F, 5, 3, 6, 0.0F);
		this.setRotateAngle(this.Shell5, 0.2617993877991494F, -0.3490658503988659F, -0.3490658503988659F);
		this.HeadShell6 = new ModelRenderer(this, 108, 41);
		this.HeadShell6.mirror = true;
		this.HeadShell6.setRotationPoint(0.0F, -2.5F, 7.0F);
		this.HeadShell6.addBox(-3.0F, 0.0F, 0.0F, 3, 0, 10, 0.0F);
		this.setRotateAngle(this.HeadShell6, -0.08726646259971647F, -0.2617993877991494F, 0.0F);
		this.Body6 = new ModelRenderer(this, 32, 0);
		this.Body6.mirror = true;
		this.Body6.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body6.addBox(-3.5F, -0.5F, 3.0F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Body6, 0.38034173732879695F, -0.2839675431162167F, -0.6403868480256188F);
		this.BodyShell2 = new ModelRenderer(this, 36, 35);
		this.BodyShell2.setRotationPoint(1.0F, -0.9F, 7.0F);
		this.BodyShell2.addBox(-1.0F, -1.0F, -1.0F, 3, 4, 5, 0.0F);
		this.setRotateAngle(this.BodyShell2, -0.2617993877991494F, 0.2617993877991494F, 0.0F);
		this.BRLeg1 = new ModelRenderer(this, 0, 8);
		this.BRLeg1.mirror = true;
		this.BRLeg1.setRotationPoint(-1.6F, 21.6F, 3.0F);
		this.BRLeg1.addBox(-1.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.BRLeg1, 0.06684611035138283F, 0.056025068989017976F, 0.08726646259971647F);
		this.BodyShell3 = new ModelRenderer(this, 94, 36);
		this.BodyShell3.setRotationPoint(1.5F, 0.0F, 3.0F);
		this.BodyShell3.addBox(0.0F, 0.0F, 0.0F, 0, 3, 15, 0.0F);
		this.JawShell2 = new ModelRenderer(this, 22, 35);
		this.JawShell2.mirror = true;
		this.JawShell2.setRotationPoint(2.0F, 0.0F, -4.0F);
		this.JawShell2.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.JawShell2, 0.17453292519943295F, 0.4363323129985824F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 18, 19);
		this.Tail2.setRotationPoint(0.0F, 1.0F, 6.0F);
		this.Tail2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 6, 0.0F);
		this.setRotateAngle(this.Tail2, 0.17453292519943295F, -0.0F, 0.0F);
		this.Shell1 = new ModelRenderer(this, 42, 27);
		this.Shell1.setRotationPoint(3.0F, 3.0F, 2.0F);
		this.Shell1.addBox(-0.5F, -1.0F, -1.0F, 2, 3, 5, 0.0F);
		this.setRotateAngle(this.Shell1, -0.17453292519943295F, 0.17453292519943295F, -0.3490658503988659F);
		this.MRLeg1 = new ModelRenderer(this, 0, 8);
		this.MRLeg1.mirror = true;
		this.MRLeg1.setRotationPoint(-1.6F, 21.6F, 1.0F);
		this.MRLeg1.addBox(-1.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.MRLeg1, 0.0F, -0.0F, 0.08726646259971647F);
		this.Body8 = new ModelRenderer(this, 112, 48);
		this.Body8.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body8.addBox(-1.0F, 0.0F, 4.0F, 0, 3, 6, 0.0F);
		this.setRotateAngle(this.Body8, 0.08063421144213803F, -0.33667401270970615F, 0.2778564169174973F);
		this.JawShell1 = new ModelRenderer(this, 22, 27);
		this.JawShell1.setRotationPoint(0.0F, 1.0F, 0.9F);
		this.JawShell1.addBox(-2.0F, 0.0F, -5.0F, 4, 2, 6, 0.0F);
		this.setRotateAngle(this.JawShell1, -0.08726646259971647F, 0.0F, 0.0F);
		this.Jaw1 = new ModelRenderer(this, 3, 19);
		this.Jaw1.setRotationPoint(0.0F, 21.2F, -2.5F);
		this.Jaw1.addBox(-1.5F, 1.3F, -3.0F, 3, 1, 4, 0.0F);
		this.setRotateAngle(this.Jaw1, 0.08726646259971647F, -0.0F, 0.0F);
		this.Body4 = new ModelRenderer(this, 18, 7);
		this.Body4.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body4.addBox(-1.5F, -1.0F, 6.0F, 3, 2, 2, 0.0F);
		this.setRotateAngle(this.Body4, -0.2617993950843811F, -0.0F, 0.0F);
		this.FLLeg1 = new ModelRenderer(this, 0, 8);
		this.FLLeg1.setRotationPoint(1.6F, 21.6F, -1.0F);
		this.FLLeg1.addBox(0.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.FLLeg1, -0.06981317007977318F, 0.05235987755982988F, 0.08726646259971647F);
		this.BodyShell9 = new ModelRenderer(this, 108, 19);
		this.BodyShell9.mirror = true;
		this.BodyShell9.setRotationPoint(0.0F, 0.0F, 12.0F);
		this.BodyShell9.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 10, 0.0F);
		this.setRotateAngle(this.BodyShell9, -0.17453292519943295F, 0.0F, 0.0F);
		this.BLLeg1 = new ModelRenderer(this, 0, 8);
		this.BLLeg1.setRotationPoint(1.6F, 21.6F, 3.0F);
		this.BLLeg1.addBox(0.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.BLLeg1, 0.06981317007977318F, -0.05235987755982988F, 0.08726646259971647F);
		this.BodyShell5 = new ModelRenderer(this, 108, 19);
		this.BodyShell5.setRotationPoint(0.0F, 0.0F, 12.0F);
		this.BodyShell5.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 10, 0.0F);
		this.setRotateAngle(this.BodyShell5, -0.17453292519943295F, 0.0F, 0.0F);
		this.MLLeg3 = new ModelRenderer(this, 6, 10);
		this.MLLeg3.setRotationPoint(1.1F, 2.2F, 0.0F);
		this.MLLeg3.addBox(0.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.MLLeg3, 0.0F, -0.0F, 0.2617993877991494F);
		this.Body5 = new ModelRenderer(this, 32, 0);
		this.Body5.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body5.addBox(0.5F, -0.5F, 3.0F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Body5, 0.38034173732879695F, 0.2839675431162167F, 0.6403868480256188F);
		this.Shell4 = new ModelRenderer(this, 94, -15);
		this.Shell4.mirror = true;
		this.Shell4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Shell4.addBox(0.0F, 0.0F, 0.0F, 0, 4, 15, 0.0F);
		this.MLLeg1 = new ModelRenderer(this, 0, 8);
		this.MLLeg1.setRotationPoint(1.6F, 21.6F, 1.0F);
		this.MLLeg1.addBox(0.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.MLLeg1, 0.0F, -0.0F, 0.08726646259971647F);
		this.BodyShell6 = new ModelRenderer(this, 36, 35);
		this.BodyShell6.mirror = true;
		this.BodyShell6.setRotationPoint(-1.0F, -0.9F, 7.0F);
		this.BodyShell6.addBox(-2.0F, -1.0F, -1.0F, 3, 4, 5, 0.0F);
		this.setRotateAngle(this.BodyShell6, -0.2617993877991494F, -0.2617993877991494F, 0.0F);
		this.HeadShell2 = new ModelRenderer(this, 0, 33);
		this.HeadShell2.setRotationPoint(2.5F, 1.0F, -3.6F);
		this.HeadShell2.addBox(-3.0F, -2.0F, 0.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(this.HeadShell2, 0.08726646259971647F, 0.2617993877991494F, 0.2617993877991494F);
		this.BRLeg2 = new ModelRenderer(this, 6, 8);
		this.BRLeg2.mirror = true;
		this.BRLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BRLeg2.addBox(-3.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.BRLeg2, 0.017453292519943295F, 0.2617993877991494F, -0.7853981633974483F);
		this.Body3 = new ModelRenderer(this, 18, 0);
		this.Body3.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body3.addBox(0.5F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Body3, -0.06722259481424472F, -0.3930658138789867F, -0.4751909408299354F);
		this.MLLeg2 = new ModelRenderer(this, 6, 8);
		this.MLLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.MLLeg2.addBox(0.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.MLLeg2, 0.0F, -0.0F, 0.7853981633974483F);
		this.FLLeg3 = new ModelRenderer(this, 6, 10);
		this.FLLeg3.setRotationPoint(1.1F, 2.2F, -0.7F);
		this.FLLeg3.addBox(0.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.FLLeg3, -0.12217304763960307F, 0.20943951023931953F, 0.2617993877991494F);
		this.FRLeg1 = new ModelRenderer(this, 0, 8);
		this.FRLeg1.mirror = true;
		this.FRLeg1.setRotationPoint(-1.6F, 21.6F, -1.0F);
		this.FRLeg1.addBox(-1.2F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(this.FRLeg1, -0.06684611035138283F, -0.056025068989017976F, 0.08726646259971647F);
		this.BodyShell11 = new ModelRenderer(this, 101, 4);
		this.BodyShell11.setRotationPoint(-0.5F, 0.5F, 6.0F);
		this.BodyShell11.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 15, 0.0F);
		this.setRotateAngle(this.BodyShell11, -0.17453292519943295F, -0.08726646259971647F, 0.0F);
		this.Shell3 = new ModelRenderer(this, 42, 27);
		this.Shell3.mirror = true;
		this.Shell3.setRotationPoint(-3.0F, 3.0F, 2.0F);
		this.Shell3.addBox(-1.5F, -1.0F, -1.0F, 2, 3, 5, 0.0F);
		this.setRotateAngle(this.Shell3, -0.17453292519943295F, -0.17453292519943295F, 0.3490658503988659F);
		this.Body2 = new ModelRenderer(this, 18, 0);
		this.Body2.mirror = true;
		this.Body2.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Body2.addBox(-2.5F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.Body2, -0.06722259481424472F, 0.3930658138789867F, 0.4751909408299354F);
		this.HeadShell5 = new ModelRenderer(this, 108, 41);
		this.HeadShell5.setRotationPoint(0.0F, -2.5F, 7.0F);
		this.HeadShell5.addBox(0.0F, 0.0F, 0.0F, 3, 0, 10, 0.0F);
		this.setRotateAngle(this.HeadShell5, -0.08726646259971647F, 0.2617993877991494F, 0.0F);
		this.FRLeg2 = new ModelRenderer(this, 6, 8);
		this.FRLeg2.mirror = true;
		this.FRLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FRLeg2.addBox(-3.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.FRLeg2, -0.017453292519943295F, -0.2617993877991494F, -0.7853981633974483F);
		this.JawShell3 = new ModelRenderer(this, 22, 35);
		this.JawShell3.setRotationPoint(-2.0F, 0.0F, -4.0F);
		this.JawShell3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(this.JawShell3, 0.17453292519943295F, -0.4363323129985824F, 0.0F);
		this.Head12 = new ModelRenderer(this, 32, 8);
		this.Head12.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head12.addBox(-1.5F, -1.0F, -0.5F, 3, 1, 7, 0.0F);
		this.setRotateAngle(this.Head12, 0.2094395160675048F, -0.0F, 0.0F);
		this.Shell2 = new ModelRenderer(this, 94, -15);
		this.Shell2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Shell2.addBox(0.0F, 0.0F, 0.0F, 0, 4, 15, 0.0F);
		this.Head1 = new ModelRenderer(this, 8, 14);
		this.Head1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.Head1.addBox(-1.0F, -1.5F, -3.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(this.Head1, 0.43633231520652765F, -0.0F, 0.0F);
		this.HeadShell1 = new ModelRenderer(this, 0, 24);
		this.HeadShell1.setRotationPoint(0.0F, -0.5F, 0.0F);
		this.HeadShell1.addBox(-2.5F, -2.0F, -4.0F, 5, 3, 6, 0.0F);
		this.BodyShell8 = new ModelRenderer(this, 106, 29);
		this.BodyShell8.mirror = true;
		this.BodyShell8.setRotationPoint(-0.5F, 0.0F, 4.0F);
		this.BodyShell8.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 12, 0.0F);
		this.setRotateAngle(this.BodyShell8, 0.2617993877991494F, 0.0F, 0.0F);
		this.HeadShell3 = new ModelRenderer(this, 0, 33);
		this.HeadShell3.mirror = true;
		this.HeadShell3.setRotationPoint(-2.5F, 1.0F, -3.6F);
		this.HeadShell3.addBox(0.0F, -2.0F, 0.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(this.HeadShell3, 0.08726646259971647F, -0.2617993877991494F, -0.2617993877991494F);
		this.Shell6 = new ModelRenderer(this, 0, 52);
		this.Shell6.setRotationPoint(2.4F, 0.2F, 3.0F);
		this.Shell6.addBox(-2.0F, -2.0F, -1.0F, 5, 3, 6, 0.0F);
		this.setRotateAngle(this.Shell6, 0.2617993877991494F, 0.3490658503988659F, 0.3490658503988659F);
		this.HeadShell4 = new ModelRenderer(this, 0, 40);
		this.HeadShell4.setRotationPoint(0.0F, 0.8F, 1.0F);
		this.HeadShell4.addBox(-3.0F, -3.0F, 0.0F, 6, 4, 8, 0.0F);
		this.setRotateAngle(this.HeadShell4, -0.2792526803190927F, 0.0F, 0.0F);
		this.BodyShell1 = new ModelRenderer(this, 32, 16);
		this.BodyShell1.setRotationPoint(0.0F, 1.5F, 0.3F);
		this.BodyShell1.addBox(-3.0F, -1.9F, 0.0F, 6, 4, 7, 0.0F);
		this.MRLeg2 = new ModelRenderer(this, 6, 8);
		this.MRLeg2.mirror = true;
		this.MRLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.MRLeg2.addBox(-3.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.MRLeg2, 0.0F, -0.0F, -0.7853981633974483F);
		this.BodyShell4 = new ModelRenderer(this, 106, 29);
		this.BodyShell4.setRotationPoint(0.5F, -0.5F, 4.0F);
		this.BodyShell4.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 12, 0.0F);
		this.setRotateAngle(this.BodyShell4, 0.2617993877991494F, 0.0F, 0.0F);
		this.FLLeg2 = new ModelRenderer(this, 6, 8);
		this.FLLeg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FLLeg2.addBox(0.0F, 0.3F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.FLLeg2, -0.017453292519943295F, 0.2617993877991494F, 0.7853981633974483F);
		this.BodyShell12 = new ModelRenderer(this, 52, 0);
		this.BodyShell12.mirror = true;
		this.BodyShell12.setRotationPoint(-1.5F, -1.3F, 7.0F);
		this.BodyShell12.addBox(-2.1F, 0.0F, 0.0F, 5, 2, 7, 0.0F);
		this.setRotateAngle(this.BodyShell12, -0.17453292519943295F, 0.0F, 0.0F);
		this.BLLeg3 = new ModelRenderer(this, 6, 10);
		this.BLLeg3.setRotationPoint(1.1F, 2.2F, 0.7F);
		this.BLLeg3.addBox(0.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.BLLeg3, 0.12217304763960307F, -0.20943951023931953F, 0.2617993877991494F);
		this.TailShell1 = new ModelRenderer(this, 28, 44);
		this.TailShell1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.TailShell1.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 6, 0.0F);
		this.BRLeg3 = new ModelRenderer(this, 6, 10);
		this.BRLeg3.mirror = true;
		this.BRLeg3.setRotationPoint(-1.1F, 2.2F, 0.7F);
		this.BRLeg3.addBox(-1.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.BRLeg3, 0.12217304763960307F, 0.20943951023931953F, -0.2617993877991494F);
		this.BodyShell7 = new ModelRenderer(this, 94, 36);
		this.BodyShell7.mirror = true;
		this.BodyShell7.setRotationPoint(-1.5F, 0.0F, 3.0F);
		this.BodyShell7.addBox(0.0F, 0.0F, 0.0F, 0, 3, 15, 0.0F);
		this.JawShell5 = new ModelRenderer(this, 19, 31);
		this.JawShell5.mirror = true;
		this.JawShell5.setRotationPoint(-2.5F, 0.0F, 0.0F);
		this.JawShell5.addBox(0.0F, -3.0F, -4.0F, 0, 3, 4, 0.0F);
		this.setRotateAngle(this.JawShell5, 0.0F, -0.17453292519943295F, 0.2617993877991494F);
		this.FRLeg3 = new ModelRenderer(this, 6, 10);
		this.FRLeg3.mirror = true;
		this.FRLeg3.setRotationPoint(-1.1F, 2.2F, -0.7F);
		this.FRLeg3.addBox(-1.0F, -0.4F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.FRLeg3, -0.12217304763960307F, -0.20943951023931953F, -0.2617993877991494F);
		this.BodyShell12.addChild(this.BodyShell13);
		this.MRLeg1.addChild(this.MRLeg3);
		this.TailShell1.addChild(this.TailShell2);
		this.Jaw1.addChild(this.Jaw2);
		this.Body5.addChild(this.BodyShell10);
		this.Jaw1.addChild(this.Jaw3);
		this.Jaw1.addChild(this.Jaw4);
		this.BLLeg1.addChild(this.BLLeg2);
		this.JawShell1.addChild(this.JawShell4);
		this.Body1.addChild(this.Shell5);
		this.HeadShell4.addChild(this.HeadShell6);
		this.BodyShell1.addChild(this.BodyShell2);
		this.BodyShell2.addChild(this.BodyShell3);
		this.JawShell1.addChild(this.JawShell2);
		this.Tail1.addChild(this.Tail2);
		this.Body1.addChild(this.Shell1);
		this.Jaw1.addChild(this.JawShell1);
		this.BodyShell8.addChild(this.BodyShell9);
		this.BodyShell4.addChild(this.BodyShell5);
		this.MLLeg1.addChild(this.MLLeg3);
		this.Shell3.addChild(this.Shell4);
		this.BodyShell1.addChild(this.BodyShell6);
		this.HeadShell1.addChild(this.HeadShell2);
		this.BRLeg1.addChild(this.BRLeg2);
		this.MLLeg1.addChild(this.MLLeg2);
		this.FLLeg1.addChild(this.FLLeg3);
		this.BodyShell10.addChild(this.BodyShell11);
		this.Body1.addChild(this.Shell3);
		this.HeadShell4.addChild(this.HeadShell5);
		this.FRLeg1.addChild(this.FRLeg2);
		this.JawShell1.addChild(this.JawShell3);
		this.Shell1.addChild(this.Shell2);
		this.Head1.addChild(this.HeadShell1);
		this.BodyShell6.addChild(this.BodyShell8);
		this.HeadShell1.addChild(this.HeadShell3);
		this.Body1.addChild(this.Shell6);
		this.Head1.addChild(this.HeadShell4);
		this.Body1.addChild(this.BodyShell1);
		this.MRLeg1.addChild(this.MRLeg2);
		this.BodyShell2.addChild(this.BodyShell4);
		this.FLLeg1.addChild(this.FLLeg2);
		this.Body6.addChild(this.BodyShell12);
		this.BLLeg1.addChild(this.BLLeg3);
		this.Tail1.addChild(this.TailShell1);
		this.BRLeg1.addChild(this.BRLeg3);
		this.BodyShell6.addChild(this.BodyShell7);
		this.JawShell1.addChild(this.JawShell5);
		this.FRLeg1.addChild(this.FRLeg3);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{

		GlStateManager.pushMatrix();

		this.Head2.render(f5);

		GlStateManager.translate(0.025f, -1.35f,0);

		this.Head11.render(f5);
		this.Head3.render(f5);
		this.Head4.render(f5);
		this.Body1.render(f5);
		this.Tail1.render(f5);
		this.Body7.render(f5);
		this.Head6.render(f5);
		this.Head5.render(f5);
		this.Body6.render(f5);
		this.BRLeg1.render(f5);
		this.MRLeg1.render(f5);
		this.Body8.render(f5);
		this.Jaw1.render(f5);
		this.Body4.render(f5);
		this.FLLeg1.render(f5);
		this.BLLeg1.render(f5);
		this.Body5.render(f5);
		this.MLLeg1.render(f5);
		this.Body3.render(f5);
		this.FRLeg1.render(f5);
		this.Body2.render(f5);
		this.Head12.render(f5);
		this.Head1.render(f5);

		GlStateManager.popMatrix();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
