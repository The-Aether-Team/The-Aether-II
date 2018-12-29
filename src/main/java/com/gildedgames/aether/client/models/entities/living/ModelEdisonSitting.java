package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Edison Sitting 1.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelEdisonSitting extends ModelBase
{
	public final ModelRenderer head;

	public final ModelRenderer scarffront;

	public final ModelRenderer chest;

	public final ModelRenderer abdomen;

	public final ModelRenderer pelvis;

	public final ModelRenderer belt;

	public final ModelRenderer leftupperarm;

	public final ModelRenderer leftforearm;

	public final ModelRenderer rightupperarm;

	public final ModelRenderer rightforearm;

	public final ModelRenderer leftupperleg;

	public final ModelRenderer leftlowerlef;

	public final ModelRenderer rightupperleg;

	public final ModelRenderer rightlowerleg;

	public final ModelRenderer pouch;

	public final ModelRenderer vialback;

	public final ModelRenderer vialfront;

	public final ModelRenderer earleft;

	public final ModelRenderer earright;

	public ModelEdisonSitting()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.earleft = new ModelRenderer(this, 48, 7);
		this.earleft.setRotationPoint(4.0F, -3.5F, -2.0F);
		this.earleft.addBox(-1.0F, -2.0F, 0.0F, 1, 4, 3, 0.0F);
		this.setRotateAngle(this.earleft, 0.0F, 0.2617993877991494F, 0.08726646259971647F);
		this.scarffront = new ModelRenderer(this, 16, 16);
		this.scarffront.setRotationPoint(0.0F, 10.0F, 0.5F);
		this.scarffront.addBox(-4.5F, 0.0F, -0.5F, 9, 3, 7, 0.0F);
		this.setRotateAngle(this.scarffront, 0.34906584024429316F, -0.0F, 0.0F);
		this.rightforearm = new ModelRenderer(this, 0, 64);
		this.rightforearm.setRotationPoint(-4.5F, 11.0F, 6.0F);
		this.rightforearm.addBox(-0.5F, -2.0F, -9.0F, 8, 4, 4, 0.0F);
		this.setRotateAngle(this.rightforearm, 0.5235987901687622F, 0.6981316804885863F, 0.0F);
		this.rightlowerleg = new ModelRenderer(this, 0, 85);
		this.rightlowerleg.setRotationPoint(-2.5F, 22.0F, 3.0F);
		this.rightlowerleg.addBox(-2.0F, -8.0F, -8.0F, 4, 4, 10, 0.0F);
		this.setRotateAngle(this.rightlowerleg, 0.7853981852531434F, 0.34906584024429316F, 0.0F);
		this.head = new ModelRenderer(this, 16, 0);
		this.head.setRotationPoint(0.0F, 10.0F, 2.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(this.head, 0.08726646006107329F, -0.0F, 0.0F);
		this.chest = new ModelRenderer(this, 17, 26);
		this.chest.setRotationPoint(0.0F, 9.0F, 3.5F);
		this.chest.addBox(-5.0F, 0.5F, -2.4000000953674316F, 10, 6, 5, 0.0F);
		this.setRotateAngle(this.chest, 0.2617993950843811F, -0.0F, 0.0F);
		this.vialback = new ModelRenderer(this, 12, 101);
		this.vialback.setRotationPoint(4.0F, 19.0F, 6.0F);
		this.vialback.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.vialback, 0.0F, -0.0F, -0.11153583228588103F);
		this.pouch = new ModelRenderer(this, 0, 101);
		this.pouch.setRotationPoint(-4.0F, 19.0F, 6.0F);
		this.pouch.addBox(-2.0F, 0.0F, -2.0F, 2, 5, 3, 0.0F);
		this.setRotateAngle(this.pouch, 0.0F, -0.0F, 0.17453292012214658F);
		this.leftupperleg = new ModelRenderer(this, 44, 75);
		this.leftupperleg.setRotationPoint(2.5F, 22.0F, 3.0F);
		this.leftupperleg.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(this.leftupperleg, -0.6108652353286743F, -0.34906584024429316F, 0.0F);
		this.vialfront = new ModelRenderer(this, 12, 101);
		this.vialfront.setRotationPoint(4.0F, 19.0F, 4.0F);
		this.vialfront.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(this.vialfront, 0.07435721904039383F, -0.0F, -0.2974288761615753F);
		this.rightupperarm = new ModelRenderer(this, 0, 56);
		this.rightupperarm.setRotationPoint(-4.5F, 11.0F, 6.0F);
		this.rightupperarm.addBox(-0.5F, -2.0F, -5.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(this.rightupperarm, 0.5235987901687622F, 0.6981316804885863F, 0.0F);
		this.abdomen = new ModelRenderer(this, 20, 37);
		this.abdomen.setRotationPoint(0.0F, 12.0F, 5.5F);
		this.abdomen.addBox(-4.0F, 1.0F, -2.5F, 8, 6, 4, 0.0F);
		this.setRotateAngle(this.abdomen, 0.08726646006107329F, -0.0F, 0.0F);
		this.leftlowerlef = new ModelRenderer(this, 36, 85);
		this.leftlowerlef.setRotationPoint(2.5F, 22.0F, 3.0F);
		this.leftlowerlef.addBox(-2.0F, -8.0F, -8.0F, 4, 4, 10, 0.0F);
		this.setRotateAngle(this.leftlowerlef, 0.9599310755729675F, -0.34906584024429316F, 0.0F);
		this.leftupperarm = new ModelRenderer(this, 48, 56);
		this.leftupperarm.setRotationPoint(4.5F, 11.0F, 6.0F);
		this.leftupperarm.addBox(-2.5F, -2.0F, -5.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(this.leftupperarm, 0.6981316804885863F, -0.5235987901687622F, 0.0F);
		this.rightupperleg = new ModelRenderer(this, 0, 75);
		this.rightupperleg.setRotationPoint(-2.5F, 22.0F, 3.0F);
		this.rightupperleg.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(this.rightupperleg, -0.7853981852531434F, 0.34906584024429316F, 0.0F);
		this.pelvis = new ModelRenderer(this, 20, 54);
		this.pelvis.setRotationPoint(0.0F, 19.0F, 5.0F);
		this.pelvis.addBox(-4.0F, 1.0F, -1.5F, 8, 4, 4, 0.0F);
		this.setRotateAngle(this.pelvis, -0.17453292012214658F, -0.0F, 0.0F);
		this.earright = new ModelRenderer(this, 8, 7);
		this.earright.setRotationPoint(-4.0F, -3.5F, -2.0F);
		this.earright.addBox(0.0F, -2.0F, 0.0F, 1, 4, 3, 0.0F);
		this.setRotateAngle(this.earright, 0.0F, -0.2617993877991494F, -0.08726646259971647F);
		this.belt = new ModelRenderer(this, 18, 47);
		this.belt.setRotationPoint(0.0F, 19.0F, 5.0F);
		this.belt.addBox(-4.5F, -1.0F, -2.0F, 9, 2, 5, 0.0F);
		this.setRotateAngle(this.belt, -0.17453292012214658F, -0.0F, 0.0F);
		this.leftforearm = new ModelRenderer(this, 40, 64);
		this.leftforearm.setRotationPoint(4.5F, 11.0F, 6.0F);
		this.leftforearm.addBox(-6.5F, -2.0F, -9.0F, 8, 4, 4, 0.0F);
		this.setRotateAngle(this.leftforearm, 0.6981316804885863F, -0.5235987901687622F, 0.0F);
		this.head.addChild(this.earleft);
		this.head.addChild(this.earright);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.scarffront.render(f5);
		this.rightforearm.render(f5);
		this.rightlowerleg.render(f5);
		this.head.render(f5);
		this.chest.render(f5);
		this.vialback.render(f5);
		this.pouch.render(f5);
		this.leftupperleg.render(f5);
		this.vialfront.render(f5);
		this.rightupperarm.render(f5);
		this.abdomen.render(f5);
		this.leftlowerlef.render(f5);
		this.leftupperarm.render(f5);
		this.rightupperleg.render(f5);
		this.pelvis.render(f5);
		this.belt.render(f5);
		this.leftforearm.render(f5);
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
}
