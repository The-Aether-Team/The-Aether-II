package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.passive.EntityAerbunny;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAerbunny extends ModelBase
{
	private ModelRenderer body, puff;

	private ModelRenderer leg_front_left;

	private ModelRenderer leg_front_right;

	private ModelRenderer leg_back_right_base;

	private ModelRenderer leg_back_left_base;

	private ModelRenderer tail;

	private ModelRenderer head;

	private ModelRenderer leg_back_right_foot;

	private ModelRenderer leg_back_left_foot;

	private ModelRenderer ear_right;

	private ModelRenderer ear_left;

	private ModelRenderer nose;

	private ModelRenderer whiskers_left;

	private ModelRenderer whiskers_right;

	public ModelAerbunny()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.leg_front_left = new ModelRenderer(this, 0, 11);
		this.leg_front_left.setRotationPoint(1.5F, 4.0F, 1.0F);
		this.leg_front_left.addBox(-0.5F, -0.2F, -2.8F, 1, 2, 3, 0.0F);
		this.setRotateAngle(this.leg_front_left, 0.17453292519943295F, -0.3490658503988659F, 0.0F);
		this.leg_back_left_foot = new ModelRenderer(this, 0, 16);
		this.leg_back_left_foot.setRotationPoint(0.5F, 1.8F, -0.3F);
		this.leg_back_left_foot.addBox(-0.5F, -0.2F, -1.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(this.leg_back_left_foot, 0.0F, -0.20943951023931953F, 0.0F);
		this.head = new ModelRenderer(this, 22, 0);
		this.head.setRotationPoint(0.0F, 2.4F, 0.0F);
		this.head.addBox(-2.0F, -2.2F, -3.0F, 4, 4, 4, 0.0F);
		this.leg_back_right_foot = new ModelRenderer(this, 0, 16);
		this.leg_back_right_foot.mirror = true;
		this.leg_back_right_foot.setRotationPoint(-0.5F, 1.8F, -0.3F);
		this.leg_back_right_foot.addBox(-0.5F, -0.2F, -1.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(this.leg_back_right_foot, 0.0F, 0.20943951023931953F, 0.0F);
		this.ear_left = new ModelRenderer(this, 16, 1);
		this.ear_left.setRotationPoint(1.0F, -2.0F, -1.2F);
		this.ear_left.addBox(-0.5F, -2.8F, -1.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(this.ear_left, -0.2617993877991494F, -0.08726646259971647F, 0.2617993877991494F);
		this.nose = new ModelRenderer(this, 0, 0);
		this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.nose.addBox(-1.0F, -0.2F, -3.3F, 2, 1, 1, 0.0F);
		this.leg_front_right = new ModelRenderer(this, 0, 11);
		this.leg_front_right.mirror = true;
		this.leg_front_right.setRotationPoint(-1.5F, 4.0F, 1.0F);
		this.leg_front_right.addBox(-0.5F, -0.2F, -2.8F, 1, 2, 3, 0.0F);
		this.setRotateAngle(this.leg_front_right, 0.17453292519943295F, 0.3490658503988659F, 0.0F);
		this.leg_back_right_base = new ModelRenderer(this, 8, 11);
		this.leg_back_right_base.mirror = true;
		this.leg_back_right_base.setRotationPoint(-2.5F, 3.5F, 5.0F);
		this.leg_back_right_base.addBox(-1.5F, -1.0F, -1.5F, 2, 3, 3, 0.0F);
		this.leg_back_left_base = new ModelRenderer(this, 8, 11);
		this.leg_back_left_base.setRotationPoint(2.5F, 3.5F, 5.0F);
		this.leg_back_left_base.addBox(-0.5F, -1.0F, -1.5F, 2, 3, 3, 0.0F);
		this.ear_right = new ModelRenderer(this, 16, 1);
		this.ear_right.mirror = true;
		this.ear_right.setRotationPoint(-1.0F, -2.0F, -1.2F);
		this.ear_right.addBox(-0.5F, -2.8F, -1.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(this.ear_right, -0.2617993877991494F, 0.08726646259971647F, -0.2617993877991494F);
		this.whiskers_left = new ModelRenderer(this, 0, 3);
		this.whiskers_left.setRotationPoint(2.0F, 0.0F, -2.0F);
		this.whiskers_left.addBox(-0.2F, -1.3F, 0.0F, 2, 3, 0, 0.0F);
		this.setRotateAngle(this.whiskers_left, -0.17453292519943295F, -0.6981317007977318F, 0.17453292519943295F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 6, 0.0F);
		this.puff = new ModelRenderer(this, 0, 0);
		this.puff.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.puff.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 6, 0.0F);
		this.tail = new ModelRenderer(this, 22, 8);
		this.tail.setRotationPoint(0.0F, 1.5F, 5.0F);
		this.tail.addBox(-1.5F, -0.4F, 0.2F, 3, 3, 2, 0.0F);
		this.setRotateAngle(this.tail, 0.2617993877991494F, 0.0F, 0.0F);
		this.whiskers_right = new ModelRenderer(this, 0, 3);
		this.whiskers_right.mirror = true;
		this.whiskers_right.setRotationPoint(-2.0F, 0.0F, -2.0F);
		this.whiskers_right.addBox(-1.8F, -1.3F, 0.0F, 2, 3, 0, 0.0F);
		this.setRotateAngle(this.whiskers_right, -0.17453292519943295F, 0.6981317007977318F, -0.17453292519943295F);
		this.body.addChild(this.leg_front_left);
		this.leg_back_left_base.addChild(this.leg_back_left_foot);
		this.body.addChild(this.head);
		this.leg_back_right_base.addChild(this.leg_back_right_foot);
		this.head.addChild(this.ear_left);
		this.head.addChild(this.nose);
		this.body.addChild(this.leg_front_right);
		this.body.addChild(this.leg_back_right_base);
		this.body.addChild(this.leg_back_left_base);
		this.head.addChild(this.ear_right);
		this.head.addChild(this.whiskers_left);
		this.body.addChild(this.tail);
		this.head.addChild(this.whiskers_right);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		final EntityAerbunny bunny = (EntityAerbunny) entity;

		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

		float rotation = bunny.getRotation();
		GlStateManager.translate(0F, 1.1F, -0.2F);
		GlStateManager.rotate(rotation, 20f, 0f, 0f);

		this.body.render(scale);

		GlStateManager.pushMatrix();

		/*
		Animation for the legs, because the rotation for the bunnie's jump is from -30 to 30, offsetting it allows the feet to be in a preset position,
		and dividing it by a greater number than it will ever become keeps the value < 1f
		 */
		if (!bunny.onGround)
		{
			leg_front_right.rotateAngleX = (rotation + 40) / 50f;
			leg_front_left.rotateAngleX = (rotation + 40)  / 50f;
			leg_back_right_base.rotateAngleX = ((rotation + 40) / 50f);
			leg_back_left_base.rotateAngleX = ((rotation + 40) / 50f);
			leg_back_right_foot.rotateAngleX = ((rotation + 40) / 50f) * -1.5f;
			leg_back_left_foot.rotateAngleX = ((rotation + 40) / 50f) * -1.5f;
		}

		final float puffScale = 1.0F + ((bunny.getPuffiness() / 10F) * 0.2F);

		GlStateManager.scale(puffScale, puffScale, puffScale);

		this.puff.render(scale);

		GlStateManager.popMatrix();
	}

	private void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		this.head.rotateAngleX = headPitch / 57.29578F;
		this.head.rotateAngleY = netHeadYaw / 57.29578F;

		if (entity.isRiding())
		{
			this.head.rotateAngleX = MathHelper.clamp(this.head.rotateAngleX * -1, -1.0f, 1.0f);
		}

		this.ear_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.ear_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;

		this.leg_back_left_base.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbSwingAmount;
		this.leg_back_right_base.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbSwingAmount;

		this.leg_front_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbSwingAmount;
		this.leg_front_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbSwingAmount;
	}

}
