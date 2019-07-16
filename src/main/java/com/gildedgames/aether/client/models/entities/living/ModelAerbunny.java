package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityAerbunny;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAerbunny extends EntityModel<EntityAerbunny>
{
	private final RendererModel body;

	private final RendererModel puff;

	private final RendererModel leg_front_left;

	private final RendererModel leg_front_right;

	private final RendererModel leg_back_right_base;

	private final RendererModel leg_back_left_base;

	private final RendererModel tail;

	private final RendererModel head;

	private final RendererModel leg_back_right_foot;

	private final RendererModel leg_back_left_foot;

	private final RendererModel ear_right;

	private final RendererModel ear_left;

	private final RendererModel nose;

	private final RendererModel whiskers_left;

	private final RendererModel whiskers_right;

	public ModelAerbunny()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.leg_front_left = new RendererModel(this, 0, 11);
		this.leg_front_left.setRotationPoint(1.5F, 4.0F, 1.0F);
		this.leg_front_left.addBox(-0.5F, -0.2F, -2.8F, 1, 2, 3, 0.0F);
		this.setRotateAngle(this.leg_front_left, 0.17453292519943295F, -0.3490658503988659F, 0.0F);
		this.leg_back_left_foot = new RendererModel(this, 0, 16);
		this.leg_back_left_foot.setRotationPoint(0.5F, 1.8F, -0.3F);
		this.leg_back_left_foot.addBox(-0.5F, -0.2F, -1.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(this.leg_back_left_foot, 0.0F, -0.20943951023931953F, 0.0F);
		this.head = new RendererModel(this, 22, 0);
		this.head.setRotationPoint(0.0F, 2.4F, 0.0F);
		this.head.addBox(-2.0F, -2.2F, -3.0F, 4, 4, 4, 0.0F);
		this.leg_back_right_foot = new RendererModel(this, 0, 16);
		this.leg_back_right_foot.mirror = true;
		this.leg_back_right_foot.setRotationPoint(-0.5F, 1.8F, -0.3F);
		this.leg_back_right_foot.addBox(-0.5F, -0.2F, -1.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(this.leg_back_right_foot, 0.0F, 0.20943951023931953F, 0.0F);
		this.ear_left = new RendererModel(this, 16, 1);
		this.ear_left.setRotationPoint(1.0F, -2.0F, -1.2F);
		this.ear_left.addBox(-0.5F, -2.8F, -1.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(this.ear_left, -0.2617993877991494F, -0.08726646259971647F, 0.2617993877991494F);
		this.nose = new RendererModel(this, 0, 0);
		this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.nose.addBox(-1.0F, -0.2F, -3.3F, 2, 1, 1, 0.0F);
		this.leg_front_right = new RendererModel(this, 0, 11);
		this.leg_front_right.mirror = true;
		this.leg_front_right.setRotationPoint(-1.5F, 4.0F, 1.0F);
		this.leg_front_right.addBox(-0.5F, -0.2F, -2.8F, 1, 2, 3, 0.0F);
		this.setRotateAngle(this.leg_front_right, 0.17453292519943295F, 0.3490658503988659F, 0.0F);
		this.leg_back_right_base = new RendererModel(this, 8, 11);
		this.leg_back_right_base.mirror = true;
		this.leg_back_right_base.setRotationPoint(-2.5F, 3.5F, 5.0F);
		this.leg_back_right_base.addBox(-1.5F, -1.0F, -1.5F, 2, 3, 3, 0.0F);
		this.leg_back_left_base = new RendererModel(this, 8, 11);
		this.leg_back_left_base.setRotationPoint(2.5F, 3.5F, 5.0F);
		this.leg_back_left_base.addBox(-0.5F, -1.0F, -1.5F, 2, 3, 3, 0.0F);
		this.ear_right = new RendererModel(this, 16, 1);
		this.ear_right.mirror = true;
		this.ear_right.setRotationPoint(-1.0F, -2.0F, -1.2F);
		this.ear_right.addBox(-0.5F, -2.8F, -1.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(this.ear_right, -0.2617993877991494F, 0.08726646259971647F, -0.2617993877991494F);
		this.whiskers_left = new RendererModel(this, 0, 3);
		this.whiskers_left.setRotationPoint(2.0F, 0.0F, -2.0F);
		this.whiskers_left.addBox(-0.2F, -1.3F, 0.0F, 2, 3, 0, 0.0F);
		this.setRotateAngle(this.whiskers_left, -0.17453292519943295F, -0.6981317007977318F, 0.17453292519943295F);
		this.body = new RendererModel(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 6, 0.0F);
		this.puff = new RendererModel(this, 0, 0);
		this.puff.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.puff.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 6, 0.0F);
		this.tail = new RendererModel(this, 22, 8);
		this.tail.setRotationPoint(0.0F, 1.5F, 5.0F);
		this.tail.addBox(-1.5F, -0.4F, 0.2F, 3, 3, 2, 0.0F);
		this.setRotateAngle(this.tail, 0.2617993877991494F, 0.0F, 0.0F);
		this.whiskers_right = new RendererModel(this, 0, 3);
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
	public void render(EntityAerbunny entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		float rotation = entity.getRotation();
		GlStateManager.translatef(0.0F, 1.1F, -0.2F);

		if (!entity.isPassenger())
		{
			GlStateManager.rotatef(rotation, 20f, 0f, 0f);
		}
		else if (entity.getRidingEntity() != null && entity.getRidingEntity().isSneaking())
		{
			GlStateManager.translatef(0.0F, 0.3F, -0.2F);
		}

		if (entity.isChild())
		{
			GlStateManager.scalef(0.7F, 0.7F, 0.7F);
			GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		}

		/*
		Animation for the legs, because the rotation for the bunnie's jump is from -30 to 30, offsetting it allows the feet to be in a preset position,
		and dividing it by a greater number than it will ever become keeps the value < 1f
		 */
		if (!entity.onGround && !entity.isPassenger())
		{
			this.leg_front_right.rotateAngleX = (rotation + 20) / 30f;
			this.leg_front_left.rotateAngleX = (rotation + 20) / 30f;
			this.leg_back_right_base.rotateAngleX = ((rotation + 40) / 50f);
			this.leg_back_left_base.rotateAngleX = ((rotation + 40) / 50f);
			this.leg_back_right_foot.rotateAngleX = ((rotation + 40) / 50f) * -1.5f;
			this.leg_back_left_foot.rotateAngleX = ((rotation + 40) / 50f) * -1.5f;
		}

		this.body.render(scale);

		GlStateManager.pushMatrix();

		final float puffScale = 1.0F + ((entity.getPuffiness() / 10F) * 0.2F);

		GlStateManager.scalef(puffScale, puffScale, puffScale);

		this.puff.render(scale);

		GlStateManager.popMatrix();
	}

	private void setRotateAngle(final RendererModel modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(EntityAerbunny entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		this.head.rotateAngleX = headPitch / 57.29578F;
		this.head.rotateAngleY = netHeadYaw / 57.29578F;

		if (entity.isPassenger())
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
