package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEdisonSitting extends ModelBase
{
	public ModelRenderer head;

	public ModelRenderer scarffront;

	public ModelRenderer chest;

	public ModelRenderer abdomen;

	public ModelRenderer pelvis;

	public ModelRenderer belt;

	public ModelRenderer leftupperarm;

	public ModelRenderer leftforearm;

	public ModelRenderer rightupperarm;

	public ModelRenderer rightforearm;

	public ModelRenderer leftupperleg;

	public ModelRenderer leftlowerlef;

	public ModelRenderer rightupperleg;

	public ModelRenderer rightlowerleg;

	public ModelRenderer pouch;

	public ModelRenderer vialback;

	public ModelRenderer vialfront;

	public ModelEdisonSitting()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.vialback = new ModelRenderer(this, 12, 101);
		this.vialback.setRotationPoint(4.0F, 19.0F, 6.0F);
		this.vialback.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(vialback, 0.0F, -0.0F, -0.11153583228588103F);
		this.pelvis = new ModelRenderer(this, 20, 54);
		this.pelvis.setRotationPoint(0.0F, 19.0F, 5.0F);
		this.pelvis.addBox(-4.0F, 1.0F, -1.5F, 8, 4, 4, 0.0F);
		this.setRotateAngle(pelvis, -0.17453292012214658F, -0.0F, 0.0F);
		this.rightupperarm = new ModelRenderer(this, 0, 56);
		this.rightupperarm.setRotationPoint(-4.5F, 11.0F, 6.0F);
		this.rightupperarm.addBox(-0.5F, -2.0F, -5.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(rightupperarm, 0.5235987901687622F, 0.6981316804885863F, 0.0F);
		this.abdomen = new ModelRenderer(this, 20, 37);
		this.abdomen.setRotationPoint(0.0F, 12.0F, 5.5F);
		this.abdomen.addBox(-4.0F, 1.0F, -2.5F, 8, 6, 4, 0.0F);
		this.setRotateAngle(abdomen, 0.08726646006107329F, -0.0F, 0.0F);
		this.scarffront = new ModelRenderer(this, 16, 16);
		this.scarffront.setRotationPoint(0.0F, 10.0F, 0.5F);
		this.scarffront.addBox(-4.5F, 0.0F, -0.5F, 9, 3, 7, 0.0F);
		this.setRotateAngle(scarffront, 0.34906584024429316F, -0.0F, 0.0F);
		this.rightforearm = new ModelRenderer(this, 0, 64);
		this.rightforearm.setRotationPoint(-4.5F, 11.0F, 6.0F);
		this.rightforearm.addBox(-0.5F, -2.0F, -9.0F, 8, 4, 4, 0.0F);
		this.setRotateAngle(rightforearm, 0.5235987901687622F, 0.6981316804885863F, 0.0F);
		this.leftforearm = new ModelRenderer(this, 40, 64);
		this.leftforearm.setRotationPoint(4.5F, 11.0F, 6.0F);
		this.leftforearm.addBox(-6.5F, -2.0F, -9.0F, 8, 4, 4, 0.0F);
		this.setRotateAngle(leftforearm, 0.6981316804885863F, -0.5235987901687622F, 0.0F);
		this.pouch = new ModelRenderer(this, 0, 101);
		this.pouch.setRotationPoint(-4.0F, 19.0F, 6.0F);
		this.pouch.addBox(-2.0F, 0.0F, -2.0F, 2, 5, 3, 0.0F);
		this.setRotateAngle(pouch, 0.0F, -0.0F, 0.17453292012214658F);
		this.belt = new ModelRenderer(this, 18, 47);
		this.belt.setRotationPoint(0.0F, 19.0F, 5.0F);
		this.belt.addBox(-4.5F, -1.0F, -2.0F, 9, 2, 5, 0.0F);
		this.setRotateAngle(belt, -0.17453292012214658F, -0.0F, 0.0F);
		this.leftlowerlef = new ModelRenderer(this, 36, 85);
		this.leftlowerlef.setRotationPoint(2.5F, 22.0F, 3.0F);
		this.leftlowerlef.addBox(-2.0F, -8.0F, -8.0F, 4, 4, 10, 0.0F);
		this.setRotateAngle(leftlowerlef, 0.9599310755729675F, -0.34906584024429316F, 0.0F);
		this.rightlowerleg = new ModelRenderer(this, 0, 85);
		this.rightlowerleg.setRotationPoint(-2.5F, 22.0F, 3.0F);
		this.rightlowerleg.addBox(-2.0F, -8.0F, -8.0F, 4, 4, 10, 0.0F);
		this.setRotateAngle(rightlowerleg, 0.7853981852531434F, 0.34906584024429316F, 0.0F);
		this.leftupperleg = new ModelRenderer(this, 44, 75);
		this.leftupperleg.setRotationPoint(2.5F, 22.0F, 3.0F);
		this.leftupperleg.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(leftupperleg, -0.6108652353286743F, -0.34906584024429316F, 0.0F);
		this.head = new ModelRenderer(this, 16, 0);
		this.head.setRotationPoint(0.0F, 10.0F, 2.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(head, 0.08726646006107329F, -0.0F, 0.0F);
		this.chest = new ModelRenderer(this, 17, 26);
		this.chest.setRotationPoint(0.0F, 9.0F, 3.5F);
		this.chest.addBox(-5.0F, 0.5F, -2.4000000953674316F, 10, 6, 5, 0.0F);
		this.setRotateAngle(chest, 0.2617993950843811F, -0.0F, 0.0F);
		this.leftupperarm = new ModelRenderer(this, 48, 56);
		this.leftupperarm.setRotationPoint(4.5F, 11.0F, 6.0F);
		this.leftupperarm.addBox(-2.5F, -2.0F, -5.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(leftupperarm, 0.6981316804885863F, -0.5235987901687622F, 0.0F);
		this.vialfront = new ModelRenderer(this, 12, 101);
		this.vialfront.setRotationPoint(4.0F, 19.0F, 4.0F);
		this.vialfront.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(vialfront, 0.07435721904039383F, -0.0F, -0.2974288761615753F);
		this.rightupperleg = new ModelRenderer(this, 0, 75);
		this.rightupperleg.setRotationPoint(-2.5F, 22.0F, 3.0F);
		this.rightupperleg.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(rightupperleg, -0.7853981852531434F, 0.34906584024429316F, 0.0F);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.vialback.render(f5);
		this.pelvis.render(f5);
		this.rightupperarm.render(f5);
		this.abdomen.render(f5);
		this.scarffront.render(f5);
		this.rightforearm.render(f5);
		this.leftforearm.render(f5);
		this.pouch.render(f5);
		this.belt.render(f5);
		this.leftlowerlef.render(f5);
		this.rightlowerleg.render(f5);
		this.leftupperleg.render(f5);
		this.head.render(f5);
		this.chest.render(f5);
		this.leftupperarm.render(f5);
		this.vialfront.render(f5);
		this.rightupperleg.render(f5);
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

	@Override
	public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch,
			final float scaleFactor, final Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		final float pitch = headPitch * 0.017453292F;
		final float yaw = netHeadYaw * 0.017453292F;

		this.head.rotateAngleX = pitch;
		this.head.rotateAngleY = yaw;
	}

}
