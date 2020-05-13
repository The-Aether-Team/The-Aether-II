package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelNecromancer extends ModelBase
{
	public ModelRenderer torso_top;
	public ModelRenderer torso_bottom;
	public ModelRenderer arm_left;
	public ModelRenderer arm_right;
	public ModelRenderer cowl_bottom;
	public ModelRenderer cape;
	public ModelRenderer head;
	public ModelRenderer neck;
	public ModelRenderer waist;
	public ModelRenderer thigh_right;
	public ModelRenderer thigh_left;
	public ModelRenderer cape_bottom;
	public ModelRenderer knee_right;
	public ModelRenderer shin_right;
	public ModelRenderer foot_right;
	public ModelRenderer knee_left;
	public ModelRenderer shin_left;
	public ModelRenderer foot_left;
	public ModelRenderer elbow_left;
	public ModelRenderer arm_left_bottom;
	public ModelRenderer elbow_right;
	public ModelRenderer arm_right_bottom;
	public ModelRenderer mask_bottom;
	public ModelRenderer mask_top;

	public ModelNecromancer()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.thigh_right = new ModelRenderer(this, 0, 79);
		this.thigh_right.setRotationPoint(-2.45F, 3.5F, 0.0F);
		this.thigh_right.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.knee_left = new ModelRenderer(this, 0, 90);
		this.knee_left.mirror = true;
		this.knee_left.setRotationPoint(0.0F, 7.5F, 0.0F);
		this.knee_left.addBox(-1.5F, -2.0F, -2.2F, 3, 4, 4, 0.0F);
		this.waist = new ModelRenderer(this, 0, 70);
		this.waist.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.waist.addBox(-4.5F, 0.0F, -2.5F, 9, 4, 5, 0.0F);
		this.cape = new ModelRenderer(this, 0, 31);
		this.cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cape.addBox(-9.5F, -4.7F, -3.0F, 19, 12, 6, 0.0F);
		this.neck = new ModelRenderer(this, 31, 16);
		this.neck.setRotationPoint(0.0F, -6.0F, -1.5F);
		this.neck.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 3, 0.0F);
		this.torso_top = new ModelRenderer(this, 0, 49);
		this.torso_top.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.torso_top.addBox(-5.5F, -4.0F, -2.5F, 11, 8, 5, 0.0F);
		this.arm_right_bottom = new ModelRenderer(this, 32, 67);
		this.arm_right_bottom.setRotationPoint(0.0F, 2.0F, 0.5F);
		this.arm_right_bottom.addBox(-2.0F, -0.5F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(arm_right_bottom, -0.08726646259971647F, 0.0F, 0.0F);
		this.elbow_right = new ModelRenderer(this, 32, 61);
		this.elbow_right.setRotationPoint(-2.0F, 6.0F, 0.0F);
		this.elbow_right.addBox(-1.5F, 0.0F, -0.25F, 3, 3, 3, 0.0F);
		this.foot_left = new ModelRenderer(this, 0, 109);
		this.foot_left.mirror = true;
		this.foot_left.setRotationPoint(0.0F, 7.0F, -1.5F);
		this.foot_left.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 1, 0.0F);
		this.torso_bottom = new ModelRenderer(this, 0, 62);
		this.torso_bottom.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.torso_bottom.addBox(-4.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F);
		this.thigh_left = new ModelRenderer(this, 0, 79);
		this.thigh_left.mirror = true;
		this.thigh_left.setRotationPoint(2.45F, 3.5F, 0.0F);
		this.thigh_left.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.shin_right = new ModelRenderer(this, 0, 98);
		this.shin_right.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shin_right.addBox(-2.01F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.elbow_left = new ModelRenderer(this, 48, 61);
		this.elbow_left.setRotationPoint(2.0F, 6.0F, 0.0F);
		this.elbow_left.addBox(-1.5F, 0.0F, -0.25F, 3, 3, 3, 0.0F);
		this.mask_bottom = new ModelRenderer(this, 0, 11);
		this.mask_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mask_bottom.addBox(-3.5F, -2.5F, -4.5F, 7, 3, 5, 0.0F);
		this.cape_bottom = new ModelRenderer(this, 16, 79);
		this.cape_bottom.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.cape_bottom.addBox(-4.5F, 0.0F, -2.5F, 9, 10, 5, 0.0F);
		this.arm_left_bottom = new ModelRenderer(this, 48, 67);
		this.arm_left_bottom.setRotationPoint(0.0F, 2.0F, 0.5F);
		this.arm_left_bottom.addBox(-2.0F, -0.5F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(arm_left_bottom, -0.08726646259971647F, 0.0F, 0.0F);
		this.arm_left = new ModelRenderer(this, 32, 49);
		this.arm_left.mirror = true;
		this.arm_left.setRotationPoint(4.5F, -2.5F, 0.0F);
		this.arm_left.addBox(0.01F, -1.0F, -2.0F, 4, 8, 4, 0.0F);
		this.setRotateAngle(arm_left, 0.0F, 0.0F, -0.17453292519943295F);
		this.cowl_bottom = new ModelRenderer(this, 0, 19);
		this.cowl_bottom.setRotationPoint(0.0F, -3.5F, -3.5F);
		this.cowl_bottom.addBox(-5.5F, -2.0F, -0.8F, 11, 3, 9, 0.0F);
		this.setRotateAngle(cowl_bottom, 0.18203784098300857F, 0.0F, 0.0F);
		this.shin_left = new ModelRenderer(this, 0, 98);
		this.shin_left.mirror = true;
		this.shin_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shin_left.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.mask_top = new ModelRenderer(this, 0, 0);
		this.mask_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mask_top.addBox(-4.5F, -8.5F, -4.5F, 9, 6, 5, 0.0F);
		this.arm_right = new ModelRenderer(this, 32, 49);
		this.arm_right.setRotationPoint(-4.5F, -2.5F, 0.0F);
		this.arm_right.addBox(-4.01F, -1.0F, -2.0F, 4, 8, 4, 0.0F);
		this.setRotateAngle(arm_right, 0.0F, 0.0F, 0.17453292519943295F);
		this.foot_right = new ModelRenderer(this, 0, 109);
		this.foot_right.setRotationPoint(0.0F, 7.0F, -1.5F);
		this.foot_right.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 1, 0.0F);
		this.head = new ModelRenderer(this, 28, 0);
		this.head.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.knee_right = new ModelRenderer(this, 0, 90);
		this.knee_right.setRotationPoint(0.0F, 7.5F, 0.0F);
		this.knee_right.addBox(-1.5F, -2.0F, -2.2F, 3, 4, 4, 0.0F);
		this.waist.addChild(this.thigh_right);
		this.thigh_left.addChild(this.knee_left);
		this.torso_bottom.addChild(this.waist);
		this.torso_top.addChild(this.cape);
		this.torso_top.addChild(this.neck);
		this.elbow_right.addChild(this.arm_right_bottom);
		this.arm_right.addChild(this.elbow_right);
		this.shin_left.addChild(this.foot_left);
		this.torso_top.addChild(this.torso_bottom);
		this.waist.addChild(this.thigh_left);
		this.knee_right.addChild(this.shin_right);
		this.arm_left.addChild(this.elbow_left);
		this.head.addChild(this.mask_bottom);
		this.waist.addChild(this.cape_bottom);
		this.elbow_left.addChild(this.arm_left_bottom);
		this.torso_top.addChild(this.arm_left);
		this.torso_top.addChild(this.cowl_bottom);
		this.knee_left.addChild(this.shin_left);
		this.head.addChild(this.mask_top);
		this.torso_top.addChild(this.arm_right);
		this.shin_right.addChild(this.foot_right);
		this.torso_top.addChild(this.head);
		this.thigh_right.addChild(this.knee_right);	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.torso_top.render(f5);
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

		final float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.75F * limbSwingAmount);
		final float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.75F * limbSwingAmount);

		this.thigh_left.rotateAngleX = 0.08726646259971647F + leftSwingX;

		this.thigh_right.rotateAngleX = 0.08726646259971647F + rightSwingX;

		this.arm_left.rotateAngleX = leftSwingX;

		this.arm_right.rotateAngleX = rightSwingX;
	}
}
