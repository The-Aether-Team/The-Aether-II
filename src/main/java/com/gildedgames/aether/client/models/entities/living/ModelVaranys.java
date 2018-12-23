package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVaranys extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer body_front;
	public ModelRenderer body_back;
	public ModelRenderer leg_front_left_joint;
	public ModelRenderer leg_front_right_joint;
	public ModelRenderer head_top;
	public ModelRenderer head_bottom;
	public ModelRenderer teeth_top;
	public ModelRenderer cheek_left;
	public ModelRenderer cheek_right;
	public ModelRenderer forehead;
	public ModelRenderer teeth_bottom;
	public ModelRenderer crest1;
	public ModelRenderer crest3;
	public ModelRenderer crest2;
	public ModelRenderer tail1;
	public ModelRenderer leg_back_left_top;
	public ModelRenderer leg_back_right_top;
	public ModelRenderer tail2;
	public ModelRenderer tail3;
	public ModelRenderer tail4;
	public ModelRenderer leg_back_left_bottom;
	public ModelRenderer foot_back_left_heel;
	public ModelRenderer foot_back_left;
	public ModelRenderer toe7;
	public ModelRenderer toe8;
	public ModelRenderer leg_back_left_bottom_1;
	public ModelRenderer foot_back_left_heel_1;
	public ModelRenderer foot_back_left_1;
	public ModelRenderer toe9;
	public ModelRenderer toe10;
	public ModelRenderer leg_front_left_top;
	public ModelRenderer leg_front_left_bottom;
	public ModelRenderer foot_front_left;
	public ModelRenderer toe1;
	public ModelRenderer toe2;
	public ModelRenderer toe3;
	public ModelRenderer leg_front_right_top;
	public ModelRenderer leg_front_right_bottom;
	public ModelRenderer foot_front_right;
	public ModelRenderer toe4;
	public ModelRenderer toe5;
	public ModelRenderer toe6;

	public ModelVaranys() {
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.toe6 = new ModelRenderer(this, 44, 41);
		this.toe6.mirror = true;
		this.toe6.setRotationPoint(-1.0F, 0.8F, -5.0F);
		this.toe6.addBox(-0.5F, 0.0F, -1.6F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe6, 0.2617993877991494F, 0.17453292519943295F, 0.0F);
		this.tail4 = new ModelRenderer(this, 0, 90);
		this.tail4.setRotationPoint(0.0F, 0.0F, 8.0F);
		this.tail4.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 10, 0.0F);
		this.setRotateAngle(tail4, 0.17453292519943295F, 0.0F, 0.0F);
		this.body_back = new ModelRenderer(this, 0, 32);
		this.body_back.setRotationPoint(0.0F, -3.5F, 5.0F);
		this.body_back.addBox(-5.5F, 0.0F, 0.0F, 11, 7, 9, 0.0F);
		this.setRotateAngle(body_back, -0.08726646259971647F, 0.0F, 0.0F);
		this.leg_back_left_bottom_1 = new ModelRenderer(this, 44, 57);
		this.leg_back_left_bottom_1.mirror = true;
		this.leg_back_left_bottom_1.setRotationPoint(-2.99F, 6.5F, -1.5F);
		this.leg_back_left_bottom_1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 3, 0.0F);
		this.setRotateAngle(leg_back_left_bottom_1, 0.4363323129985824F, 0.0F, 0.0F);
		this.body_front = new ModelRenderer(this, 0, 17);
		this.body_front.setRotationPoint(0.0F, -3.5F, -5.0F);
		this.body_front.addBox(-5.5F, 0.0F, -8.0F, 11, 7, 8, 0.0F);
		this.setRotateAngle(body_front, 0.2617993877991494F, 0.0F, 0.0F);
		this.foot_front_left = new ModelRenderer(this, 44, 26);
		this.foot_front_left.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.foot_front_left.addBox(-2.0F, 0.0F, -5.0F, 4, 2, 5, 0.0F);
		this.foot_back_left_heel_1 = new ModelRenderer(this, 44, 65);
		this.foot_back_left_heel_1.mirror = true;
		this.foot_back_left_heel_1.setRotationPoint(0.5F, 5.0F, 3.0F);
		this.foot_back_left_heel_1.addBox(-1.5F, 0.0F, -2.0F, 3, 2, 2, 0.0F);
		this.setRotateAngle(foot_back_left_heel_1, -0.4363323129985824F, 0.0F, 0.0F);
		this.tail2 = new ModelRenderer(this, 0, 63);
		this.tail2.setRotationPoint(0.0F, -0.5F, 8.0F);
		this.tail2.addBox(-3.5F, -2.5F, 0.0F, 7, 5, 9, 0.0F);
		this.setRotateAngle(tail2, -0.08726646259971647F, 0.0F, 0.0F);
		this.head_top = new ModelRenderer(this, 68, 0);
		this.head_top.setRotationPoint(0.0F, 2.0F, -8.0F);
		this.head_top.addBox(-3.5F, -3.0F, -11.0F, 7, 4, 12, 0.0F);
		this.setRotateAngle(head_top, -0.17453292519943295F, 0.0F, 0.0F);
		this.forehead = new ModelRenderer(this, 68, 16);
		this.forehead.setRotationPoint(0.0F, -3.0F, -7.0F);
		this.forehead.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 7, 0.0F);
		this.setRotateAngle(forehead, 0.2617993877991494F, 0.0F, 0.0F);
		this.toe4 = new ModelRenderer(this, 44, 33);
		this.toe4.mirror = true;
		this.toe4.setRotationPoint(1.0F, 0.5F, -5.0F);
		this.toe4.addBox(-0.5F, 0.0F, -2.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe4, 0.2617993877991494F, -0.17453292519943295F, 0.0F);
		this.head_bottom = new ModelRenderer(this, 68, 33);
		this.head_bottom.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.head_bottom.addBox(-3.0F, 0.0F, -11.0F, 6, 2, 12, 0.0F);
		this.setRotateAngle(head_bottom, 0.3490658503988659F, 0.0F, 0.0F);
		this.toe7 = new ModelRenderer(this, 44, 72);
		this.toe7.setRotationPoint(-0.5F, 0.0F, -2.0F);
		this.toe7.addBox(-1.0F, 0.0F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(toe7, 0.17453292519943295F, 0.0F, 0.0F);
		this.toe8 = new ModelRenderer(this, 44, 75);
		this.toe8.setRotationPoint(-0.5F, 0.0F, -2.0F);
		this.toe8.addBox(0.0F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(toe8, 0.2617993877991494F, 0.0F, 0.0F);
		this.leg_front_left_top = new ModelRenderer(this, 44, 0);
		this.leg_front_left_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_front_left_top.addBox(-3.5F, -1.5F, -3.0F, 7, 9, 5, 0.0F);
		this.setRotateAngle(leg_front_left_top, 0.0F, -1.5707963267948966F, -0.8726646259971648F);
		this.crest3 = new ModelRenderer(this, 118, 20);
		this.crest3.setRotationPoint(-2.0F, 0.0F, 5.5F);
		this.crest3.addBox(-2.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(crest3, 0.0F, 0.0F, -0.6981317007977318F);
		this.leg_back_left_bottom = new ModelRenderer(this, 44, 57);
		this.leg_back_left_bottom.setRotationPoint(2.99F, 6.5F, -1.5F);
		this.leg_back_left_bottom.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 3, 0.0F);
		this.setRotateAngle(leg_back_left_bottom, 0.4363323129985824F, 0.0F, 0.0F);
		this.foot_front_right = new ModelRenderer(this, 44, 26);
		this.foot_front_right.mirror = true;
		this.foot_front_right.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.foot_front_right.addBox(-2.0F, 0.0F, -5.0F, 4, 2, 5, 0.0F);
		this.leg_front_left_bottom = new ModelRenderer(this, 44, 14);
		this.leg_front_left_bottom.setRotationPoint(5.0F, 3.7F, 2.0F);
		this.leg_front_left_bottom.addBox(-2.5F, -1.0F, -3.5F, 5, 8, 4, 0.0F);
		this.foot_back_left_heel = new ModelRenderer(this, 44, 65);
		this.foot_back_left_heel.setRotationPoint(-0.5F, 5.0F, 3.0F);
		this.foot_back_left_heel.addBox(-1.5F, 0.0F, -2.0F, 3, 2, 2, 0.0F);
		this.setRotateAngle(foot_back_left_heel, -0.4363323129985824F, 0.0F, 0.0F);
		this.toe5 = new ModelRenderer(this, 44, 37);
		this.toe5.mirror = true;
		this.toe5.setRotationPoint(0.0F, 0.6F, -5.0F);
		this.toe5.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe5, 0.2617993877991494F, 0.0F, 0.0F);
		this.crest2 = new ModelRenderer(this, 106, 21);
		this.crest2.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.crest2.addBox(-4.0F, 0.0F, -1.0F, 4, 2, 2, 0.0F);
		this.tail1 = new ModelRenderer(this, 0, 48);
		this.tail1.setRotationPoint(0.0F, 4.0F, 8.0F);
		this.tail1.addBox(-4.5F, -3.5F, 0.0F, 9, 6, 9, 0.0F);
		this.setRotateAngle(tail1, -0.08726646259971647F, 0.0F, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 13.3F, 0.0F);
		this.body.addBox(-6.0F, -3.5F, -5.0F, 12, 7, 10, 0.0F);
		this.cheek_left = new ModelRenderer(this, 68, 25);
		this.cheek_left.setRotationPoint(3.5F, -1.0F, -6.0F);
		this.cheek_left.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 6, 0.0F);
		this.setRotateAngle(cheek_left, 0.0F, 0.3490658503988659F, 0.0F);
		this.toe9 = new ModelRenderer(this, 44, 72);
		this.toe9.mirror = true;
		this.toe9.setRotationPoint(1.5F, 0.0F, -2.0F);
		this.toe9.addBox(-1.0F, 0.0F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(toe9, 0.17453292519943295F, 0.0F, 0.0F);
		this.toe10 = new ModelRenderer(this, 44, 75);
		this.toe10.mirror = true;
		this.toe10.setRotationPoint(-0.5F, 0.0F, -2.0F);
		this.toe10.addBox(0.0F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(toe10, 0.2617993877991494F, 0.0F, 0.0F);
		this.leg_front_right_joint = new ModelRenderer(this, 0, 0);
		this.leg_front_right_joint.setRotationPoint(-4.5F, -2.0F, -4.0F);
		this.leg_front_right_joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(leg_front_right_joint, 0.0F, 0.17453292519943295F, 0.0F);
		this.teeth_top = new ModelRenderer(this, 68, 47);
		this.teeth_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.teeth_top.addBox(-2.5F, 0.5F, -10.0F, 5, 1, 11, 0.0F);
		this.cheek_right = new ModelRenderer(this, 68, 25);
		this.cheek_right.mirror = true;
		this.cheek_right.setRotationPoint(-3.5F, -1.0F, -6.0F);
		this.cheek_right.addBox(0.0F, 0.0F, 0.0F, 3, 2, 6, 0.0F);
		this.setRotateAngle(cheek_right, 0.0F, -0.3490658503988659F, 0.0F);
		this.foot_back_left = new ModelRenderer(this, 44, 69);
		this.foot_back_left.setRotationPoint(0.5F, 0.6F, -2.0F);
		this.foot_back_left.addBox(-1.5F, 0.0F, -2.0F, 3, 1, 2, 0.0F);
		this.setRotateAngle(foot_back_left, 0.08726646259971647F, 0.0F, 0.0F);
		this.leg_front_left_joint = new ModelRenderer(this, 0, 0);
		this.leg_front_left_joint.setRotationPoint(4.5F, -2.0F, -4.0F);
		this.leg_front_left_joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(leg_front_left_joint, 0.0F, -0.17453292519943295F, 0.0F);
		this.foot_back_left_1 = new ModelRenderer(this, 44, 69);
		this.foot_back_left_1.mirror = true;
		this.foot_back_left_1.setRotationPoint(-0.5F, 0.6F, -2.0F);
		this.foot_back_left_1.addBox(-1.5F, 0.0F, -2.0F, 3, 1, 2, 0.0F);
		this.setRotateAngle(foot_back_left_1, 0.08726646259971647F, 0.0F, 0.0F);
		this.leg_front_right_top = new ModelRenderer(this, 44, 0);
		this.leg_front_right_top.mirror = true;
		this.leg_front_right_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_front_right_top.addBox(-3.5F, -1.5F, -3.0F, 7, 9, 5, 0.0F);
		this.setRotateAngle(leg_front_right_top, 0.0F, 1.5707963267948966F, 0.8726646259971648F);
		this.leg_front_right_bottom = new ModelRenderer(this, 44, 14);
		this.leg_front_right_bottom.mirror = true;
		this.leg_front_right_bottom.setRotationPoint(-5.0F, 3.7F, 2.0F);
		this.leg_front_right_bottom.addBox(-2.5F, -1.0F, -3.5F, 5, 8, 4, 0.0F);
		this.toe2 = new ModelRenderer(this, 44, 37);
		this.toe2.setRotationPoint(0.0F, 0.6F, -5.0F);
		this.toe2.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe2, 0.2617993877991494F, 0.0F, 0.0F);
		this.crest1 = new ModelRenderer(this, 98, 18);
		this.crest1.setRotationPoint(2.0F, 0.0F, 5.5F);
		this.crest1.addBox(0.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(crest1, 0.0F, 0.0F, 0.6981317007977318F);
		this.leg_back_right_top = new ModelRenderer(this, 44, 45);
		this.leg_back_right_top.mirror = true;
		this.leg_back_right_top.setRotationPoint(-4.9F, 1.9F, 7.4F);
		this.leg_back_right_top.addBox(-5.0F, -1.5F, -2.0F, 5, 8, 4, 0.0F);
		this.setRotateAngle(leg_back_right_top, 0.0F, 0.3490658503988659F, 0.0F);
		this.tail3 = new ModelRenderer(this, 0, 77);
		this.tail3.setRotationPoint(0.0F, 0.0F, 8.0F);
		this.tail3.addBox(-2.5F, -2.0F, 0.0F, 5, 4, 9, 0.0F);
		this.setRotateAngle(tail3, -0.08726646259971647F, 0.0F, 0.0F);
		this.leg_back_left_top = new ModelRenderer(this, 44, 45);
		this.leg_back_left_top.setRotationPoint(4.9F, 1.9F, 7.4F);
		this.leg_back_left_top.addBox(0.0F, -1.5F, -2.0F, 5, 8, 4, 0.0F);
		this.setRotateAngle(leg_back_left_top, 0.0F, -0.3490658503988659F, 0.0F);
		this.toe1 = new ModelRenderer(this, 44, 33);
		this.toe1.setRotationPoint(-1.0F, 0.5F, -5.0F);
		this.toe1.addBox(-0.5F, 0.0F, -2.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe1, 0.2617993877991494F, 0.17453292519943295F, 0.0F);
		this.toe3 = new ModelRenderer(this, 44, 41);
		this.toe3.setRotationPoint(1.0F, 0.8F, -5.0F);
		this.toe3.addBox(-0.5F, 0.0F, -1.6F, 1, 1, 3, 0.0F);
		this.setRotateAngle(toe3, 0.2617993877991494F, -0.17453292519943295F, 0.0F);
		this.teeth_bottom = new ModelRenderer(this, 68, 59);
		this.teeth_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.teeth_bottom.addBox(-2.5F, -0.5F, -10.0F, 5, 1, 11, 0.0F);
		this.foot_front_right.addChild(this.toe6);
		this.tail3.addChild(this.tail4);
		this.body.addChild(this.body_back);
		this.leg_back_right_top.addChild(this.leg_back_left_bottom_1);
		this.body.addChild(this.body_front);
		this.leg_front_left_bottom.addChild(this.foot_front_left);
		this.leg_back_left_bottom_1.addChild(this.foot_back_left_heel_1);
		this.tail1.addChild(this.tail2);
		this.body_front.addChild(this.head_top);
		this.head_top.addChild(this.forehead);
		this.foot_front_right.addChild(this.toe4);
		this.head_top.addChild(this.head_bottom);
		this.foot_back_left.addChild(this.toe7);
		this.foot_back_left.addChild(this.toe8);
		this.leg_front_left_joint.addChild(this.leg_front_left_top);
		this.forehead.addChild(this.crest3);
		this.leg_back_left_top.addChild(this.leg_back_left_bottom);
		this.leg_front_right_bottom.addChild(this.foot_front_right);
		this.leg_front_left_joint.addChild(this.leg_front_left_bottom);
		this.leg_back_left_bottom.addChild(this.foot_back_left_heel);
		this.foot_front_right.addChild(this.toe5);
		this.crest1.addChild(this.crest2);
		this.body_back.addChild(this.tail1);
		this.head_top.addChild(this.cheek_left);
		this.foot_back_left_1.addChild(this.toe9);
		this.foot_back_left_1.addChild(this.toe10);
		this.body.addChild(this.leg_front_right_joint);
		this.head_top.addChild(this.teeth_top);
		this.head_top.addChild(this.cheek_right);
		this.foot_back_left_heel.addChild(this.foot_back_left);
		this.body.addChild(this.leg_front_left_joint);
		this.foot_back_left_heel_1.addChild(this.foot_back_left_1);
		this.leg_front_right_joint.addChild(this.leg_front_right_top);
		this.leg_front_right_joint.addChild(this.leg_front_right_bottom);
		this.foot_front_left.addChild(this.toe2);
		this.forehead.addChild(this.crest1);
		this.body_back.addChild(this.leg_back_right_top);
		this.tail2.addChild(this.tail3);
		this.body_back.addChild(this.leg_back_left_top);
		this.foot_front_left.addChild(this.toe1);
		this.foot_front_left.addChild(this.toe3);
		this.head_bottom.addChild(this.teeth_bottom);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
			Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.head_top.rotateAngleX = headPitch * 0.017453292F;

		float rotValue =  netHeadYaw * 0.027453292F + (float) Math.PI / 3f;

		if (rotValue > 1)
		{
			rotValue -= 2f;
		}

		this.head_top.rotateAngleY = rotValue;

		this.leg_front_right_joint.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * .6F * limbSwingAmount;
		this.leg_front_left_joint.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * .6F * limbSwingAmount;
		this.leg_back_right_top.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * .6F * limbSwingAmount;
		this.leg_back_left_top.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * .6F * limbSwingAmount;

		float jawLower = MathHelper.cos(ageInTicks * .1f) / 5f;

		this.head_bottom.rotateAngleX = 0.3490658503988659F - jawLower;

		float bodyVal = (limbSwing) * .5f;
		float bodySway = MathHelper.cos(bodyVal) / 2.5f;
		float bodySwaySin = MathHelper.sin(bodyVal) / 5f;

		this.body_front.rotateAngleY = bodySwaySin;
		this.body_back.rotateAngleY = bodySway;

		float tailVal = (limbSwing * .3f + ageInTicks * .06f);
		float tailSway = MathHelper.cos(tailVal) / 3f;
		float tailSwaySin = MathHelper.sin(tailVal) / 3f;

		this.tail1.rotateAngleY = tailSway;
		this.tail2.rotateAngleY = tailSwaySin;
		this.tail3.rotateAngleY = tailSway;
		this.tail4.rotateAngleY = tailSwaySin;

		float tailBase = -0.08726646259971647F;
		float tail4Base = 0.17453292519943295F;


		float tailSwing = MathHelper.cos(limbSwing * 0.6662F) * .05F * limbSwingAmount;

		this.tail1.rotateAngleX = tailBase + tailSwing;
		this.tail2.rotateAngleX = tailBase + tailSwing;
		this.tail3.rotateAngleX = tailBase + tailSwing;
		this.tail4.rotateAngleX = tail4Base + tailSwing;
	}
}
