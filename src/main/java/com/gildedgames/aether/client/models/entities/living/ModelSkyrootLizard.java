package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

/**
 * skyroot_lizard - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelSkyrootLizard extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer neck_1;
	public ModelRenderer tail_1;
	public ModelRenderer leg_front_left_1;
	public ModelRenderer leg_front_right_1;
	public ModelRenderer leg_back_left_1;
	public ModelRenderer leg_back_right_1;
	public ModelRenderer neck_2;
	public ModelRenderer neck_1_flaps;
	public ModelRenderer neck_2_flaps;
	public ModelRenderer head_1;
	public ModelRenderer head_2;
	public ModelRenderer earflap_left;
	public ModelRenderer earflap_right;
	public ModelRenderer tongue;
	public ModelRenderer tail_2;
	public ModelRenderer tail_1_flaps;
	public ModelRenderer tail_3;
	public ModelRenderer tail_2_flaps;
	public ModelRenderer tail_3_flaps;
	public ModelRenderer leg_front_left_2;
	public ModelRenderer foot_front_left;
	public ModelRenderer claw_front_left_1;
	public ModelRenderer claw_front_left_2;
	public ModelRenderer claw_front_left_3;
	public ModelRenderer leg_front_right_2;
	public ModelRenderer foot_front_right;
	public ModelRenderer claw_front_right_1;
	public ModelRenderer claw_front_right_2;
	public ModelRenderer claw_front_right_3;
	public ModelRenderer leg_back_left_2;
	public ModelRenderer foot_back_left;
	public ModelRenderer claw_back_left_1;
	public ModelRenderer claw_back_left_2;
	public ModelRenderer claw_back_left_3;
	public ModelRenderer leg_front_right_2_1;
	public ModelRenderer foot_front_right_1;
	public ModelRenderer claw_front_right_1_1;
	public ModelRenderer claw_front_right_2_1;
	public ModelRenderer claw_front_right_3_1;

	public ModelSkyrootLizard() {
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.foot_front_left = new ModelRenderer(this, 49, 93);
		this.foot_front_left.setRotationPoint(5.5F, 0.0F, 0.0F);
		this.foot_front_left.addBox(0.0F, -0.1F, 0.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(foot_front_left, 0.04363323129985824F, 0.8464846872172498F, -0.3490658503988659F);
		this.claw_front_right_1 = new ModelRenderer(this, 8, 97);
		this.claw_front_right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_1.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.earflap_left = new ModelRenderer(this, 44, 69);
		this.earflap_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.earflap_left.addBox(1.4F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(earflap_left, -0.17453292519943295F, 0.0F, 0.04363323129985824F);
		this.leg_back_right_1 = new ModelRenderer(this, 0, 21);
		this.leg_back_right_1.setRotationPoint(-3.0F, 1.3F, 4.5F);
		this.leg_back_right_1.addBox(-7.0F, -1.0F, -1.5F, 8, 2, 3, 0.0F);
		this.setRotateAngle(leg_back_right_1, 0.0F, 0.3490658503988659F, -0.08726646259971647F);
		this.claw_front_left_1 = new ModelRenderer(this, 50, 97);
		this.claw_front_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_left_1.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.tail_3_flaps = new ModelRenderer(this, 13, 97);
		this.tail_3_flaps.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tail_3_flaps.addBox(-7.5F, 0.0F, 0.0F, 15, 0, 6, 0.0F);
		this.leg_back_left_2 = new ModelRenderer(this, 44, 17);
		this.leg_back_left_2.setRotationPoint(7.0F, 0.0F, 0.0F);
		this.leg_back_left_2.addBox(-0.5F, -0.7F, -1.1F, 7, 2, 2, 0.0F);
		this.setRotateAngle(leg_back_left_2, -0.2617993877991494F, -0.6108652381980153F, 0.4363323129985824F);
		this.foot_back_left = new ModelRenderer(this, 49, 13);
		this.foot_back_left.setRotationPoint(5.5F, 0.0F, 0.0F);
		this.foot_back_left.addBox(0.0F, -0.1F, 0.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(foot_back_left, -0.04363323129985824F, 0.8464846872172498F, -0.3490658503988659F);
		this.neck_2_flaps = new ModelRenderer(this, 13, 120);
		this.neck_2_flaps.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.neck_2_flaps.addBox(-7.5F, 0.0F, -6.0F, 15, 0, 6, 0.0F);
		this.leg_front_right_1 = new ModelRenderer(this, 0, 84);
		this.leg_front_right_1.setRotationPoint(-3.0F, 1.3F, -4.5F);
		this.leg_front_right_1.addBox(-7.0F, -1.0F, -1.5F, 8, 2, 3, 0.0F);
		this.setRotateAngle(leg_front_right_1, 0.0F, -0.3490658503988659F, -0.08726646259971647F);
		this.claw_front_right_2 = new ModelRenderer(this, 8, 97);
		this.claw_front_right_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_2.addBox(2.8F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_right_2, 0.0F, -0.7853981633974483F, 0.0F);
		this.leg_front_left_1 = new ModelRenderer(this, 42, 84);
		this.leg_front_left_1.setRotationPoint(3.0F, 1.3F, -4.5F);
		this.leg_front_left_1.addBox(-1.0F, -1.0F, -1.5F, 8, 2, 3, 0.0F);
		this.setRotateAngle(leg_front_left_1, 0.0F, 0.3490658503988659F, 0.08726646259971647F);
		this.tail_1 = new ModelRenderer(this, 20, 19);
		this.tail_1.setRotationPoint(0.0F, 1.0F, 6.0F);
		this.tail_1.addBox(-2.0F, -1.5F, -1.0F, 4, 3, 8, 0.0F);
		this.setRotateAngle(tail_1, 0.3490658503988659F, 0.0F, 0.0F);
		this.neck_1 = new ModelRenderer(this, 20, 45);
		this.neck_1.setRotationPoint(0.0F, 1.0F, -6.0F);
		this.neck_1.addBox(-2.0F, -1.5F, -7.0F, 4, 3, 8, 0.0F);
		this.setRotateAngle(neck_1, -0.5235987755982988F, 0.0F, 0.0F);
		this.leg_back_left_1 = new ModelRenderer(this, 42, 21);
		this.leg_back_left_1.setRotationPoint(3.0F, 1.3F, 4.5F);
		this.leg_back_left_1.addBox(-1.0F, -1.0F, -1.5F, 8, 2, 3, 0.0F);
		this.setRotateAngle(leg_back_left_1, 0.0F, -0.3490658503988659F, 0.08726646259971647F);
		this.tail_3 = new ModelRenderer(this, 22, 0);
		this.tail_3.setRotationPoint(0.0F, 0.0F, 7.0F);
		this.tail_3.addBox(-1.0F, -0.5F, -1.0F, 2, 1, 8, 0.0F);
		this.setRotateAngle(tail_3, -0.2617993877991494F, 0.0F, 0.0F);
		this.claw_front_right_3 = new ModelRenderer(this, 8, 97);
		this.claw_front_right_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_3.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_right_3, 0.0F, -1.5707963267948966F, 0.0F);
		this.claw_front_right_1_1 = new ModelRenderer(this, 8, 11);
		this.claw_front_right_1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_1_1.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.foot_front_right_1 = new ModelRenderer(this, 7, 13);
		this.foot_front_right_1.setRotationPoint(-5.5F, 0.0F, 0.0F);
		this.foot_front_right_1.addBox(0.0F, -0.1F, 0.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(foot_front_right_1, 0.04363323129985824F, -2.4172810140121466F, 0.3490658503988659F);
		this.claw_back_left_3 = new ModelRenderer(this, 50, 11);
		this.claw_back_left_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_back_left_3.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_back_left_3, 0.0F, -1.5707963267948966F, 0.0F);
		this.claw_back_left_1 = new ModelRenderer(this, 50, 11);
		this.claw_back_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_back_left_1.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.neck_2 = new ModelRenderer(this, 20, 56);
		this.neck_2.setRotationPoint(0.0F, 0.0F, -7.0F);
		this.neck_2.addBox(-2.0F, -1.5F, -7.5F, 4, 3, 8, 0.0F);
		this.setRotateAngle(neck_2, 0.6981317007977318F, 0.0F, 0.0F);
		this.tail_2 = new ModelRenderer(this, 21, 9);
		this.tail_2.setRotationPoint(0.0F, 0.0F, 7.0F);
		this.tail_2.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 8, 0.0F);
		this.setRotateAngle(tail_2, -0.2617993877991494F, 0.0F, 0.0F);
		this.leg_front_right_2_1 = new ModelRenderer(this, 2, 17);
		this.leg_front_right_2_1.setRotationPoint(-7.0F, 0.0F, 0.0F);
		this.leg_front_right_2_1.addBox(-6.5F, -0.7F, -1.1F, 7, 2, 2, 0.0F);
		this.setRotateAngle(leg_front_right_2_1, -0.2617993877991494F, 0.6108652381980153F, -0.4363323129985824F);
		this.claw_front_right_2_1 = new ModelRenderer(this, 8, 11);
		this.claw_front_right_2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_2_1.addBox(2.8F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_right_2_1, 0.0F, -0.7853981633974483F, 0.0F);
		this.tongue = new ModelRenderer(this, 24, 84);
		this.tongue.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tongue.addBox(-1.5F, 0.5F, -10.5F, 3, 0, 5, 0.0F);
		this.claw_front_left_2 = new ModelRenderer(this, 50, 97);
		this.claw_front_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_left_2.addBox(2.8F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_left_2, 0.0F, -0.7853981633974483F, 0.0F);
		this.head_2 = new ModelRenderer(this, 22, 76);
		this.head_2.setRotationPoint(-2.0F, 0.0F, 0.0F);
		this.head_2.addBox(0.0F, 0.9F, -5.4F, 4, 2, 6, 0.0F);
		this.setRotateAngle(head_2, -0.3490658503988659F, 0.0F, 0.0F);
		this.neck_1_flaps = new ModelRenderer(this, 14, 115);
		this.neck_1_flaps.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.neck_1_flaps.addBox(-7.5F, 0.0F, -7.0F, 15, 0, 5, 0.0F);
		this.tail_2_flaps = new ModelRenderer(this, 12, 103);
		this.tail_2_flaps.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tail_2_flaps.addBox(-7.5F, 0.0F, 0.0F, 15, 0, 7, 0.0F);
		this.tail_1_flaps = new ModelRenderer(this, 16, 110);
		this.tail_1_flaps.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tail_1_flaps.addBox(-7.5F, 0.0F, 4.0F, 15, 0, 3, 0.0F);
		this.leg_front_right_2 = new ModelRenderer(this, 2, 89);
		this.leg_front_right_2.setRotationPoint(-7.0F, 0.0F, 0.0F);
		this.leg_front_right_2.addBox(-6.5F, -0.7F, -1.1F, 7, 2, 2, 0.0F);
		this.setRotateAngle(leg_front_right_2, 0.2617993877991494F, -0.6108652381980153F, -0.4363323129985824F);
		this.claw_front_right_3_1 = new ModelRenderer(this, 8, 11);
		this.claw_front_right_3_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_right_3_1.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_right_3_1, 0.0F, -1.5707963267948966F, 0.0F);
		this.body = new ModelRenderer(this, 14, 30);
		this.body.setRotationPoint(0.0F, 21.0F, 0.0F);
		this.body.addBox(-3.0F, 0.0F, -6.0F, 6, 3, 12, 0.0F);
		this.leg_front_left_2 = new ModelRenderer(this, 44, 89);
		this.leg_front_left_2.setRotationPoint(7.0F, 0.0F, 0.0F);
		this.leg_front_left_2.addBox(-0.5F, -0.7F, -1.1F, 7, 2, 2, 0.0F);
		this.setRotateAngle(leg_front_left_2, 0.2617993877991494F, 0.6108652381980153F, 0.4363323129985824F);
		this.claw_front_left_3 = new ModelRenderer(this, 50, 97);
		this.claw_front_left_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_front_left_3.addBox(2.0F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_front_left_3, 0.0F, -1.5707963267948966F, 0.0F);
		this.claw_back_left_2 = new ModelRenderer(this, 50, 11);
		this.claw_back_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.claw_back_left_2.addBox(2.8F, -0.1F, 0.0F, 3, 2, 0, 0.0F);
		this.setRotateAngle(claw_back_left_2, 0.0F, -0.7853981633974483F, 0.0F);
		this.head_1 = new ModelRenderer(this, 20, 67);
		this.head_1.setRotationPoint(0.0F, -0.3F, -7.0F);
		this.head_1.addBox(-2.5F, -1.5F, -6.0F, 5, 2, 7, 0.0F);
		this.earflap_right = new ModelRenderer(this, 14, 69);
		this.earflap_right.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.earflap_right.addBox(-2.4F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(earflap_right, -0.17453292519943295F, 0.0F, -0.04363323129985824F);
		this.foot_front_right = new ModelRenderer(this, 7, 93);
		this.foot_front_right.setRotationPoint(-5.5F, 0.0F, 0.0F);
		this.foot_front_right.addBox(0.0F, -0.1F, 0.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(foot_front_right, -0.04363323129985824F, -2.4172810140121466F, 0.3490658503988659F);
		this.leg_front_left_2.addChild(this.foot_front_left);
		this.foot_front_right.addChild(this.claw_front_right_1);
		this.head_1.addChild(this.earflap_left);
		this.body.addChild(this.leg_back_right_1);
		this.foot_front_left.addChild(this.claw_front_left_1);
		this.tail_3.addChild(this.tail_3_flaps);
		this.leg_back_left_1.addChild(this.leg_back_left_2);
		this.leg_back_left_2.addChild(this.foot_back_left);
		this.neck_2.addChild(this.neck_2_flaps);
		this.body.addChild(this.leg_front_right_1);
		this.foot_front_right.addChild(this.claw_front_right_2);
		this.body.addChild(this.leg_front_left_1);
		this.body.addChild(this.tail_1);
		this.body.addChild(this.neck_1);
		this.body.addChild(this.leg_back_left_1);
		this.tail_2.addChild(this.tail_3);
		this.foot_front_right.addChild(this.claw_front_right_3);
		this.foot_front_right_1.addChild(this.claw_front_right_1_1);
		this.leg_front_right_2_1.addChild(this.foot_front_right_1);
		this.foot_back_left.addChild(this.claw_back_left_3);
		this.foot_back_left.addChild(this.claw_back_left_1);
		this.neck_1.addChild(this.neck_2);
		this.tail_1.addChild(this.tail_2);
		this.leg_back_right_1.addChild(this.leg_front_right_2_1);
		this.foot_front_right_1.addChild(this.claw_front_right_2_1);
		this.head_1.addChild(this.tongue);
		this.foot_front_left.addChild(this.claw_front_left_2);
		this.head_1.addChild(this.head_2);
		this.neck_1.addChild(this.neck_1_flaps);
		this.tail_2.addChild(this.tail_2_flaps);
		this.tail_1.addChild(this.tail_1_flaps);
		this.leg_front_right_1.addChild(this.leg_front_right_2);
		this.foot_front_right_1.addChild(this.claw_front_right_3_1);
		this.leg_front_left_1.addChild(this.leg_front_left_2);
		this.foot_front_left.addChild(this.claw_front_left_3);
		this.foot_back_left.addChild(this.claw_back_left_2);
		this.neck_2.addChild(this.head_1);
		this.head_1.addChild(this.earflap_right);
		this.leg_front_right_2.addChild(this.foot_front_right);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
		GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
		GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
		this.body.render(f5);
		GlStateManager.popMatrix();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}


	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor, entity);

		this.head_1.rotateAngleX = headPitch * 0.017453292F;
		this.head_1.rotateAngleY = headYaw * 0.017453292F;

		this.tongue.showModel = MathHelper.cos(ageInTicks / 15) > 0 && MathHelper.cos(ageInTicks) > 0;

		float swingCos = MathHelper.cos(limbSwing * 0.7662F);
		float swingSin = MathHelper.sin(limbSwing * 0.7662F);

		this.leg_front_right_1.rotateAngleY =  -0.35F + (swingCos -.5f) * .6F * limbSwingAmount;
		this.leg_front_right_2.rotateAngleY =  -0.35F + swingSin * .6F * limbSwingAmount;

		this.leg_back_left_1.rotateAngleY =  -0.35F + (swingCos -.5f) * .3F * limbSwingAmount;
		this.leg_back_left_2.rotateAngleY =  -0.35F + swingSin * .3F * limbSwingAmount;

		this.leg_front_left_1.rotateAngleY =  0.35F + (swingSin -.5f) * .6F * limbSwingAmount;
		this.leg_front_left_2.rotateAngleY =  0.35F + swingCos * .6F * limbSwingAmount;

		this.leg_back_right_1.rotateAngleY =  0.35F + (swingSin -.5f) * .3F * limbSwingAmount;
		this.leg_front_right_2_1.rotateAngleY =  0.35F + swingCos * .3F * limbSwingAmount;

		float tailVal = (limbSwing * .3f + ageInTicks * .06f);
		float tailSway = MathHelper.cos(tailVal) / 3f;
		float tailSwaySin = MathHelper.sin(tailVal) / 3f;

		this.tail_1.rotateAngleY = tailSway;
		this.tail_2.rotateAngleY = tailSwaySin;

		float tailBase = -0.08726646259971647F;
		float tailSwing = MathHelper.cos(limbSwing * 0.6662F) * .01F * limbSwingAmount;
		float tailSwingSin = MathHelper.sin(limbSwing * 0.6662F) * .01F * limbSwingAmount;

		this.tail_1.rotateAngleX = tailBase + tailSwing;
		this.tail_2.rotateAngleX = tailBase + tailSwing;
		this.tail_3.rotateAngleX = tailBase + tailSwing;

		this.neck_1.rotateAngleX = tailSway / 2f + tailSwing / 5f - 0.52f;
		this.neck_2.rotateAngleX = .7f + tailSwaySin / 2f + tailSwingSin / 5f;
		this.head_1.rotateAngleX -= tailSwing / 2f + tailSway / 5f + .1f;
	}
}
